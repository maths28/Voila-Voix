(function() {
    'use strict';

    angular
        .module('voilaVoix2App')
        .controller('SubscriptionDetailController', SubscriptionDetailController);

    SubscriptionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Subscription'];

    function SubscriptionDetailController($scope, $rootScope, $stateParams, previousState, entity, Subscription) {
        var vm = this;

        vm.subscription = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('voilaVoix2App:subscriptionUpdate', function(event, result) {
            vm.subscription = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
