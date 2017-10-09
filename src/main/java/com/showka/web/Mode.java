package com.showka.web;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Mode {
	READ("read"), REGISTER("register"), UPDATE("update");
	@Getter(value = AccessLevel.PROTECTED)
	private String code;
}
