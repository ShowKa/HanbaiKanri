package com.showka.domain;

import com.showka.system.exception.EmptyProxy;
import com.showka.system.exception.SystemException;
import com.showka.value.Kakaku;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 売上明細
 * 
 * @author ShowKa
 *
 */
@AllArgsConstructor
@Getter
public class UriageMeisaiDomain extends DomainBase {

	// private member
	/** ID. */
	private String uriageId = STRING_EMPTY;

	/** 明細番号. */
	private Integer meisaiNumber = INTEGER_EMPTY;

	/** 商品. */
	private ShohinDomain shohinDomain = EmptyProxy.domain(ShohinDomain.class);

	/** 売上数. */
	private Integer hanbaiNumber = INTEGER_EMPTY;

	/** 商品販売価格. */
	private Kakaku hanbaiKakaku = EmptyProxy.value(Kakaku.class);

	/** バージョン(排他制御用) */
	private Integer version = INTEGER_EMPTY;

	// public method
	/**
	 * 明細合計価格取得
	 * 
	 * @return 明細合計価格
	 */
	public Kakaku getMeisaiGokeiKakaku() {
		return hanbaiKakaku.multiply(hanbaiNumber);
	}

	@Override
	public void validate() throws SystemException {
		// nothing to do
	}

	@Override
	protected boolean equals(DomainBase other) {
		UriageMeisaiDomain o = (UriageMeisaiDomain) other;
		return (uriageId.equals(o.uriageId) && meisaiNumber.equals(o.meisaiNumber));
	}

	@Override
	public int hashCode() {
		return generateHashCode(uriageId, meisaiNumber);
	}

}
