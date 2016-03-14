'use strict';

var lastGalaxyUrl = "/last-galaxy/api/";

/* Factories */

angular.module('appLastGalaxy.factories', [])

	.factory('houseFactory', ['$http', function ($http) {
		var url = lastGalaxyUrl + "house/";
		var factory = {};
		factory.getHouse = function () {
			return $http.get(url);
		};
		return factory;
	}])

	.factory('userFactory', ['$http', '$rootScope', 'localStorageService', function ($http, $rootScope, localStorageService) {
		var url = lastGalaxyUrl + "user/";
		var factory = {};
		//var currentUser = null;
		var userKey = "currentUser";
		//TODO need to store current user in local storage, so we can return him without
		//using a promise (check local storage if current user is null)

		factory.getUser = function () {
//			if (currentUser) {
//				return currentUser;
//			} else {
				var userFromStorage = localStorageService.get(userKey);
				if (userFromStorage) {
					return userFromStorage;
				}
//			}
			//return currentUser;
		};
		factory.loginUser = function (user) {
			return $http.post(lastGalaxyUrl + "login?email=" + user.email + "&password=" + user.password).success(function (loggedInUser) {
				//currentUser = user;
				localStorageService.set(userKey, loggedInUser);
				$rootScope.$broadcast('userUpdate', loggedInUser);
			});
		};
		factory.createUser = function (user) {
			return $http.post(lastGalaxyUrl + "public/user/", user);
		};
		factory.logout = function () {
			return $http.post(lastGalaxyUrl + "logout").success(function () {
				$rootScope.$broadcast('userUpdate', null);
				localStorageService.remove(userKey);
			});
		};
		return factory;
	}])


;