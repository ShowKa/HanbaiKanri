package com.showka.kubun;

import com.showka.kubun.i.Kubun;
import com.showka.system.exception.SystemException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NyukinTsukiKubun implements Kubun<NyukinTsukiKubun> {
	当月("00"), 翌月("10"), 翌々月("20"), EMPTY("");
	String code;

	/**
	 * 当月=0として、月のスパンを返却する
	 * 
	 * @return
	 */
	public int getMonthSpan() {
		switch (this) {
		case 当月:
			return 0;
		case 翌月:
			return 1;
		case 翌々月:
			return 2;
		case EMPTY:
			return 0;
		default:
			break;
		}
		throw new SystemException("存在しない区分");
	}
}
