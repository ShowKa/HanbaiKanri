package com.showka.system.exception;

/**
 * アプリケーション例外.
 * 
 * <pre>
 * プログラムのバグではなく、ユーザーの入力ミス等でThrowされる。
 * </pre>
 * 
 */
public abstract class ApplicationException extends RuntimeException {
	/**
	 * SID.
	 */
	private static final long serialVersionUID = 7911381036041646154L;

	/**
	 * メッセージ.
	 */
	private String message;

	/**
	 * コンストラクタ.
	 * 
	 * @param message
	 *            メッセージ
	 */
	public ApplicationException(String message) {
		this.message = message;
	}

	/**
	 * メッセージ取得.
	 * 
	 * @return メッセージ
	 */
	@Override
	public String getMessage() {
		return this.message;
	}
}
