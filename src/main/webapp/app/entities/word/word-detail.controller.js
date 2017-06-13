(function() {
    'use strict';

    angular
        .module('voilaVoix2App')
        .controller('WordDetailController', WordDetailController);

    WordDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Word', 'Definition'];

    function WordDetailController($scope, $rootScope, $stateParams, previousState, entity, Word, Definition) {
        var vm = this;

        vm.word = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('voilaVoix2App:wordUpdate', function(event, result) {
            vm.word = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
