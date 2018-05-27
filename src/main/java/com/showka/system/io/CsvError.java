package com.showka.system.io;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CsvError {

	/** 行番号. */
	private Integer lineNumber;

	/** エラー原因. */
	private Exception cause;
}
