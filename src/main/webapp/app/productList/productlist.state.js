(function() {
    'use strict';

    angular
        .module('voilaVoixApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('productlist', {
            parent: 'app',
            url: '/productlist',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/productList/productlist.html',
                    controller: 'ProductListController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home'); //Provisoire
                    return $translate.refresh();
                }]
            }
        });
    }
})();
