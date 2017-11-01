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

$(document).ready(
		function() {
			// double click
			$("#uriageMeisai").on("dblclick", "tbody tr", function() {
				var $row = $(this);
				var index = $row.attr("index");
				$("#uriageMeisai" + index).modal("show");
			});

			// registered
			$(".uriageMeisaiModal").on(
					"success.register.modal",
					function() {
						// get value
						var $this = $(this);
						var index = $this.attr("index");
						var $meisaiNumber = $this.findByName(selector("meisaiNumber"));

						// table
						var $table = $("#uriageMeisai");
						var $row = $table.find("[index=" + index + "]");
						$row.find("[column=meisaiNumber]").text($meisaiNumber.val());
						
						function selector(name) {
							return "meisai"
							+ selectorEscape("[" + index + "].")
							+ name;
						}
					});
		});

function registerMeisai(index) {
	var $table = $("#uriageMeisai" + index);
	var selector = "meisai" + selectorEscape("[" + index + "].")
			+ "meisaiNumber";
	var $meisaiNumber = $table.findByName(selector);

	// validate

	// trigger event
	$table.trigger("success.register.modal");
}

function selectorEscape(val) {
	return val.replace(/[ !"#$%&'()*+,.\/:;<=>?@\[\\\]^`{|}~]/g, '\\$&');
}