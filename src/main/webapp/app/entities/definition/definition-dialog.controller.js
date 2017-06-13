(function() {
    'use strict';

    angular
        .module('voilaVoixApp')
        .controller('DefinitionDialogController', DefinitionDialogController);

    DefinitionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Definition', 'Word'];

    function DefinitionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Definition, Word) {
        var vm = this;

        vm.definition = entity;
        vm.clear = clear;
        vm.save = save;
        vm.words = Word.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.definition.id !== null) {
                Definition.update(vm.definition, onSaveSuccess, onSaveError);
            } else {
                Definition.save(vm.definition, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('voilaVoixApp:definitionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
