// 拡張
function commonExtend($scope, common) {
	// 継承
	angular.extend(commonExtend.prototype, common);
	// 訂正登録モード追加
	var registerTeisei = "registerTeisei";
	$scope.isRegisterTeisei = function() {
		return registerTeisei === $scope.mode;
	};
	this.toRegisterTeisei = function() {
		common.setMode(registerTeisei);
	};
	// 訂正更新モード追加
	var updateTeisei = "updateTeisei";
	$scope.isUpdateTeisei = function() {
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
	$scope.initialize = function() {
		$scope.header = {
			editing : true,
			edit : function(editable) {
				this.editing = editable;
			},
		};
		if (!$scope.meisaiList) {
			$scope.meisaiList = [];
		}
	};
	/**
	 * 参照モード
	 */
	$scope.toRead = function() {
		common.toRead();
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
	$scope.toRegisterTeiseai = function() {
	};
	/**
	 * 訂正更新モード
	 */
	$scope.toUpdateTeisei = function() {
	};
	/**
	 * 商品入荷取得.
	 */
	$scope.get = function(nyukaId) {
		var callback = function() {
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
		$httpw.post("/u11g003/validateHeader", {}, callback);
	};
	/**
	 * 明細追加
	 */
	$scope.addMeisai = function() {
		$scope.meisaiList.push(createMeisai());
	};
	/**
	 * 明細編集完了.
	 */
	$scope.done = function(meisai) {
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
		meisai.edit();
	}
	/**
	 * 明細削除.
	 */
	$scope.removeMeisai = function(target) {
		meisaiService.remove(target, $scope.meisaiList);
	};
	/**
	 * 新規登録.
	 */
	$scope.register = function() {
		// check
		if (!meisaiService.check($scope.meisaiList)) {
			return;
		}
		var callback = function() {
			// 参照モードへ
			common.toRead();
		};
		$httpw.post("/u11g003/register", {}, callback);
	};
	/**
	 * 更新.
	 */
	$scope.update = function() {
		var callback = function () {
		};
		$httpw.post("/u11g003/update", {}, callback);
	};
	/**
	 * 削除.
	 */
	$scope.delete = function() {
		var callback = function () {
		};
		$httpw.post("/u11g003/delete", {}, callback);
	};
	/**
	 * 訂正登録.
	 */
	$scope.registerTeisei = function() {
		if (!meisaiService.check($scope.meisaiList)) {
			return;
		}
		var callback = function() {
			common.toRead();
		};
		$httpw.post("/u11g003/registerTeisei", {}, callback);
	};
	/**
	 * 訂正更新.
	 */
	$scope.updateTeisei = function() {
		var callback = function () {
			common.toRead();
		};
		$httpw.post("/u11g003/updateTeisei", {}, callback);
	};
	/**
	 * 訂正削除.
	 */
	$scope.deleteTeisei = function() {
		var callback = function () {
			common.toRead();
		};
		$httpw.post("/u11g003/deleteTeisei", {}, callback);
	}
	// 内部関数
	/**
	 * 明細作成
	 */
	var createMeisai = function() {
		var m = {
			shohinCode : '',
		};
		meisaiService.convertToMeisai(m);
		m.edit();
		return m;
	};
	// 初期処理
	if ($scope.isReadMode()) {
		var nyukaId = "入荷IDを設定してください。";
		$scope.get(nyukaId);
	} else if ($scope.isRegisterMode()) {
		// 初期化
		$scope.initialize();
	}
} ]);
