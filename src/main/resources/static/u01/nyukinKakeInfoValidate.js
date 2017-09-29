function isEmpty(val) {
	if (val == "" || val == null) {
		return true;
	} else {
		return false;
	}
}
/*
 * 該当タグを<div class="validated">で囲み、内部にメッセージを追加
 */
function setMessage(tag, message) {
	//既に<div class="validated">で囲われている場合はメッセージの表示を行う
	if ($(tag).closest('.validated').length > 0) {
		$(tag).show();
		return;
	}
	//該当タグを<div class="validated">で囲み、内部にメッセージを追加する
	$(tag).wrap('<div class="validated"></div>');
	$(tag).after('<div class="ErrMsg">' + message.bold() + '</div>');
}
/*
 * プルダウンで空白もしくはnullが選択されていたらメッセージを表示
 */
function selectCheck(tag) {
	if (isEmpty($(tag).val())) {
		setMessage(tag, '*選択必須です')
		return false;
	}
	return true;
}
/*
 * テキストボックスが空白ならメッセージを表示
 */
function checkRequired(tag) {
	if (isEmpty($(tag).val())) {
		setMessage(tag, '*入力必須です')
		return false;
	}
	return true;
}
/*
 * validation
 */
function nyukinKakeInfoValid() {

	// 掛売り以外のときは無条件でOK
	if (HanbaiKubun.kakeuri != $('#registerForm [name=hanbaiKubun]').val()) {
		return true;
	}

	var result = true;
	var shimebiTag = '#registerForm [name=shimebi]';
	var nyukinHohoKubunTag = '#registerForm [name=nyukinHohoKubun]';
	var nyukinTsukiKubunTag = '#registerForm [name=nyukinTsukiKubun]';
	var nyukinDateTag = '#registerForm [name=nyukinDate]';

	result = selectCheck(shimebiTag) && result;
	result = selectCheck(nyukinHohoKubunTag) && result;
	result = selectCheck(nyukinTsukiKubunTag) && result;
	result = checkRequired(nyukinDateTag) && result;

	return result;
}
function controlShowHide(_this) {
	if (isEmpty(_this.val())) {
		_this.next().show();
	} else {
		_this.next().hide();
	}
}
/*
 * 一度でもvalidationに引っかかったら、以後は内容が変更されるたびにチェックする
 */
$(function() {
	var shimebiTag = '#registerForm [name=shimebi]';
	var nyukinHohoKubunTag = '#registerForm [name=nyukinHohoKubun]';
	var nyukinTsukiKubunTag = '#registerForm [name=nyukinTsukiKubun]';
	var nyukinDateTag = '#registerForm [name=nyukinDate]'

	$(shimebiTag).change(function() {
		if ($(shimebiTag).closest('.validated').length > 0) {
			controlShowHide($(this));
		}
	});

	$(nyukinHohoKubunTag).change(function() {
		if ($(nyukinHohoKubunTag).closest('.validated').length > 0) {
			controlShowHide($(this));
		}
	});

	$(nyukinTsukiKubunTag).change(function() {
		if ($(nyukinTsukiKubunTag).closest('.validated').length > 0) {
			controlShowHide($(this));
		}
	});

	$(nyukinDateTag).change(function() {
		if ($(nyukinDateTag).closest('.validated').length > 0) {
			controlShowHide($(this));
		}
	});

});
