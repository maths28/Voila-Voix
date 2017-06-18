(function() {
    'use strict';

    angular
        .module('voilaVoixApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('demo', {
            parent: 'app',
            url: '/demo',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/demo/demo.html',
                    controller: 'resultController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }]
                ,
                isDemo: function(){return true}

            }
        });
    }
})();
