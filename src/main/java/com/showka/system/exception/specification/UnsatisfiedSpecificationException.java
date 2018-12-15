package com.showka.system.exception.specification;

import com.showka.system.exception.ApplicationException;

/**
 * 仕様を満たさない.
 * 
 */
public class UnsatisfiedSpecificationException extends ApplicationException {

	/**
	 * SID.
	 */
	private static final long serialVersionUID = 1035163649566600605L;

	public UnsatisfiedSpecificationException(String message) {
		super(message);
	}
}
