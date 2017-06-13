(function() {
    'use strict';

    angular
        .module('voilaVoix2App')
        .controller('AudioDetailController', AudioDetailController);

    AudioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Audio'];

    function AudioDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Audio) {
        var vm = this;

        vm.audio = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('voilaVoix2App:audioUpdate', function(event, result) {
            vm.audio = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
