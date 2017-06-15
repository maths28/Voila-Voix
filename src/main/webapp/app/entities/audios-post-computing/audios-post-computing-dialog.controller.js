(function() {
    'use strict';

    angular
        .module('voilaVoix2App')
        .controller('AudiosPostComputingDialogController', AudiosPostComputingDialogController);

    AudiosPostComputingDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AudiosPostComputing'];

    function AudiosPostComputingDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AudiosPostComputing) {
        var vm = this;

        vm.audiosPostComputing = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.audiosPostComputing.id !== null) {
                AudiosPostComputing.update(vm.audiosPostComputing, onSaveSuccess, onSaveError);
            } else {
                AudiosPostComputing.save(vm.audiosPostComputing, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('voilaVoix2App:audiosPostComputingUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
