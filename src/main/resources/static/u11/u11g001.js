ngModules.service('zaiko', [ '$rootScope', '$filter',
// モデルの操作
function($scope, $filter) {

} ])
// main controller
.controller('MainController', [ '$scope', '$http', 'zaiko', 'common', 'meisai',
// コントロール
function($scope, $http, zaikoService, common, meisaiService) {
	// 部署商品在庫全取得
	$scope.getAll = function() {
		// callback
		var successCallback = function(model) {
			$scope.zaikoList = model.zaikoList;
			$scope.$apply();
		};
		// request
		_.simpleRequest("/u11g001/getAll", "searchForm", successCallback);
	};
	// 部署商品在庫取得(1つだけ)
	$scope.get = function(shohinCode) {
		$http({
			method : "POST",
			url : "/u11g001/get",
			params : {
				bushoCode : $scope.bushoCode,
				date : $scope.date,
				shohinCode : shohinCode,
			}
		}).then(function successCallback(response) {
			$scope.shohinIdoList = response.data.model.shohinIdoList;
		}, function errorCallback(response) {
			showErroeMessage(response.data.message);
		});
	};
} ]);
