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
	private String targetName;

	/** 区分 */
	private Kubun<?> kubun;

	/**
	 * 区分不正例外
	 * 
	 * <pre>
	 * targetにkubunは設定できません。
	 * </pre>
	 * 
	 * @param targetName
	 * @param kubun
	 */
	public IncorrectKubunException(String targetName, Kubun<?> kubun) {
		super(targetName + "に" + kubun.toString() + "は設定できません。");
		this.targetName = targetName;
		this.kubun = kubun;
	}

}
