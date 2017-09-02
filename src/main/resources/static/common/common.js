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
			$("#errorMessage").remove();
			$("#" + formId).before("<div class='alert alert-danger' id='errorMessage'>"+data.responseText+"</div>");
		}
	});
}
