(function() {
    'use strict';

    angular
        .module('voilaVoixApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('audioAnalysis', {
            parent: 'app',
            url: '/audioAnalysis',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            views: {
                'content@': {
                    templateUrl: 'app/rest/audioAnalysis.html',
                    controller: 'audioAnalysisController',
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
