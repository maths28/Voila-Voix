(function() {
    'use strict';

    angular
        .module('voilaVoix2App')
        .controller('NewsController', NewsController);

    NewsController.$inject = ['News'];

    function NewsController(News) {

        var vm = this;

        vm.news = [];

        loadAll();

        function loadAll() {
            News.query(function(result) {
                vm.news = result;
                vm.searchQuery = null;
            });
        }
    }
})();
