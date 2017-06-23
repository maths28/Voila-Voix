(function () {
    'use strict';

    angular
        .module('voilaVoix2App')
        .factory('httpGetString');

    httpGetString.$inject = ['$http'];

    function httpGetString($http) {
        var self = this;

        var httpFunction = function(uri, theFonction){


            $http.get("http://localhost:8080/" + uri).success(function (data) {

                theFonction();
                return data;

            }).error(function (data, status) {

            });

        }

    }
})();
