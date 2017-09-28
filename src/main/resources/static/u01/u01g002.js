function register() {
	var $form = $('form');
	if ($form.valid()) {
		crud("/u01g002/register", "registerForm", "/u01g002/refer");
	}
}
function update() {
	var $form = $('form');
	if ($form.valid()) {
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
$(function() {
    $('#hanbaiKubun').change(function(){
    	controlNyukinKakeInfoForm();
    });
});

function controlNyukinKakeInfoForm() {
	if ($('#hanbaiKubun').val() == HanbaiKubun.kakeuri) {
		$('.nyukinKakeInfo').show();
	} else {
		$('.nyukinKakeInfo').hide();
	}
}

$(document).ready(function() {
	swithElementActivationByMode();
	controlNyukinKakeInfoForm();
});