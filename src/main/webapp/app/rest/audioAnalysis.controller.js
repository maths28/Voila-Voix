(function () {
    'use strict';

    angular
        .module('voilaVoixApp')
        .controller('audioAnalysisController', audioAnalysisController);

    audioAnalysisController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$resource', 'RestRequest', 'News', 'NewsSearch', 'ParseLinks', 'AlertService', 'paginationConstants', 'DataUtils', 'Audio', 'SMService'];

    function audioAnalysisController($scope, Principal, LoginService, $state, $resource, RestRequest, News, NewsSearch, ParseLinks, AlertService, paginationConstants, DataUtils, Audio, SMService) {
        var vm = this;
        vm.news = [];

        vm.account = null;
        vm.isAuthenticated = null;
        vm.filename = "";
        vm.audio = {
            name: null,
            file: null,
            fileContentType: null,
            id: null
        };

        function getAccount() {
            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }

        function register() {
            $state.go('register');
        }

        vm.btnClicked = function () {
            if (!(vm.filename && vm.champ2)) {
                vm.result = null;
            } else {
                vm.result = vm.filename + " " + vm.champ2;
            }
        };

        vm.getjson = function () {
            vm.json = RestRequest.query();
        }
        vm.loadAll = loadAll;
        loadAll();


        function loadAll() {
            News.query(function (result) {
                vm.news = result;
            })

        }

        vm.setFile = function ($file, audio) {
            if ($file) {
                audio.file = $file;
                // DataUtils.toBase64($file, function(base64Data) {
                //     $scope.$apply(function() {
                //         audio.file = base64Data;
                //         audio.fileContentType = $file.type;
                //         audio.name = $file.name;
                //     });
                // });
            }
        };

        vm.submit = function () {
            if (vm.audio.file) {

                SMService.audioFile = vm.audio.file;
                $state.go('result');
                // Audio.save(vm.audio.file, function (result) {
                //     SMService.idAudioUploaded = result.id;
                //     $state.go('result');
                // }, function () {
                //     console.log("ERROR");
                // });
                // Upload.upload({
                //     url: 'testres/ok',
                //     data: {file: vm.file}
                // }).then(function (resp) {
                //     console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ' + resp.data);
                // }, function (resp) {
                //     console.log('Error status: ' + resp.status);
                // }, function (evt) {
                //     var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                //     console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
                // });
            }
        }
    }

})();
