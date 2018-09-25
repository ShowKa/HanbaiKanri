package com.showka.web.u08;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.showka.web.FormBase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class U08B005Form extends FormBase {

	/** SID. */
	private static final long serialVersionUID = -4746812490180975442L;

	/** 伝送日付. */
	@DateTimeFormat(pattern = "yyyyMMdd")
	private Date date;
}
