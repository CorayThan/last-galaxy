'use strict';


// Declare app level module which depends on filters, and services
angular.module('appLastGalaxy', [
	'ngRoute',
	'LocalStorageModule',
	'appLastGalaxy.filters',
	'appLastGalaxy.factories',
	'appLastGalaxy.directives',
	'appLastGalaxy.controllers',
	'appLastGalaxy.values'
]).config(['$routeProvider', 'localStorageServiceProvider', function($routeProvider, localStorageServiceProvider) {
	//setup "routes" here, which will let us navigate between our partials
	$routeProvider.when('/welcome', {templateUrl: 'partials/welcome-partial.html', controller: 'WelcomeCtrl'});
	$routeProvider.when('/about', {templateUrl: 'partials/about-partial.html'});
	$routeProvider.when('/house', {templateUrl: 'partials/house-partial.html', controller: 'HouseCtrl'});
	$routeProvider.when('/create-house', {templateUrl: 'partials/create-house-partial.html', controller: 'HouseCtrl'});
	$routeProvider.otherwise({redirectTo: '/welcome'});

	localStorageServiceProvider.setPrefix('appLastGalaxy');
}]);