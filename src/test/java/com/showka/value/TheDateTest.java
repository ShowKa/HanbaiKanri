package com.showka.value;

import org.junit.Test;

import com.showka.common.SimpleTestCase;

public class TheDateTest extends SimpleTestCase {

	// 日調整（通常）
	@Test
	public void test_WithDayOfMonth_01() throws Exception {
		// input
		TheDate date = new TheDate(2018, 2, 1);
		// do
		TheDate actual = date.withDayOfMonth(28);
		// check
		assertEquals(new TheDate(2018, 2, 28), actual);
	}

	// 日調整（月末調整）
	@Test
	public void test_WithDayOfMonth_02() throws Exception {
		// input
		TheDate date = new TheDate(2018, 2, 1);
		// do
		TheDate actual = date.withDayOfMonth(30);
		// check
		assertEquals(new TheDate(2018, 2, 28), actual);
	}

	// 日調整（厳密）
	@Test
	public void test_WithDayOfMonthStrictly_01() throws Exception {
		// input
		TheDate date = new TheDate(2018, 2, 1);
		// do
		TheDate actual = date.withDayOfMonthStrictly(28);
		// check
		assertEquals(new TheDate(2018, 2, 28), actual);
	}

	// 月日数取得
	@Test
	public void test_getLengthOfMonth_01() throws Exception {
		// input
		TheDate date = new TheDate(2000, 2, 1);
		// do
		int actual = date.getLengthOfMonth();
		// check
		assertEquals(29, actual);
	}

}
