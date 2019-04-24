// 明細
function Meisai(columns, members) {
	// columns
	this.keys = [];
	this.columns = [];
	for (var i = 0; i < columns.length; i++) {
		var column = columns[i];
		this.columns.push(column.name);
		if (column.uniqueKey === true) {
			this.keys.push(column.name);
		}
	}
	// member
	Object.assign(this, members);
	// initial value
	this.init = {};
	Object.assign(this.init, members);
	// status
	this.status = "added";
	// editing
	this.editing = false;
	// _version
	this._version = 0;
	// return
	return this;
}
Meisai.define = function(columns) {
	return function(members) {
		return new Meisai(columns, members);
	}
}
Meisai.prototype.editDone = function() {
	this.editing = false;
	if (this.updated()) {
		this.moveStatus();
		this._version += 1;
	} else {
		this.status = "notUpdated";
		this._version = 0;
	}
}
Meisai.prototype.moveStatus = function() {
	switch (this.status) {
	case "added":
		this.status = "newRegistered";
		break;
	case "notUpdated":
	case "deleted":
		this.status = "updated";
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
	for ( var k of this.columns) {
		if (this.init[k] != this[k]) {
			return true;
		}
	}
	return false;
}
Meisai.prototype.delete = function() {
	this.status = "deleted";
}
Meisai.prototype.equals = function(other) {
	for(var i = 0; i < this.keys.length; i++){ 
		var key = this.keys[i];
		if (this[key] != other[key]) {
			return false;
		}
	}
	return true;
}
Meisai.prototype.initialize = function() {
	Object.assign(this, this.init);
}
Meisai.prototype.isAdded = function() {
	return "added" === this.status;
}
Meisai.prototype.isNewRegistered = function() {
	return "newRegistered" === this.status;
}
// 明細リスト
function MeisaiList() {
	this.deletedList = [];
};
MeisaiList.prototype = new Array;
MeisaiList.prototype.delete = function(target) {
	for (var i = 0; i < this.length; i++) {
		var meisai = this[i];
		if (target.equals(meisai)) {
			this.splice(i, 1);
			if(meisai.isAdded() || meisai.isNewRegistered()) {
				return;
			} else {
				meisai.delete();
				this.deletedList.push(meisai);
				return;
			}
		}
	}
}
MeisaiList.prototype.push = function(newer) {
	var meisiaList = this;
	Array.prototype.push.call(this, newer);
	newer.watch("_version", function() {
		for (var i = 0; i < meisiaList.deletedList.length; i++) {
			var deleted = meisiaList.deletedList[i];
			if (deleted.equals(newer)) {
				meisiaList.deletedList.splice(i, 1);
				newer.init = deleted.init;
				newer.status = "updated";
				newer.editDone();
				return;
			}
		}
	});
}
MeisaiList.prototype.mergeRequestParameters = function(key, param) {
	for (var i = 0; i < this.length; i++) {
		var meisai = this[i];
		for (var prop of meisai.columns) {
			var v = meisai[prop];
			param[key + "[" + i + "]." + prop] = v;
		}
		param[key + "[" + i + "]." + "status"] = meisai.status;
	}
	for (var k = 0; k < this.deletedList.length; k++) {
		var meisai = this.deletedList[k];
		for (var prop of meisai.columns) {
			var v = meisai[prop];
			param[key + "[" + i + "]." + prop] = v;
		}
		param[key + "[" + i + "]." + "status"] = meisai.status;
		i++;
	}
}
