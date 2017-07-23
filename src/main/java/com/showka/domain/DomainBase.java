package com.showka.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.showka.system.exception.SystemException;

public abstract class DomainBase {

	/**
	 * Empty String
	 */
	protected static final String STRING_EMPTY = "";

	/**
	 * Empty Integer
	 */
	protected static final Integer INTEGER_EMPTY = new Integer(0);

	/**
	 * 空チェック
	 * 
	 * @return 空オブジェクトならtrue
	 */
	public boolean isEmpty() {
		return false;
	}

	/**
	 * ドメイン整合性検証
	 * 
	 * <pre>
	 * 画面入力ミスによる不整合ではなく、ドメインの設定ミス（プログラムミス）がないか検証する。
	 * </pre>
	 * 
	 * @throws SystemException
	 */
	public abstract void validate() throws SystemException;

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

	/**
	 * 文字列化
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	/**
	 * ハッシュコード取得
	 * 
	 * @return ハッシュ値
	 */
	@Override
	public abstract int hashCode();

	/**
	 * ハッシュコード生成用関数
	 * 
	 * @param objects
	 *            equals()で比較対象となるメンバの値を渡してください。
	 * @return ハッシュ値
	 */
	protected final static int generateHashCode(Object... objects) {
		int hash = 1;
		for (Object o : objects) {
			hash = 31 * hash + (o == null ? 0 : o.hashCode());
		}
		return hash;
	}
}
