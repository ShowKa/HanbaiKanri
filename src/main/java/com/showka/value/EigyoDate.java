package com.showka.value;

import java.time.LocalDate;
import java.util.Calendar;

public class EigyoDate extends TheDate {

	public EigyoDate() {
		super();
	}

	public EigyoDate(Calendar calendar) {
		super(calendar);
	}

	public EigyoDate(int year, int month, int date) {
		super(year, month, date);
	}

	public EigyoDate(LocalDate date) {
		super(date);
	}

	public EigyoDate(java.util.Date date) {
		super(date);
	}

}
