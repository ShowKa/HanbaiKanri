package com.showka.domain;

import java.math.BigDecimal;

import com.showka.system.exception.SystemException;

import lombok.Getter;

/**
 * 売上履歴明細
 * 
 * @author ShowKa
 *
 */
@Getter
public class UriageRirekiMeisaiDomain extends UriageMeisaiDomain {

	// private member
	public UriageRirekiMeisaiDomain(String uriageId, Integer meisaiNumber, ShohinDomain shohinDomain,
			Integer hanbaiNumber, BigDecimal hanbaiTanka) {
		super(uriageId, meisaiNumber, shohinDomain, hanbaiNumber, hanbaiTanka);
	}

	/**
	 * 売上履歴テーブルのレコードIDです。
	 */
	@Override
	public String getUriageId() {
		return super.getUriageId();
	}

	// public method
	@Override
	public void validate() throws SystemException {
		// nothing to do
	}

	@Override
	protected boolean equals(DomainBase other) {
		UriageRirekiMeisaiDomain o = (UriageRirekiMeisaiDomain) other;
		return (getUriageId().equals(o.getUriageId()) && getMeisaiNumber().equals(o.getMeisaiNumber()));
	}

	@Override
	public int hashCode() {
		return generateHashCode(getUriageId(), getMeisaiNumber());
	}

	public boolean sameMeisaiNumberWith(UriageMeisaiDomain uriageMeisai) {
		return getMeisaiNumber().equals(uriageMeisai.getMeisaiNumber());
	}

	public UriageRirekiMeisaiDomain getOverriddenBy(UriageMeisaiDomain uriageMeisai) {
		return new UriageRirekiMeisaiDomain(getUriageId(), uriageMeisai.getHanbaiNumber(),
				uriageMeisai.getShohinDomain(), uriageMeisai.getHanbaiNumber(), uriageMeisai.getHanbaiTanka());
	}
}
