package com.showka.domain;

import com.showka.kubun.BushoKubun;
import com.showka.kubun.JigyoKubun;
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
public class BushoDomain extends DomainBase {

	/** 顧客コード */
	private String code = STRING_EMPTY;

	/** 部署区分 */
	private BushoKubun bushoKubun = BushoKubun.EMPTY;

	/** 事業区分 */
	private JigyoKubun jigyoKubun = JigyoKubun.EMPTY;

	/** 部署名 */
	private String name = STRING_EMPTY;

	@Override
	public void validate() throws SystemException {
	}

	@Override
	protected boolean equals(DomainBase other) {
		BushoDomain o = (BushoDomain) other;
		return code.equals(o.code);
	}

	@Override
	public int hashCode() {
		return code.hashCode();
	}

}
