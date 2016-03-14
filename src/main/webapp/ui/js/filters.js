'use strict';

/* Filters */

angular.module('appLastGalaxy.filters', [])

.filter('prettyDate', [function() {
	return function(millis) {
		return new Date(millis).toLocaleString();
	};
}]);