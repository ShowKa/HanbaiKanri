package com.showka.domain;

import java.util.Map;

import com.showka.system.exception.SystemException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KokyakuUrikakeZandaka extends DomainBase {

	// private members
	/** 顧客 */
	private Kokyaku kokyaku;

	/** 売上(残高あり) */
	private Map<Uriage, Integer> uriageZandaka;

	// public methods
	public Integer getGokei() {
		int gokei = 0;
		uriageZandaka.forEach((uriage, zandaka) -> {
		});
		return gokei;

	}

	// override methods
	@Override
	public void validate() throws SystemException {
		// do nothing
	}

	@Override
	protected boolean equals(DomainBase other) {
		KokyakuUrikakeZandaka o = (KokyakuUrikakeZandaka) other;
		return kokyaku.equals(o.kokyaku);
	}

	@Override
	public int hashCode() {
		return kokyaku.hashCode();
	}
}
