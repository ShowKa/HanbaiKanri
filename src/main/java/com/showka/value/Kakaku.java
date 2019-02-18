package com.showka.value;

import java.math.BigDecimal;

import com.showka.system.exception.SystemException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Kakaku extends ValueBase {

	// private members
	/** 税抜価格 */
	private AmountOfMoney zeinuki;

	/** 税(小数) */
	private TaxRate shohizeiRate;

	// constructor
	/**
	 * long and double constructor.
	 * 
	 * @param kakaku
	 * @param zei
	 */
	public Kakaku(long kakaku, double zei) {
		this.zeinuki = new AmountOfMoney(kakaku);
		this.shohizeiRate = new TaxRate(zei);
	}

	/**
	 * BigDecimal and double constructor.
	 * 
	 * @param kakaku
	 * @param zei
	 */
	public Kakaku(BigDecimal kakaku, double zei) {
		this.zeinuki = new AmountOfMoney(kakaku);
		this.shohizeiRate = new TaxRate(zei);
	}

	/**
	 * BigDecimal and TaxRate constructor.
	 * 
	 * @param kakaku
	 * @param zei
	 */
	public Kakaku(BigDecimal kakaku, TaxRate zei) {
		this.zeinuki = new AmountOfMoney(kakaku);
		this.shohizeiRate = zei;
	}

	// public method
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
	public AmountOfMoney getZeikomi() {
		BigDecimal multiplied = zeinuki.multiply(shohizeiRate.getRate().add(BigDecimal.ONE)).getAmount();
		return new AmountOfMoney(multiplied.setScale(0, BigDecimal.ROUND_DOWN));
	}

	/**
	 * 税価格取得
	 * 
	 * <pre>
	 * 税価格 = 税込価格 - 税抜価格
	 * </pre>
	 * 
	 * @return 税込価格
	 */
	public AmountOfMoney getShohizei() {
		return this.getZeikomi().subtract(this.zeinuki);
	}

	// math
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
		if (!shohizeiRate.equals(other.shohizeiRate)) {
			throw new SystemException("加算対象の金額の税率が一致しません。");
		}
		return new Kakaku(zeinuki.add(other.zeinuki), shohizeiRate);
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
		Kakaku base = new Kakaku(this.zeinuki, this.shohizeiRate);
		for (Kakaku o : others) {
			if (!shohizeiRate.equals(o.shohizeiRate)) {
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
		if (!shohizeiRate.equals(other.shohizeiRate)) {
			throw new SystemException("減産対象の金額の税率が一致しません。");
		}
		return new Kakaku(zeinuki.subtract(other.zeinuki), shohizeiRate);
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
		return new Kakaku(zeinuki.multiply(multiplicand), shohizeiRate);
	}

	// override
	/**
	 * 価格と税が同じなら一致とみなす.
	 */
	@Override
	protected boolean equals(ValueBase other) {
		Kakaku o = (Kakaku) other;
		if (!zeinuki.equals(o.zeinuki)) {
			return false;
		}
		if (!shohizeiRate.equals(o.shohizeiRate)) {
			return false;
		}
		return true;
	}
}
