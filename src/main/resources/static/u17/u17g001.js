ngModules.controller('MainController', [ '$scope', '$http', 'common', 'meisai',
// main controller
function($scope, $http, common, meisaiService) {
	// エラーメッセージ表示
	var errorCallback = function(response) {
		showErroeMessage(response.data.message);
	};
	// 部署リスト
	$scope.getBushoList = function() {
		var successCallback = function(response) {
			$scope.bushoList = response.data.model.bushoList;
		};
		$http({
			method : "POST",
			url : "/u17g001/getBushoList",
		}).then(successCallback, errorCallback);
	};
	// 締め
	$scope.close = function(bushoCode, eigyoDate) {
		if (!confirm("締め処理を開始します。\n" 
				+ "部署コード : "+bushoCode + "\n"
				+ "営業日 : "+eigyoDate)) {
			return;
		}
		var successCallback = function(response) {
			showSuccessMessage(response.data.model.form.successMessage);
			$scope.getBushoList();
		};
		$http({
			method : "POST",
			url : "/u17g001/close",
			params : {
				bushoCode : bushoCode,
				eigyoDate : eigyoDate,
			},
		}).then(successCallback, errorCallback);
	};
	// execute after loading
	$scope.getBushoList();
} ]);