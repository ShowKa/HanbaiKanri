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
			var zaikoList = model.zaikoList;
			$scope.zaikoList = zaikoList;
			$scope.bushoNameForCaption = model.bushoName;
			$scope.eigyoDateForCaption = $scope.date;
			$scope.$apply();
			if (zaikoList.length > 0) {
				$scope.get(zaikoList[0].code);
			} else {
				clearShohinIdoList();
			}
		};
		// request
		_.simpleRequest("/u11g001/getAll", "searchForm", successCallback);
	};
	// 部署商品在庫取得(1つだけ)
	$scope.get = function(shohinCode) {
		var successCallback = function(response) {
			var model = response.data.model;
			$scope.shohinIdoList = model.shohinIdoList;
			$scope.kurikoshiZaiko = model.kurikoshiZaiko;
			$scope.shohinNameForCaption = model.shohinName;
		}
		$http({
			method : "POST",
			url : "/u11g001/get",
			params : {
				bushoCode : $scope.bushoCode,
				date : $scope.date,
				shohinCode : shohinCode,
			}
		}).then(successCallback);
	};
	// 商品移動クリア
	var clearShohinIdoList = function() {
		$scope.shohinIdoList = {};
		$scope.kurikoshiZaiko = null;
		$scope.shohinNameForCaption = "";
		$scope.$apply();
	};
} ]);
