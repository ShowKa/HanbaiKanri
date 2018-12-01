
ngModules.service('denpyo', [ '$rootScope', '$filter', '$httpw',
// モデルの操作
function($scope, $filter, $httpw) {
	/**
	 * 明細作成.
	 */
	this.createMeisai = function() {
		return {
			shohinCode : '',
			hanbaiNumber : 0,
			hanbaiTanka : 0
		};
	};
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
	/**
	 * 伝票整合性検証.
	 */
	this.validateHeader = function(params, callback) {
		$httpw.post("/u05g002/validateHeader",{
			kokyakuCode : params.kokyakuCode,
			denpyoNumber : params.denpyoNumber,
			uriageDate : params.uriageDate,
			hanbaiKubun : params.hanbaiKubun,
		}, callback);
	};
	/**
	 * 明細整合性検証.
	 */
	this.validateMeisai = function(header, meisai, callback) {
		$httpw.post("/u05g002/validateMeisai", {
			kokyakuCode : header.kokyakuCode,
			denpyoNumber : header.denpyoNumber,
			uriageDate : header.uriageDate,
			hanbaiKubun : header.hanbaiKubun,
			"meisai[0].shohinCode" : meisai.shohinCode,
			"meisai[0].hanbaiNumber" : meisai.hanbaiNumber,
			"meisai[0].hanbaiTanka" : meisai.hanbaiTanka,
		}, callback);
	};
} ])
// main controller
.controller('MainController', [ '$scope', '$http', 'denpyo', 'common', 'meisai',
// HDRと明細
function($scope, $http, denpyoService, common, meisaiService) {
	// validate header
	var validateHeader = function (callback) {
		denpyoService.validateHeader({
			kokyakuCode : $scope.kokyakuCode,
			denpyoNumber : $scope.denpyoNumber,
			uriageDate : $scope.uriageDate,
			hanbaiKubun : $scope.hanbaiKubun,
		}, callback);
	}
	// validate 明細
	var validateMeisai = function (meisai, callback) {
		var header = {
			kokyakuCode : $scope.kokyakuCode,
			denpyoNumber : $scope.denpyoNumber,
			uriageDate : $scope.uriageDate,
			hanbaiKubun : $scope.hanbaiKubun,
		};
		denpyoService.validateMeisai(header, meisai, callback);
	}
	// 明細入力完了
	$scope.done = function(meisai) {
		validateMeisai(meisai, function () {
			meisai.editDone();
		});
	};
	// 新規登録
	$scope.register = function() {
		// check
		if (!meisaiService.check($scope.meisaiList)) {
			return;
		}
		_.crud({
			url : "/u05g002/register",
			formId : "uriageDenpyo",
			redirect : {
				url : "/u05g002/refer",
				param : {
					kokyakuCode : $scope.kokyakuCode,
					denpyoNumber : $scope.denpyoNumber
				}
			}
		});
	};
	// 更新
	$scope.update = function() {
		// check
		if (!meisaiService.check($scope.meisaiList)) {
			return;
		}
		_.crud({
			url : "/u05g002/update",
			formId : "uriageDenpyo",
			redirect : {
				url : "/u05g002/refer",
				param : {
					kokyakuCode : $scope.kokyakuCode,
					denpyoNumber : $scope.denpyoNumber
				}
			}
		});
	};
	// リストモデルに新しい明細行を追加する
	$scope.addMeisai = function() {
		if($scope.header.editing == true && $scope.isRegisterMode()) {
			validateHeader(function() {
				$scope.meisaiList.push(createNewMeisai());
				$scope.header.edit(false);
			});
		} else {
			$scope.meisaiList.push(createNewMeisai());
		}
	};
	// 履歴取得
	$scope.getRireki = function() {
		var callback = function(model) {
			$scope.meisaiList = [];
			var rirekiList = model.rirekiList;
			for (var rireki of rirekiList) {
				var meisai = createNewMeisai();
				meisai.meisaiNumber = rireki.meisaiNumber;
				meisai.shohinCode = rireki.shohinCode;
				meisai.hanbaiNumber = rireki.hanbaiNumber;
				meisai.hanbaiTanka = rireki.hanbaiTanka;
				meisai.editing = false;
				$scope.meisaiList.push(meisai);
			}
			$scope.$apply();
		};
		_.simpleRequest("/u05g002/getRireki", "uriageDenpyo", callback);
		return;
	};
	// 新規明細作成
	var createNewMeisai = function () {
		var m = denpyoService.createMeisai();
		meisaiService.convertToMeisai(m);
		m.edit();
		return m;
	}
	// 編集開始
	$scope.edit = function(meisai) {
		meisai.edit();
	};
	// 任意の明細行をリストモデルから取り除く
	$scope.removeMeisai = function(target) {
		meisaiService.remove(target, $scope.meisaiList);
	};
	// 引数から小計を計算して返す
	$scope.getSubtotal = function(meisai) {
		return denpyoService.getSubtotal(meisai);
	};
	// 明細小計の合計
	$scope.getTotalAmount = function(meisaiList) {
		return denpyoService.getTotalAmount(meisaiList);
	};
	// 初期化
	$scope.initialize = function() {
		$scope.header = {
			editing : true,
			edit : function(editable) {
				this.editing = editable;
			},
		};
		if(!$scope.meisaiList){
			$scope.meisaiList = [];
		}
		$scope.$watchCollection('meisaiList', function(newMeisaiList, oldMeisaiList) {
			meisaiService.convertCollectionToMeisai(newMeisaiList);
		});
	};
	// 更新画面へ
	$scope.updateForm = function() {	
		if(!!$scope.isSeikyuZumi == true) {
			if(!confirm("売上伝票は請求済みです。変更を行いますか？")) {
				return;
			}
		} else if(!!$scope.isKeijoZumi == true) {
			if(!confirm("売上伝票は計上済みです。変更を行いますか？")) {
				return;
			}
		}
		_.submitForm("/u05g002/updateForm", "uriageDenpyo");
	};
	// 削除
	$scope.delete = function() {
		_.crud({
			url : "/u05g002/delete",
			formId : "uriageDenpyo",
			redirect : {
				url : "/u00g001"
			}
		});
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
		_.crud({
			url : "/u05g002/cancel",
			formId : "uriageDenpyo",
			redirect : {
				url : "/u05g002/refer",
				param : {
					kokyakuCode : $scope.kokyakuCode,
					denpyoNumber : $scope.denpyoNumber
				}
			}
		});
	};
	$scope.initialize();
} ]);
