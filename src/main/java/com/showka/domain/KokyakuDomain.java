package com.showka.domain;

import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.KokyakuKubun;
import com.showka.system.exception.SystemException;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 顧客 Domain
 *
 * @author 25767
 *
 */
@AllArgsConstructor
@Getter
public class KokyakuDomain extends DomainBase {

	/** 顧客コード */
	private String code = STRING_EMPTY;

	/** 顧客名 */
	private String name = STRING_EMPTY;

	/** 顧客住所 */
	private String address = STRING_EMPTY;

	/** 顧客区分 */
	private KokyakuKubun kokyakuKubun = KokyakuKubun.EMPTY;

	/** 販売区分 */
	private HanbaiKubun hanbaiKubun = HanbaiKubun.EMPTY;

	/** 主幹部署ID */
	private String shukanBushoId = STRING_EMPTY;

	@Override
	public void validate() throws SystemException {
	}

	@Override
	protected boolean equals(DomainBase other) {
		KokyakuDomain o = (KokyakuDomain) other;
		return code.equals(o.code);
	}

	@Override
	public int hashCode() {
		return code.hashCode();
	}

}
