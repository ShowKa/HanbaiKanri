package com.showka.domain.u11;

import com.showka.domain.DomainRoot;
import com.showka.system.exception.SystemException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NyukaSaki extends DomainRoot {

	private String code;
	private String name;
	private String address;

	@Override
	protected boolean equals(DomainRoot other) {
		NyukaSaki o = (NyukaSaki) other;
		return this.code.equals(o.code);
	}

	@Override
	public int hashCode() {
		return generateHashCode(this.code);
	}

	@Override
	public void validate() throws SystemException {
		// nothing to do
	}
}
