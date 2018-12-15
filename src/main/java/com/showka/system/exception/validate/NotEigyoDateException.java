package com.showka.system.exception.validate;

import com.showka.value.TheDate;

public class NotEigyoDateException extends ValidateException {

	/**
	 * SID.
	 */
	private static final long serialVersionUID = 8811782492654596371L;

	/**
	 * target: yyyy/MM/dd は営業日ではありません。
	 * 
	 * @param target
	 */
	public NotEigyoDateException(String target, TheDate date) {
		super(target + ": " + date.toString() + "は営業日ではありません。");
	}

}
