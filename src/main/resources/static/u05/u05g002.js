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
function update() {
}
$(document).ready(function() {
	swithElementActivationByMode();
});

$(document).ready(function() {
	// double click
	$("#uriageMeisai").on("dblclick", "tbody tr", function() {
		var $row = $(this);
		var index = $row.attr("index");
		$("#uriageMeisai" + index).modal("show");
	});

	// save
	$(".uriageMeisaiModal").on("shown.bs.modal", function() {
		var $this = $(this);
		var index = $this.attr("index");
		var $meisaiNumber = $this.findByName(selector("meisaiNumber", index));
		var $shohinCode = $this.findByName(selector("shohinCode", index));
		var $hanbaiNumber = $this.findByName(selector("hanbaiNumber", index));
		var $hanbaiTanka = $this.findByName(selector("hanbaiTanka", index));
		$meisaiNumber.data("previous", $meisaiNumber.val());
		$shohinCode.data("previous", $shohinCode.val());
		$hanbaiNumber.data("previous", $hanbaiNumber.val());
		$hanbaiTanka.data("previous", $hanbaiTanka.val());
	});

	// registered
	$(".uriageMeisaiModal").on("success", function() {
		// get value
		var $this = $(this);
		var index = $this.attr("index");
		var $meisaiNumber = $this.findByName(selector("meisaiNumber", index));
		var $shohinCode = $this.findByName(selector("shohinCode", index));
		var $hanbaiNumber = $this.findByName(selector("hanbaiNumber", index));
		var $hanbaiTanka = $this.findByName(selector("hanbaiTanka", index));

		// table
		var $table = $("#uriageMeisai");
		var $row = $table.find("[index=" + index + "]");
		$row.find("[column=meisaiNumber]").text($meisaiNumber.val());
		$row.find("[column=shohinCode]").text($shohinCode.val());
		$row.find("[column=hanbaiNumber]").text($hanbaiNumber.val());
		$row.find("[column=hanbaiTanka]").text($hanbaiTanka.val());
	});

});

function registerMeisai(index) {
	var $this = $("#uriageMeisai" + index);
	var selector = "meisai" + selectorEscape("[" + index + "].")
			+ "meisaiNumber";
	var $meisaiNumber = $this.findByName(selector);

	// validate
	validate({
		url : "/u05g002/validateMeisai",
		form : $("#urageDenpyo"),
		detail : {
			name : "meisai",
			index : index
		}
	});

	// trigger event
	$this.trigger("success");
	$this.modal("hide");
}

function dismiss(index) {
	var $this = $("#uriageMeisai" + index);

	var $meisaiNumber = $this.findByName(selector("meisaiNumber", index));
	var $shohinCode = $this.findByName(selector("shohinCode", index));
	var $hanbaiNumber = $this.findByName(selector("hanbaiNumber", index));
	var $hanbaiTanka = $this.findByName(selector("hanbaiTanka", index));

	$meisaiNumber.val($meisaiNumber.data("previous"));
	$shohinCode.val($shohinCode.data("previous"));
	$hanbaiNumber.val($hanbaiNumber.data("previous"));
	$hanbaiTanka.val($hanbaiTanka.data("previous"));

	$this.modal("hide");
}

function selector(name, index) {
	return "meisai" + selectorEscape("[" + index + "].") + name;
}

function selectorEscape(val) {
	return val.replace(/[ !"#$%&'()*+,.\/:;<=>?@\[\\\]^`{|}~]/g, '\\$&');
}

angular.module('App', [])
// services
.service('meisai', [ '$rootScope', '$filter', function($scope, $filter) {

} ])
// creation controller
.controller('CreationController', [ '$scope', '$http', function($scope, $http) {
	function createLine() {
		return {
			shohinCode : '',
			hanbaiNumber : 0,
			hanbaiTanka : 0,
			editing : true
		};
	}

	// リストモデルに新しい明細行を追加する
	$scope.addLine = function() {
		var newLine = createLine();
		$scope.lines.push(newLine);
	};

	// リストモデルを初期化する
	$scope.initialize = function() {
		$scope.lines = [ createLine() ];
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
		return line.hanbaiNumber * line.hanbaiTanka;
	};

	// 明細小計の合計
	$scope.getTotalAmount = function(lines) {
		var totalAmount = 0;
		angular.forEach(lines, function(line) {
			totalAmount += $scope.getSubtotal(line);
		});
		return totalAmount;
	};
} ])
// main controller
.controller('MainController', [ '$scope', '$http', function($scope, $http) {
	
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
			line.editing = false;
		}, function errorCallback(response) {
			showErroeMessage(response.data.message);
		});
	};
} ]);