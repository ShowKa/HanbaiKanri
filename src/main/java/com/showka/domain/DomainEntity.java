package com.showka.domain;

import lombok.Getter;
import lombok.Setter;

public abstract class DomainEntity extends Domain {

	/** record_id */
	private RecordID recordId;

	/** バージョン(排他制御用) */
	@Getter
	@Setter
	private Integer version;

	public String getRecordId() {
		return this.recordId.toString();
	}

	public void setRecordId(RecordID recordId) {
		this.recordId = recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = new RecordID(recordId);
	}

	public void initRecordId() {
		this.recordId = new RecordID();
	}

}
