(function () {
    'use strict';

    angular
        .module('voilaVoix2App')
        .controller('resultController', resultController);

    resultController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$resource', 'RestRequest', 'SMService', '$location', 'isDemo', '$timeout'];

    function resultController($scope, Principal, LoginService, $state, $resource, RestRequest, SMService, $location, isDemo, $timeout) {
        var vm = this;
        vm.id_analyse = null;
        vm.requestSent = false;
        vm.responseSent = false;
        vm.result = null;
        vm.speakers = [];
        vm.words = [];
        vm.show = false;
        vm.textBtn = "Afficher";
        vm.umnamedTab = [];
        vm.wordToSearch = "";
        vm.wordToSearchResult = [];
        vm.idToReset = [];
        vm.readyToH = false;
        vm.readyToS = false;
        vm.analyse = false;
        vm.partOfTraitement = 0;
        vm.readyToHButton = true;
        vm.idElmt = document.getElementById("m" + [vm.partOfTraitement]);
        var items = [{
            "lane": 0, "id": "Gastronomie", "start": 3, "end": 10
        }, {
            "lane": 0, "id": "Genin", "start": 11, "end": 29
        }, {
            "lane": 0, "id": "Paris", "start": 30, "end": 45
        }, {
            "lane": 0, "id": "Majestic12", "start": 45, "end": 60
        }];


        vm.tabOk = function (a) {
            if (a == vm.nbrWords) {
                vm.readyToH = true;
            }

        }
        vm.actualiziDE = function (i) {
            $scope.$watch(vm.idElmt);
            vm.idElmt = document.getElementById("m" + [i]);
            vm.idElmt.style = "display:yes";
            vm.partOfTraitement = parseInt(i * 100 / vm.nbrWords + 1);
            if (vm.partOfTraitement > 90) {
                vm.idElmt = document.getElementById("timeline");
                vm.idElmt.style = "display:yes";

            }


        }

        vm.f3k = function () {
            var idElmt = document.getElementById("timeline");
            idElmt.style = "display:none";

            console.log(vm.nbrWords);
            for (var i = 0; i < vm.nbrWords; i++) {
                (function (i) {
                    $timeout(function () {
                        Math.random();
                        vm.actualiziDE(i);
                    }, (parseInt(i / 12)) * 1000);
                })(i);
            }

        }


        vm.mrHide = function () {
            for (var i = 0; i <= vm.nbrWords; i++) {
                var idElmt = document.getElementById("m" + [i]);
                idElmt.style = "display:none";
            }
            vm.readyToS = true;
        }


        vm.speakersFunction = function (speaker) {
            var json = vm.result;
            for (var i = 0; i <= vm.nbrSpeakers; i++) {
                var idElmt = document.getElementById(json['speakers'][i]['name']);
                idElmt.className = "btn btn-primary";
            }
            var idElmt = document.getElementById(speaker);
            idElmt.className = " btn btn-success";

            for (var i = 0; i < vm.nbrWords; i++) {
                var idElmt = document.getElementById('m' + i);
                idElmt.style.color = '#337ab7';
                idElmt.style.fontWeight = 'normal';
            }

            for (var i = 0; i < vm.nbrWords; i++) {
                if (json['words'][i]['speaker'] == speaker) {
                    var idElmt = document.getElementById('m' + i);
                    idElmt.style.color = 'green';
                    idElmt.style.fontWeight = 'normal';

                }
            }
        }

        vm.wordToSearchFunction = function () {
            var json = vm.result;
            vm.wordToSearchResult = [];

            if (!vm.idToReset.isEmpty) {
                for (var id in vm.idToReset) {
                    var idElmt = document.getElementById(vm.idToReset[id]);
                    idElmt.style.color = 'blue';
                    idElmt.style.fontWeight = 'normal';
                }
                vm.idToReset = [];
            }

            for (var i = 0; i < vm.nbrWords; i++) {
                if ((json['words'][i]['name']).toUpperCase() == vm.wordToSearch.toUpperCase()) {
                    vm.wordToSearchResult.push(json['words'][i]);
                    var idElmt = document.getElementById('m' + json['words'][i]['id']);
                    idElmt.style.color = 'red';
                    idElmt.style.fontWeight = 'bold';
                    vm.idToReset.push('m' + json['words'][i]['id'])
                }
            }
        }

        vm.wordsToggleOff = function () {
            vm.show = false;
            vm.textBtn = "Afficher";
        }
        vm.wordsToggleOn = function () {
            vm.show = true;
            vm.textBtn = "Masquer";
        }

        vm.showWords = function () {
            if (vm.show == true) {
                vm.wordsToggleOff();
            } else {
                vm.wordsToggleOn();
            }
        };

        vm.extractResult = function (json) {
            vm.nbrSpeakers = 0;
            for (var nbr in json["speakers"]) {
                vm.nbrSpeakers = nbr;
            }

            vm.nbrWords = 0;
            for (var nbr in json["words"]) {
                vm.nbrWords = nbr;
            }

            for (var i = 0; i <= vm.nbrSpeakers; i++) {
                vm.speakers.push(json["speakers"][i]);
            }

            for (var i = 0; i <= vm.nbrWords; i++) {
                vm.words.push(json["words"][i]);
            }

            for (var i = 0; i < vm.words.length; i++) {
                for (var y = 0; y < vm.speakers.length; y++) {

                    if (parseFloat(json["words"][i]["time"]) >= parseFloat(json["speakers"][y]["time"]) && parseFloat(json["words"][i]["time"]) <= parseFloat(json["speakers"][y]["time"]) + parseFloat(json["speakers"][y]["duration"])) {

                        json["words"][i]["speaker"] = json["speakers"][y]["name"];
                        json["words"][i]["id"] = i;

                    }

                }

            }
            vm.analyse = true;
        };

        vm.isString = function (value) {
            return angular.isString(value);
        };

        vm.appelSM = function () {
            if (!vm.requestSent) {
                SMService.post().then(function (result) {
                    var result = result.data;
                    if (result.error) {
                        vm.result = "Aucun resultat";
                        vm.requestSent = true;
                    } else {
                        vm.result = result;
                        vm.id_analyse = vm.result.id;
                        vm.requestSent = true;

                    }


                }, function () {
                    vm.result = "Aucun resultat";
                    vm.requestSent = true;
                });
            }
            else if (!vm.responseSent) {
                SMService.get({id: vm.id_analyse}).$promise.then(function (result) {
                    vm.result = result;
                    if (angular.isDefined(vm.result.words)) {
                        vm.extractResult(vm.result);
                        vm.responseSent = true;
                    }
                }).catch(function () {
                    vm.result = "Aucun resultat";
                    vm.responseSent = true;
                });
            }


        };

        // vm.result = json;
        // vm.requestSent = true;
        // vm.responseSent = true;


        vm.target = function (target, btnBool) {
            window.location = '#' + $location.path() + '#' + target;
            if (btnBool == true) {
                vm.wordsToggleOn();
            }

        }


        if (!isDemo) {
            vm.appelSM();
            // vm.extractResult(json)

        }
        else {
            vm.result =
                {
                    "job": {
                        "lang": "fr",
                        "user_id": 12066,
                        "name": "camus1.mp3",
                        "duration": 60,
                        "created_at": "Thu Mar  2 20:29:50 2017",
                        "id": 2071638
                    },
                    "speakers": [
                        {
                            "duration": "4.350000",
                            "confidence": null,
                            "name": "M1",
                            "time": "5.480000"
                        },
                        {
                            "duration": "49.950000",
                            "confidence": null,
                            "name": "M2",
                            "time": "9.830000"
                        }
                    ],
                    "words": [
                        {
                            "duration": "0.480000",
                            "confidence": "0.800",
                            "name": "Madame",
                            "time": "5.480000"
                        },
                        {
                            "duration": "0.510000",
                            "confidence": "1.000",
                            "name": "Altesse",
                            "time": "6.020000"
                        },
                        {
                            "duration": "0.510000",
                            "confidence": "1.000",
                            "name": "Royale",
                            "time": "6.530000"
                        },
                        {
                            "duration": "0.510000",
                            "confidence": "1.000",
                            "name": "mesdames",
                            "time": "7.130000"
                        },
                        {
                            "duration": "0.540000",
                            "confidence": "1.000",
                            "name": "messieurs",
                            "time": "7.730000"
                        },
                        {
                            "duration": "1.560000",
                            "confidence": "1.000",
                            "name": ".",
                            "time": "8.270000"
                        },
                        {
                            "duration": "1.560000",
                            "confidence": "1.000",
                            "name": "En",
                            "time": "9.830000"
                        },
                        {
                            "duration": "0.660000",
                            "confidence": "1.000",
                            "name": "recevant",
                            "time": "9.950000"
                        },
                        {
                            "duration": "0.150000",
                            "confidence": "1.000",
                            "name": "la",
                            "time": "10.610000"
                        },
                        {
                            "duration": "0.900000",
                            "confidence": "1.000",
                            "name": "distinction",
                            "time": "10.760000"
                        },
                        {
                            "duration": "0.240000",
                            "confidence": "1.000",
                            "name": "dans",
                            "time": "11.660000"
                        },
                        {
                            "duration": "0.410000",
                            "confidence": "1.000",
                            "name": "votre",
                            "time": "11.900000"
                        },
                        {
                            "duration": "0.450000",
                            "confidence": "0.610",
                            "name": "Libre",
                            "time": "12.320000"
                        },
                        {
                            "duration": "0.540000",
                            "confidence": "0.690",
                            "name": "Académie",
                            "time": "12.770000"
                        },
                        {
                            "duration": "0.090000",
                            "confidence": "0.990",
                            "name": "a",
                            "time": "13.310000"
                        },
                        {
                            "duration": "0.240000",
                            "confidence": "1.000",
                            "name": "bien",
                            "time": "13.400000"
                        },
                        {
                            "duration": "0.270000",
                            "confidence": "1.000",
                            "name": "voulu",
                            "time": "13.640000"
                        },
                        {
                            "duration": "0.420000",
                            "confidence": "1.000",
                            "name": "honorer",
                            "time": "13.970000"
                        },
                        {
                            "duration": "1.260000",
                            "confidence": "1.000",
                            "name": ".",
                            "time": "14.390000"
                        },
                        {
                            "duration": "1.260000",
                            "confidence": "1.000",
                            "name": "Ma",
                            "time": "15.650000"
                        },
                        {
                            "duration": "0.630000",
                            "confidence": "1.000",
                            "name": "gratitude",
                            "time": "15.770000"
                        },
                        {
                            "duration": "0.180000",
                            "confidence": "0.990",
                            "name": "était",
                            "time": "16.400000"
                        },
                        {
                            "duration": "0.390000",
                            "confidence": "0.960",
                            "name": "d'autant",
                            "time": "16.580000"
                        },
                        {
                            "duration": "0.210000",
                            "confidence": "1.000",
                            "name": "plus",
                            "time": "16.970000"
                        },
                        {
                            "duration": "0.780000",
                            "confidence": "1.000",
                            "name": "profonde",
                            "time": "17.180000"
                        },
                        {
                            "duration": "0.090000",
                            "confidence": "1.000",
                            "name": "que",
                            "time": "19.010000"
                        },
                        {
                            "duration": "0.180000",
                            "confidence": "0.990",
                            "name": "j'ai",
                            "time": "19.100000"
                        },
                        {
                            "duration": "0.540000",
                            "confidence": "0.990",
                            "name": "mesuré",
                            "time": "19.280000"
                        },
                        {
                            "duration": "0.060000",
                            "confidence": "1.000",
                            "name": "à",
                            "time": "19.820000"
                        },
                        {
                            "duration": "0.240000",
                            "confidence": "1.000",
                            "name": "quel",
                            "time": "19.880000"
                        },
                        {
                            "duration": "0.300000",
                            "confidence": "1.000",
                            "name": "point",
                            "time": "20.120000"
                        },
                        {
                            "duration": "0.270000",
                            "confidence": "1.000",
                            "name": "cette",
                            "time": "20.420000"
                        },
                        {
                            "duration": "0.720000",
                            "confidence": "1.000",
                            "name": "récompense",
                            "time": "20.690000"
                        },
                        {
                            "duration": "0.570000",
                            "confidence": "1.000",
                            "name": "dépassait",
                            "time": "21.440000"
                        },
                        {
                            "duration": "0.150000",
                            "confidence": "1.000",
                            "name": "mes",
                            "time": "22.010000"
                        },
                        {
                            "duration": "0.480000",
                            "confidence": "1.000",
                            "name": "mérites",
                            "time": "22.160000"
                        },
                        {
                            "duration": "0.690000",
                            "confidence": "1.000",
                            "name": "personnels",
                            "time": "22.670000"
                        },
                        {
                            "duration": "1.290000",
                            "confidence": "1.000",
                            "name": ".",
                            "time": "23.360000"
                        },
                        {
                            "duration": "1.290000",
                            "confidence": "1.000",
                            "name": "Tout",
                            "time": "24.650000"
                        },
                        {
                            "duration": "0.380000",
                            "confidence": "1.000",
                            "name": "homme",
                            "time": "24.920000"
                        },
                        {
                            "duration": "0.220000",
                            "confidence": "0.920",
                            "name": "et",
                            "time": "25.370000"
                        },
                        {
                            "duration": "0.120000",
                            "confidence": "0.990",
                            "name": "à",
                            "time": "25.640000"
                        },
                        {
                            "duration": "0.180000",
                            "confidence": "1.000",
                            "name": "plus",
                            "time": "25.760000"
                        },
                        {
                            "duration": "0.390000",
                            "confidence": "1.000",
                            "name": "forte",
                            "time": "25.940000"
                        },
                        {
                            "duration": "0.440000",
                            "confidence": "1.000",
                            "name": "raison",
                            "time": "26.330000"
                        },
                        {
                            "duration": "0.240000",
                            "confidence": "1.000",
                            "name": "tout",
                            "time": "26.780000"
                        },
                        {
                            "duration": "0.630000",
                            "confidence": "1.000",
                            "name": "artiste",
                            "time": "27.020000"
                        },
                        {
                            "duration": "0.690000",
                            "confidence": "1.000",
                            "name": ".",
                            "time": "27.650000"
                        },
                        {
                            "duration": "0.690000",
                            "confidence": "1.000",
                            "name": "Désire",
                            "time": "28.340000"
                        },
                        {
                            "duration": "0.240000",
                            "confidence": "1.000",
                            "name": "être",
                            "time": "28.970000"
                        },
                        {
                            "duration": "0.540000",
                            "confidence": "0.870",
                            "name": "reconnu",
                            "time": "29.210000"
                        },
                        {
                            "duration": "0.500000",
                            "confidence": "0.890",
                            "name": ".",
                            "time": "29.780000"
                        },
                        {
                            "duration": "0.150000",
                            "confidence": "1.000",
                            "name": "Je",
                            "time": "30.980000"
                        },
                        {
                            "duration": "0.120000",
                            "confidence": "1.000",
                            "name": "le",
                            "time": "31.130000"
                        },
                        {
                            "duration": "0.480000",
                            "confidence": "1.000",
                            "name": "désire",
                            "time": "31.250000"
                        },
                        {
                            "duration": "0.280000",
                            "confidence": "0.730",
                            "name": "aussi",
                            "time": "31.750000"
                        },
                        {
                            "duration": "1.380000",
                            "confidence": "1.000",
                            "name": ".",
                            "time": "32.030000"
                        },
                        {
                            "duration": "1.380000",
                            "confidence": "1.000",
                            "name": "Mais",
                            "time": "33.410000"
                        },
                        {
                            "duration": "0.120000",
                            "confidence": "1.000",
                            "name": "il",
                            "time": "33.560000"
                        },
                        {
                            "duration": "0.120000",
                            "confidence": "1.000",
                            "name": "ne",
                            "time": "33.680000"
                        },
                        {
                            "duration": "0.120000",
                            "confidence": "0.940",
                            "name": "m'a",
                            "time": "33.800000"
                        },
                        {
                            "duration": "0.240000",
                            "confidence": "1.000",
                            "name": "pas",
                            "time": "33.920000"
                        },
                        {
                            "duration": "0.240000",
                            "confidence": "1.000",
                            "name": "été",
                            "time": "34.160000"
                        },
                        {
                            "duration": "0.750000",
                            "confidence": "1.000",
                            "name": "possible",
                            "time": "34.400000"
                        },
                        {
                            "duration": "0.720000",
                            "confidence": "0.990",
                            "name": "d'apprendre",
                            "time": "35.270000"
                        },
                        {
                            "duration": "0.270000",
                            "confidence": "1.000",
                            "name": "votre",
                            "time": "35.990000"
                        },
                        {
                            "duration": "0.930000",
                            "confidence": "1.000",
                            "name": "décision",
                            "time": "36.260000"
                        },
                        {
                            "duration": "0.930000",
                            "confidence": "1.000",
                            "name": ".",
                            "time": "37.190000"
                        },
                        {
                            "duration": "0.930000",
                            "confidence": "1.000",
                            "name": "Sans",
                            "time": "38.120000"
                        },
                        {
                            "duration": "0.810000",
                            "confidence": "0.870",
                            "name": "comparaison",
                            "time": "38.420000"
                        },
                        {
                            "duration": "1.080000",
                            "confidence": "0.750",
                            "name": "retentissements",
                            "time": "39.230000"
                        },
                        {
                            "duration": "0.120000",
                            "confidence": "0.980",
                            "name": "à",
                            "time": "40.400000"
                        },
                        {
                            "duration": "0.240000",
                            "confidence": "1.000",
                            "name": "ce",
                            "time": "40.520000"
                        },
                        {
                            "duration": "0.120000",
                            "confidence": "1.000",
                            "name": "que",
                            "time": "40.760000"
                        },
                        {
                            "duration": "0.180000",
                            "confidence": "1.000",
                            "name": "je",
                            "time": "40.880000"
                        },
                        {
                            "duration": "0.360000",
                            "confidence": "1.000",
                            "name": "suis",
                            "time": "41.060000"
                        },
                        {
                            "duration": "0.550000",
                            "confidence": "1.000",
                            "name": "réellement",
                            "time": "41.480000"
                        },
                        {
                            "duration": "0.520000",
                            "confidence": "0.600",
                            "name": ".",
                            "time": "42.040000"
                        },
                        {
                            "duration": "0.360000",
                            "confidence": "0.560",
                            "name": "Comment",
                            "time": "43.640000"
                        },
                        {
                            "duration": "0.270000",
                            "confidence": "1.000",
                            "name": "un",
                            "time": "44.000000"
                        },
                        {
                            "duration": "0.480000",
                            "confidence": "1.000",
                            "name": "homme",
                            "time": "44.270000"
                        },
                        {
                            "duration": "0.510000",
                            "confidence": "1.000",
                            "name": "presque",
                            "time": "44.840000"
                        },
                        {
                            "duration": "0.480000",
                            "confidence": "1.000",
                            "name": "jeune",
                            "time": "45.350000"
                        },
                        {
                            "duration": "0.540000",
                            "confidence": "0.990",
                            "name": "riche",
                            "time": "46.750000"
                        },
                        {
                            "duration": "0.150000",
                            "confidence": "1.000",
                            "name": "de",
                            "time": "47.300000"
                        },
                        {
                            "duration": "0.210000",
                            "confidence": "0.880",
                            "name": "ses",
                            "time": "47.450000"
                        },
                        {
                            "duration": "0.350000",
                            "confidence": "0.950",
                            "name": "seuls",
                            "time": "47.660000"
                        },
                        {
                            "duration": "0.570000",
                            "confidence": "1.000",
                            "name": "doutes",
                            "time": "48.020000"
                        },
                        {
                            "duration": "0.090000",
                            "confidence": "1.000",
                            "name": "et",
                            "time": "48.710000"
                        },
                        {
                            "duration": "0.300000",
                            "confidence": "1.000",
                            "name": "d'une",
                            "time": "48.800000"
                        },
                        {
                            "duration": "0.660000",
                            "confidence": "0.780",
                            "name": "oeuvre",
                            "time": "49.100000"
                        },
                        {
                            "duration": "0.510000",
                            "confidence": "1.000",
                            "name": "encore",
                            "time": "49.850000"
                        },
                        {
                            "duration": "0.090000",
                            "confidence": "1.000",
                            "name": "en",
                            "time": "50.360000"
                        },
                        {
                            "duration": "0.630000",
                            "confidence": "1.000",
                            "name": "chantier",
                            "time": "50.450000"
                        },
                        {
                            "duration": "0.490000",
                            "confidence": "0.860",
                            "name": ".",
                            "time": "51.100000"
                        },
                        {
                            "duration": "0.630000",
                            "confidence": "0.730",
                            "name": "Habitué",
                            "time": "52.130000"
                        },
                        {
                            "duration": "0.120000",
                            "confidence": "1.000",
                            "name": "à",
                            "time": "52.760000"
                        },
                        {
                            "duration": "0.480000",
                            "confidence": "1.000",
                            "name": "vivre",
                            "time": "52.880000"
                        },
                        {
                            "duration": "0.120000",
                            "confidence": "1.000",
                            "name": "dans",
                            "time": "53.360000"
                        },
                        {
                            "duration": "0.090000",
                            "confidence": "1.000",
                            "name": "la",
                            "time": "53.480000"
                        },
                        {
                            "duration": "0.600000",
                            "confidence": "1.000",
                            "name": "solitude",
                            "time": "53.570000"
                        },
                        {
                            "duration": "0.180000",
                            "confidence": "1.000",
                            "name": "du",
                            "time": "54.170000"
                        },
                        {
                            "duration": "0.780000",
                            "confidence": "1.000",
                            "name": "travail",
                            "time": "54.350000"
                        },
                        {
                            "duration": "0.130000",
                            "confidence": "0.990",
                            "name": "ou",
                            "time": "55.930000"
                        },
                        {
                            "duration": "0.130000",
                            "confidence": "0.740",
                            "name": "dans",
                            "time": "56.060000"
                        },
                        {
                            "duration": "0.120000",
                            "confidence": "1.000",
                            "name": "les",
                            "time": "56.240000"
                        },
                        {
                            "duration": "0.690000",
                            "confidence": "0.990",
                            "name": "retraites",
                            "time": "56.360000"
                        },
                        {
                            "duration": "0.150000",
                            "confidence": "0.900",
                            "name": "de",
                            "time": "57.080000"
                        },
                        {
                            "duration": "0.690000",
                            "confidence": "0.980",
                            "name": "l'amitié",
                            "time": "57.230000"
                        },
                        {
                            "duration": "0.300000",
                            "confidence": "1.000",
                            "name": "n'aurait",
                            "time": "58.940000"
                        },
                        {
                            "duration": "0.180000",
                            "confidence": "1.000",
                            "name": "il",
                            "time": "59.240000"
                        },
                        {
                            "duration": "0.290000",
                            "confidence": "1.000",
                            "name": "pas",
                            "time": "59.420000"
                        },
                        {
                            "duration": "0.110000",
                            "confidence": "1.000",
                            "name": ".",
                            "time": "59.780000"
                        }
                    ],
                    "format": "1.0"
                }
            if (angular.isDefined(vm.result.words)) {
                vm.extractResult(vm.result);
                vm.responseSent = true;
                vm.requestSent = true;
                var player = document.querySelector('#audioPlayer');
                player.addEventListener('loadedmetadata', function () {
                    vm.durationAudioFile = player.duration;

                });


            }

        }

        var lanes = ["Mots"],
            laneLength = lanes.length,
            timeBegin = 0,
            timeEnd = items[items.length - 1]['end'];

        vm.timelineJsonMaker = function () {

            // for (var forCompt = 0; forCompt < vm.nbrWords; forCompt++) {
            //     var word = vm.words[forCompt]['name'];
            //     var start = parseFloat(vm.words[forCompt]['time'] * 100);
            //     var end = start + parseFloat(vm.words[forCompt]['duration'] * 100);
            //     var jsonOfWord = {}
            //     jsonOfWord.lane = 0;
            //     jsonOfWord.id = word;
            //     jsonOfWord.start = start;
            //     jsonOfWord.end = end;
            //     jsonOfWord.idHtml = forCompt;
            //
            //     items.push(jsonOfWord);
            // }


        }
        vm.timelineJsonMaker();
        console.log(items);

        var m = [20, 55, 15, 120], //top right bottom left
            w = 999 - m[1] - m[3], //Pour l'adapter a la taille de l'ecran
            h = 200 - m[0] - m[2],
            miniHeight = laneLength * 12 + 50,
            mainHeight = h - miniHeight;


        //scales
        var x = d3.scale.linear()
            .domain([timeBegin, timeEnd])
            .range([0, w]);
        var x1 = d3.scale.linear()
            .range([0, w]);
        var y1 = d3.scale.linear()
            .domain([0, laneLength])
            .range([0, mainHeight]);
        var y2 = d3.scale.linear()
            .domain([0, laneLength])
            .range([0, miniHeight]);

        var chart = d3.selectAll('#timeline')
            .append("svg")
            .attr("width", w + m[1] + m[3])
            .attr("height", h + m[0] + m[2])
            .attr("class", "chart");

        chart.append("defs").append("clipPath")
            .attr("id", "clip")
            .append("rect")
            .attr("width", w)
            .attr("height", mainHeight);

        var main = chart.append("g")
            .attr("transform", "translate(" + m[3] + "," + m[0] + ")")
            .attr("width", w)
            .attr("height", mainHeight)
            .attr("class", "main");

        var mini = chart.append("g")
            .attr("transform", "translate(" + m[3] + "," + (mainHeight + m[0]) + ")")
            .attr("width", w)
            .attr("height", miniHeight)
            .attr("class", "mini");

        //main lanes and texts
        main.append("g").selectAll(".laneLines")
            .data(items)
            .enter().append("line")
            .attr("x1", m[1])
            .attr("y1", function (d) {
                return y1(d.lane);
            })
            .attr("x2", w)
            .attr("y2", function (d) {
                return y1(d.lane);
            })
            .attr("stroke", "lightgray")

        main.append("g").selectAll(".laneText")
            .data(lanes)
            .enter().append("text")
            .text(function (d) {
                return d;
            })
            .attr("x", -m[1])
            .attr("y", function (d, i) {
                return y1(i);
            })
            .attr("dy", ".5ex")
            .attr("text-anchor", "end")
            .attr("class", "laneText");

        //mini lanes and texts
        mini.append("g").selectAll(".laneLines")
            .data(items)
            .enter().append("line")
            .attr("x1", m[1])
            .attr("y1", function (d) {
                return y2(d.lane);
            })
            .attr("x2", w)
            .attr("y2", function (d) {
                return y2(d.lane);
            })
            .attr("stroke", "lightgray");

        mini.append("g").selectAll(".laneText")
            .data(lanes)
            .enter().append("text")
            .text(function (d) {
                return d;
            })
            .attr("x", -m[1])
            .attr("y", function (d, i) {
                return y2(i + .5);
            })
            .attr("dy", ".5ex")
            .attr("text-anchor", "end")
            .attr("class", "laneText");

        var itemRects = main.append("g")
            .attr("clip-path", "url(#clip)");

        //mini item rects
        mini.append("g").selectAll("miniItems")
            .data(items)
            .enter().append("rect")
            .attr("class", function (d) {
                return "miniItem" + d.lane;
            })
            .attr("x", function (d) {
                return x(d.start);
            })
            .attr("y", function (d) {
                return y2(d.lane + .5) - 5; //Permet de regler l'emplacement  de la barre rose du mini
            })
            .attr("width", function (d) {
                return x(d.end - d.start);
            })
            .attr("height", 10);

        //mini labels
        mini.append("g").selectAll(".miniLabels")
            .data(items)
            .enter().append("text") //Recupere le texte pour les mini vignette
            .text(function (d) {
                return d.id;
            })
            .attr("x", function (d) {
                return x(d.start);
            })
            .attr("y", function (d) {
                return y2(d.lane + .5); //Lane correspond au niveau, dans le cas present il n'y en a qu'un
            })
            .attr("dy", ".5ex");

        //brush
        var brush = d3.svg.brush()
            .x(x)
            .on("brush", display);

        mini.append("g")
            .attr("class", "x brush")
            .call(brush)
            .selectAll("rect")
            .attr("y", 1)
            .attr("height", miniHeight - 1);

        function display() {
            console.log(vm.partOfTraitement + " : " + vm.words.length - 1);

            var rects, labels,
                minExtent = brush.extent()[0],
                maxExtent = brush.extent()[1],
                visItems = items.filter(function (d) {

                    var tabShow = [];
                    //ici pour l'interval pour disparaite ici pas en bas !
                    for (var y = 0; y < vm.words.length - 1; y++) {
                        if ((parseFloat(vm.words[y]['time']) > parseFloat(minExtent)) && (vm.words[y]['time'] < parseFloat(maxExtent))) {
                            console.log(vm.words[y]['name']);
                            tabShow.push(y);
                        } else {
                        }
                    }
                    console.log(tabShow);

                    for (var i = 0; i <= vm.nbrWords; i++) {
                        var idElmt = document.getElementById("m" + [i]);
                        idElmt.style = "display:none";
                    }
                    for (var i = 0; i < tabShow.length; i++) {
                        var idElmt = document.getElementById("m" + tabShow[i]);
                        idElmt.style = "display:yes";

                    }


                    return d.start < maxExtent && d.end > minExtent;
                });

            mini.select(".brush")
                .call(brush.extent([minExtent, maxExtent]));

            x1.domain([minExtent, maxExtent]);

            //update main item rects
            rects = itemRects.selectAll("rect")
                .data(visItems, function (d) {
                    //Pour eliminer les elements visibles
                    // document.getElementById("m" + [d.idHtml]).style = "display:yes";
                    return d.id;
                })
                .attr("x", function (d) {
                    return x1(d.start);
                })
                .attr("width", function (d) {
                    return x1(d.end) - x1(d.start);
                });

            rects.enter().append("rect")
                .attr("class", function (d) {
                    return "miniItem" + d.lane;
                })
                .attr("x", function (d) {
                    return x1(d.start);
                })
                .attr("y", function (d) {
                    return y1(d.lane) + 10;
                })
                .attr("width", function (d) {
                    return x1(d.end) - x1(d.start);
                })
                .attr("height", function (d) {
                    return .8 * y1(1);
                });

            rects.exit().remove();

            //update the item labels
            labels = itemRects.selectAll("text")
                .data(visItems, function (d) {
                    return d.id;
                })
                .attr("x", function (d) {
                    return x1(Math.max(d.start, minExtent) + 2);
                });

            labels.enter().append("text")
                .text(function (d) {
                    return d.id;
                })
                .attr("x", function (d) {
                    return x1(Math.max(d.start, minExtent));
                })
                .attr("y", function (d) {
                    return y1(d.lane + .5);
                })
                .attr("text-anchor", "start");

            labels.exit().remove();
        }


    }


})();
