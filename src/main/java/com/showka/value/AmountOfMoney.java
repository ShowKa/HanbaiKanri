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

	// constructor
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

	// math
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
	 * 加算.
	 * 
	 * @param augend
	 *            加算対象
	 * @return 加算後(new instance)
	 */
	public AmountOfMoney add(Integer augend) {
		return new AmountOfMoney(this.amount.add(BigDecimal.valueOf(augend)));
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
	 * 乗算.
	 * 
	 * @param multiplicand
	 *            乗算する数
	 * @return 乗算後 (new instance)
	 */
	public AmountOfMoney multiply(Integer multiplicand) {
		return new AmountOfMoney(this.amount.multiply(BigDecimal.valueOf(multiplicand)));
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

	/**
	 * 減算.
	 * 
	 * @param subtrahend
	 *            減算対象
	 * @return 減算後 (new instance)
	 */
	public AmountOfMoney subtract(Integer subtrahend) {
		return new AmountOfMoney(this.amount.subtract(BigDecimal.valueOf(subtrahend)));
	}

	// 比較
	/**
	 * 大小比較.
	 * 
	 * @param another
	 *            比較対象
	 * @return this > another なら true
	 */
	public boolean greaterThan(AmountOfMoney another) {
		return this.intValue() > another.intValue();
	}

	/**
	 * 大小比較.
	 * 
	 * @param another
	 *            比較対象
	 * @return this > another なら true
	 */
	public boolean greaterThan(Integer another) {
		return this.intValue() > another.intValue();
	}

	/**
	 * 大小比較.
	 * 
	 * @param another
	 *            比較対象
	 * @return this >= another なら true
	 */
	public boolean greaterThanEquals(AmountOfMoney another) {
		return this.intValue() >= another.intValue();
	}

	/**
	 * 大小比較.
	 * 
	 * @param another
	 *            比較対象
	 * @return this >= another なら true
	 */
	public boolean greaterThanEquals(Integer another) {
		return this.intValue() >= another.intValue();
	}

	/**
	 * 大小比較.
	 * 
	 * @param another
	 *            比較対象
	 * @return this < another なら true
	 */
	public boolean lesserThan(AmountOfMoney another) {
		return this.intValue() < another.intValue();
	}

	/**
	 * 大小比較.
	 * 
	 * @param another
	 *            比較対象
	 * @return this < another なら true
	 */
	public boolean lesserThan(Integer another) {
		return this.intValue() < another.intValue();
	}

	/**
	 * 大小比較.
	 * 
	 * @param another
	 *            比較対象
	 * @return this <= another なら true
	 */
	public boolean lesserThanEquals(AmountOfMoney another) {
		return this.intValue() <= another.intValue();
	}

	/**
	 * 大小比較.
	 * 
	 * @param another
	 *            比較対象
	 * @return this <= another なら true
	 */
	public boolean lesserThanEquals(Integer another) {
		return this.intValue() <= another.intValue();
	}

	// get formatted
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

	// cast
	/**
	 * 整数取得.
	 * 
	 * <pre>
	 * 小数は無視。
	 * </pre>
	 * 
	 * @return 整数
	 */
	public int intValue() {
		return amount.intValue();
	}

	/**
	 * 同値比較.
	 * 
	 * @param other
	 *            比較対象
	 * @return 金額と同じ数値ならtrue
	 */
	public boolean equals(long other) {
		return this.amount.compareTo(new BigDecimal(other)) == 0;
	}

	// override
	@Override
	protected boolean equals(ValueBase other) {
		AmountOfMoney o = (AmountOfMoney) other;
		return amount.equals(o.amount);
	}
}
