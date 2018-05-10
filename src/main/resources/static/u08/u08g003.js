ngModules.service('keshikomi', [ '$rootScope', '$filter', '$httpw',
// モデルの操作・取得
function($scope, $filter, $httpw) {
	/**
	 * 更新form.
	 */
	this.updateForm = function(params, callback) {
		// do
		$httpw.post("/u08g003/updateForm", {
			nyukinId : params.nyukinId,
		}, callback);
	};
	/**
	 * update
	 */
	this.update = function(params, callback) {
		// do
		$httpw.post("/u08g003/update", {
			nyukinId : params.nyukinId,
			version : params.version,
			meisai : params.meisai,
		}, callback);
	};
	/**
	 * 消込取得.
	 */
	this.get = function(params, callback) {
		// do
		$httpw.post("/u08g003/get", {
			nyukinId : params.nyukinId,
		}, callback);
	};
} ])
// controllers
.controller('MainController', [ '$scope', 'keshikomi', 'common', 'meisai',
// main
function($scope, keshikomiService, common, meisaiService) {
	// 変数初期設定
	function init() {
		// 入金ID
		$scope.nyukinId = $("#nyukinId").val();
		// 編集不可
		$scope.editable = false;
	}
	// 更新モードへ
	$scope.updateForm = function() {
		var callback = function(model) {
			var keshikomiList = model.keshikomiList;
			var newKeshikomiList = model.newKeshikomiList;
			keshikomiList = keshikomiList.concat(newKeshikomiList);
			$scope.keshikomiList = keshikomiList;
		};
		// 全取得
		keshikomiService.updateForm({
			nyukinId : $scope.nyukinId,
		}, callback);
		// 編集可
		$scope.editable = true;
	};
	// 更新
	$scope.update = function() {
		var meisai = [];
		$scope.keshikomiList.forEach(function (k){
			var m = {};
			m.urikakeId = k.urikakeId;
			m.urikakeVersion = k.urikakeVersion;
			m.keshikomiId = k.keshikomiId;
			m.kingaku = k.kingaku;
			m.version = k.version;
			meisai.push(m);
		});
		var callback = function(model) {
			// 編集不可
			$scope.editable = false;
			// 再取得
			$scope.get();
		};
		// 全取得
		keshikomiService.update({
			nyukinId : $scope.nyukinId,
			version : $scope.version,
			meisai : meisai,
		}, callback);
	};
	// 入金消込取得
	$scope.get = function() {
		// callback
		var callback = function(model) {
			$scope.kokyakuName = model.kokyakuName;
			$scope.bushoName = model.bushoName;
			$scope.nyukinDate = model.nyukinDate;
			$scope.nyukinHoho = model.nyukinHoho;
			$scope.nyukinKingaku = model.nyukinKingaku;
			$scope.mikeshikomi = model.mikeshikomi;
			$scope.version = model.form.version;
			$scope.keshikomiList = model.keshikomiList;
		};
		// 全取得
		keshikomiService.get({
			nyukinId : $scope.nyukinId,
		}, callback);
	};
	// get
	init();
	$scope.get();
} ]);
