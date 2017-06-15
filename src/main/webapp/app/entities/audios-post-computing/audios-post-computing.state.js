(function() {
    'use strict';

    angular
        .module('voilaVoix2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('audios-post-computing', {
            parent: 'entity',
            url: '/audios-post-computing',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'voilaVoix2App.audiosPostComputing.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/audios-post-computing/audios-post-computings.html',
                    controller: 'AudiosPostComputingController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('audiosPostComputing');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('audios-post-computing-detail', {
            parent: 'audios-post-computing',
            url: '/audios-post-computing/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'voilaVoix2App.audiosPostComputing.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/audios-post-computing/audios-post-computing-detail.html',
                    controller: 'AudiosPostComputingDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('audiosPostComputing');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'AudiosPostComputing', function($stateParams, AudiosPostComputing) {
                    return AudiosPostComputing.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'audios-post-computing',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('audios-post-computing-detail.edit', {
            parent: 'audios-post-computing-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/audios-post-computing/audios-post-computing-dialog.html',
                    controller: 'AudiosPostComputingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AudiosPostComputing', function(AudiosPostComputing) {
                            return AudiosPostComputing.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('audios-post-computing.new', {
            parent: 'audios-post-computing',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/audios-post-computing/audios-post-computing-dialog.html',
                    controller: 'AudiosPostComputingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                idApi: null,
                                result: null,
                                shaOne: null,
                                date: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('audios-post-computing', null, { reload: 'audios-post-computing' });
                }, function() {
                    $state.go('audios-post-computing');
                });
            }]
        })
        .state('audios-post-computing.edit', {
            parent: 'audios-post-computing',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/audios-post-computing/audios-post-computing-dialog.html',
                    controller: 'AudiosPostComputingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AudiosPostComputing', function(AudiosPostComputing) {
                            return AudiosPostComputing.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('audios-post-computing', null, { reload: 'audios-post-computing' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('audios-post-computing.delete', {
            parent: 'audios-post-computing',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/audios-post-computing/audios-post-computing-delete-dialog.html',
                    controller: 'AudiosPostComputingDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AudiosPostComputing', function(AudiosPostComputing) {
                            return AudiosPostComputing.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('audios-post-computing', null, { reload: 'audios-post-computing' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
