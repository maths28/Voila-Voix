(function() {
    'use strict';

    angular
        .module('voilaVoixApp')
        .controller('UserInfoDetailController', UserInfoDetailController);

    UserInfoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserInfo', 'User', 'Subscription'];

    function UserInfoDetailController($scope, $rootScope, $stateParams, previousState, entity, UserInfo, User, Subscription) {
        var vm = this;

        vm.userInfo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('voilaVoixApp:userInfoUpdate', function(event, result) {
            vm.userInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
