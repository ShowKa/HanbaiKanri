ngModules.service('zaiko', [ '$rootScope', '$filter', '$httpw',
// モデルの操作・取得
function($scope, $filter, $httpw) {
	/**
	 * 部署商品在庫全取得.
	 */
	this.getAll = function(params, callback) {
		// do
		$httpw.post("/u11g001/getAll", {
			bushoCode : params.bushoCode,
			date : params.date,
		}, callback);
	};
	/**
	 * 商品在庫取得.
	 */
	this.get = function(params, callback) {
		// do
		$httpw.post("/u11g001/get", {
			bushoCode : params.bushoCode,
			date : params.date,
			shohinCode : params.shohinCode,
		}, callback);
	};
} ])
// controllers
.controller('MainController', [ '$scope', '$http', 'zaiko', 'common', 'meisai',
// main
function($scope, $http, zaikoService, common, meisaiService) {
	// 部署商品在庫全取得
	$scope.getAll = function() {
		// callback
		var callback = function(model) {
			var zaikoList = model.zaikoList;
			$scope.zaikoList = zaikoList;
			$scope.bushoNameForCaption = model.bushoName;
			$scope.eigyoDateForCaption = $scope.date;
			if (zaikoList.length > 0) {
				$scope.get(zaikoList[0].code);
			} else {
				clearShohinIdoList();
			}
		};
		// 全取得
		zaikoService.getAll({
			bushoCode : $scope.bushoCode,
			date : $scope.date,
		}, callback);
	};
	// 部署商品在庫取得(1つだけ)
	$scope.get = function(shohinCode) {
		// callback
		var callback = function(model) {
			$scope.shohinIdoList = model.shohinIdoList;
			$scope.kurikoshiZaiko = model.kurikoshiZaiko;
			$scope.shohinNameForCaption = model.shohinName;
		};
		// 取得
		zaikoService.get({
			bushoCode : $scope.bushoCode,
			date : $scope.date,
			shohinCode : shohinCode
		}, callback);
	};
	// 商品移動クリア
	var clearShohinIdoList = function() {
		$scope.shohinIdoList = {};
		$scope.kurikoshiZaiko = null;
		$scope.shohinNameForCaption = "";
	};
} ]);
