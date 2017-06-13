(function() {
    'use strict';

    angular
        .module('voilaVoixApp')
        .controller('DefinitionController', DefinitionController);

    DefinitionController.$inject = ['$scope', '$state', 'Definition', 'DefinitionSearch'];

    function DefinitionController ($scope, $state, Definition, DefinitionSearch) {
        var vm = this;

        vm.definitions = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Definition.query(function(result) {
                vm.definitions = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            DefinitionSearch.query({query: vm.searchQuery}, function(result) {
                vm.definitions = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
