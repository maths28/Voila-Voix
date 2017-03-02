(function() {
    'use strict';

    angular
        .module('voilaVoixApp')
        .controller('TestController', TestController);

    TestController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$resource', 'RestRequest'];

    function TestController ($scope, Principal, LoginService, $state, $resource, RestRequest) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.filename = "";
        vm.uri ="testres";
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
            vm.json = RestRequest.query();

        }


    }
})();
