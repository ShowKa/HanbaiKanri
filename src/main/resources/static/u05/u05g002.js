function register() {
	var $form = $('#urageDenpyo');
	var param = {
		url : "/u05g002/register",
		formId : "urageDenpyo",
		redirect : {
			url : "/u05g002/refer",
			param : {
				kokyakuCode : $form.findByName("kokyakuCode").val(),
				denpyoNumber : $form.findByName("denpyoNumber").val()
			}
		}
	};
	crud(param);
}
function update() {
}
$(document).ready(function() {
	swithElementActivationByMode();
});