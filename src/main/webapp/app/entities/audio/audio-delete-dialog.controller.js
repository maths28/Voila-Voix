(function() {
    'use strict';

    angular
        .module('voilaVoix2App')
        .controller('AudioDeleteController',AudioDeleteController);

    AudioDeleteController.$inject = ['$uibModalInstance', 'entity', 'Audio'];

    function AudioDeleteController($uibModalInstance, entity, Audio) {
        var vm = this;

        vm.audio = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Audio.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
