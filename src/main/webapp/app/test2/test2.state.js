(function() {
    'use strict';

    angular
        .module('voilaVoixApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('test2', {
            parent: 'app',
            url: '/test2',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            views: {
                'content@': {
                    templateUrl: 'app/test2/test2.html',
                    controller: 'Test2Controller',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
