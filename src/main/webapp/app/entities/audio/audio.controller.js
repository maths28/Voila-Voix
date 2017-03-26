(function() {
    'use strict';

    angular
        .module('voilaVoixApp')
        .controller('AudioController', AudioController);

    AudioController.$inject = ['$scope', '$state', 'DataUtils', 'Audio', 'AudioSearch'];

    function AudioController ($scope, $state, DataUtils, Audio, AudioSearch) {
        var vm = this;

        vm.audios = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Audio.query(function(result) {
                vm.audios = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            AudioSearch.query({query: vm.searchQuery}, function(result) {
                vm.audios = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
