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

			$scope.fillInputBox = function(description, code) {
				$scope.airport = description;
				$scope.hidethis = true;
				$scope.origincode = code;
				$scope.ticketPriceCalculationFunction();
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

			$scope.destfillInputBox = function(description, code) {
				$scope.destairport = description;
				$scope.desthidethis = true;
				$scope.destcode = code;
				$scope.ticketPriceCalculationFunction();
			};
			
			
			/** Ticket price calculation **/
			$scope.ticketPriceCalculationFunction = function() {
				
				if ( $scope.destcode != null & $scope.origincode != null ) {
					$scope.hideofferprice = false;
					$http.get('../calculateOfferPrice/' + $scope.origincode + '/' + $scope.destcode ).then(
						function succes(response) {
							$scope.offeredprice = response.data;
						});
				} else {
					$scope.hideofferprice = true;
				}

			};
			
		});
