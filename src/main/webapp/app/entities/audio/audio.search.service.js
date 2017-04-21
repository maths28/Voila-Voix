(function() {
    'use strict';

    angular
        .module('voilaVoixApp')
        .factory('AudioSearch', AudioSearch);

    AudioSearch.$inject = ['$resource'];

    function AudioSearch($resource) {
        var resourceUrl =  'api/_search/audios/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
