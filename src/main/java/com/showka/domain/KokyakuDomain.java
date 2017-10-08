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
	private String code;

	/** 顧客名 */
	private String name;

	/** 顧客住所 */
	private String address;

	/** 顧客区分 */
	private KokyakuKubun kokyakuKubun;

	/** 販売区分 */
	private HanbaiKubun hanbaiKubun;

	/** 主幹部署 */
	private BushoDomain shukanBusho;

	/** 入金掛情報 */
	private NyukinKakeInfoDomain nyukinKakeInfo;

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
