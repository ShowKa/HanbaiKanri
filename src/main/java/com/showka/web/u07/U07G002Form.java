package com.showka.web.u07;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.showka.web.FormBase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class U07G002Form extends FormBase {
	/** SID. */
	private static final long serialVersionUID = -7099827747103381192L;
	/** 顧客コード. */
	private String kokyakuCode;
	/** 請求日. */
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date seikyuDate;
}
