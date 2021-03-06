package com.showka.domain.u01;

import com.showka.domain.DomainSub;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.kubun.NyukinTsukiKubun;
import com.showka.system.exception.SystemException;
import com.showka.value.TheDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 入金掛情報 Doman
 * 
 * @author ShowKa
 *
 */
@AllArgsConstructor
@Getter
public class NyukinKakeInfo extends DomainSub {

	// private member
	/** 締日 */
	private Integer shimeDate;

	/** 入金月区分 */
	private NyukinTsukiKubun nyukinTsukiKubun;

	/** 入金方法 */
	private NyukinHohoKubun nyukinHohoKubun;

	/** 入金日 */
	private Integer nyukinDate;

	/**
	 * 入金サイト取得
	 * 
	 * <pre>
	 * 入金サイト = 請求締日から入金予定日までの日数
	 * </pre>
	 * 
	 * @return 入金サイト
	 */
	public Integer getNyukinSight() {
		return (30 * nyukinTsukiKubun.getMonthSpan()) + (nyukinDate - shimeDate);
	}

	/**
	 * 引数で渡した日付を基準にして、次の請求締日を取得
	 * 
	 * <pre>
	 * 引数.日付 = 今月締日 -> そのまま今月締日を返却.
	 * 
	 * <pre>
	 * 
	 * @param date
	 *            基準日
	 * @return 次の請求締日
	 */
	public TheDate getNextSeikyuSimeDate(TheDate date) {
		TheDate shimeDateOfThisMonth = date.withDayOfMonth(shimeDate);
		if (shimeDateOfThisMonth.isAfterOrEq(date)) {
			return shimeDateOfThisMonth;
		}
		// 今月締日が過ぎているので来月の締日を返却する。
		return shimeDateOfThisMonth.plusMonths(1);
	}

	/**
	 * 
	 * 引数で渡した日付を基準にして、入金予定日を取得する。
	 * 
	 * @param date
	 *            基準日
	 * @return 入金予定日
	 */
	public TheDate getNyukinYoteiDate(TheDate date) {
		// 基準日より直近の締日を取得
		TheDate nextShimeDate = getNextSeikyuSimeDate(date);
		// 入金月区分だけ月数を加算
		TheDate nyukinMonth = nextShimeDate.plusMonths(nyukinTsukiKubun.getMonthSpan());
		return nyukinMonth.withDayOfMonth(nyukinDate);
	}

	/**
	 * 請求締日と入金日の前後関係を判定する.
	 * 
	 * <pre>
	 * 当月入金なのに、締日と入金日が前後していたらfalse
	 * </pre>
	 * 
	 * @return
	 */
	public boolean shimeDateBeforeNyukinDate() {
		if (this.nyukinTsukiKubun.isIncludedIn(NyukinTsukiKubun.翌月, NyukinTsukiKubun.翌々月)) {
			return true;
		}
		if (nyukinDate.compareTo(shimeDate) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void validate() throws SystemException {
	}

}
