package com.showka.system.exception;

import lombok.Getter;

/**
 * 存在例外
 *
 * <pre>
 * 既に存在しているときにThrowされる。
 * </pre>
 *
 */
@Getter
public class AlreadyExistsException extends ValidateException {

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
	 * target : code は既に存在しています。
	 * </pre>
	 *
	 * @param target
	 *            存在するものの名称
	 * @param code
	 *            入力されたコード
	 */
	public AlreadyExistsException(String target, String code) {
		super(target + ":" + code + "は既に存在しています");
		this.target = target;
		this.code = code;
	}
}
