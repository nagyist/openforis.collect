var collectApp = angular.module('collectApp', []).

    controller('RecordCtrl', ['$scope', '$http', 'recordService',
        function ($scope, $http, recordService) {
            recordService.loadRecord(1, 123).then(function (record) {
                $scope.record = record;
            });
        }]);
