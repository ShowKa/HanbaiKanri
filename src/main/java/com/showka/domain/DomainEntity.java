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

	@Override
	public abstract int hashCode();

	/**
	 * ハッシュコード生成用関数
	 * 
	 * @param objects
	 *            equals()で比較対象となるメンバの値を渡してください。
	 * @return ハッシュ値
	 */
	protected final static int generateHashCode(Object... objects) {
		int hash = 1;
		for (Object o : objects) {
			hash = 31 * hash + (o == null ? 0 : o.hashCode());
		}
		return hash;
	}

}
