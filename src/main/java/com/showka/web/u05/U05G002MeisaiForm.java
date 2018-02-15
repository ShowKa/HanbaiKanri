package com.showka.web.u05;

import com.showka.web.MeisaiFormBase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class U05G002MeisaiForm extends MeisaiFormBase {

	/**
	 * SID.
	 */
	private static final long serialVersionUID = 8827535996218492194L;

	/** 明細番号. */
	private Integer meisaiNumber;

	/** 商品ID. */
	private String shohinCode;

	/** 売上数 */
	private Integer hanbaiNumber;

	/** 商品販売単価. */
	private Integer hanbaiTanka;

	/** version. */
	private Integer version;
}
