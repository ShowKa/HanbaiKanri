package com.showka.value;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TheDate extends ValueBase implements Comparable<TheDate> {

	// member
	private LocalDate date = LocalDate.now();

	// constructor
	public TheDate(java.util.Date date) {
		this.date = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public TheDate(Calendar calendar) {
		int y = calendar.get(Calendar.YEAR);
		int m = calendar.get(Calendar.MONTH + 1);
		int d = calendar.get(Calendar.DATE);
		this.date = LocalDate.of(y, m, d);
	}

	public TheDate(int year, int month, int dayOfMonth) {
		this.date = LocalDate.of(year, month, dayOfMonth);
	}

	// cast to other type
	public java.util.Date toDate() {
		return java.util.Date.from(this.date.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public LocalDateTime toLocalDateTime() {
		return date.atTime(LocalTime.MIN);
	}

	public Calendar toCalendar() {
		Calendar c = Calendar.getInstance();
		c.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
		return c;
	}

	public Timestamp toTimestamp() {
		LocalDateTime localDateTime = date.atTime(LocalTime.MIN);
		return Timestamp.valueOf(localDateTime);
	}

	// cast to LocalDateTime
	public LocalDateTime atTime(int hour, int minute, int second) {
		return this.date.atTime(hour, minute, second);
	}

	public LocalDateTime atStartOfDay() {
		return this.date.atStartOfDay();
	}

	// compare
	public boolean isAfter(TheDate other) {
		return this.date.isAfter(other.date);
	}

	public boolean isAfterOrEq(TheDate other) {
		return !this.isBefore(other);
	}

	public boolean isBefore(TheDate other) {
		return this.date.isBefore(other.date);
	}

	public boolean isBeforeOrEq(TheDate other) {
		return !this.isAfter(other);
	}

	public boolean isEqual(TheDate other) {
		return this.date.isEqual(other.date);
	}

	// roll days
	public TheDate plusDays(long daysToAdd) {
		return new TheDate(this._plusDays(daysToAdd));
	}

	public TheDate plusMonths(long monthsToAdd) {
		return new TheDate(this._plusMonths(monthsToAdd));
	}

	public TheDate withDayOfMonth(int dayOfMonth) {
		return new TheDate(this._withDayOfMonth(dayOfMonth));
	}

	public TheDate getLastDateOfMonth() {
		return new TheDate(this._getLastDateOfMonth());
	}

	public TheDate getFirstDateOfMonth() {
		return new TheDate(this._getFirstDateOfMonth());
	}

	protected LocalDate _plusDays(long daysToAdd) {
		return this.date.plusDays(daysToAdd);
	}

	protected LocalDate _plusMonths(long monthsToAdd) {
		return this.date.plusMonths(monthsToAdd);
	}

	protected LocalDate _withDayOfMonth(int dayOfMonth) {
		return this.date.withDayOfMonth(dayOfMonth);
	}

	protected LocalDate _getFirstDateOfMonth() {
		return this._withDayOfMonth(1);
	}

	protected LocalDate _getLastDateOfMonth() {
		return this._withDayOfMonth(date.lengthOfMonth());
	}

	// getter
	public String getDayOfWeek() {
		Locale locale = Locale.getDefault();
		DateTimeFormatter formatterOutput = DateTimeFormatter.ofPattern("E").withLocale(locale);
		return formatterOutput.format(this.date);
	}

	public int getDayOfMonth() {
		return date.getDayOfMonth();
	}

	public Month getMonth() {
		return date.getMonth();
	}

	// validate
	public boolean isFirstDateOfMonth() {
		return this.getDayOfMonth() == 1;
	}

	public boolean isLastDateOfMonth() {
		return this.getDayOfMonth() == this.getLastDateOfMonth().getDayOfMonth();
	}

	public boolean isWeekDay() {
		return !this.isWeekend();
	}

	public boolean isWeekend() {
		DayOfWeek dow = this.date.getDayOfWeek();
		return dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY;
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
		return date.format(f);
	}

	// override
	@Override
	public boolean isEmpty() {
		if (this.date == null) {
			return true;
		}
		return false;
	}

	@Override
	protected boolean equals(ValueBase other) {
		if (!(other instanceof TheDate)) {
			return false;
		}
		TheDate _other = (TheDate) other;
		return this.date.equals(_other.date);
	}

	@Override
	public int hashCode() {
		return date.hashCode();
	}

	@Override
	public String toString() {
		DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		return date.format(f);
	}

	@Override
	public int compareTo(TheDate o) {
		return date.compareTo(o.date);
	}
}
