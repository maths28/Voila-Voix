(function () {
    'use strict';

    angular
        .module('voilaVoix2App')
        .controller('resultController', resultController);

    resultController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$resource', 'RestRequest', 'SMService', '$location', 'isDemo', '$timeout', '$stateParams', '$http'];

    function resultController($scope, Principal, LoginService, $state, $resource, RestRequest, SMService, $location, isDemo, $timeout, $stateParams, $http) {
        var vm = this;
        vm.id_analyse = null;
        vm.requestSent = false;
        vm.responseSent = false;
        vm.result = null;
        vm.speakers = [];
        vm.words = [];
        vm.show = false; //Si true alors il est possible de commencer a afficher les mots
        vm.textBtn = "Afficher";
        vm.umnamedTab = [];
        vm.wordToSearch = "";
        vm.wordToSearchResult = [];
        vm.idToReset = [];              //L'id des mots dont il est necesaire de reset la couleur
        vm.readyToH = false;            //Le contenu du texte peut etre desactivé
        vm.readyToS = false;            //Le contenu du texte est pret a etre affiché
        vm.analyse = false;
        vm.partOfTraitement = 0;        //% de traitement effectué
        vm.readyToHButton = true;       //Il fut un temps ...
        vm.result = null;
        vm.idElmt = document.getElementById("m");
        vm.isD3init = false;
        var items = null;  //Contient les "themes

        var getHardCodedDemoData = function () { //Permet d'executer une fonction necessitant une valeur hardcodé dans le back ou la bdd. Il faut virer l'url en dure
            $http.get("/demojson").success(function (data) {
                vm.result = data;
                isDefined();
            }).error(function (data, status) {

            });


        };

        var getHardCodedTimeline = function () {
            $http.get("/timelinesens").success(function (data) {
                items = data;
                console.log(items);


            }).error(function (data, status) {

            });
        };

        var initD3js = function () {

            var lanes = [""],
                laneLength = lanes.length,
                timeBegin = 0,
                timeEnd = items[items.length - 1]['end'];

            console.log(items);

            var m = [20, 55, 15, 60], //top right bottom left
                w = 800 - m[1] - m[3], //Pour l'adapter a la taille de l'ecran
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

        var isDefined = function () {
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

        vm.tabOk = function (a) {
            if (a == parseInt(vm.nbrWords)) {
                vm.readyToH = true;
            }

        }
        vm.actualiziDE = function (i) {
            $scope.$watch(vm.idElmt);
            vm.idElmt = document.getElementById("m" + [i]);
            vm.idElmt.style = "display:yes";
            vm.partOfTraitement = parseInt(i * 100 / vm.nbrWords - 1);
            if (vm.partOfTraitement > 90) {
                vm.partOfTraitement = 100;
                vm.idElmt = document.getElementById("timeline");
                vm.idElmt.style = "display:yes";
                if (!vm.isD3init) {
                    initD3js();
                    vm.isD3init = true;
                }

            }


        } //Permet d'actualiser l'affichage des mots i etant l'id du mot

        vm.showWordsByPart = function () {
            var idElmt = document.getElementById("timeline");
            idElmt.style = "display:none";

            console.log(vm.nbrWords);
            for (var i = 0; i < vm.nbrWords; i++) {
                (function (i) {
                    $timeout(function () {
                        vm.actualiziDE(i);
                    }, (parseInt(i / 12)) * 1000);
                })(i);
            }

        }

        vm.hideWordsAfterFirstLoading = function () {
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
        } //Permet de selectionner le texte au auditeur specifique

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
        } //Recherche de mot

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

        //hum devrait peut etre se trouver dans le back
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
                    vm.appelSM();
                }, function () {
                    vm.result = "Aucun resultat";
                    vm.requestSent = true;
                    vm.appelSM();
                });
            }
            else if (!vm.responseSent) {
                SMService.get({id: vm.id_analyse}).$promise.then(function (result) {
                    vm.result = result;
                    if (angular.isDefined(vm.result.words)) {
                        vm.extractResult(vm.result);
                        vm.responseSent = true;
                    } else {
                        vm.appelSM();
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

        //Au chargement de la page, selon la route.
        if (!isDemo) {
            vm.appelSM();
            // vm.extractResult(json)
        }
        else {
            getHardCodedDemoData();
        }
        getHardCodedTimeline();


    }


})();
