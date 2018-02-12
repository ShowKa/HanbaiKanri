ngModules.service('close', [ '$rootScope', '$filter', '$httpw',
// モデルの操作・取得
function($scope, $filter, $httpw) {
	/**
	 * 部署リスト取得.
	 */
	this.getBushoList = function(callback) {
		$httpw.post("/u17g001/getBushoList", {}, callback);
	};
	/**
	 * 締め処理.
	 */
	this.close = function(params, callback) {
		$httpw.post("/u17g001/close", {
			bushoCode : params.bushoCode,
			eigyoDate : params.eigyoDate,
		}, callback);
	}
} ])
// controllers
.controller('MainController', [ '$scope', '$http', 'close',
// main
function($scope, $http, closeService) {
	// 部署リスト取得
	$scope.getBushoList = function() {
		var callback = function(model) {
			$scope.bushoList = model.bushoList;
		};
		closeService.getBushoList(callback);
	};
	// 締め
	$scope.close = function(bushoCode, eigyoDate) {
		if (!confirm("締め処理を開始します。\n" + "部署コード : " + bushoCode + "\n" + "営業日 : " + eigyoDate)) {
			return;
		}
		var callback = function(model) {
			$scope.getBushoList();
		};
		closeService.close({
			bushoCode : bushoCode,
			eigyoDate : eigyoDate,
		}, callback);
	};
	// execute after loading
	$scope.getBushoList();
} ]);