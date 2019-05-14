//拡張
function commonExtend($scope, common) {
	// 継承
	angular.extend(commonExtend.prototype, common);
	// 訂正登録モード追加
	var registerTeisei = "registerTeisei";
	$scope.isRegisterTeiseiMode = function() {
		return registerTeisei === $scope.mode;
	};
	this.toRegisterTeisei = function() {
		common.setMode(registerTeisei);
	};
	// 訂正更新モード追加
	var updateTeisei = "updateTeisei";
	$scope.isUpdateTeiseiMode = function() {
		return updateTeisei === $scope.mode;
	};
	this.toUpdateTeisei = function() {
		common.setMode(updateTeisei);
	};
}
// modules
ngModules
// add extended modules
.service('commonExtend', ['$rootScope', 'common', commonExtend])
// controller
.controller('MainController', [ '$scope', '$httpw', 'commonExtend', 'meisai',
// main
	function($scope, $httpw, common, meisaiService) {
	// scope
	/**
	 * 初期化.
	 */
	$scope.init = function(nyukaId){
		if ($scope.isReadMode()) {
			$scope.get(nyukaId);
		} else if ($scope.isRegisterMode()) {
			$scope.header = {
				editing : true,
				edit : function(editable) {
					this.editing = editable;
				}
			};
			if (!$scope.meisaiList_Nyuka) {
				$scope.meisaiList_Nyuka = new MeisaiList();
			}
		}
	};
	/**
	 * 参照モード
	 */
	$scope.toRead = function() {
		common.toRead();
		$scope.get($scope.nyukaId);
	};
	/**
	 * 更新モード
	 */
	$scope.toUpdate = function() {
		common.toUpdate();
	};
	/**
	 * 訂正モード
	 */
	$scope.toRegisterTeisei = function() {
		common.toRegisterTeisei();
	};
	/**
	 * 訂正更新モード
	 */
	$scope.toUpdateTeisei = function() {
		common.toUpdateTeisei();
	};
	/**
	 * 商品入荷取得.
	 */
	$scope.get = function(nyukaId) {
		var callback = function(model) {
			// set
			$scope.bushoCode = model.bushoCode;
			$scope.nyukaSakiCode = model.nyukaSakiCode;
			$scope.nyukaDate = model.nyukaDate;
			$scope.meisaiList_NyukaTeisei = model.meisaiList_NyukaTeisei;
			$scope.nyukaId = model.nyukaId;
			$scope.version = model.version;
			$scope.target = model.target;
			$scope.teiseiDone = model.teiseiDone;
			$scope.display = "nyuka";
			// 明細
			$scope.meisaiList_Nyuka = new MeisaiList();
			for (var i = 0; i < model.meisaiList_Nyuka.length; i++) {
				var _meisai = model.meisaiList_Nyuka[i];
				var meisai = createMeisai(_meisai);
				$scope.meisaiList_Nyuka.push(meisai);
			}
		};
		$httpw.post("/u11g003/get", {
			nyukaId : nyukaId,
		}, callback);
	};
	/**
	 * 明細リスト入力開始.
	 */
	$scope.startEditingMeisaiList = function() {
		// validateHeader
		var callback = function() {
			$scope.header.edit(false);
			$scope.addMeisai();
		};
		$httpw.post("/u11g003/validateHeader", {
			bushoCode:$scope.bushoCode,
			nyukaSakiCode:$scope.nyukaSakiCode
		}, callback);
	};
	/**
	 * 明細追加
	 */
	$scope.addMeisai = function() {
		var meisai = createMeisai();
		meisai.edit();
		$scope.meisaiList_Nyuka.push(meisai);
	};
	/**
	 * 明細編集完了.
	 */
	$scope.done = function(meisai) {
		if(!meisaiService.errorIfDuplicate($scope.meisaiList_Nyuka)) {
			return;
		}
		var callback = function() {
			meisai.editDone();
		}
		$httpw.post("/u11g003/validateMeisai", {
			// ヘッダ
			bushoCode : $scope.bushoCode,
			// 明細
			"meisai[0].shohinCode" : meisai.shohinCode,
		}, callback);
	};
	/**
	 * 明細編集.
	 */
	$scope.edit = function(meisai) {
		if(!meisaiService.errorIfEditing($scope.meisaiList_Nyuka)) {
			return;
		}
		meisai.edit();
	}
	/**
	 * 明細削除.
	 */
	$scope.removeMeisai = function(target) {
		$scope.meisaiList_Nyuka.delete(target);
	};
	/**
	 * 新規登録.
	 */
	$scope.register = function() {
		// check
		if (!meisaiService.check($scope.meisaiList_Nyuka)) {
			return;
		}
		// callback
		var callback = function(model) {
			// 参照モードへ
			common.toRead();
			$scope.get(model.nyukaId);
		};
		// param
		var param = {
			bushoCode: $scope.bushoCode,
			nyukaSakiCode: $scope.nyukaSakiCode
		};
		$scope.meisaiList_Nyuka.merge("meisai", param);
		$httpw.post("/u11g003/register", param, callback);
	};
	/**
	 * 更新.
	 */
	$scope.update = function() {
		// check
		if (!meisaiService.check($scope.meisaiList_Nyuka)) {
			return;
		}
		var callback = function (model) {
			// 参照モードへ
			common.toRead();
			$scope.get($scope.nyukaId);
		};
		var param = {
			nyukaId : $scope.nyukaId,
			version: $scope.version
		};
		$scope.meisaiList_Nyuka.merge("meisai", param);
		$httpw.post("/u11g003/update", param, callback);
	};
	/**
	 * 削除.
	 */
	$scope.delete = function() {
		var callback = function(model) {
			_.submitForm("/u11g001/refer", "nyuka");
		};
		var param = {
			nyukaId: $scope.nyukaId,
			version: $scope.version,
		};
		$httpw.post("/u11g003/delete", param, callback);
	};
	/**
	 * 訂正登録.
	 */
	$scope.registerTeisei = function() {
		if (!meisaiService.check($scope.meisaiList_Nyuka)) {
			return;
		}
		var callback = function (model) {
			// 参照モードへ
			common.toRead();
			$scope.get($scope.nyukaId);
		};
		var param = {
			nyukaId : $scope.nyukaId,
			version: $scope.version
		};
		$scope.meisaiList_Nyuka.merge("meisai", param);
		$httpw.post("/u11g003/registerTeisei", param, callback);
	};
	/**
	 * 訂正更新.
	 */
	$scope.updateTeisei = function() {
		// check
		if (!meisaiService.check($scope.meisaiList_Nyuka)) {
			return;
		}
		var callback = function (model) {
			// 参照モードへ
			common.toRead();
			$scope.get($scope.nyukaId);
		};
		var param = {
			nyukaId : $scope.nyukaId,
			version: $scope.version
		};
		$scope.meisaiList_Nyuka.merge("meisai", param);
		$httpw.post("/u11g003/updateTeisei", param, callback);
	};
	/**
	 * 訂正削除.
	 */
	$scope.deleteTeisei = function() {
		var callback = function(model) {
			// 参照モードへ
			common.toRead();
			$scope.get($scope.nyukaId);
		};
		var param = {
			nyukaId: $scope.nyukaId,
			version: $scope.version,
		};
		$httpw.post("/u11g003/deleteTeisei", param, callback);
	};
	/**
	 * 対象切替.
	 */
	$scope.toggleTeiseiRireki = function() {
		var nt = $scope.display == "nyukaTeiseiRireki";
		$scope.display = nt ? "nyuka" : "nyukaTeiseiRireki";
	};
	/**
	 * 明細定義
	 */
	var createMeisai = Meisai.define([{
		name: "shohinCode",
		uniqueKey: true
	}, {
		name: "nyukaSu",
		uniqueKey: false
	}]);
} ]);
