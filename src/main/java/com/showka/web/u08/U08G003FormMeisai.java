package com.showka.web.u08;

import com.showka.web.FormBase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class U08G003FormMeisai extends FormBase {

	/** SID. */
	private static final long serialVersionUID = -423046719788412124L;

	/** 売掛ID. */
	private String urikakeId;

	/** 消込ID. */
	private String keshikomiId;

	/** 消込金額. */
	private Integer kingaku;
}
