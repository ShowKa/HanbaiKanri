package com.showka.system.exception.validate;

import lombok.Getter;

/**
 * 非存在例外
 *
 */
@Getter
public class NotExistException extends ValidateException {

	/**
	 * SID.
	 */
	private static final long serialVersionUID = 7070269325428973023L;

	/**
	 * 存在しないものの名称
	 */
	private String target;

	/**
	 * 存在しないコード
	 */
	private String code;

	/**
	 * コンストラクタ
	 * 
	 * <pre>
	 * target : code は存在しません。
	 * </pre>
	 * 
	 * @param target
	 *            存在しないものの名称
	 * @param code
	 *            入力されたコード
	 */
	public NotExistException(String target, String code) {
		super(target + ":" + code + "は存在しません。");
		this.target = target;
		this.code = code;
	}

}
