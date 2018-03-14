package com.showka.system.exception;

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
