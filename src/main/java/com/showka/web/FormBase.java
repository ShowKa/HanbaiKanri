package com.showka.web;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormBase implements Serializable {

	/**
	 * SID.
	 */
	private static final long serialVersionUID = -824041068500111832L;

	/**
	 * 成功メッセージ.
	 */
	private String successMessage;

	/**
	 * 通知メッセージ.
	 */
	private String infoMessage;

	/**
	 * 警告メッセージ.
	 */
	private String warningMessage;

	/**
	 * エラーメッセージ.
	 */
	private String errorMessage;

	/**
	 * 文字列化
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
