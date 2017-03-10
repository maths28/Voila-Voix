(function() {
    'use strict';

    angular
        .module('voilaVoixApp')
        .controller('SubscriptionController', SubscriptionController);

    SubscriptionController.$inject = ['$scope', '$state', 'Subscription', 'SubscriptionSearch'];

    function SubscriptionController ($scope, $state, Subscription, SubscriptionSearch) {
        var vm = this;

        vm.subscriptions = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Subscription.query(function(result) {
                vm.subscriptions = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            SubscriptionSearch.query({query: vm.searchQuery}, function(result) {
                vm.subscriptions = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
