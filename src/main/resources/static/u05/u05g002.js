
// services
ngModules.service('meisai', [ '$rootScope', '$filter',
// 明細
function($scope, $filster) {
	
	this.convertToMeisai = function(meisai, edit) {
		if (meisai.editing) {
			return;
		}
		edit = edit != null ? edit :false;
		meisai.editing = edit;
		meisai.edit = function (e) {
			e = e != null ? e : true;
			this.editing = e;
		};
	};
	
	this.convertCollectionToMeisai = function(lines, edit) {
		for (var l of lines) {
			this.convertToMeisai(l, edit);
		}
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
}]);
	
ngModules.service('denpyo', [ '$rootScope', '$filter', 'meisai',
// モデルの操作
function($scope, $filter, meisai) {
	
	this.checkLines = function(lines) {
		return meisai.checkLines(lines);
	}

	this.createLine = function() {
		var l = {
			shohinCode : '',
			hanbaiNumber : 0,
			hanbaiTanka : 0
		};
		meisai.convertToMeisai(l, true);
		return l;
	};
	
	this.convertCollectionToMeisai = function(lines) {
		meisai.convertCollectionToMeisai(lines);
	};
	
	this.getSubtotal = function (line) {
		return line.hanbaiNumber * line.hanbaiTanka;
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
			line.edit(false);
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

	// 更新
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
		line.edit(true);
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
		$scope.$watchCollection('lines', function(newLines, oldLines) {
			denpyo.convertCollectionToMeisai(newLines);
		});
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

