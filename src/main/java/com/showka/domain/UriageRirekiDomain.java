package com.showka.domain;

import java.util.List;

import com.showka.kubun.HanbaiKubun;
import com.showka.system.exception.SystemException;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;

import lombok.Getter;

/**
 * 売上履歴ドメイン
 * 
 * <pre>
 * 売上ドメインを継承。
 * 異なる点は、「顧客、伝票番号、計上日」で同値判定を行うこと。
 * </pre>
 * 
 * @author ShowKa
 *
 */

public class UriageRirekiDomain extends UriageDomain {

	/** 売上ID. */
	@Getter
	private String uriageId;

	public UriageRirekiDomain(String uriageId, KokyakuDomain kokyaku, String denpyoNumber, TheDate uriageDate,
			TheDate keijoDate, HanbaiKubun hanbaiKubun, TaxRate shohizeiritsu,
			List<UriageRirekiMeisaiDomain> uriageMeisai) {
		super(kokyaku, denpyoNumber, uriageDate, keijoDate, hanbaiKubun, shohizeiritsu, uriageMeisai);
		this.uriageId = uriageId;
	}

	@Override
	public void validate() throws SystemException {
		// do nothing
	}

	@Override
	public boolean equals(DomainBase other) {
		UriageRirekiDomain o = (UriageRirekiDomain) other;
		return uriageId.equals(o.uriageId) && getKeijoDate().equals(o.getKeijoDate());
	}

	@Override
	public int hashCode() {
		return generateHashCode(uriageId, getKeijoDate());
	}
}