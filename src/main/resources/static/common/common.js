var _ = {};

/**
 * 単純なAjaxリクエスト.
 */
_.simpleRequest = function (url, form, successCallback, erroCallback) {
	var url = url + "?" + $("#" + form).serialize();
	$.ajax({
		type : "POST",
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
function searchAndBuildList(url, formId, targetId) {
	var paramUrl = url + "?" + $("#" + formId).serialize();
	$.ajax({
		type : "GET",
		url : paramUrl,
		dataType : "html",
		success : function(data, status, xhr) {
			$("#" + targetId).html(data);
		},
		error : function(data, status, xhr) {
			showErroeMessage(data.responseText);
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
function crud(param) {

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
						$form
								.appendInput("successMessage",
										form.successMessage);
					}
					if (form.infoMessage) {
						$form.appendInput("infoMessage", form.infoMessage);
					}
					if (form.warningMessage) {
						$form
								.appendInput("warningMessage",
										form.warningMessage);
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
				console.log(data);
				showErroeMessage(data.responseJSON.message);
				return;
			} else if (data.status === 409) {
				showErroeMessage(data.responseJSON.message);
				return;
			}
			$("html").html(data.responseText);
		}
	});
}

function validate(param) {
	// set parameters
	var url = param.url;
	var detailName = param.detail.name;
	var detailIndex = param.detail.index;

	var $form;
	if (param.form instanceof String) {
		$form = $(param.form);
	} else {
		$form = param.form;
	}

	var $clonedForm = $form.clone();
	var $inputs = $clonedForm.find("[name]");
	var data = {};
	var reg = new RegExp("^" + detailName + "\\[\\d\\]");
	var regStrictly = new RegExp("^" + detailName + "\\[" + detailIndex + "\\]");
	$inputs.each(function() {
		if (this.name.match(reg)) {
			if (this.name.match(regStrictly)) {
				data[this.name] = this.value;
			}
		} else {
			data[this.name] = this.value;
		}
	});

	$.ajax({
		type : "POST",
		url : url,
		dataType : "json",
		data : data
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
function submitForm(url, formId) {
	$("#" + formId).attr("action", url).submit();
}

/**
 * エラーメッセージ表示.
 * 
 * @param message
 *            メッセージ
 */
function showErroeMessage(message) {
	var $message = $("#errorMessage");
	if ($message.length > 0) {
		$message.text(message).show();
	} else {
		var $new = $("<div>").attr("id", "errorMessage").addClass(
				'alert alert-danger').text(message);
		$("#message-container").append($new);
	}
}

/**
 * 成功メッセージ表示.
 * 
 * @param message
 *            メッセージ
 */
function showSuccessMessage(message) {
	var $message = $("#successMessage");
	if ($message.length > 0) {
		$message.text(message).show();
	} else {
		var $new = $("<div>").attr("id", "successMessage").addClass(
				'alert alert-success').text(message);
		$("#message-container").append($new);
	}
}


/**
 * エラーメッセージを消す。
 */
function hideErrorMessage() {
	$("#errorMessage").hide();
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
 * 要素の活性化状態をモードで切替る。
 */
function swithElementActivationByMode() {
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
 * escape jquery's selector value
 * @param val
 * @returns
 */
function selectorEscape(val) {
	return val.replace(/[ !"#$%&'()*+,.\/:;<=>?@\[\\\]^`{|}~]/g, '\\$&');
}

$(document).ready(function() {
	// set select readonly
	$("body").on("mousedown keydown", "select[readonly]", function(e) {
		// tab
		if(e.which === 9) {
			return;
		}
		return false;
	});
});