// 明細
function Meisai(columns, members) {
	// keys & columns
	this.keys = [];
	this.columns = [];
	for (var i = 0; i < columns.length; i++) {
		var column = columns[i];
		this.columns.push(column.name);
		if (column.uniqueKey === true) {
			this.keys.push(column.name);
		}
	}
	// members
	var _members = members != null ? members : {};
	for (var j = 0; j < this.columns.length; j++) {
		var column = this.columns[j];
		if (_members[column] == undefined) {
			_members[column] = "";
		}
	}
	Object.assign(this, _members);
	// initial value
	this.init = {};
	Object.assign(this.init, _members);
	// status
	var hasUniqueKey = members == null ? false : this.hasUniqueKey();
	this._status = hasUniqueKey ? "notUpdated" : "added";
	// editing
	this.editing = false;
	// version
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
	this.moveStatus();
	this._version += 1;
}
Meisai.prototype.moveStatus = function() {
	if (this.updated()) {
		switch (this._status) {
		case "added":
			this._status = "newRegistered";
			break;
		case "notUpdated":
		case "deleted":
			this._status = "updated";
			break;
		case "newRegistered":
			break;
		}
	} else {
		this._status = "notUpdated";
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
Meisai.prototype.hasUniqueKey = function() {
	if (this.keys.length == 0) {
		return false;
	}
	for(var i = 0; i < this.keys.length; i++){ 
		var key = this.keys[i];
		var val = this[key];
		if (val == undefined || val == null || val.length == 0) {
			return false;
		}
	}
	return true;
}
Meisai.prototype.delete = function() {
	this._status = "deleted";
}
Meisai.prototype.equals = function(other) {
	if (!this.hasUniqueKey()) {
		return false;
	}
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
	return "added" === this._status;
}
Meisai.prototype.isNewRegistered = function() {
	return "newRegistered" === this._status;
}
// 明細リスト
function MeisaiList() {
	this.deletedList = [];
};
MeisaiList.prototype = new Array;
MeisaiList.prototype.delete = function(target) {
	for (var i = 0; i < this.length; i++) {
		var meisai = this[i];
		if (target == meisai) {
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
				newer._status = "updated";
				this.moveStatus();
				return;
			}
		}
	});
}
MeisaiList.prototype.updated = function() {
	if (this.deletedList.length > 0) {
		return true;
	}
	for ( var i = 0; i < this.length; i++) {
		var meisai = this[i];
		if (meisai.isNewRegistered()) {
			return true;
		}
		if (meisai.updated()) {
			return true;
		}
	}
}
MeisaiList.prototype.nothing = function() {
	return this.length === 0;
}
MeisaiList.prototype.hasEditing = function() {
	for ( var i = 0; i < this.length; i++) {
		var meisai = this[i];
		if (meisai.editing == true) {
			return true;
		}
	}
	return false;
}
MeisaiList.prototype.merge = function(key, param) {
	for (var i = 0; i < this.length; i++) {
		var meisai = this[i];
		for (var prop of meisai.columns) {
			var v = meisai[prop];
			param[key + "[" + i + "]." + prop] = v;
		}
		param[key + "[" + i + "]." + "_status"] = meisai._status;
	}
	for (var k = 0; k < this.deletedList.length; k++) {
		var meisai = this.deletedList[k];
		for (var prop of meisai.columns) {
			var v = meisai[prop];
			param[key + "[" + i + "]." + prop] = v;
		}
		param[key + "[" + i + "]." + "_status"] = meisai._status;
		i++;
	}
}
