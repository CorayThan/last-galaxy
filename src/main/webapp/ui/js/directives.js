'use strict';

/* Directives */

angular.module('appLastGalaxy.directives', [])

	.directive('lgInfoMessage', [function () {
		return {
			restrict: 'E',
			scope: {
				type: '=',
				message: '='
			},
			templateUrl: 'directive-templates/info-message.html'
		};
	}])

	.directive('lgFocus', [function () {
		return {
			link: function (scope, element) {
				element[0].focus();
			}
		};
	}])

;