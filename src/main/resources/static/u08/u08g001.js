ngModules.service('nyukin', [ '$rootScope', '$filter', '$httpw',
// モデルの操作・取得
function($scope, $filter, $httpw) {
	/**
	 * 入金リスト取得.
	 */
	this.search = function(params, callback) {
		// do
		$httpw.post("/u08g001/search", {
			kokyakuCode : params.kokyakuCode,
			bushoCode: params.bushoCode,
			minNyukinDate: params.minNyukinDate,
			maxNyukinDate: params.maxNyukinDate,
			minKingaku: params.minKingaku,
			maxKingaku: params.maxKingaku,
			includeKeshikomiDone: params.includeKeshikomiDone,
			tantoShainCode: params.tantoShainCode,
			nyukinHoho: params.nyukinHoho,
		}, callback);
	};
} ])
// controllers
.controller('MainController', [ '$scope', '$window', '$http', 'nyukin', 'common', 'meisai',
// main
function($scope, $window, $http, nyukinService, common, meisaiService) {
	// 入金方法
	$scope.nyukinHoho = [];
	// 集金の場合担当社員が入力可能
	$scope.selectNyukinHoho = function () {
		$scope.selectShukin = $scope.nyukinHoho.includes("00");
	};
	// 入金リスト取得
	$scope.search = function() {
		// callback
		var callback = function(model) {
			$scope.nyukinList = model.nyukinList;
		};
		// 全取得
		nyukinService.search({
			kokyakuCode : $scope.kokyakuCode,
			bushoCode: $scope.bushoCode,
			minNyukinDate: $scope.minNyukinDate,
			maxNyukinDate: $scope.maxNyukinDate,
			minKingaku: $scope.minKingaku,
			maxKingaku: $scope.maxKingaku,
			includeKeshikomiDone: $scope.includeKeshikomiDone,
			tantoShainCode: $scope.tantoShainCode,
			nyukinHoho : $scope.nyukinHoho,
		}, callback);
	};
	// 入金取得
	$scope.get = function(nyukinId) {
		$window.open("/u08g002/refer?nyukinId=" + nyukinId);
	};
	// 消込
	$scope.keshikomi = function(nyukinId) {
		$window.open("/u08g003/refer?nyukinId=" + nyukinId);
	};
} ]);
