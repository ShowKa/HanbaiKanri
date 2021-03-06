package com.showka.web.u11;

import com.showka.web.MeisaiFormBase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class U11G003MeisaiForm extends MeisaiFormBase {

	/** SID. */
	private static final long serialVersionUID = -3963136864166877183L;

	/** 商品コード. */
	private String shohinCode;

	/** 入荷数. */
	private Integer nyukaSu;

}
