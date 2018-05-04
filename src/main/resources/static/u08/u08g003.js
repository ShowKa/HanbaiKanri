ngModules.service('keshikomi', [ '$rootScope', '$filter', '$httpw',
// モデルの操作・取得
function($scope, $filter, $httpw) {
	/**
	 * 消込リスト取得.
	 */
	this.getList = function(params, callback) {
		// do
		$httpw.post("/u08g003/getList", {
			kokyakuCode : params.kokyakuCode,
		}, callback);
	};
} ])
// controllers
.controller('MainController', [ '$scope', '$window', '$http', 'keshikomi', 'common', 'meisai',
// main
function($scope, $window, $http, keshikomiService, common, meisaiService) {
	// 消込リスト取得
	$scope.getList = function(kokyakuCode) {
		// callback
		var callback = function(model) {
			var keshikomiList = model.keshikomiList;
			$scope.keshikomiList = keshikomiList;
			$scope.kokyakuName = model.kokyakuName;
		};
		// 全取得
		keshikomiService.getList({
			kokyakuCode : $scope.kokyakuCode,
		}, callback);
	};
	// 消込取得
	$scope.get = function(keshikomiDate) {
		var kokyakuCode = $scope.kokyakuCode;
		$window.open("/u08g002/refer?kokyakuCode=" + kokyakuCode + "&keshikomiDate=" + keshikomiDate, "_blank");
	};
} ]);
