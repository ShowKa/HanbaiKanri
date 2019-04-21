function Meisai(keys, members) {
	// keys
	this.keys = keys;
	// member
	Object.assign(this, members);
	// initial value
	this.init = {};
	Object.assign(this.init, members);
	// status
	this.status = "added";
	// editing
	this.editing = false;
	// return
	return this;
}
Meisai.prototype.editDone = function() {
	this.editing = false;
	if (this.updated()) {
		this.moveStatus();
	} else {
		this.editMode = "notUpdated";
	}
}
Meisai.prototype.moveStatus = function() {
	switch (this.editMode) {
	case "added":
		this.editMode = "newRegistered";
		break;
	case "notUpdated":
	case "deleted":
		this.editMode = "updated";
		break;
	case "newRegistered":
		break;
	}
}
Meisai.prototype.edit = function(e) {
	e = e != null ? e : true;
	this.editing = e;
}
Meisai.prototype.updated = function() {
	for ( var k in this.init) {
		if (this.init[k] !== this[k]) {
			return true;
		}
	}
	return false;
}
Meisai.prototype.delete = function() {
	this.editMode = "deleted";
}
Meisai.prototype.equals = function(other) {
	for(var i = 0; i < this.keys.length; i++){ 
		var key = this.keys[i];
		if (this[key] !== other[key]) {
			return false;
		}
	}
	return true;
}