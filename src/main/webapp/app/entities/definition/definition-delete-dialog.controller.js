(function() {
    'use strict';

    angular
        .module('voilaVoix2App')
        .controller('DefinitionDeleteController',DefinitionDeleteController);

    DefinitionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Definition'];

    function DefinitionDeleteController($uibModalInstance, entity, Definition) {
        var vm = this;

        vm.definition = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Definition.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
