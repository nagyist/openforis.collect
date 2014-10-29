angular.module('collectApp').
    directive('attribute', ['$timeout', function ($timeout) {
        return {
            restrict: 'A',
            require: 'ngModel',
            priority: 1,
            link: function (scope, element, attributes, ngModelCtrl) {
                if (attributes.type === 'radio' || attributes.type === 'checkbox') return;

                element.unbind('input').unbind('keydown').unbind('change');
                element.bind('blur', function () {
                    scope.$apply(function () {
                        if (element.val().length == 0 && ngModelCtrl.$viewValue === undefined)
                            return;
                        ngModelCtrl.$setViewValue(element.val());
                    });
                });

                scope.$watch(attributes.ngModel, function(val, oldVal) {
                    if (val === oldVal) return;
                    // apply class
                    element.addClass('highlight');

                    // auto remove after some delay
                    $timeout(function () {
                        element.removeClass('highlight');
                    }, 300);

                });
            }
        };
    }]);