angular.module('collectApp').
    directive('renderNode', ['recordService', function (recordService) {
        return {
            restrict: 'E',
            scope: {
                node: '='
            },
            template: "<div ng-include src=\"'partials/' + node.type + '.html'\"></div>",
            link: function ($scope) {
                $scope.attributeUpdated = function (attribute) {
                    recordService.updateAttribute({
                        id: attribute.id,
                        value: attribute.value
                    });
                }
            }
        };
    }]);
