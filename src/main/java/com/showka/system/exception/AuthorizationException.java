package com.showka.system.exception;

/**
 * 認可エラー.
 */
public class AuthorizationException extends ApplicationException {

	/**
	 * SID.
	 */
	private static final long serialVersionUID = -4084625922014765737L;

	/**
	 * 許可されていません。理由：....
	 * 
	 * @param reason
	 *            理由
	 */
	public AuthorizationException(String reason) {
		super("許可されていません。理由：" + reason);
	}

}
