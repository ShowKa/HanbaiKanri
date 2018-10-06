package com.showka.domain.u01;

import com.showka.domain.DomainBase;
import com.showka.system.exception.SystemException;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 振込依頼人名.
 * 
 * <pre>
 * 顧客と紐づく振込依頼人名を保有する。
 * 
 * </pre>
 *
 */
@AllArgsConstructor
@Getter
public class FurikomiIrainin extends DomainBase {

	/** 振込依頼人名. */
	private String furikomiIraininName;

	@Override
	protected boolean equals(DomainBase other) {
		FurikomiIrainin o = (FurikomiIrainin) other;
		return this.getRecordId().equals(o.getRecordId());
	}

	@Override
	public void validate() throws SystemException {
		// do nothing
	}

	@Override
	public int hashCode() {
		return generateHashCode(this.getRecordId());
	}
}
