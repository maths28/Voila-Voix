(function() {
    'use strict';

    angular
        .module('voilaVoixApp')
        .factory('Account', Account);

    Account.$inject = ['$resource'];

    function Account ($resource) {
        var service = $resource('api/account', {}, {
            'get': { method: 'GET', params: {}, isArray: false,
                interceptor: {
                    response: function(response) {
                        // expose response
                        return response;
                    }
                }
            }
        });

        var resourceUrl =  'api/user-infos/user/:login';

        var userInfo = $resource(resourceUrl, {}, {
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            }
         });

        var info = userInfo.get({login : service.login}) ;

        //service.userInfo = info ;
        service.userInfo = "TrucMuche"
        return service;
    }

    // old Version

    // function Account ($resource) {
    //     var service = $resource('api/account', {}, {
    //         'get': { method: 'GET', params: {}, isArray: false,
    //             interceptor: {
    //                 response: function(response) {
    //                     // expose response
    //                     return response;
    //                 }
    //             }
    //         }
    //     });
    //
    //     return service;
    // }


})();
