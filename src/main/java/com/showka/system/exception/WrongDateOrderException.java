package com.showka.system.exception;

import lombok.Getter;

/**
 * 日付の関係がおかしい場合の例外。
 * 
 */
@Getter
public class WrongDateOrderException extends ValidateException {

	private String before;

	private String after;

	/**
	 * 日付関係不正例外
	 * 
	 * <pre>
	 * 「" + before + "」は、「" + after + "」より前の日付である必要があります。
	 * 
	 * <pre>
	 * 
	 * @param before
	 * @param after
	 */
	public WrongDateOrderException(String before, String after) {
		super("「" + before + "」は、「" + after + "」より前の日付である必要があります。");
		this.before = before;
		this.after = after;
	}

	/**
	 * SID.
	 */
	private static final long serialVersionUID = 8188609961102739375L;
}
