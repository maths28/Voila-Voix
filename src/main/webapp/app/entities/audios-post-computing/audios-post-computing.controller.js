(function() {
    'use strict';

    angular
        .module('voilaVoix2App')
        .controller('AudiosPostComputingController', AudiosPostComputingController);

    AudiosPostComputingController.$inject = ['AudiosPostComputing'];

    function AudiosPostComputingController(AudiosPostComputing) {

        var vm = this;

        vm.audiosPostComputings = [];

        loadAll();

        function loadAll() {
            AudiosPostComputing.query(function(result) {
                vm.audiosPostComputings = result;
                vm.searchQuery = null;
            });
        }
    }
})();
