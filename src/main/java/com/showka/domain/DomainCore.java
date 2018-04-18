package com.showka.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.showka.system.exception.SystemException;

public abstract class DomainCore {

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
