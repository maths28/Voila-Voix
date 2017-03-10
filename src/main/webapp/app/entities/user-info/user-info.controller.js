(function() {
    'use strict';

    angular
        .module('voilaVoixApp')
        .controller('UserInfoController', UserInfoController);

    UserInfoController.$inject = ['$scope', '$state', 'UserInfo', 'UserInfoSearch'];

    function UserInfoController ($scope, $state, UserInfo, UserInfoSearch) {
        var vm = this;

        vm.userInfos = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            UserInfo.query(function(result) {
                vm.userInfos = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            UserInfoSearch.query({query: vm.searchQuery}, function(result) {
                vm.userInfos = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
