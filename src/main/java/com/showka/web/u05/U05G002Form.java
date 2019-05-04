package com.showka.web.u05;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.showka.web.FormBase;
import com.showka.web.MeisaiList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class U05G002Form extends FormBase {

	/**
	 * SID.
	 */
	private static final long serialVersionUID = 4225148289825600871L;

	/** 顧客コード. */
	private String kokyakuCode;

	/** 伝票番号. */
	private String denpyoNumber;

	/** 売上日. */
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date uriageDate;

	/** 販売区分. */
	private String hanbaiKubun;

	/** 売上明細. */
	private MeisaiList<U05G002MeisaiForm> meisaiList = new MeisaiList<U05G002MeisaiForm>();

	/** record_id */
	private String recordId;

	/** version. */
	private Integer version;

	/** 売掛version. */
	private Integer urikakeVersion;
}