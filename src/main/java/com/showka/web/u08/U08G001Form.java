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
public class U08G001Form extends FormBase {

	/**
	 * SID.
	 */
	private static final long serialVersionUID = 5075354912272796805L;

	/** 顧客コード. */
	private String kokyakuCode;
	/** 部署コード. */
	private String bushoCode;
	/** 入金日. */
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date minNyukinDate;
	/** 入金日. */
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date maxNyukinDate;
	/** 金額. */
	private Integer minKingaku;
	/** 金額. */
	private Integer maxKingaku;
	/** 入金方法区分. */
	private String[] nyukinHoho = new String[] {};
	/** 消込完了含む. */
	private boolean includeKeshikomiDone;
	/** 担当社員コード. */
	private String tantoShainCode;

}
