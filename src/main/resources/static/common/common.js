var _ = {};

/**
 * 単純なAjaxリクエスト.
 */
_.get = function(url, form, successCallback, erroCallback) {
	var $form;
	var type = $.type(form);
	if (type == "string") {
		$form = $("#" + form);
	} else {
		$form = form;
	}
	var url = url + "?" + $form.serialize();
	$.ajax({
		type : "GET",
		url : url,
		dataType : "json",
		success : function(data, status, xhr) {
			if (successCallback) {
				successCallback(data.model);
			}
		},
		error : function(data, status, xhr) {
			if (erroCallback) {
				erroCallback(data.model);
			}
		}
	})
}

/**
 * 検索 & 検索結果のリスト構築.
 * 
 * @param url
 *            検索URL
 * @param formId
 *            formのid属性値
 * @param targetId
 *            検索結果のリストのid属性値
 */
_.searchAndBuildList = function(url, formId, targetId) {
	var paramUrl = url + "?" + $("#" + formId).serialize();
	$.ajax({
		type : "GET",
		url : paramUrl,
		dataType : "html",
		success : function(data, status, xhr) {
			$("#" + targetId).html(data);
		},
		error : function(data, status, xhr) {
			_.showErroeMessage(data.responseText);
		}
	});
}

/**
 * CRUD処理.
 * 
 * <pre>
 * ただし最初にAjaxでサーバーにアクセスし、処理が成功すればそのままリダイレクトする。
 * <br>
 * ValidateExceptionが発生した場合はリダイレクトせずにエラーメッセージを表示する。
 * </pre>
 * 
 * @param url
 *            CRUD処理のURL
 * @param formId
 *            formのid属性値
 * @param redirect.url
 *            リダイレクト先URL
 * @param redirect.param
 *            リダイレクト先に渡すパラメータ
 */
_.crud = function(param) {

	// set parameters
	var url = param.url;
	var formId = param.formId;
	var redirectUrl = param.redirect.url;
	var redirectParam = param.redirect.param;

	// set data from form
	var $form = $("#" + formId);
	var data = {};
	$.each($form.serializeArray(), function(_, kv) {
		data[kv.name] = kv.value;
	});

	// post
	$.ajax({
		type : "POST",
		url : url,
		dataType : "json",
		data : data,
		success : function(data, status, xhr) {
			var $form = $("<form>").hide();
			var model = data.model;
			if (model) {
				var form = model.form;
				if (form) {
					// リダイレクト先にメッセージを引き継ぐ
					if (form.successMessage) {
						$form.appendInput("successMessage", form.successMessage);
					}
					if (form.infoMessage) {
						$form.appendInput("infoMessage", form.infoMessage);
					}
					if (form.warningMessage) {
						$form.appendInput("warningMessage", form.warningMessage);
					}
					if (form.errorMessage) {
						$form.appendInput("errorMessage", form.errorMessage);
					}
				}
			}
			for ( var key in redirectParam) {
				$form.appendInput(key, redirectParam[key]);
			}
			$("body").append($form);
			$form.attr("action", redirectUrl).submit();
		},
		error : function(data, status, xhr) {
			if (data.status === 400) {
				_.showErroeMessage(data.responseJSON.message);
				return;
			} else if (data.status === 409) {
				_.showErroeMessage(data.responseJSON.message);
				return;
			}
			$("html").html(data.responseText);
		}
	});
}

/**
 * formをsubmitする.
 * 
 * @param url
 *            submit先のURL
 * @param formId
 *            formのid属性値
 */
_.submitForm = function(url, formId) {
	$("#" + formId).attr("action", url).submit();
}

/**
 * エラーメッセージ表示.
 * 
 * @param message
 *            メッセージ
 */
_.showErroeMessage = function(message) {
	var $message = $("#errorMessage");
	if ($message.length > 0) {
		$message.html(message).show();
	} else {
		var $new = $("<div>").attr("id", "errorMessage").addClass('alert alert-danger').html(message);
		$("#message-container").append($new);
	}
}

/**
 * 成功メッセージ表示.
 * 
 * @param message
 *            メッセージ
 */
_.showSuccessMessage = function(message) {
	var $message = $("#successMessage");
	if ($message.length > 0) {
		$message.html(message).show();
	} else {
		var $new = $("<div>").attr("id", "successMessage").addClass('alert alert-success').html(message);
		$("#message-container").append($new);
	}
}

/**
 * エラーメッセージを消す。
 */
_.hideErrorMessage = function() {
	$("#errorMessage").hide();
}

/**
 * 要素の活性化状態をモードで切替る。
 */
_.swithElementActivationByMode = function() {
	var $elements = $(".whenRead,.whenRegister,.whenUpdate");
	if (isReadMode()) {
		$elements.each(function(event) {
			var $this = $(this);
			if ($this.hasClass("whenRead")) {
				$this.activate();
			} else {
				$this.inactivate();
			}
		});
	} else if (isRegisterMode()) {
		$elements.each(function(event) {
			var $this = $(this);
			if ($this.hasClass("whenRegister")) {
				$this.activate();
			} else {
				$this.inactivate();
			}
		});
	} else if (isUpdateMode()) {
		$elements.each(function(event) {
			var $this = $(this);
			if ($this.hasClass("whenUpdate")) {
				$this.activate();
			} else {
				$this.inactivate();
			}
		});
	}
}

/**
 * 文字列操作
 */
_.replaceText = function(model, labelText) {
	var _text = labelText;
	// regular expression to parse labelText
	var reg = /\$\{[^\}]+\}/g;
	var matchedArray = labelText.match(reg);
	for ( var i in matchedArray) {
		var matched = matchedArray[i];
		var tKey = matched.substring(2, matched.length - 1);
		var value = model[tKey];
		if (!value) {
			_text = "";
			break;
		}
		_text = _text.replace(new RegExp("\\$\\{" + tKey + "\\}", "g"), value);
	}
	return _text;
}

/**
 * escape jquery's selector value
 * 
 * @param val
 * @returns
 */
function selectorEscape(val) {
	return val.replace(/[ !"#$%&'()*+,.\/:;<=>?@\[\\\]^`{|}~]/g, '\\$&');
}

/**
 * jauery拡張. form要素にinput要素を動的に追加するために使用する。
 */
(function($) {
	$.fn.appendInput = function(name, value, type) {
		type = type ? type : "hidden";
		var $input = $("<input>");
		$input.attr("name", name);
		$input.attr("type", type);
		$input.val(value);
		this.append($input);
		return this;
	};
})(jQuery);

/**
 * jauery拡張. name属性値を用いてform内要素を取得する。
 * 
 */
(function($) {
	$.fn.findByName = function(name) {
		return this.find("[name=" + name + "]");
	};
})(jQuery);

/**
 * jauery拡張. 要素の活性化
 */
(function($) {
	$.fn.activate = function() {
		if (this.is("input[type=button],button")) {
			this.prop("disabled", false);
		} else if (this.is("input")) {
			this.prop("readonly", false);
		} else if (this.is("select")) {
			this.find("option[disabled]").prop("disabled", false);
		}
		return this;
	};
})(jQuery);

/**
 * jauery拡張. 要素の非活性化
 */
(function($) {
	$.fn.inactivate = function() {
		if (this.is("input[type=button],button")) {
			this.prop("disabled", true);
		} else if (this.is("input")) {
			this.prop("readonly", true);
		} else if (this.is("select")) {
			this.find("option:not(:selected)").prop("disabled", true);
		}
		return this;
	};
})(jQuery);

/**
 * jauery拡張. コード補完
 */
(function($) {
	$.fn.instrumentalityOfCode = function(options) {
		// options
		var target = options.target;
		var url = options.url;
		var key = options.key ? options.key : "code";
		var labelText = options.labelText;
		var autoInit = options.autoInit ? options.autoInit : false;
		var hideWhenInput = options.hideWhenInput ? options.hideWhenInput : false;
		return this.each(function() {
			// label
			var $label = $(this);
			// target input
			target = target ? target : $label.attr("ioc-target");
			var $target = $.type(target) == "string" ? $("#" + target) : target;
			// show or hide label
			if (hideWhenInput === true) {
				$target.on("focusin", function() {
					$label.hide();
				});
				$target.on("focusout", function() {
					$label.show();
				});
			}
			// update label text
			$target.on("change", function() {
				$label.text("");
				var $form = $("<form>");
				$form.appendInput(key, this.value);
				var callback = function(model) {
					var _text = "";
					if ($.type(labelText) == "string") {
						_text = _.replaceText(model, labelText);
					} else if ($.type(labelText) == "function") {
						_text = labelText(model);
					}
					$label.text(_text);
				};
				_.get(url, $form, callback);
			});
			// trigger event automatically
			if (autoInit === true) {
				if ($target.val()) {
					$target.trigger("change");
				}
			}
		});
	};
})(jQuery);

$(document).ready(function() {
	// set select readonly
	$("body").on("mousedown keydown", "select[readonly]", function(e) {
		// tab
		if (e.which === 9) {
			return;
		}
		return false;
	});
});

//object.watch
// https://gist.github.com/eligrey/384583
if (!Object.prototype.watch) {
	Object.defineProperty(Object.prototype, "watch", {
		enumerable : false,
		configurable : true,
		writable : false,
		value : function(prop, handler) {
			var oldval = this[prop], newval = oldval, getter = function() {
				return newval;
			}, setter = function(val) {
				oldval = newval;
				return newval = handler.call(this, prop, oldval, val);
			};

			if (delete this[prop]) { // can't watch constants
				Object.defineProperty(this, prop, {
					get : getter,
					set : setter,
					enumerable : true,
					configurable : true
				});
			}
		}
	});
}

// object.unwatch
if (!Object.prototype.unwatch) {
	Object.defineProperty(Object.prototype, "unwatch", {
		enumerable : false,
		configurable : true,
		writable : false,
		value : function(prop) {
			var val = this[prop];
			delete this[prop]; // remove accessors
			this[prop] = val;
		}
	});
}