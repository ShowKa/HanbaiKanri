package com.showka.web.u01;

import com.showka.web.FormBase;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class U01G002Form extends FormBase {

	/** 顧客コード */
	private String code;

	/** 顧客名 */
	private String name;

	/** 顧客住所 */
	private String address;

	/** 顧客区分 */
	private String kokyakuKubun;

	/** 販売区分 */
	private String hanbaiKubun;

	/** 主幹部署ID */
	private String shukanBushoCode;

	/** 入金方法区分 */
	private String nyukinHohoKubun;

	/** 入金月区分 */
	private String nyukinTsukiKubun;

	/** 請求締日 */
	private Integer shimebi;

	/** 入金日 */
	private Integer nyukinDate;

	/** 顧客情報を排他制御するためのversion */
	private Integer kokyakuVersion;

	/** 入金掛情報を排他制御するためのversion */
	private Integer nyukinKakeInfoVersion;

	/** 顧客レコードID */
	private String kokyakuRecordId;

	/** 入金掛け情報レコードID */
	private String nyukinKakeInfoRecordId;

}
