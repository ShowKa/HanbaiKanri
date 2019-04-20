package com.showka.system.exception.validate;

/**
 * 明細の一意制約違反の例外.
 */
public class MeisaiUniqueConstraintException extends ValidateException {

	/**
	 * SID.
	 */
	private static final long serialVersionUID = -2234408241078858230L;

	/**
	 * 重複する明細が存在します。重複条件: targets
	 */
	public MeisaiUniqueConstraintException(String... targets) {
		super("重複する明細が存在します。重複条件:" + arrayFormat(targets));
	}
}
