package com.showka.domain.u01;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.NyukinKakeInfoBuilder;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.kubun.NyukinTsukiKubun;
import com.showka.value.TheDate;

public class NyukinKakeInfoTest extends SimpleTestCase {

	// 20日締、翌月30日入金
	// 基準日 2/20 -> 次回締日 2/20
	@Test
	public void test_GetNextSeikyuSimeDate_01() throws Exception {
		// domain
		NyukinKakeInfoBuilder nb = new NyukinKakeInfoBuilder();
		nb.withShimeDate(20);
		nb.withNyukinDate(30);
		nb.withNyukinHohoKubun(NyukinHohoKubun.口座振替);
		nb.withNyukinTsukiKubun(NyukinTsukiKubun.翌月);
		NyukinKakeInfo domain = nb.build();
		// input
		TheDate date = new TheDate(2018, 2, 20);
		// do
		TheDate actual = domain.getNextSeikyuSimeDate(date);
		// check
		assertEquals(date, actual);
	}

	// 20日締、翌月30日入金
	// 基準日 2/21 -> 次回締日 3/20
	@Test
	public void test_GetNextSeikyuSimeDate_02() throws Exception {
		// domain
		NyukinKakeInfoBuilder nb = new NyukinKakeInfoBuilder();
		nb.withShimeDate(20);
		nb.withNyukinDate(30);
		nb.withNyukinHohoKubun(NyukinHohoKubun.口座振替);
		nb.withNyukinTsukiKubun(NyukinTsukiKubun.翌月);
		NyukinKakeInfo domain = nb.build();
		// input
		TheDate date = new TheDate(2018, 2, 21);
		// do
		TheDate actual = domain.getNextSeikyuSimeDate(date);
		// check
		assertEquals(new TheDate(2018, 3, 20), actual);
	}

	// 20日締、翌月30日入金
	// 基準日 1/20 -> 入金予定日 2/28
	@Test
	public void test_getNyukinYoteiDate_01() throws Exception {
		// domain
		NyukinKakeInfoBuilder nb = new NyukinKakeInfoBuilder();
		nb.withShimeDate(20);
		nb.withNyukinDate(30);
		nb.withNyukinHohoKubun(NyukinHohoKubun.口座振替);
		nb.withNyukinTsukiKubun(NyukinTsukiKubun.翌月);
		NyukinKakeInfo domain = nb.build();
		// input
		TheDate date = new TheDate(2018, 1, 20);
		// do
		TheDate actual = domain.getNyukinYoteiDate(date);
		// check
		assertEquals(new TheDate(2018, 2, 28), actual);
	}

}
