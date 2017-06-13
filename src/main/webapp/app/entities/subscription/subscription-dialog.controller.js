(function() {
    'use strict';

    angular
        .module('voilaVoix2App')
        .controller('SubscriptionDialogController', SubscriptionDialogController);

    SubscriptionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Subscription'];

    function SubscriptionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Subscription) {
        var vm = this;

        vm.subscription = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.subscription.id !== null) {
                Subscription.update(vm.subscription, onSaveSuccess, onSaveError);
            } else {
                Subscription.save(vm.subscription, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('voilaVoix2App:subscriptionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
