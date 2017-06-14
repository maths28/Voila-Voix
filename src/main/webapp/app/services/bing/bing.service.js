(function() {
    'use strict';

    angular
        .module('voilaVoix2App')
        .factory('BingService', BingService);

    BingService.$inject = ['$resource'];

    function BingService ($resource) {
        var service = $resource('testres/demo', {}, {
            'get': { method: 'GET', params: {}, isArray: false,
                interceptor: {
                    response: function(response) {
                        // expose response
                        return response.data;
                    }
                }
            }
        });

        return service;
    }
})();
