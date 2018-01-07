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

	/**
	 * コンストラクタ.
	 * 
	 * @param uriageId
	 *            売上履歴ドメインのレコードID
	 * @param meisaiNumber
	 *            明細番号
	 * @param shohinDomain
	 *            商品
	 * @param hanbaiNumber
	 *            販売数
	 * @param hanbaiTanka
	 *            販売単価
	 */
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

	/**
	 * 売上明細ドメインで上書したドメインのインスタンスを返却します。
	 * 
	 * <pre>
	 * 上書対象:商品、販売数、販売単価
	 * </pre>
	 * 
	 * @param uriageMeisai
	 *            売上明細
	 * @return 売上履歴明細
	 */
	public UriageRirekiMeisaiDomain getOverriddenBy(UriageMeisaiDomain uriageMeisai) {
		UriageRirekiMeisaiDomain domain = new UriageRirekiMeisaiDomain(getUriageId(), getMeisaiNumber(),
				uriageMeisai.getShohinDomain(), uriageMeisai.getHanbaiNumber(), uriageMeisai.getHanbaiTanka());
		domain.setRecordId(getRecordId());
		domain.setVersion(getVersion());
		return domain;
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
}
