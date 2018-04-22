package com.showka.web.u05;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.showka.web.FormBase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class U05G001Form extends FormBase {

	/** SID. */
	private static final long serialVersionUID = 5259805324298992220L;

	/** 顧客コード. */
	private String kokyakuCode;

	/** 売上日（from). */
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date from;

	/** 売上日（to). */
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date to;

	/** 売掛残. */
	private boolean existsUrikake;
}
