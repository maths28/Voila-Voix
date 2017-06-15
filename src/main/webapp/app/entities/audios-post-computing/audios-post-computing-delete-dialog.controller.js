(function() {
    'use strict';

    angular
        .module('voilaVoix2App')
        .controller('AudiosPostComputingDeleteController',AudiosPostComputingDeleteController);

    AudiosPostComputingDeleteController.$inject = ['$uibModalInstance', 'entity', 'AudiosPostComputing'];

    function AudiosPostComputingDeleteController($uibModalInstance, entity, AudiosPostComputing) {
        var vm = this;

        vm.audiosPostComputing = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AudiosPostComputing.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
