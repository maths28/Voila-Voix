(function() {
    'use strict';

    angular
        .module('voilaVoix2App')
        .controller('DefinitionController', DefinitionController);

    DefinitionController.$inject = ['Definition'];

    function DefinitionController(Definition) {

        var vm = this;

        vm.definitions = [];

        loadAll();

        function loadAll() {
            Definition.query(function(result) {
                vm.definitions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
