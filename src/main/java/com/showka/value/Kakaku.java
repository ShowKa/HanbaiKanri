package com.showka.value;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.showka.system.exception.SystemException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Kakaku extends ValueBase {

	// private members
	/** 税抜価格 */
	private BigDecimal zeinukiKakaku;

	/** 税(小数) */
	private TaxRate zei;

	/** 価格表示用フォーマッタ */
	private static DecimalFormat formatter = new DecimalFormat("\u00A5###,###");

	// constructor
	public Kakaku(long kakaku, double zei) {
		this.zeinukiKakaku = BigDecimal.valueOf(kakaku);
		this.zei = new TaxRate(zei);
	}

	// public method
	/**
	 * 価格と税が同じなら一致とみなす.
	 */
	@Override
	protected boolean equals(ValueBase other) {
		Kakaku o = (Kakaku) other;
		if (!zeinukiKakaku.equals(o.zeinukiKakaku)) {
			return false;
		}
		if (!zei.equals(o.zei)) {
			return false;
		}
		return true;
	}

	/**
	 * 税込価格取得
	 * 
	 * <pre>
	 * 端数がある場合は、ゼロに近づける。
	 * 絶対値が同じ場合、負の価格であっても、正の価格の場合と税込価格の絶対値が同じになる。
	 * </pre>
	 * 
	 * @return 税込価格
	 */
	public BigDecimal getZeikomiKakaku() {
		return zeinukiKakaku.multiply(zei.getRate().add(BigDecimal.ONE)).setScale(0, BigDecimal.ROUND_DOWN);
	}

	/**
	 * 価格加算
	 * 
	 * <pre>
	 * 新しいインスタンスを返却する。呼び出し元の価格は変更しない。
	 * 税率が一致しない場合はエラー
	 * </pre>
	 * 
	 * @param other
	 *            加算対象
	 * @return 加算後の価格
	 */
	public Kakaku add(Kakaku other) {
		if (!zei.equals(other.zei)) {
			throw new SystemException("加算対象の金額の税率が一致しません。");
		}
		return new Kakaku(zeinukiKakaku.add(other.zeinukiKakaku), zei);
	}

	/**
	 * 価格加算
	 * 
	 * <pre>
	 * 新しいインスタンスを返却する。呼び出し元の価格は変更しない。
	 * 税率が一致しない場合はエラー
	 * </pre>
	 * 
	 * @param others
	 *            加算対象
	 * @return 加算後の価格
	 */
	public Kakaku add(Kakaku... others) {
		Kakaku base = new Kakaku(this.zeinukiKakaku, this.zei);
		for (Kakaku o : others) {
			if (!zei.equals(o.zei)) {
				throw new SystemException("加算対象の金額の税率が一致しません。");
			}
			base = base.add(o);
		}
		return base;
	}

	/**
	 * 価格減算
	 * 
	 * <pre>
	 * 新しいインスタンスを返却する。呼び出し元の価格は変更しない。
	 * 税率が一致しない場合はエラー
	 * </pre>
	 * 
	 * @param other
	 *            減算対象
	 * @return 減算後の価格
	 */
	public Kakaku subtract(Kakaku other) {
		if (!zei.equals(other.zei)) {
			throw new SystemException("減産対象の金額の税率が一致しません。");
		}
		return new Kakaku(zeinukiKakaku.subtract(other.zeinukiKakaku), zei);
	}

	/**
	 * 価格乗算
	 * 
	 * <pre>
	 * 新しいインスタンスを返却する。呼び出し元の価格は変更しない。
	 * </pre>
	 * 
	 * @param multiplicand
	 *            被乗数
	 * @return 乗算後の価格
	 */
	public Kakaku multiply(Integer multiplicand) {
		return multiply(new BigDecimal(multiplicand));
	}

	/**
	 * 価格乗算
	 * 
	 * <pre>
	 * 新しいインスタンスを返却する。呼び出し元の価格は変更しない。
	 * </pre>
	 * 
	 * @param multiplicand
	 *            被乗数
	 * @return 乗算後の価格
	 */
	public Kakaku multiply(BigDecimal multiplicand) {
		return new Kakaku(zeinukiKakaku.multiply(multiplicand), zei);
	}

	/**
	 * 税込価格をフォーマットした状態で取得
	 * 
	 * <pre>
	 * 例 : ¥1,080
	 * </pre>
	 */
	public String getZeikomiKakakuFormatted() {
		return formatter.format(getZeikomiKakaku());
	}

	/**
	 * 税抜価格をフォーマットした状態で取得
	 * 
	 * <pre>
	 * 例 : ¥1,000
	 * </pre>
	 */
	public String getZeinukiKakakuFormatted() {
		return formatter.format(zeinukiKakaku);
	}

}
