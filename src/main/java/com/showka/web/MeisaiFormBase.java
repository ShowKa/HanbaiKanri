package com.showka.web;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeisaiFormBase implements Serializable {

	/**
	 * SID.
	 */
	private static final long serialVersionUID = -5376895824409763664L;

	/** 編集モード */
	private EditMode editMode = EditMode.notUpdated;

	@AllArgsConstructor
	private enum EditMode {
		added("added"), notUpdated("notUpdated"), newRegistered("newRegistered"), updated("updated"), deleted(
				"deleted");
		@Getter(value = AccessLevel.PROTECTED)
		private String code;
	}

	/**
	 * 文字列化
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
