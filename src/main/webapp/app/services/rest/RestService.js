(function () {
    'use strict';

    angular
        .module('voilaVoixApp')
        .factory('RestRequest', RestRequest);

    RestRequest.$inject = ['$resource'];


    function RestRequest($resource) {
        var vm = this;

        vm.jsonres = $resource('bingService/analyse', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'save': {method: 'POST'},
            'update': {method: 'PUT'},
            'delete': {method: 'DELETE'}
        });
        return vm.jsonres;
    }
})();
