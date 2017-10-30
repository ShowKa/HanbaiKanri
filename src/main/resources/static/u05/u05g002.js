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

$(document).ready(function() {
	$("#uriageMeisai").on("dblclick", "tbody tr", function() {
		var $row = $(this);
		var row_number = $row.attr("row_number");
		$("#exampleModal" + row_number).modal("show");
	});
});

function registerMeisai() {
	alert("登録");
}