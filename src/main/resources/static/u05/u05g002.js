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
	// double click
	$("#uriageMeisai").on("dblclick", "tbody tr", function() {
		var $row = $(this);
		var index = $row.attr("index");
		$("#uriageMeisai" + index).modal("show");
	});

	// save
	$(".uriageMeisaiModal").on("shown.bs.modal", function() {
		var $this = $(this);
		var index = $this.attr("index");
		var $meisaiNumber = $this.findByName(selector("meisaiNumber", index));
		var $shohinCode = $this.findByName(selector("shohinCode", index));
		var $hanbaiNumber = $this.findByName(selector("hanbaiNumber", index));
		var $hanbaiTanka = $this.findByName(selector("hanbaiTanka", index));
		$meisaiNumber.data("previous", $meisaiNumber.val());
		$shohinCode.data("previous", $shohinCode.val());
		$hanbaiNumber.data("previous", $hanbaiNumber.val());
		$hanbaiTanka.data("previous", $hanbaiTanka.val());
	});

	// registered
	$(".uriageMeisaiModal").on("success", function() {
		// get value
		var $this = $(this);
		var index = $this.attr("index");
		var $meisaiNumber = $this.findByName(selector("meisaiNumber", index));
		var $shohinCode = $this.findByName(selector("shohinCode", index));
		var $hanbaiNumber = $this.findByName(selector("hanbaiNumber", index));
		var $hanbaiTanka = $this.findByName(selector("hanbaiTanka", index));

		// table
		var $table = $("#uriageMeisai");
		var $row = $table.find("[index=" + index + "]");
		$row.find("[column=meisaiNumber]").text($meisaiNumber.val());
		$row.find("[column=shohinCode]").text($shohinCode.val());
		$row.find("[column=hanbaiNumber]").text($hanbaiNumber.val());
		$row.find("[column=hanbaiTanka]").text($hanbaiTanka.val());
	});

});

function registerMeisai(index) {
	var $this = $("#uriageMeisai" + index);
	var selector = "meisai" + selectorEscape("[" + index + "].")
			+ "meisaiNumber";
	var $meisaiNumber = $this.findByName(selector);
	
	// validate
	validate({
		url: "/u05g002/validateMeisai",
		targetId : $this[0].id
	});

	// trigger event
	$this.trigger("success");
	$this.modal("hide");
}

function dismiss(index) {
	var $this = $("#uriageMeisai" + index);

	var $meisaiNumber = $this.findByName(selector("meisaiNumber", index));
	var $shohinCode = $this.findByName(selector("shohinCode", index));
	var $hanbaiNumber = $this.findByName(selector("hanbaiNumber", index));
	var $hanbaiTanka = $this.findByName(selector("hanbaiTanka", index));

	$meisaiNumber.val($meisaiNumber.data("previous"));
	$shohinCode.val($shohinCode.data("previous"));
	$hanbaiNumber.val($hanbaiNumber.data("previous"));
	$hanbaiTanka.val($hanbaiTanka.data("previous"));

	$this.modal("hide");
}

function selector(name, index) {
	return "meisai" + selectorEscape("[" + index + "].") + name;
}

function selectorEscape(val) {
	return val.replace(/[ !"#$%&'()*+,.\/:;<=>?@\[\\\]^`{|}~]/g, '\\$&');
}
