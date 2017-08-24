package com.showka.web;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class FormBase {

	/**
	 * 文字列化
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
