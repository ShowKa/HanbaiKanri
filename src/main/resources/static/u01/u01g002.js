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
