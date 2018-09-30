package com.showka.system.exception;

/**
 * 単黒目チェックで問題があった際にthrowする例外。
 * 
 */
public class SingleInputValidateException extends ValidateException {
	/**
	 * SID.
	 */
	private static final long serialVersionUID = 5054793245036910803L;

	/**
	 * constructor.
	 * 
	 * @param message
	 *            メッセージ
	 */
	public SingleInputValidateException(String message) {
		super(message);
	}

}
