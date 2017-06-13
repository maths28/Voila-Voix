(function() {
    'use strict';

    angular
        .module('voilaVoixApp')
        .controller('WordController', WordController);

    WordController.$inject = ['$scope', '$state', 'Word', 'WordSearch'];

    function WordController ($scope, $state, Word, WordSearch) {
        var vm = this;

        vm.words = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Word.query(function(result) {
                vm.words = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            WordSearch.query({query: vm.searchQuery}, function(result) {
                vm.words = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
