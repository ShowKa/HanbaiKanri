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
public class Shain extends DomainBase {

	/** コード */
	private String code;

	/** 名前 */
	private String name;

	/** 所属部署 */
	private Busho shozokuBusho;

	@Override
	public void validate() throws SystemException {
	}

	@Override
	protected boolean equals(DomainBase other) {
		Shain o = (Shain) other;
		return code.equals(o.code);
	}

	@Override
	public int hashCode() {
		return code.hashCode();
	}

}
