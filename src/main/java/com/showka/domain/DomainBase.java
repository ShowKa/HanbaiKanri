package com.showka.domain;

import lombok.Getter;
import lombok.Setter;

public abstract class DomainBase extends DomainCore {

	/** record_id */
	@Getter
	@Setter
	private String recordId;

	/** バージョン(排他制御用) */
	@Getter
	@Setter
	private Integer version;

	/**
	 * 空チェック
	 * 
	 * @return 空オブジェクトならtrue
	 */
	public boolean isEmpty() {
		return false;
	}

	/**
	 * 同値判定
	 * 
	 * <pre>
	 * 全く同じ存在 (同一部署、同一顧客、同一伝票等) とみなせる場合true
	 * DBテーブルに置ける主キーの値同士を比較して、同一か否かを判定する。
	 * </pre>
	 * 
	 * @param other
	 *            比較対象
	 * @return 同じものならtrue
	 */
	protected abstract boolean equals(DomainBase other);

	/**
	 * 同値判定
	 * 
	 * <pre>
	 * 全く同じ存在 (同一部署、同一顧客、同一伝票等) とみなせる場合true
	 * DBテーブルに置ける主キーの値同士を比較して、同一か否かを判定する。
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

		if (other instanceof DomainBase) {
			return equals((DomainBase) other);
		}
		return false;
	}
}
