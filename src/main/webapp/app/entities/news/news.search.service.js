(function() {
    'use strict';

    angular
        .module('voilaVoixApp')
        .factory('NewsSearch', NewsSearch);

    NewsSearch.$inject = ['$resource'];

    function NewsSearch($resource) {
        var resourceUrl =  'api/_search/news/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
