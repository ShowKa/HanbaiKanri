ngModules.service('denpyo', [ '$rootScope', '$filter', '$httpw',
//	service
	function($scope, $filter, $httpw) {
	/**
	 * 明細の小計取得.
	 */
	this.getSubtotal = function (meisai) {
		return meisai.hanbaiNumber * meisai.hanbaiTanka;
	};
	/**
	 * 合計金額取得.
	 */
	this.getTotalAmount = function (meisaiList) {
		var total = 0;
		for (var m of meisaiList) {
			total += this.getSubtotal(m);
		}
		return total;
	};
} ])
//main controller
.controller('MainController', [ '$scope', '$httpw', 'denpyo', 'common', 'meisai',
	function($scope, $httpw, service, common, meisaiService) {
	/**
	 * 伝票整合性検証.
	 */
	var validateHeader = function (callback) {
		var param = {
				kokyakuCode : $scope.kokyakuCode,
				denpyoNumber : $scope.denpyoNumber,
				uriageDate : $scope.uriageDate,
				hanbaiKubun : $scope.hanbaiKubun,
		};
		$httpw.post("/u05g002/validateHeader", param, callback);
	}
	/**
	 * 伝票明細整合性検証.
	 */
	var validateMeisai = function (meisai, callback) {
		if(!meisaiService.errorIfDuplicate($scope.meisaiList)) {
			return;
		}
		$httpw.post("/u05g002/validateMeisai", {
			kokyakuCode : $scope.kokyakuCode,
			denpyoNumber : $scope.denpyoNumber,
			uriageDate : $scope.uriageDate,
			hanbaiKubun : $scope.hanbaiKubun,
			"meisaiList[0].shohinCode" : meisai.shohinCode,
			"meisaiList[0].hanbaiNumber" : meisai.hanbaiNumber,
			"meisaiList[0].hanbaiTanka" : meisai.hanbaiTanka,
		}, callback);
	}
	// 初期化
	$scope.init = function(kokyakuCode, denpyoNumber){
		$scope.header = {
				editing : false,
				edit : function(editable) {
					this.editing = editable;
				}
		};
		if ($scope.isReadMode()) {
			$scope.get(kokyakuCode, denpyoNumber);
		} else if ($scope.isRegisterMode()) {
			$scope.header.editing = true;
			if(!$scope.meisaiList){
				$scope.meisaiList = new MeisaiList();
			}
		}
	};
	// 取得
	$scope.get = function(kokyakuCode, denpyoNumber) {
		var callback = function(model) {
			// set
			$scope.kokyakuCode = model.kokyakuCode;
			$scope.denpyoNumber = model.denpyoNumber;
			$scope.hanbaiKubun = model.hanbaiKubun;
			$scope.uriageDate = model.uriageDate;
			$scope.recordId = model.recordId;
			$scope.version = model.version;
			$scope.urikakeVersion = model.urikakeVersion;
			$scope.isKeijoZumi = model.isKeijoZumi;
			$scope.isSeikyuZumi = model.isSeikyuZumi;
			// 明細
			$scope.meisaiList = new MeisaiList();
			for (var i = 0; i < model.meisaiList.length; i++) {
				var _meisai = model.meisaiList[i];
				var meisai = $scope.createMeisai(_meisai);
				$scope.meisaiList.push(meisai);
			}
		};
		$httpw.post("/u05g002/get", {
			kokyakuCode : kokyakuCode,
			denpyoNumber : denpyoNumber,
		}, callback);
	}
	// 明細入力完了
	$scope.done = function(meisai) {
		validateMeisai(meisai, function () {
			meisai.editDone();
		});
	};
	// 新規登録
	$scope.register = function() {
		// callback
		var callback = function(model) {
			// 参照モードへ
			common.toRead();
			$scope.get(model.form.kokyakuCode, model.form.denpyoNumber);
		};
		// param
		var param = {
				kokyakuCode : $scope.kokyakuCode,
				denpyoNumber : $scope.denpyoNumber,
				uriageDate : $scope.uriageDate,
				hanbaiKubun : $scope.hanbaiKubun,
		};
		$scope.meisaiList.merge("meisaiList", param);
		$httpw.post("/u05g002/register", param, callback);
	};
	// 更新
	$scope.update = function() {
		if (!meisaiService.check($scope.meisaiList)) {
			return;
		}
		// callback
		var callback = function(model) {
			common.toRead();
			$scope.get(model.form.kokyakuCode, model.form.denpyoNumber);
		};
		// param
		var param = {
				kokyakuCode : $scope.kokyakuCode,
				denpyoNumber : $scope.denpyoNumber,
				uriageDate : $scope.uriageDate,
				hanbaiKubun : $scope.hanbaiKubun,
				recordId : $scope.recordId,
				version : $scope.version,
				urikakeVersion : $scope.urikakeVersion,
		};
		$scope.meisaiList.merge("meisaiList", param);
		$httpw.post("/u05g002/update", param, callback);
	};
	// リストモデルに新しい明細行を追加する
	$scope.addMeisai = function() {
		if($scope.header.editing == true && $scope.isRegisterMode()) {
			validateHeader(function() {
				var meisai = $scope.createMeisai();
				meisai.edit();
				$scope.meisaiList.push(meisai);
				$scope.header.edit(false);
			});
		} else {
			var meisai = $scope.createMeisai();
			meisai.edit();
			$scope.meisaiList.push(meisai);
		}
	};
	// 履歴取得
	$scope.getRireki = function() {
		var callback = function(model) {
			$scope.meisaiList = new MeisaiList();
			var rirekiList = model.rirekiList;
			for (var rireki of rirekiList) {
				var meisai = {};
				meisai.meisaiNumber = rireki.meisaiNumber;
				meisai.shohinCode = rireki.shohinCode;
				meisai.hanbaiNumber = rireki.hanbaiNumber;
				meisai.hanbaiTanka = rireki.hanbaiTanka;
				$scope.meisaiList.push($scope.createMeisai(meisai));
			}
		};
		var param = {
				recordId: $scope.recordId,
		}
		$httpw.get("/u05g002/getRireki", param, callback);
	};
	// 編集開始
	$scope.edit = function(meisai) {
		meisai.edit();
	};
	// 任意の明細行をリストモデルから取り除く
	$scope.removeMeisai = function(target) {
		$scope.meisaiList.delete(target);
	};
	// 引数から小計を計算して返す
	$scope.getSubtotal = function(meisai) {
		return service.getSubtotal(meisai);
	};
	// 明細小計の合計
	$scope.getTotalAmount = function(meisaiList) {
		return service.getTotalAmount(meisaiList);
	};
	// 更新画面へ
	$scope.toUpdateMode = function() {	
		if(!!$scope.isSeikyuZumi == true) {
			if(!confirm("売上伝票は請求済みです。変更を行いますか？")) {
				return;
			}
		} else if(!!$scope.isKeijoZumi == true) {
			if(!confirm("売上伝票は計上済みです。変更を行いますか？")) {
				return;
			}
		}
		common.toUpdate();
	};
	// 削除
	$scope.delete = function() {
		var callback = function(model) {
			_.submitForm("/u05g001/refer", "uriageDenpyo");
		};
		var param = {
				kokyakuCode : $scope.kokyakuCode,
				denpyoNumber : $scope.denpyoNumber,
				recordId: $scope.recordId,
				version: $scope.version,
				urikakeVersion: $scope.urikakeVersion,
		}
		$httpw.post("/u05g002/delete", param, callback);
	};
	// キャンセル
	$scope.cancel = function() {
		if(!!$scope.isSeikyuZumi == true) {
			if(!confirm("売上伝票は請求済みです。キャンセルを行いますか？（一度キャンセルを行うと、以降更新はできません。）")) {
				return;
			}
		} else {
			if(!confirm("キャンセルを行いますか？（一度キャンセルを行うと、以降更新はできません。）")) {
				return;
			}
		}
		var callback = function(model) {
			common.toRead();
			$scope.get(model.form.kokyakuCode, model.form.denpyoNumber);
		};
		var param = {
				kokyakuCode : $scope.kokyakuCode,
				denpyoNumber : $scope.denpyoNumber,
				recordId: $scope.recordId,
				version: $scope.version,
				urikakeVersion: $scope.urikakeVersion,
		}
		$httpw.post("/u05g002/cancel", param, callback);
	};
	/**
	 * 明細作成.
	 */
	$scope.createMeisai = Meisai.define([{
		name : "shohinCode",
		uniqueKey : true
	}, {
		name : "meisaiNumber",
		uniqueKey : false
	}, {
		name : "hanbaiNumber",
		uniqueKey : false
	}, {
		name : "hanbaiTanka",
		uniqueKey : false
	}]);
} ]);
