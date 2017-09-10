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
 * @param nextUrl
 *            リダイレクト先URL
 */
function crud(url, formId, nextUrl) {
	var paramUrl = url + "?" + $("#" + formId).serialize();
	$.ajax({
		type : "GET",
		url : paramUrl,
		dataType : "json",
		success : function(data, status, xhr) {
			var model = data.model;
			if (model) {
				var form = model.form;
				if (form) {
					// リダイレクト先にメッセージを引き継ぐ
					var $form = $("#" + formId);
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
			submitForm(nextUrl, formId);
		},
		error : function(data, status, xhr) {
			if (data.status === 400) {
				showErroeMessage(data.responseText);
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
 * jauery拡張. form要素にinput要素を動的に追加するために使用する。
 */
(function($) {
	$.fn.appendInput = function(name, value, type) {
		type = type == null ? type : "hidden";
		var $input = $("<input>");
		$input.attr("name", name);
		$input.attr("type", type);
		$input.val(value);
		this.append($input);
		return this;
	};
})(jQuery);
