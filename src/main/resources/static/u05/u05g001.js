ngModules.service('uriage', [ '$rootScope', '$filter', '$httpw',
// モデルの操作・取得
function($scope, $filter, $httpw) {
	/**
	 * 売上リスト取得.
	 */
	this.search = function(params, callback) {
		// do
		$httpw.post("/u05g001/search", {
			kokyakuCode : params.kokyakuCode,
			from : params.from,
			to : params.to,
			onlyUrikake : params.onlyUrikake,
		}, callback);
	};
} ])
// controllers
.controller('MainController', [ '$scope', '$window', '$http', 'uriage', 'common', 'meisai',
// main
function($scope, $window, $http, uriageService, common, meisaiService) {
	// 売上リスト取得
	$scope.search = function() {
		// callback
		var callback = function(model) {
			var uriageList = model.uriageList;
			$scope.uriageList = uriageList;
		};
		// 全取得
		uriageService.search({
			kokyakuCode : $scope.kokyakuCode,
			from : $scope.from,
			to : $scope.to,
			onlyUrikake : $scope.onlyUrikake,
		}, callback);
	};
	// 売上取得
	$scope.get = function(kokyakuCode, denpyoNumber) {
		$window.open("/u05g002/refer?kokyakuCode=" + kokyakuCode + "&denpyoNumber=" + denpyoNumber, "_blank");
	};
} ]);

$(document).ready(function() {
	var $labels = $("[ioc-for=kokyakuCode]");
	$labels.each(function() {
		var $label = $(this);
		var $target = $("#" + $label.attr("ioc-for"));
		$target.on("change", function() {
			var $t = $(this);
			$label.text("");
			var $form = $("<form>");
			$form.appendInput("code", $t.val());
			var callback = function(model) {
				var exists = model.exists;
				if (exists == true) {
					var code = model.code;
					var name = model.name;
					var display = "(" + code + ":" + name + ")";
					$label.text(display);
				}
			};
			_.get("/info/getKokyaku", $form, callback);
		});
		$target.on("focusin", function() {
			$label.hide();
		});
		$target.on("focusout", function() {
			$label.show();
		});
	}); 
});