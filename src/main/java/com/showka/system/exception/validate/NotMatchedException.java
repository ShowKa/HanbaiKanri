package com.showka.system.exception.validate;

/**
 * 値を一致させる必要があるときにthrowする例外
 */
public class NotMatchedException extends ValidateException {

	/**
	 * SID.
	 */
	private static final long serialVersionUID = -703929688155712328L;

	/**
	 * 
	 * targets...を一致させる必要があります。
	 * 
	 * @param targets
	 *            対象項目
	 */
	public NotMatchedException(String... targets) {
		super(arrayFormat(targets) + "を一致させる必要があります。");
	}

}
