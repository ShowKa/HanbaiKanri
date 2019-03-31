var ngModules = angular.module('App', []);

//services
ngModules.service('$httpw', [ '$rootScope', '$http', '$filter',
	// $http wrapper
	function($scope, $http, $filter) {
	// エラーメッセージ表示
	var errorCallback = function(response) {
		_.showErroeMessage(response.data.message);
	};
	// post and redirect
	this.postAndRedirect = function(url, params, redirectUrl) {
		var callback = function(model) {
			var $form = $("<form>");
			$form.hide();
			if (model.form && model.form.successMessage) {
				$form.appendInput("successMessage", model.form.successMessage);
			}
			$form.attr("action", redirectUrl);
			$form.attr("method", "get");
			$form.submit();
		};
		this.post(url, params, callback);
	};
	// post
	this.post = function(url, params, successCallback) {
		// FIXME test code for -> List<U08G003FormMeisai> meisai;
		var _params = {};
		for(var key in params) {
			var value = params[key];
			if(Array.isArray(value)) {
				_params[key] = [];
				var count = 0;
				for (var i = 0; i < value.length; i++){
					var v = value[i];
					if (v == undefined) {
						continue;
					}
					var type = typeof v;
					switch(type) {
					case "undefined":
						continue;
					case "number":
					case "string":
						_params[key][count++] = v;
						break;
					default:
						for (var p in v) {
							_params[key +　"[" + i + "]." + p] = v[p];
						} 
					delete _params[key];
					break;
					}
				}
			} else {
				_params[key] = params[key];
			}
		}
		$http({
			method : "POST",
			url : url,
			params : _params,
		}).then(function(response) {
			var model = response.data.model;
			successCallback(model);
			// show success message if exists
			if (model.form && model.form.successMessage) {
				_.showSuccessMessage(model.form.successMessage);
			}
			// hide error message
			_.hideErrorMessage();
			// update mode
			if (model.mode) {
				$scope.mode = model.mode;
			}
		}, errorCallback);
	};
} ]);

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
		meisai.editDone = function() {
			this.editing = false;
			this.updated();
		}

		if(!meisai.editMode) {
			meisai.editMode = "added";
		}
		var tmp = {
				isNewRegistered: function () {
					return this.editMode == 'newRegistered';
				},
				isUpdated: function() {
					return this.editMode == "updated"
				},
				updated : function() {
					switch(this.editMode) {
					case "added":
						this.editMode = "newRegistered";
						break;
					case "notUpdated":
					case "deleted" :
						this.editMode = "updated";
						break;
					case "newRegistered" : 
						console.log("this is new-registered meisai, cannot set editMode updated");
						break;
					}
				},
				isDeleted: function() {
					return this.editMode == "deleted"
				},
				delete: function() {
					this.editMode = "deleted";
				},
				isAdded: function() {
					return this.editMode == "added";
				}
		};

		Object.assign(meisai, tmp);
	};

	this.convertCollectionToMeisai = function(meisaiList, edit) {
		for (var l of meisaiList) {
			this.convertToMeisai(l, edit);
		}
	};

	this.check = function (meisaiList) {
		if (meisaiList.length === 0) {
			_.showErroeMessage("明細を追加してください。");
			return false;
		}
		for ( var l of meisaiList) {
			if (l.editing == true) {
				_.showErroeMessage("編集中の明細が残っています。");
				return false;
			}
		}
		return true;
	};

	// 任意の明細行をリストモデルから取り除く
	this.remove = function(target, meisaiList) {
		var index = meisaiList.indexOf(target);
		if (index !== -1) {
			meisaiList.splice(index, 1);
		}
	};
}]);

//directive
ngModules.directive('ngInitFromValue', function() {
	return {
		restrict : 'A',
		link : function postLink(scope, element, attrs, ctrl) {
			var ngModel = attrs["ngModel"];
			if (ngModel) {

				var value = element.val();
				var type = attrs["type"];

				switch(type) {
				case "checkbox" :
					if(value == "true") {
						scope[ngModel] = true;
					} else {
						scope[ngModel] = false;
					}
					break;
				default :
					scope[ngModel] = value;
				break;
				}

			}
		}
	};
});

ngModules.directive('ngInitIterator', function() {
	return {
		restrict : 'E',
		priority : 1001,
		compile : function(tElement, tAttrs) { //Compile関数
			return {
				pre : function preLink(scope, element, attrs, ctrl) { //Link関数その1
					var match = attrs["for"].match(/^\s*([\s\S]+?)\s+in\s+([\s\S]+?)(?:\s+as\s+([\s\S]+?))?(?:\s+track\s+by\s+([\s\S]+?))?\s*$/);
					var lhs = match[1];
					var rhs = match[2];
					scope[rhs] = scope[rhs] ? scope[rhs] : [];

					var ngModels = element.find("[ng-model]");
					var line = {};
					for(nm of ngModels) {
						var $nm = $(nm);
						var attr = $nm.attr("ng-model");
						attr = attr.split(".");
						if (attr[0] == lhs) {
							var target = line;
							for (var i = 1; i < attr.length; i++) {
								if (i != attr.length -1) {
									target[attr[i]] = target[attr[i]] ? target[attr[i]] : {};
									target = target[attr[i]];
								} else {
									var v = $nm.val();
									if ($nm.attr("type") == "number") {
										v = parseInt(v);
									}
									target[attr[i]] = v;
								}
							}
						}
					}
					scope[rhs].push(line);
					element.remove();
				},
				post : function postLink(scope, element, attrs, ctrl) { //Link関数その2
				}
			};
		}
	};
});