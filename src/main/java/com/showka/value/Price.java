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
}
