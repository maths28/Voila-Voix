(function() {
    'use strict';
    angular
        .module('voilaVoixApp')
        .factory('Audio', Audio);

    Audio.$inject = ['$resource'];

    function Audio ($resource) {
        var resourceUrl =  'api/audios/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
