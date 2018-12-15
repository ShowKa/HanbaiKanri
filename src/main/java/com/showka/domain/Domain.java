package com.showka.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.showka.system.exception.SystemException;

public abstract class Domain {

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
		// FIXME もっと便利に。
		// (1) インデントし整列させたい
		// (2) toStringでエラーが発生する要素があってもそれを無視して出力できるものを出力する。
		String s;
		try {
			s = ReflectionToStringBuilder.toString(this);
		} catch (Exception e) {

			String name = this.getClass().getSimpleName();
			int hash = this.getClass().hashCode();
			s = name + "@" + hash + " : 未完成ビルド";
		}
		return s;
	}
}
