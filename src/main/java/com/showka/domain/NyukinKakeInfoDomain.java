package com.showka.domain;

import java.time.LocalDate;

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
public class NyukinKakeInfoDomain extends DomainBase {

	// private member
	/** ID */
	private String kokyakuId = STRING_EMPTY;

	/** 締日 */
	private Integer shimeDate = INTEGER_EMPTY;

	/** 入金月区分 */
	private NyukinTsukiKubun nyukinTsukiKubun = NyukinTsukiKubun.EMPTY;

	/** 入金方法 */
	private NyukinHohoKubun nyukinHohoKubun = NyukinHohoKubun.EMPTY;

	/** 入金日 */
	private Integer nyukinDate = INTEGER_EMPTY;

	/** バージョン(排他制御用) */
	private Integer version = INTEGER_EMPTY;

	// public method
	@Override
	public boolean isEmpty() {
		return false;
	}

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
	 * @param date
	 *            基準日
	 * @return 次の請求締日
	 */
	public TheDate getNextSeikyuSimeDate(TheDate date) {
		LocalDate _d = date.getDate();
		LocalDate shimeDateOfThisMonth = _d.withDayOfMonth(shimeDate);
		if (shimeDateOfThisMonth.getDayOfMonth() > shimeDate) {
			return new TheDate(shimeDateOfThisMonth);
		}
		// 今月締日が過ぎているので来月の締日を返却する。
		return new TheDate(shimeDateOfThisMonth.plusMonths(1));
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
		TheDate nextShimeDate = getNextSeikyuSimeDate(date);
		LocalDate nyukinMonth = nextShimeDate.getDate().plusMonths(nyukinTsukiKubun.getMonthSpan());
		return new TheDate(nyukinMonth.withDayOfMonth(nyukinDate));
	}

	/**
	 * 整合性チェック
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
	protected boolean equals(DomainBase other) {
		NyukinKakeInfoDomain o = (NyukinKakeInfoDomain) other;
		return kokyakuId.equals(o.kokyakuId);
	}

	@Override
	public int hashCode() {
		return kokyakuId.hashCode();
	}

	@Override
	public void validate() throws SystemException {
	}

}