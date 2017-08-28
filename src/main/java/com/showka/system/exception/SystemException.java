package com.showka.system.exception;

import lombok.Getter;

/**
 * システム例外.
 * 
 * <pre>
 * 再起不可能例外。
 * 概ねプログラムのバグと判断できる場合にThrowする。
 * </pre>
 * 
 * @author ShowKa
 *
 */
@Getter
public class SystemException extends RuntimeException {
	/**
	 * SID.
	 */
	private static final long serialVersionUID = 7911381036041646154L;

	/**
	 * メッセージ.
	 */
	private String message;

	/**
	 * 原因
	 */
	private Throwable cause;

	/**
	 * コンストラクタ.
	 * 
	 * @param message
	 *            メッセージ
	 */
	public SystemException(String message) {
		this.message = message;
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param message
	 *            メッセージ
	 * @param cause
	 *            原因
	 */
	public SystemException(String message, Throwable cause) {
		this.message = message;
		this.cause = cause;
		cause.printStackTrace();
	}

}
