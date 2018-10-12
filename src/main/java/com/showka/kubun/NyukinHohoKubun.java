package com.showka.kubun;

import com.showka.kubun.i.Kubun;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NyukinHohoKubun implements Kubun<NyukinHohoKubun> {
	集金("00"), 振込("10"), 口座振替("20"), EMPTY("");
	String code;

	/**
	 * 現金入金判定
	 * 
	 * @return 現金入金の場合、true
	 */
	public boolean isGenkin() {
		return this == 集金;
	}

	/**
	 * 振込入金判定
	 * 
	 * @return 振込入金の場合、true
	 */
	public boolean isFurikomi() {
		return this.isIncludedIn(振込, 口座振替);
	}
}
