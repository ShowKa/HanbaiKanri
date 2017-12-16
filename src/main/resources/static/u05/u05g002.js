// services
ngModules.service('denpyo', [ '$rootScope', '$filter',
// モデルの操作
function($scope, $filter) {

	this.createLine = function() {
		return {
			shohinCode : '',
			hanbaiNumber : 0,
			hanbaiTanka : 0,
			editing : true
		};
	};
	
	this.getSubtotal = function (line) {
		return line.hanbaiNumber * line.hanbaiTanka;
	};
	
	this.checkLines = function (lines) {
		if (lines.length === 0) {
			showErroeMessage("明細を追加してください。");
			return false;
		}
		for ( var l of lines) {
			if (l.editing == true) {
				showErroeMessage("編集中の明細が残っています。");
				return false;
			}
		}
		return true;
	};
	
} ])
// main controller
.controller('MainController', [ '$scope', '$http', 'denpyo', 'common',
// HDRと明細
function($scope, $http, denpyo, common) {
		
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

	var validateMeisai = function (line, callback) {
		var meisai = {
			"meisai[0].shohinCode" : line.shohinCode,
			"meisai[0].hanbaiNumber" : line.hanbaiNumber,
			"meisai[0].hanbaiTanka" : line.hanbaiTanka
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
	$scope.done = function(line) {
		validateMeisai(line, function () {
			line.editing = false;
		});
	};

	// 新規登録
	$scope.register = function() {

		// check
		if (!denpyo.checkLines($scope.lines)) {
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

	// 新規登録
	$scope.update = function() {

		// check
		if (!denpyo.checkLines($scope.lines)) {
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
	$scope.addLine = function() {
		if($scope.header.editing == true && $scope.isRegisterMode()) {
			validateHeader(function() {
				$scope.lines.push(denpyo.createLine());
				$scope.header.edit(false);
			});
		} else {
			$scope.lines.push(denpyo.createLine());
		}
		
	};

	// 編集開始
	$scope.edit = function(line) {
		line.editing = true;
	}

	// 任意の明細行をリストモデルから取り除く
	$scope.removeLine = function(target) {
		var lines = $scope.lines;
		var index = lines.indexOf(target);
		if (index !== -1) {
			lines.splice(index, 1);
		}
	};

	// 引数から小計を計算して返す
	$scope.getSubtotal = function(line) {
		return denpyo.getSubtotal(line);
	};

	// 明細小計の合計
	$scope.getTotalAmount = function(lines) {
		var totalAmount = 0;
		angular.forEach(lines, function(line) {
			totalAmount += $scope.getSubtotal(line);
		});
		return totalAmount;
	};

	// 初期化
	$scope.initialize = function() {
		$scope.header = {
			editing : true,
			edit : function(editable) {
				this.editing = editable;
			},
		};
		if(!$scope.lines){
			$scope.lines = [];
		}
	};
	
	// 更新画面へ
	$scope.updateForm = function() {
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

