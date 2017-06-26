(function () {
    'use strict';

    angular
        .module('voilaVoix2App')
        .factory('SMService', SMService);

    SMService.$inject = ['$resource', 'Upload'];

    function SMService($resource, Upload) {
        var service = $resource('upload/job/:id', {}, {
            'get': {
                method: 'GET', params: {}, isArray: false,
                interceptor: {
                    response: function (response) {
                        // expose response
                        return response.data;
                    }
                }
            }
        });

        service.post = function () {
            return Upload.upload({
                url: 'upload/job',
                data: {
                    file: service.audioFile,
                    startTime: service.startTime,
                    endTime: service.endTime
                }
            });
            //     .then(function (resp) {
            //     console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ' + resp.data);
            // }, function (resp) {
            //     console.log('Error status: ' + resp.status);
            // }, function (evt) {
            //     var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            //     console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
            // });
        };

        service.idAudioUploaded = undefined;
        service.audioFile = undefined;
        service.startTime = undefined;
        service.endTime = undefined;
        return service;
    }
})();
