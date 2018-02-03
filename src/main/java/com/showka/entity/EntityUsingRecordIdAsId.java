package com.showka.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * レコードIDを@ID（主キー）としてoverride
 * 
 */
@MappedSuperclass
public abstract class EntityUsingRecordIdAsId extends EntityBase {

	@Id
	@AttributeOverride(name = "recordId", column = @Column(name = "record_id"))
	private String id;

	@Override
	public String getRecordId() {
		return this.id;
	}

	@Override
	public void setRecordId(String recordId) {
		this.id = recordId;
	}
}
