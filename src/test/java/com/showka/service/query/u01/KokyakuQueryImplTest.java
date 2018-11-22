package com.showka.service.query.u01;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.PersistenceTestCase;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.z00.Busho;
import com.showka.service.query.u01.KokyakuQueryImpl;
import com.showka.value.ShimeDate;

public class KokyakuQueryImplTest extends PersistenceTestCase {

	@Autowired
	private KokyakuQueryImpl service;

	/** 顧客. */
	private static final Object[] M_KOKYAKU_01 = { "KK01", "aaaa", "左京区", "01", "10", "r-BS01", "r-KK01" };
	/** 入金掛売情報. */
	private static final Object[] M_NYUKIN_KAKE_IFNO_01 = { "r-KK01", "00", "10", "20", "30", "r-KK01" };
	/** 部署. */
	private static final Object[] M_BUSHO_01 = { "BS01", "01", "01", "部署01", "r-BS01" };
	/** 部署日付. */
	private static final Object[] M_BUSHO_DATE_01 = { "r-BS01", d("20160120"), "r-BS01" };

	@Test
	public void test01_getKokyakuOnShimeDate() throws Exception {
		// database
		super.deleteAndInsert(M_KOKYAKU, M_KOKYAKU_COLUMN, M_KOKYAKU_01);
		super.deleteAndInsert(M_NYUKIN_KAKE_INFO, M_NYUKIN_KAKE_INFO_COLUMN, M_NYUKIN_KAKE_IFNO_01);
		super.deleteAndInsert(M_BUSHO, M_BUSHO_COLUMN, M_BUSHO_01);
		super.deleteAndInsert(M_BUSHO_DATE, M_BUSHO_DATE_COLUMN, M_BUSHO_DATE_01);
		// input
		// 部署
		BushoBuilder bb = new BushoBuilder();
		bb.withRecordId("r-BS01");
		Busho busho = bb.build();
		// 締日
		ShimeDate shimeDate = new ShimeDate(20);
		List<ShimeDate> shimeDates = Arrays.asList(shimeDate);
		// do
		List<Kokyaku> actual = service.getOnShimeDate(busho, shimeDates);
		// check
		assertEquals(1, actual.size());
		assertEquals("r-KK01", actual.get(0).getRecordId());
	}
}
