package com.showka.domain;

import com.showka.system.exception.SystemException;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 社員Domain
 *
 * @author ShowKa
 *
 */
@AllArgsConstructor
@Getter
public class ShainDomain extends DomainBase {

	/** コード */
	private String code;

	/** 名前 */
	private String name;

	/** 所属部署 */
	private BushoDomain shozokuBusho;

	@Override
	public void validate() throws SystemException {
	}

	@Override
	protected boolean equals(DomainBase other) {
		ShainDomain o = (ShainDomain) other;
		return code.equals(o.code);
	}

	@Override
	public int hashCode() {
		return code.hashCode();
	}

}
