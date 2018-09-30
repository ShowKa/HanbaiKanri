package com.showka.system.io;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonPropertyOrder(value = { "lineNumber", "errorMessage" })
public class CsvError {

	private static final String ls = System.getProperty("line.separator");

	/** 行番号. */
	@JsonProperty(value = "lineNumber")
	private Integer lineNumber;

	/** エラー原因. */
	@JsonIgnore
	private Exception cause;

	@JsonProperty(value = "errorMessage")
	public String getErrorMessage() {
		return this.cause.getMessage().split(ls)[0];
	}
}
