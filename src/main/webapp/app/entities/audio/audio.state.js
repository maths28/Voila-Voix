(function() {
    'use strict';

    angular
        .module('voilaVoixApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('audio', {
            parent: 'entity',
            url: '/audio',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'voilaVoixApp.audio.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/audio/audios.html',
                    controller: 'AudioController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('audio');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('audio-detail', {
            parent: 'audio',
            url: '/audio/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'voilaVoixApp.audio.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/audio/audio-detail.html',
                    controller: 'AudioDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('audio');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Audio', function($stateParams, Audio) {
                    return Audio.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'audio',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('audio-detail.edit', {
            parent: 'audio-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/audio/audio-dialog.html',
                    controller: 'AudioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Audio', function(Audio) {
                            return Audio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('audio.new', {
            parent: 'audio',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/audio/audio-dialog.html',
                    controller: 'AudioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                file: null,
                                fileContentType: null,
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('audio', null, { reload: 'audio' });
                }, function() {
                    $state.go('audio');
                });
            }]
        })
        .state('audio.edit', {
            parent: 'audio',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/audio/audio-dialog.html',
                    controller: 'AudioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Audio', function(Audio) {
                            return Audio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('audio', null, { reload: 'audio' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('audio.delete', {
            parent: 'audio',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/audio/audio-delete-dialog.html',
                    controller: 'AudioDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Audio', function(Audio) {
                            return Audio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('audio', null, { reload: 'audio' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
