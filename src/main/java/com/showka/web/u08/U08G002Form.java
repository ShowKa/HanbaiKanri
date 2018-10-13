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
public class U08G002Form extends FormBase {

	/**
	 * SID.
	 */
	private static final long serialVersionUID = 220645743326995288L;

	/** 顧客コード. */
	private String kokyakuCode;
	/** 部署コード. */
	private String bushoCode;
	/** 伝票番号. */
	private String denpyoNumber;
	/** 入金日. */
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date nyukinDate;
	/** 担当社員コード. */
	private String tantoShainCode;
	/** 金額. */
	private Integer kingaku;
	/** 入金レコードのバージョン(排他制御用、集金でなくてよい) */
	private Integer version;
	/** 入金ID(集金ID). */
	private String nyukinId;
}
