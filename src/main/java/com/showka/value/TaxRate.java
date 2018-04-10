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
	private BigDecimal rate;

	/**
	 * 100.
	 */
	public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

	/**
	 * パーセント記号.
	 */
	public static final String PERCENTAGE = "%";

	/**
	 * Constructor.
	 * 
	 * @param rate
	 *            税率
	 */
	public TaxRate(double rate) {
		this.rate = BigDecimal.valueOf(rate);
	}

	/**
	 * Constructor.
	 * 
	 * @param rate
	 *            税率
	 */
	public TaxRate(BigDecimal rate) {
		this.rate = rate;
	}

	/**
	 * パーセント表示(整数).
	 * 
	 * @return 例 : 0.08 -> 8%
	 */
	public String toPercentage() {
		return this.toPercentage(0);
	}

	/**
	 * パーセント表示.
	 * 
	 * @param scale
	 *            小数点以下桁数
	 * @return 例 -> 0.08, 桁数2 -> 8.00%
	 */
	public String toPercentage(int scale) {
		return this.rate.multiply(ONE_HUNDRED).setScale(scale).toString() + PERCENTAGE;
	}

	@Override
	protected boolean equals(ValueBase other) {
		TaxRate o = (TaxRate) other;
		return rate.equals(o.getRate());
	}

}
