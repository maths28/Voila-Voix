(function() {
    'use strict';

    angular
        .module('voilaVoix2App')
        .controller('AudioController', AudioController);

    AudioController.$inject = ['DataUtils', 'Audio'];

    function AudioController(DataUtils, Audio) {

        var vm = this;

        vm.audios = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Audio.query(function(result) {
                vm.audios = result;
                vm.searchQuery = null;
            });
        }
    }
})();
