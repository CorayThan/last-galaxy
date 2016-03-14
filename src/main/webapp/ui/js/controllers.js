'use strict';

/* Controllers */

angular.module('appLastGalaxy.controllers', [])

	.controller('HouseCtrl', ['$scope', '$rootScope', 'houseFactory', 'userFactory', function ($scope, $rootScope, houseFactory, userFactory) {

		$rootScope.lastGalaxyGlobalDisplayMessage = null;

		houseFactory.getHouse().success(function (house) {
			$scope.houseName = house.name;
		});

		$scope.createHouse = function () {
			houseFactory.createHouse($scope.house)
				.success(function (house) {
					alert("You made a house: " + JSON.stringify(house));
				}).error(function (error) {
					$scope.status = "There was an error when saving the config.";
				});
		};

	}])

	.controller('WelcomeCtrl', ['$scope', '$location', 'userFactory', function ($scope, $location, userFactory) {

		$scope.login = true;
		$scope.user = {};
		$scope.switchView = function() {
			$scope.login = !$scope.login;
			if ($scope.login) {
				$("#loginEmail").focus();
			} else {
				$("#username").focus();
			}
		};

		$scope.loginUser = function (user) {
			userFactory.loginUser(user)
				.success(function (user) {
					$location.path("house");
				}).error(function (error) {
					$scope.loginFailed = true;
				});
		};
		$scope.createUser = function () {
			userFactory.createUser($scope.user)
				.success(function () {
					$scope.loginUser($scope.user).success(function () {
						alert("You created a new account!");
					});

				}).error(function () {
					alert("uh oh");
				});
		};

	}])

	.controller('IndexCtrl', ['$scope', '$location', 'userFactory', function ($scope, $location, userFactory) {

		$scope.menuActive = false;

		$scope.isCurrentPage = function (page) {
			var currentPage = $location.path();
			return page === currentPage.substring(1, currentPage.length);
		};

		$scope.menuItems = [];
		addMenuItem("creatingHouse", 'create-house', 'Setup House');
		addMenuItem("loggedIn", 'house', 'House');
		addMenuItem("loggedIn", 'galaxy', 'Galaxy');
		addMenuItem("loggedIn", '', 'Logout', userFactory.logout);
		addMenuItem("loggedOut", 'welcome', 'Welcome');
		addMenuItem("loggedOut", 'about', 'About');

		function addMenuItem(displayWhen, routeName, pageName, whenClicked) {
			var item = {displayWhen: displayWhen, routeName: routeName, pageName: pageName, whenClicked: whenClicked};
			$scope.menuItems.push(item);
			return item;
		}

		function flipIf(flipOn, displayWhenOrName) {
			for (var x = 0; x < $scope.menuItems.length; x++) {
				var item = $scope.menuItems[x];
				if (item.displayWhen === displayWhenOrName || item.routeName === displayWhenOrName) {
					item.display = flipOn;
				}
			}
		}
		flipIf(true, "loggedOut");
		$scope.$on('userUpdate', function (event, user) {
			for (var x = 0; x < $scope.menuItems.length; x++) {
				var item = $scope.menuItems[x];
				item.display = false;
			}
			if (user === null) {
				flipIf(true, "loggedOut");
			} else {
				flipIf(true, "loggedIn");
				if (user.house.houseCreation !== null) {
					flipIf(true, "creatingHouse");
					flipIf(false, "create-house");
				}
			}
		});

		$scope.user = userFactory.getUser();

	}])

;