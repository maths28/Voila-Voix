(function() {
    'use strict';

    angular
        .module('voilaVoix2App')
        .controller('AudiosPostComputingDetailController', AudiosPostComputingDetailController);

    AudiosPostComputingDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AudiosPostComputing'];

    function AudiosPostComputingDetailController($scope, $rootScope, $stateParams, previousState, entity, AudiosPostComputing) {
        var vm = this;

        vm.audiosPostComputing = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('voilaVoix2App:audiosPostComputingUpdate', function(event, result) {
            vm.audiosPostComputing = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
