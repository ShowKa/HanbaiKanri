package com.showka.value;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 時刻型 : ミリまで保持
 *
 */
public class TheTimestamp extends ValueBase implements Comparable<TheTimestamp> {

	private LocalDateTime timestamp;

	/**
	 * コンストラクタ.
	 * 
	 * <pre>
	 * 現在時刻のオブジェクト
	 * </pre>
	 */
	public TheTimestamp() {
		timestamp = LocalDateTime.now();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param timestamp
	 */
	public TheTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Date型から生成
	 * 
	 * @param date
	 */
	public TheTimestamp(java.util.Date date) {
		LocalDateTime t = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		this.timestamp = t;
	}

	/**
	 * カレンダーから生成
	 * 
	 * @param calendar
	 */
	public TheTimestamp(Calendar calendar) {
		int y = calendar.get(Calendar.YEAR);
		int mon = calendar.get(Calendar.MONTH + 1);
		int d = calendar.get(Calendar.DATE);
		int h = calendar.get(Calendar.HOUR);
		int m = calendar.get(Calendar.MINUTE);
		int s = calendar.get(Calendar.SECOND);
		int ms = calendar.get(Calendar.MILLISECOND);
		this.timestamp = LocalDateTime.of(y, mon, d, h, m, s, ms);
	}

	/**
	 * 年月日の数字から生成。ただし時刻は00:00:00.000
	 */
	public TheTimestamp(int year, int month, int day) {
		this.timestamp = LocalDateTime.of(year, month, day, 0, 0, 0, 0);
	}

	/**
	 * 数字から生成
	 */
	public TheTimestamp(int year, int month, int day, int hour, int minute, int second, int nanoSecond) {
		this.timestamp = LocalDateTime.of(year, month, day, hour, minute, second, nanoSecond);
	}

	/**
	 * Date型に変換.
	 * 
	 * @return Date
	 */
	public Date toDate() {
		return java.util.Date.from(this.timestamp.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * yyyy/MM/dd HH:mm:ss にフォーマット
	 * 
	 * @return フォーマット後のString
	 */

	public String formatToSecond() {
		DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		return timestamp.format(f);
	}

	/**
	 * 文字列フォーマット.
	 * 
	 * @param pattern
	 *            フォーマットパターン
	 * @return フォーマット後のString
	 */

	public String toString(String pattern) {
		DateTimeFormatter f = DateTimeFormatter.ofPattern(pattern);
		return timestamp.format(f);
	}

	/**
	 * 同値判定
	 * 
	 * @return 時刻 が一致するならtrue
	 */
	@Override
	protected boolean equals(ValueBase other) {
		if (!(other instanceof TheTimestamp)) {
			return false;
		}
		TheTimestamp _other = (TheTimestamp) other;
		return this.timestamp.equals(_other.timestamp);
	}

	@Override
	public String toString() {
		DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSSSSS");
		return timestamp.format(f);
	}

	@Override
	public int compareTo(TheTimestamp o) {
		return this.timestamp.compareTo(o.timestamp);
	}
}
