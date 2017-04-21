(function() {
    'use strict';

    angular
        .module('voilaVoixApp')
        .controller('AudioDialogController', AudioDialogController);

    AudioDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Audio'];

    function AudioDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Audio) {
        var vm = this;

        vm.audio = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.audio.id !== null) {
                Audio.update(vm.audio, onSaveSuccess, onSaveError);
            } else {
                Audio.save(vm.audio, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('voilaVoixApp:audioUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setFile = function ($file, audio) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        audio.file = base64Data;
                        audio.fileContentType = $file.type;
                    });
                });
            }
        };

    }
})();
