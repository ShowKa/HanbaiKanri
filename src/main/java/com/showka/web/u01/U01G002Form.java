package com.showka.web.u01;

import com.showka.web.FormBase;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class U01G002Form extends FormBase {

	/** 顧客コード */
	private String kokyakuCode;

	/** 顧客名 */
	private String kokyakuName;

	/** 顧客住所 */
	private String kokyakuAddress;

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
	private int shimebi;

	/** 入金日 */
	private int nyukinDate;

}
