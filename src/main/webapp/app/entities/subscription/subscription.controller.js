(function() {
    'use strict';

    angular
        .module('voilaVoix2App')
        .controller('SubscriptionController', SubscriptionController);

    SubscriptionController.$inject = ['Subscription'];

    function SubscriptionController(Subscription) {

        var vm = this;

        vm.subscriptions = [];

        loadAll();

        function loadAll() {
            Subscription.query(function(result) {
                vm.subscriptions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
