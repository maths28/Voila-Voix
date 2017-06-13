(function() {
    'use strict';

    angular
        .module('voilaVoixApp')
        .controller('WordDetailController', WordDetailController);

    WordDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Word', 'Definition'];

    function WordDetailController($scope, $rootScope, $stateParams, previousState, entity, Word, Definition) {
        var vm = this;

        vm.word = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('voilaVoixApp:wordUpdate', function(event, result) {
            vm.word = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
