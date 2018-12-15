package com.showka.domain;

/**
 * ドメイン集約.
 * 
 * <pre>
 * ドメインを集めて機能を提供するクラスです。
 * </pre>
 */
public abstract class DomainAggregation extends Domain {
	/**
	 * 同値判定
	 * 
	 * <pre>
	 * 全く同じ存在とみなせる場合true
	 * </pre>
	 * 
	 * @param other
	 *            比較対象
	 * @return 同じものならtrue
	 */
	protected abstract boolean equals(DomainAggregation other);

	/**
	 * 同値判定
	 * 
	 * <pre>
	 * 全く同じ存在  とみなせる場合true
	 * </pre>
	 * 
	 * @param other
	 *            比較対象
	 * @return 同じものならtrue
	 */
	@Override
	public final boolean equals(Object other) {
		if (other == this) {
			return true;
		}

		if (other == null) {
			return false;
		}

		if (other instanceof DomainAggregation) {
			return equals((DomainAggregation) other);
		}
		return false;
	}
}
