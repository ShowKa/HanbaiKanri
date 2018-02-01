package com.showka.value;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TheDate extends ValueBase implements Comparable<TheDate> {

	private LocalDate date = LocalDate.MIN;

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

	public java.util.Date toDate() {
		return java.util.Date.from(this.date.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public Calendar toCalendar() {
		Calendar c = Calendar.getInstance();
		c.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
		return c;
	}

	public boolean isAfter(TheDate other) {
		return this.date.isAfter(other.date);
	}

	public boolean isBefore(TheDate other) {
		return this.date.isBefore(other.date);
	}

	public boolean isEqual(TheDate other) {
		return this.date.isEqual(other.date);
	}

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
	public String toString() {
		return date.toString();
	}

	@Override
	public int compareTo(TheDate o) {
		return date.compareTo(o.date);
	}

}
