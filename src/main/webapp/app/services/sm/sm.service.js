(function() {
    'use strict';

    angular
        .module('voilaVoixApp')
        .factory('SMService', SMService);

    SMService.$inject = ['$resource'];

    function SMService ($resource) {
        var service = $resource('testres/demo/:id', {}, {
            'post': { method: 'POST', params: {}, isArray: false,
                interceptor: {
                    response: function(response) {
                        // expose response
                        return response.data;
                    }
                }
            },
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
