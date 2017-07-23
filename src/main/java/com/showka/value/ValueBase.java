package com.showka.value;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public abstract class ValueBase {
	/**
	 * 空チェック
	 * 
	 * @return 値オブジェクトとして意味をなさない状態ならtrue
	 */
	abstract public boolean isEmpty();

	/**
	 * 同値判定
	 * 
	 * @param other
	 * @return
	 */
	protected abstract boolean equals(ValueBase other);

	/**
	 * 同値判定
	 * 
	 * @param other
	 * @return
	 */
	public final boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof ValueBase) {
			return equals((ValueBase) other);
		}
		return false;
	}

	/**
	 * 文字列化
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
