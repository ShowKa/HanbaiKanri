package com.showka.web;

import java.util.HashMap;

import com.showka.value.AmountOfMoney;
import com.showka.value.TheDate;

public class MavMap extends HashMap<String, Object> {

	/** SID. */
	private static final long serialVersionUID = 5068310650339030231L;

	/**
	 * 日付追加（文字列化を行う）.
	 * 
	 * @param key
	 *            key
	 * @param date
	 *            日付
	 * @return
	 */
	public Object put(String key, TheDate date) {
		return super.put(key, date.toString());
	}

	/**
	 * 金額追加（フォーマット&文字列化実施）.
	 * 
	 * @param key
	 *            key
	 * @param amountOfMoney
	 *            金額
	 * @return
	 */
	public Object put(String key, AmountOfMoney amountOfMoney) {
		return super.put(key, amountOfMoney.getFormatted());
	}
}
