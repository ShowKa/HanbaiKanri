function register() {
	var $form = $('form');
	if (kokyakuValid($form)) {
		crud("/u01g002/register", "registerForm", "/u01g002/refer");
	}
}
function update() {
	var $form = $('form');
	if (kokyakuValid($form)) {
		crud("/u01g002/update", "registerForm", "/u01g002/refer");
	}
}
function deleteKokyaku() {
	crud("/u01g002/delete", "registerForm", "/u01g001/");
}
function updateForm() {
   var form = document.forms["registerForm"];

   form.method = "GET"; // method(GET or POST)を設定する
   form.action = "/u01g002/updateForm"; // action(遷移先URL)を設定する
   form.submit(); // submit する
}
//販売区分を変更したとき、値に応じて請求情報の表示/非表示を切り替える
$(function() {
    $('#hanbaiKubun').change(function(){
    	controlNyukinKakeInfoForm();
    });
});
//販売区分の値に応じて請求情報の表示/非表示を切り替える
function controlNyukinKakeInfoForm() {
	if ($('#hanbaiKubun').val() == HanbaiKubun.kakeuri) {
		$('.nyukinKakeInfo').show();
	} else {
		$('.nyukinKakeInfo').hide();
	}
}
//顧客情報・部署情報の単項目valid、請求情報（入金掛情報）のvalidを一括で行う
function kokyakuValid(form){
	var nyukinKakeInfoValidResult = nyukinKakeInfoValid();
	return form.valid() && nyukinKakeInfoValidResult;
}
$(document).ready(function() {
	swithElementActivationByMode();
	controlNyukinKakeInfoForm();
});