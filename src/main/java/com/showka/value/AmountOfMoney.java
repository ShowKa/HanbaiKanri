package com.showka.value;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 金額.
 */
@AllArgsConstructor
public class AmountOfMoney extends ValueBase {

	/** 金額. */
	@Getter(value = AccessLevel.PROTECTED)
	private BigDecimal amount;

	/** 価格表示用フォーマッタ. */
	private static DecimalFormat formatter = new DecimalFormat("\u00A5###,###.#####");

	/**
	 * Integer コンストラクタ.
	 * 
	 * @param kingaku
	 */
	public AmountOfMoney(Long kingaku) {
		this.amount = BigDecimal.valueOf(kingaku);
	}

	/**
	 * Long コンストラクタ.
	 * 
	 * @param kingaku
	 */
	public AmountOfMoney(Integer kingaku) {
		this.amount = BigDecimal.valueOf(kingaku);
	}

	/**
	 * 税抜価格をフォーマットした状態で取得
	 * 
	 * <pre>
	 * 例 : ¥1,000
	 * </pre>
	 */
	public String getFormatted() {
		return formatter.format(amount);
	}

	@Override
	protected boolean equals(ValueBase other) {
		AmountOfMoney o = (AmountOfMoney) other;
		return amount.equals(o.amount);
	}

	/**
	 * 加算.
	 * 
	 * @param augend
	 *            加算対象
	 * @return 加算後(new instance)
	 */
	public AmountOfMoney add(AmountOfMoney augend) {
		return new AmountOfMoney(this.amount.add(augend.amount));
	}

	/**
	 * 乗算.
	 * 
	 * @param multiplicand
	 *            乗算する数
	 * @return 乗算後 (new instance)
	 */
	public AmountOfMoney multiply(BigDecimal multiplicand) {
		return new AmountOfMoney(this.amount.multiply(multiplicand));
	}

	/**
	 * 減算.
	 * 
	 * @param subtrahend
	 *            減算対象
	 * @return 減算後 (new instance)
	 */
	public AmountOfMoney subtract(AmountOfMoney subtrahend) {
		return new AmountOfMoney(this.amount.subtract(subtrahend.amount));
	}
}
