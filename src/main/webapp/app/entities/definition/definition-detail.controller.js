(function() {
    'use strict';

    angular
        .module('voilaVoixApp')
        .controller('DefinitionDetailController', DefinitionDetailController);

    DefinitionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Definition', 'Word'];

    function DefinitionDetailController($scope, $rootScope, $stateParams, previousState, entity, Definition, Word) {
        var vm = this;

        vm.definition = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('voilaVoixApp:definitionUpdate', function(event, result) {
            vm.definition = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
