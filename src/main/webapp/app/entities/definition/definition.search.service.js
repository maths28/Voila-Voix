(function() {
    'use strict';

    angular
        .module('voilaVoixApp')
        .factory('DefinitionSearch', DefinitionSearch);

    DefinitionSearch.$inject = ['$resource'];

    function DefinitionSearch($resource) {
        var resourceUrl =  'api/_search/definitions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
