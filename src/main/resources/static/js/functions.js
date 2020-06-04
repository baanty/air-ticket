angular.module("app", []).controller(
		"HelloWorldCtrl",
		function($scope, $http) {
			$scope.message = "Choose Airports To get Fare Offer";

			$scope.getAirportData = function(string, $scope, $http) {
				$http.get('../findAirportWithUserEntry/' + string).then(
						function succes(response) {
							$scope.allairports = response.data;
						});
			};

			$scope.cname = function(string) {
				$scope.getAirportData(string, $scope, $http);
				$scope.hidethis = false;

			};

			$scope.fillInputBox = function(string) {
				$scope.airport = string;
				$scope.hidethis = true;
			};
			
			
			
			/**  Destination Airport**/
			
			$scope.getDestAirportData = function(string, $scope, $http) {
				$http.get('../findAirportWithUserEntry/' + string).then(
						function succes(response) {
							$scope.destallairports = response.data;
						});
			};

			$scope.destcname = function(string) {
				$scope.getDestAirportData(string, $scope, $http);
				$scope.desthidethis = false;

			};

			$scope.destfillInputBox = function(string) {
				$scope.destairport = string;
				$scope.desthidethis = true;
			};

		});
