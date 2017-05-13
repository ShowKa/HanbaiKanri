package com.showka.common;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class AbstractForm {

	/**
	 * 文字列化
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
