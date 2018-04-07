ngModules.service('seikyu', [ '$rootScope', '$filter', '$httpw',
// モデルの操作・取得
function($scope, $filter, $httpw) {
	/**
	 * 請求リスト取得.
	 */
	this.getList = function(params, callback) {
		// do
		$httpw.post("/u07g001/getList", {
			kokyakuCode : params.kokyakuCode,
		}, callback);
	};
} ])
// controllers
.controller('MainController', [ '$scope', '$http', 'seikyu', 'common', 'meisai',
// main
function($scope, $http, seikyuService, common, meisaiService) {
	// 請求リスト取得
	$scope.getList = function(kokyakuCode) {
		// callback
		var callback = function(model) {
			var seikyuList = model.seikyuList;
			$scope.seikyuList = seikyuList;
			$scope.kokyakuName = model.kokyakuName;
		};
		// 全取得
		seikyuService.getList({
			kokyakuCode : $scope.kokyakuCode,
		}, callback);
	};
	// 請求取得
	$scope.get = function(kokyakuCode, seikyuDate) {
	};
} ]);
