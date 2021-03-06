(function() {
    'use strict';

    angular
        .module('voilaVoix2App')
        .controller('ProductListController', ProductListController);

    ProductListController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'Subscription'];

    function ProductListController ($scope, Principal, LoginService, $state, Subscription) {
        var vm = this;

        vm.subscriptions = [];
        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();


        loadAll();

        function loadAll() {
            Subscription.query(function(result) {
                vm.subscriptions = result;
                vm.searchQuery = null;
            });
        }

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();
