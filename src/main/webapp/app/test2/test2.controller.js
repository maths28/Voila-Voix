(function() {
    'use strict';

    angular
        .module('voilaVoixApp')
        .controller('Test2Controller', Test2Controller);

    Test2Controller.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$resource', 'SMService'];

    function Test2Controller ($scope, Principal, LoginService, $state, $resource, SMService) {
        var vm = this;
        vm.id_analyse = null;
        vm.requestSent = false;
        vm.responseSent = false;
        vm.resultSM = null;
        vm.resultSMText = null;
        vm.account = null;
        vm.isAuthenticated = null;
        vm.filename = "";
        vm.champ2 = "";
        vm.result = null;
        vm.history = ["Stuff", "AnotherStuff"];
        vm.jsonres = $resource('testres/json', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'save': { method:'POST' },
            'update': { method:'PUT' },
            'delete':{ method:'DELETE'}
        });

        vm.json = null;



        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }

        vm.btnClicked = function () {
            if(!(vm.filename && vm.champ2)){
                vm.result = null;
            } else {
                vm.result = vm.filename + " " + vm.champ2;
            }
        };

        vm.getjson = function () {
            vm.json = vm.jsonres.query();
        };

        vm.appelSM = function () {
            if(!vm.requestSent){
                SMService.post().$promise.then(function (result) {
                    vm.resultSM = result;
                    vm.id_analyse = vm.resultSM.id;
                    vm.requestSent = true;
                }).catch(function () {
                    vm.resultSM = "Aucun resultat";
                    vm.requestSent = true;
                });
            } else if(!vm.responseSent) {
                SMService.get({id: vm.id_analyse}).$promise.then(function (result) {
                    vm.resultSM = result;
                    if(angular.isDefined(vm.resultSM.words)){
                        vm.transformResult();
                        vm.responseSent = true;
                    }
                }).catch(function () {
                    vm.resultSM = "Aucun resultat";
                    vm.responseSent = true;
                });
            }

        };

        vm.transformResult = function () {
            vm.resultSMText = "";
            var words = vm.resultSM.words;
            var cmpt = 0;
            angular.forEach(words, function (value) {
                if(cmpt != 0 || value.name != "."){
                    vm.resultSMText+=" ";
                }
                vm.resultSMText+= value.name;
            });
        }


    }
})();
