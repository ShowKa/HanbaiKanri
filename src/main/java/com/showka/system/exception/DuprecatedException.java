package com.showka.system.exception;

/**
 * 重複するデータがすでに存在する場合にthrowする例外.
 */
public class DuprecatedException extends ValidateException {

	/**
	 * SID.
	 */
	private static final long serialVersionUID = 9134355932303394487L;

	/**
	 * Constructor.
	 */
	public DuprecatedException(String... targets) {
		super("重複するデータが存在します。重複条件:" + targets);
	}

}
