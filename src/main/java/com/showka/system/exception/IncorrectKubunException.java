package com.showka.system.exception;

import com.showka.kubun.i.Kubun;

import lombok.Getter;

/**
 * 設定対象に設定不可の区分が設定された場合の例外
 *
 */
@Getter
public class IncorrectKubunException extends ValidateException {
	/**
	 * SID.
	 */
	private static final long serialVersionUID = -7827002962832575044L;

	/** 設定対象 */
	private String target;

	/** 区分 */
	private Kubun<?> kubun;

	/** 不可理由 */
	private String reason;

	/**
	 * 区分不正例外
	 *
	 * <pre>
	 * targetにkubunは設定できません。（理由：reason）
	 * </pre>
	 *
	 * @param target
	 * @param string
	 */
	public IncorrectKubunException(String target, Kubun<?> kubun, String reason) {
		super(target + "に" + kubun.toString() + "は設定できません。（理由：" + reason + "）");
		this.target = target;
		this.kubun = kubun;
		this.reason = reason;
	}

}
