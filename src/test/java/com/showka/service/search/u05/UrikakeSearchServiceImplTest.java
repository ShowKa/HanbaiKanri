package com.showka.service.search.u05;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.CrudServiceTestCase;
import com.showka.domain.Urikake;

public class UrikakeSearchServiceImplTest extends CrudServiceTestCase {

	@Autowired
	private UrikakeSearchServiceImpl service;

	/** 顧客. */
	private static final Object[] M_KOKYAKU_01 = { "KK01", "aaaa", "左京区", "01", "10", "r-BS01", "r-KK01" };
	/** 入金掛売情報. */
	private static final Object[] M_NYUKIN_KAKE_IFNO_01 = { "r-KK01", "00", "10", "20", "30", "r-KK01" };
	/** 部署. */
	private static final Object[] M_BUSHO_01 = { "BS01", "01", "01", "部署01", "r-BS01" };
	/** 部署日付. */
	private static final Object[] M_BUSHO_DATE_01 = { "r-BS01", d("20160120"), "r-BS01" };
	/** 売上. */
	private static final Object[] T_URIAGE_01 = {
			"r-KK01",
			"00001",
			new Date(),
			new Date(),
			"00",
			0.08,
			"r-KK01-00001" };
	private static final Object[] T_URIAGE_02 = {
			"r-KK01",
			"00002",
			new Date(),
			new Date(),
			"00",
			0.08,
			"r-KK01-00002" };
	/** 売掛. */
	private static final Object[] T_URIKAKE_01 = { "r-KK01-00001", 1, d("20170101"), "r-KK01-00001" };
	private static final Object[] T_URIKAKE_02 = { "r-KK01-00002", 0, d("20170101"), "r-KK01-00002" };

	@Test
	public void test() throws Exception {
		// database
		super.deleteAndInsert(M_KOKYAKU, M_KOKYAKU_COLUMN, M_KOKYAKU_01);
		super.deleteAndInsert(M_NYUKIN_KAKE_INFO, M_NYUKIN_KAKE_INFO_COLUMN, M_NYUKIN_KAKE_IFNO_01);
		super.deleteAndInsert(M_BUSHO, M_BUSHO_COLUMN, M_BUSHO_01);
		super.deleteAndInsert(M_BUSHO_DATE, M_BUSHO_DATE_COLUMN, M_BUSHO_DATE_01);
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, T_URIAGE_01, T_URIAGE_02);
		super.deleteAndInsert(T_URIKAKE, T_URIKAKE_COLUMN, T_URIKAKE_01, T_URIKAKE_02);
		// do
		List<Urikake> actual = service.getUrikakeOfKokyaku("r-KK01");
		// check
		assertEquals(1, actual.size());
	}
}
