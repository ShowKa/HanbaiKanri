package com.showka.system.exception;

import lombok.Getter;

@Getter
public class CanNotUpdateException extends ValidateException {

	/**
	 * SID.
	 */
	private static final long serialVersionUID = -4154073639032756077L;

	private final String reason;

	/**
	 * コンストラクタ.
	 * 
	 * <pre>
	 * 更新できません。（理由：reason）
	 * </pre>
	 * 
	 * @param reson
	 *            更新不可理由
	 */
	public CanNotUpdateException(String reason) {
		super("更新できません（理由：" + reason + "）");
		this.reason = reason;
	}
}
