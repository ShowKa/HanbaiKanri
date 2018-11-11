package com.showka.value;

import com.showka.system.exception.SystemException;

import lombok.Getter;

@Getter
public class ShimeDate extends ValueBase implements Comparable<ShimeDate> {

	/** 締日 */
	private Integer dateValue;

	/**
	 * 締日生成.
	 * 
	 * @param date
	 *            締日(1-30を想定)
	 */
	public ShimeDate(Integer date) {
		this.dateValue = date;
		this.validate();
	}

	/**
	 * 締日生成.
	 * 
	 * @param date
	 *            締日(1-30を想定)
	 */
	public ShimeDate(TheDate date) {
		this.dateValue = date.getDate().getDayOfMonth();
		this.validate();
	}

	/**
	 * 整合性検証.
	 */
	private void validate() {
		if (this.dateValue.intValue() < 0 || this.dateValue.intValue() > 30) {
			throw new SystemException("不正の締日が指定されました: " + dateValue);
		}
	}

	@Override
	protected boolean equals(ValueBase other) {
		ShimeDate o = (ShimeDate) other;
		return this.dateValue.equals(o.dateValue);
	}

	@Override
	public int hashCode() {
		return this.dateValue.hashCode();
	}

	@Override
	public int compareTo(ShimeDate o) {
		return this.dateValue.compareTo(o.dateValue);
	}
}
