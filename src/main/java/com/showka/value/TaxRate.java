package com.showka.value;

import java.math.BigDecimal;

import lombok.Getter;

/**
 * 税率.
 * 
 * @author ShowKa
 *
 */
@Getter
public class TaxRate extends ValueBase {

	/**
	 * 税率
	 */
	BigDecimal rate;

	/**
	 * Constructor.
	 * 
	 * @param rate
	 *            税率
	 */
	public TaxRate(double rate) {
		this.rate = BigDecimal.valueOf(rate).setScale(2, BigDecimal.ROUND_UNNECESSARY);
	}

	/**
	 * Constructor.
	 * 
	 * @param rate
	 *            税率
	 */
	public TaxRate(BigDecimal rate) {
		this.rate = rate.setScale(2, BigDecimal.ROUND_UNNECESSARY);
	}

	@Override
	protected boolean equals(ValueBase other) {
		TaxRate o = (TaxRate) other;
		return rate.equals(o.getRate());
	}

}
