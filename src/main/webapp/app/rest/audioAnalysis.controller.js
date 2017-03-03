(function () {
    'use strict';

    angular
        .module('voilaVoixApp')
        .controller('audioAnalysisController', audioAnalysisController);

    audioAnalysisController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$resource', 'RestRequest', 'News', 'NewsSearch', 'ParseLinks', 'AlertService', 'paginationConstants'];

    function audioAnalysisController($scope, Principal, LoginService, $state, $resource, RestRequest, News, NewsSearch, ParseLinks, AlertService, paginationConstants) {
        var vm = this;
        vm.news = [];

        vm.account = null;
        vm.isAuthenticated = null;
        vm.filename = "";
        function getAccount() {
            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }

        function register() {
            $state.go('register');
        }

        vm.btnClicked = function () {
            if (!(vm.filename && vm.champ2)) {
                vm.result = null;
            } else {
                vm.result = vm.filename + " " + vm.champ2;
            }
        };

        vm.getjson = function () {
            vm.json = RestRequest.query();
        }
        vm.loadAll = loadAll;
        loadAll();


        function loadAll() {
            News.query(function (result) {
                vm.news = result;
            })

        }
    }

})();
