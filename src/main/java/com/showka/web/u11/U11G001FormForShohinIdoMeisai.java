package com.showka.web.u11;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.showka.web.FormBase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class U11G001FormForShohinIdoMeisai extends FormBase {
	/** SID. */
	private static final long serialVersionUID = -532640114536996242L;
	/** 部署コード. */
	private String bushoCode;
	/** 商品移動日. */
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date date;
	/** 商品コード./ */
	private String shohinCode;
}
