package com.showka.web.u08;

import java.util.Date;

import com.showka.web.FormBase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class U08B003Form extends FormBase {

	/** SID. */
	private static final long serialVersionUID = 7468917606321424157L;

	/** 部署コード. */
	private String bushoCode;

	/** 伝送日付. */
	private Date date;
}
