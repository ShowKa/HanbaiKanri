package com.showka.system.exception;

import lombok.Getter;

/**
 * 未設定例外
 * 
 */
@Getter
public class EmptyException extends ValidateException {

	/**
	 * SID.
	 */
	private static final long serialVersionUID = -1183953047533837162L;

	/**
	 * 存在しないものの名称
	 */
	private String target;

	/**
	 * targetを設定する必要があります。
	 * 
	 * @param target
	 */
	public EmptyException(String target) {
		super(target + "を設定する必要があります。");
		this.target = target;
	}

}
