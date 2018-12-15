package com.showka.system.exception.validate;

import com.showka.system.exception.ApplicationException;

/**
 * データ整合性例外.
 * 
 */
public class ValidateException extends ApplicationException {

	/**
	 * SID.
	 */
	private static final long serialVersionUID = 2652435245081204638L;

	/**
	 * コンストラクタ.
	 * 
	 * @param message
	 */
	public ValidateException(String message) {
		super(message);
	}

	protected static String arrayFormat(String... targets) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (String target : targets) {
			sb.append(target);
			sb.append(", ");
		}
		sb.delete(sb.length() - 2, sb.length());
		sb.append("]");
		return sb.toString();
	}
}
