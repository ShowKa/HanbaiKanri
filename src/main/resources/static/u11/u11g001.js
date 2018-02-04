ngModules.service('zaiko', [ '$rootScope', '$filter',
// モデルの操作
function($scope, $filter) {

} ])
// main controller
.controller('MainController', [ '$scope', '$http', 'zaiko', 'common', 'meisai',
// コントロール
function($scope, $http, zaikoService, common, meisaiService) {
	// 部署商品在庫全取得
	$scope.getAll = function() {
		// callback
		var successCallback = function(model) {
			$scope.zaikoList = model.zaikoList;
			$scope.$apply();
		};
		// request
		_.simpleRequest("/u11g001/getAll", "searchForm", successCallback);
	};
} ]);
