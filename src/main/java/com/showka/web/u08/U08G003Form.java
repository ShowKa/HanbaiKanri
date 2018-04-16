package com.showka.web.u08;

import java.util.List;

import com.showka.web.FormBase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class U08G003Form extends FormBase {

	/** SID. */
	private static final long serialVersionUID = -581972315754787852L;

	/** 入金ID. */
	private String nyukinId;

	/** 入金バージョン（排他制御用）. */
	private Integer version;

	/** 明細. */
	private List<U08G003FormMeisai> meisai;
}
