package com.showka.system.exception.validate;

/**
 * 重複するデータがすでに存在する場合にthrowする例外.
 */
public class DuprecatedException extends ValidateException {

	/**
	 * SID.
	 */
	private static final long serialVersionUID = 9134355932303394487L;

	/**
	 * 重複するデータが存在します。重複条件: targets...
	 */
	public DuprecatedException(String... targets) {
		super("重複するデータが存在します。重複条件:" + arrayFormat(targets));
	}
}
