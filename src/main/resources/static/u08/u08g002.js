ngModules.service('shukin', [ '$rootScope', '$filter', '$httpw',
// モデルの操作・取得
function($scope, $filter, $httpw) {
	/**
	 * 登録.
	 */
	this.register = function(params, callback) {
		// do
		$httpw.post("/u08g002/register", {
			kokyakuCode : params.kokyakuCode,
			bushoCode : params.bushoCode,
			denpyoNumber : params.denpyoNumber,
			tantoShainCode : params.tantoShainCode,
			kingaku : params.kingaku,
			version : params.version,
		}, callback);
	};
	/**
	 * 更新.
	 */
	this.update = function(params, callback) {
		// do
		$httpw.post("/u08g002/update", {
			tantoShainCode : params.tantoShainCode,
			kingaku : params.kingaku,
			nyukinId : params.nyukinId,
			version : params.version,
		}, callback);
	};
	/**
	 * 削除.
	 */
	this.delete = function(params, callback) {
		// do
		$httpw.postAndRedirect("/u08g002/delete", {
			nyukinId : params.nyukinId,
			version : params.version,
		}, "/u08g002/registerForm");
	};
	/**
	 * 取得.
	 */
	this.get = function(params, callback) {
		// do
		$httpw.post("/u08g002/get", {
			nyukinId : params.nyukinId,
		}, callback);
	};
} ])
// controllers
.controller('MainController', [ '$scope', 'shukin', 'common', 'meisai',
// main
function($scope, shukinService, common, meisaiService) {
	// 変数初期設定
	function init() {
		// 入金ID
		$scope.nyukinId = $("#nyukinId").val();
	}
	// 登録
	$scope.register = function() {
		var callback = function(model) {
			var form = model.form;
			$scope.nyukinId = form.nyukinId;
			// 画面再描画
			$scope.get();
			common.toRead();
		};
		// 登録Service
		shukinService.register({
			kokyakuCode : $scope.kokyakuCode,
			bushoCode : $scope.bushoCode,
			denpyoNumber : $scope.denpyoNumber,
			tantoShainCode : $scope.tantoShainCode,
			kingaku : $scope.kingaku,
			version : $scope.version,
		}, callback);
	};
	// 更新モードへ
	$scope.updateForm = function() {
		common.toUpdate();
	};
	// 更新
	$scope.update = function() {
		var callback = function(model) {
			// 再取得
			$scope.get();
			common.toRead();
		};
		// 更新
		shukinService.update({
			tantoShainCode : $scope.tantoShainCode,
			kingaku : $scope.kingaku,
			nyukinId : $scope.nyukinId,
			version : $scope.version,
		}, callback);
	};
	// 削除
	$scope.delete = function() {
		var callback = function(model) {
			
		};
		shukinService.delete({
			nyukinId : $scope.nyukinId,
			version : $scope.version,
		}, callback);
	};
	// 取得
	$scope.get = function() {
		// callback
		var callback = function(model) {
			var form = model.form;
			$scope.kokyakuCode = form.kokyakuCode;
			$scope.bushoCode = form.bushoCode;
			$scope.denpyoNumber = form.denpyoNumber;
			$scope.nyukinDate = form.nyukinDate;
			$scope.tantoShainCode = form.tantoShainCode;
			$scope.kingaku = form.kingaku;
			$scope.version = form.version;
			$scope.mode = "read";
		};
		// 全取得
		shukinService.get({
			nyukinId : $scope.nyukinId,
		}, callback);
	};
	// 初期制御
	init();
	if (isReadMode()) {
		$scope.get();
	}
} ]);
