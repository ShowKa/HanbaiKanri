package com.showka.value;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;

import lombok.Getter;

/**
 * 時刻型 : 分まで保持
 *
 */
@Getter
public class TheTime extends ValueBase {
	private LocalTime time;

	/**
	 * コンストラクタ
	 * 
	 * <pre>
	 * 秒を0に調整する。
	 * </pre>
	 * 
	 * @param time
	 */
	public TheTime(LocalTime time) {
		this.time = time.withSecond(0).withNano(0);
	}

	/**
	 * Date型から生成
	 * 
	 * @param date
	 */
	public TheTime(java.util.Date date) {
		LocalTime t = date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
		this.time = t.withSecond(0);
	}

	/**
	 * カレンダーから生成
	 * 
	 * @param calendar
	 */
	public TheTime(Calendar calendar) {
		int h = calendar.get(Calendar.HOUR);
		int m = calendar.get(Calendar.MINUTE);
		this.time = LocalTime.of(h, m);
	}

	/**
	 * 数字から生成
	 */
	public TheTime(int hour, int minute) {
		this.time = LocalTime.of(hour, minute);
	}

	/**
	 * 同値判定
	 * 
	 * @return 時刻 が一致するならtrue
	 */
	@Override
	protected boolean equals(ValueBase other) {
		if (!(other instanceof TheTime)) {
			return false;
		}
		TheTime _other = (TheTime) other;
		return this.time.equals(_other.getTime());
	}

}
