
ngModules.service('denpyo', [ '$rootScope', '$filter',
// モデルの操作
function($scope, $filter) {
	
	this.createMeisai = function() {
		return {
			shohinCode : '',
			hanbaiNumber : 0,
			hanbaiTanka : 0
		};
	};
	
	this.getSubtotal = function (meisai) {
		return meisai.hanbaiNumber * meisai.hanbaiTanka;
	};
	
	this.getTotalAmount = function (meisaiList) {
		var total = 0;
		for (var m of meisaiList) {
			total += this.getSubtotal(m);
		}
		return total;
	}

} ])
// main controller
.controller('MainController', [ '$scope', '$http', 'denpyo', 'common', 'meisai',
// HDRと明細
function($scope, $http, denpyoService, common, meisaiService) {

	// validate header
	var validateHeader = function (callback) {
		$http({
			method : 'POST',
			url : '/u05g002/validateHeader',
			responseType : 'text',
			params : {
				kokyakuCode : $scope.kokyakuCode,
				denpyoNumber : $scope.denpyoNumber,
				uriageDate : $scope.uriageDate,
				hanbaiKubun : $scope.hanbaiKubun,
			}
		}).then(function successCallback(response) {
			hideErrorMessage();
			callback();
		}, function errorCallback(response) {
			showErroeMessage(response.data.message);
		});
	}

	// validate meisai
	var validateMeisai = function (meisai, callback) {
		var meisai = {
			"meisai[0].shohinCode" : meisai.shohinCode,
			"meisai[0].hanbaiNumber" : meisai.hanbaiNumber,
			"meisai[0].hanbaiTanka" : meisai.hanbaiTanka
		}
		
		$http({
			method : 'POST',
			url : '/u05g002/validateMeisai',
			responseType : 'text',
			params : Object.assign({
				kokyakuCode : $scope.kokyakuCode,
				denpyoNumber : $scope.denpyoNumber,
				uriageDate : $scope.uriageDate,
				hanbaiKubun : $scope.hanbaiKubun,
				}, meisai)
		}).then(function successCallback(response) {
			hideErrorMessage();
			callback();
		}, function errorCallback(response) {
			showErroeMessage(response.data.message);
		});
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

		crud({
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

		crud({
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
		if(!!$scope.isKeijoZumi == true) {
			if(!confirm("売上伝票は計上済みです。変更を行いますか？")) {
				return;
			}
		}
		submitForm("/u05g002/updateForm", "uriageDenpyo");
	};

	// 削除
	$scope.delete = function() {
		crud({
			url : "/u05g002/delete",
			formId : "uriageDenpyo",
			redirect : {
				url : "/u00g001"
			}
		});
	};
	
	$scope.initialize();
} ]);

