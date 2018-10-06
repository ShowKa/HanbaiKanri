package com.showka.domain.u05;

import com.showka.domain.DomainBase;
import com.showka.system.exception.SystemException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UriageCancel extends DomainBase {

	/** 売上. */
	private Uriage uriageDomain;

	/**
	 * 売上ID取得.
	 * 
	 * <pre>
	 * 実際は売上ドメインのレコードIDを返却する
	 * </pre>
	 * 
	 * @return 売上ID
	 */
	public String getUriageId() {
		return uriageDomain.getRecordId();
	}

	@Override
	public void validate() throws SystemException {
		// nothing to do
	}

	@Override
	protected boolean equals(DomainBase other) {
		UriageCancel o = (UriageCancel) other;
		return uriageDomain.equals(o.uriageDomain);
	}

	@Override
	public int hashCode() {
		return generateHashCode(uriageDomain);
	}

}
