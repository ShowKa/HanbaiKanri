function register() {
	var $form = $('#urageDenpyo');
	var param = {
		url : "/u05g002/register",
		formId : "urageDenpyo",
		redirect : {
			url : "/u05g002/refer",
			param : {
				kokyakuCode : $form.findByName("kokyakuCode").val(),
				denpyoNumber : $form.findByName("denpyoNumber").val()
			}
		}
	};
	crud(param);
}

$(document).ready(function() {
	swithElementActivationByMode();
});

angular.module('App', [])
// services
.service('denpyo', [ '$rootScope', '$filter',
// モデルの操作
function($scope, $filter) {

	this.createLine = function() {
		return {
			shohinCode : '',
			hanbaiNumber : 0,
			hanbaiTanka : 0,
			editing : true,
			edit : function(editable) {
				this.editing = editable;
			},
			getSubtotal : function() {
				return this.hanbaiNumber * this.hanbaiTanka;
			}
		};
	}
} ])
// main controller
.controller('MainController', [ '$scope', '$http', 'denpyo',
// HDRと明細
function($scope, $http, denpyo) {

	// 明細入力完了
	$scope.done = function(line) {
		$http({
			method : 'POST',
			url : '/u05g002/validateMeisai',
			responseType : 'text',
			params : {
				kokyakuCode : $scope.kokyakuCode,
				denpyoNumber : $scope.denpyoNumber,
				uriageDate : $scope.uriageDate,
				hanbaiKubun : $scope.hanbaiKubun,
				"meisai[0].shohinCode" : line.shohinCode,
				"meisai[0].hanbaiNumber" : line.hanbaiNumber,
				"meisai[0].hanbaiTanka" : line.hanbaiTanka
			}
		}).then(function successCallback(response) {
			hideErrorMessage();
			line.edit(false);
		}, function errorCallback(response) {
			showErroeMessage(response.data.message);
		});
	};

	// 新規登録
	$scope.register = function() {

		// check
		var lines = $scope.lines;
		if (lines.length === 0) {
			showErroeMessage("明細を追加してください。");
			return;
		}
		for ( var l of lines) {
			console.log(l);
			if (l.editing == true) {
				showErroeMessage("編集中の明細が残っています。");
				return;
			}
		}

		var line = lines[0];
		$http({
			method : 'POST',
			url : '/u05g002/register',
			responseType : 'text',
			params : {
				kokyakuCode : $scope.kokyakuCode,
				denpyoNumber : $scope.denpyoNumber,
				uriageDate : $scope.uriageDate,
				hanbaiKubun : $scope.hanbaiKubun,
				"meisai[0].shohinCode" : line.shohinCode,
				"meisai[0].hanbaiNumber" : line.hanbaiNumber,
				"meisai[0].hanbaiTanka" : line.hanbaiTanka
			}
		}).then(function successCallback(response) {
			alert("登録成功");
		}, function errorCallback(response) {
			showErroeMessage(response.data.message);
		});
	};

	// 初期化
	$scope.initialize = function() {
		$scope.lines = [];
	};

	// リストモデルに新しい明細行を追加する
	$scope.addLine = function() {
		$scope.lines.push(denpyo.createLine());
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
		return line.getSubtotal();
	};

	// 明細小計の合計
	$scope.getTotalAmount = function(lines) {
		var totalAmount = 0;
		angular.forEach(lines, function(line) {
			totalAmount += $scope.getSubtotal(line);
		});
		return totalAmount;
	};

	$scope.initialize();
} ]);
