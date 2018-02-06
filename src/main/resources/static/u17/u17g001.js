ngModules.controller('MainController', [ '$scope', '$http', 'common', 'meisai',
// main controller
function($scope, $http, common, meisaiService) {
	// 部署リスト
	$scope.getBushoList = function() {
		var successCallback = function(response) {
			$scope.bushoList = response.data.model.bushoList;
			$scope.$apply();
		};
		$http({
			method : "POST",
			url : "/u17g001/getBushoList",
		}).then(successCallback);
	};
	// 締め
	$scope.close = function(bushoCode, eigyoDate) {
		var successCallback = function(response) {
		};
		$http({
			method : "POST",
			url : "/u17g001/close",
			params : {
				bushoCode : bushoCode,
				eigyoDate : eigyoDate,
			},
		}).then(successCallback);
	};
	// execute after loading
	$scope.getBushoList();
} ]);