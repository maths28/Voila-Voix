(function () {
    'use strict';

    angular
        .module('voilaVoix2App')
        .controller('resultController', resultController);

    resultController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$resource', 'RestRequest', 'SMService', '$location'];

    function resultController($scope, Principal, LoginService, $state, $resource, RestRequest, SMService, $location) {
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
                    console.log(json['words'][i]['name']);
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
        vm.appelSM();
        // vm.extractResult(json)
    }


})();
