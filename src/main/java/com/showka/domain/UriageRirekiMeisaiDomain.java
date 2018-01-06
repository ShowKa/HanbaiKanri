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

	public UriageRirekiMeisaiDomain getOverriddenBy(UriageMeisaiDomain uriageMeisai) {
		UriageRirekiMeisaiDomain domain = new UriageRirekiMeisaiDomain(getUriageId(), uriageMeisai.getHanbaiNumber(),
				uriageMeisai.getShohinDomain(), uriageMeisai.getHanbaiNumber(), uriageMeisai.getHanbaiTanka());
		domain.setRecordId(getRecordId());
		domain.setVersion(getVersion());
		return domain;
	}

}
