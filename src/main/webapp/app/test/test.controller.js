(function() {
    'use strict';

    angular
        .module('voilaVoixApp')
        .controller('TestController', TestController);

    TestController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$resource'];

    function TestController ($scope, Principal, LoginService, $state, $resource) {
        var vm = this;

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
        }


    }
})();
