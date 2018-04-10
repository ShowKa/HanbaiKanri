package com.showka.value;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 金額.
 * 
 *
 */
@AllArgsConstructor
@Getter
public class Price extends ValueBase {

	/** 金額. */
	private BigDecimal price;

	/** 価格表示用フォーマッタ. */
	private static DecimalFormat formatter = new DecimalFormat("\u00A5###,###.#####");

	/**
	 * Integer コンストラクタ.
	 * 
	 * @param kingaku
	 */
	public Price(Long kingaku) {
		this.price = BigDecimal.valueOf(kingaku);
	}

	/**
	 * Long コンストラクタ.
	 * 
	 * @param kingaku
	 */
	public Price(Integer kingaku) {
		this.price = BigDecimal.valueOf(kingaku);
	}

	/**
	 * 税抜価格をフォーマットした状態で取得
	 * 
	 * <pre>
	 * 例 : ¥1,000
	 * </pre>
	 */
	public String getFormatted() {
		return formatter.format(price);
	}

	@Override
	protected boolean equals(ValueBase other) {
		Price o = (Price) other;
		return price.equals(o.price);
	}

	/**
	 * 加算.
	 * 
	 * @param augend
	 *            加算対象
	 * @return 加算後(new instance)
	 */
	public Price add(Price augend) {
		return new Price(this.price.add(augend.price));
	}

	/**
	 * 乗算.
	 * 
	 * @param multiplicand
	 *            乗算する数
	 * @return 乗算後 (new instance)
	 */
	public Price multiply(BigDecimal multiplicand) {
		return new Price(this.price.multiply(multiplicand));
	}

	/**
	 * 減算.
	 * 
	 * @param subtrahend
	 *            減算対象
	 * @return 減算後 (new instance)
	 */
	public Price subtract(Price subtrahend) {
		return new Price(this.price.subtract(subtrahend.price));
	}
}
