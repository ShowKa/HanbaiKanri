package com.showka.common;

import javax.persistence.Column;
import javax.persistence.Version;

public abstract class AbstractEntity {
	@Version
	@Column(nullable = false)
	private int version = -666;

	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}
