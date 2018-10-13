package com.showka.system.exception;

import lombok.Getter;

@Getter
public class CanNotDeleteException extends ValidateException {

	/**
	 * SID.
	 */
	private static final long serialVersionUID = -7851357548254200683L;

	private final String reason;

	/**
	 * コンストラクタ.
	 * 
	 * <pre>
	 * 削除できません。（理由：reason）
	 * </pre>
	 * 
	 * @param reson
	 *            削除不可理由
	 */
	public CanNotDeleteException(String reason) {
		super("削除できません（理由：" + reason + "）");
		this.reason = reason;
	}
}
