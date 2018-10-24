package com.showka.system.exception;

import lombok.Getter;

@Getter
public class CanNotUpdateOrDeleteException extends ValidateException {

	/**
	 * SID.
	 */
	private static final long serialVersionUID = -4154073639032756077L;

	private final String reason;

	/**
	 * コンストラクタ.
	 * 
	 * <pre>
	 * 更新・削除できません。（理由：reason）
	 * </pre>
	 * 
	 * @param reson
	 *            更新不可理由
	 */
	public CanNotUpdateOrDeleteException(String reason) {
		super("更新・削除はできません（理由：" + reason + "）");
		this.reason = reason;
	}
}
