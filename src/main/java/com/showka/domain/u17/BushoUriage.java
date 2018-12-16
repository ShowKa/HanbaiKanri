package com.showka.domain.u17;

import com.showka.domain.DomainAggregation;
import com.showka.domain.z00.Busho;
import com.showka.system.exception.SystemException;
import com.showka.value.TheDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * その日における部署の売上集計.
 */
@AllArgsConstructor
@Getter
public class BushoUriage extends DomainAggregation {

	// private members
	/** 部署. */
	private Busho busho;

	/** 計上日. */
	private TheDate keijoDate;

	/** 計上金額（売上の合計金額。訂正分は除く）. */
	private Integer keijoKingaku;

	/** 計上訂正金額（売上訂正の合計金額。マイナスになる）. */
	private Integer teiseiKingaku;

	// public methods
	public Integer getKeijoKingakuGokei() {
		return keijoKingaku + teiseiKingaku;
	}

	// overridden
	@Override
	public void validate() throws SystemException {
		// nothing to do
	}
}
