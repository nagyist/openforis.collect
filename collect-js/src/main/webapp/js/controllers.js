var collectApp = angular.module('collectApp', []).

    controller('RecordCtrl', ['$scope', '$http', 'recordService',
        function ($scope, $http, recordService) {
            recordService.loadRecord(123).then(function (record) {
                $scope.record = record;
                console.log("loaded record", $scope.record);
            });
        }]).

    directive('renderNode', ['recordService', function (recordService) {
        return {
            restrict: 'E',
            scope: {
                node: '='
            },
            template: "<div ng-include src=\"'partials/' + node.type + '.html'\"></div>",
            link: function ($scope, element, attrs) {
                $scope.attributeUpdated = function (attribute) {
                    recordService.updateAttribute({
                        id: attribute.id,
                        value: attribute.value
                    });
                }
            }
        };
    }]).

    directive('ngModelOnblur', function () {
        return {
            restrict: 'A',
            require: 'ngModel',
            priority: 1,
            link: function (scope, elm, attr, ngModelCtrl) {
                if (attr.type === 'radio' || attr.type === 'checkbox') return;

                elm.unbind('input').unbind('keydown').unbind('change');
                elm.bind('blur', function () {
                    scope.$apply(function () {
                        if (elm.val().length == 0 && ngModelCtrl.$viewValue === undefined)
                            return;
                        ngModelCtrl.$setViewValue(elm.val());
                    });
                });
            }
        };
    });