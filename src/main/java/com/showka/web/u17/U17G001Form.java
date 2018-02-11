package com.showka.web.u17;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.showka.web.FormBase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class U17G001Form extends FormBase {
	/** SID. */
	private static final long serialVersionUID = -1290861033857805268L;
	/** 部署コード. */
	private String bushoCode;
	/** 締め対象の営業日. */
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date eigyoDate;
}
