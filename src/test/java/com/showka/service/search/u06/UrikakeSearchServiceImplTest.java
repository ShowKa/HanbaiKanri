package com.showka.service.search.u06;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.CrudServiceTestCase;
import com.showka.domain.u06.Urikake;
import com.showka.entity.TUrikake;

import mockit.Injectable;

public class UrikakeSearchServiceImplTest extends CrudServiceTestCase {

	@Autowired
	private UrikakeSearchServiceImpl service;

	/** 売掛. */
	private static final Object[] T_URIKAKE_01 = { "r-KK01-00001", 1, d("20170101"), "r-KK01-00001" };
	/** 売上. */
	private static final Object[] T_URIAGE_01 = {
			"r-KK01",
			"00001",
			new Date(),
			new Date(),
			"00",
			0.08,
			"r-KK01-00001" };
	// 別顧客の売掛
	private static final Object[] T_URIKAKE_02 = { "r-KK01-00002", 1, d("20170101"), "r-KK01-00002" };
	private static final Object[] T_URIAGE_02 = {
			"r-KK02",
			"00002",
			new Date(),
			new Date(),
			"00",
			0.08,
			"r-KK01-00002" };

	@Test
	public void test01_getUrikakeOfKokyaku(@Injectable Urikake _urikake) throws Exception {
		// database
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, T_URIAGE_01, T_URIAGE_02);
		super.deleteAndInsert(T_URIKAKE, T_URIKAKE_COLUMN, T_URIKAKE_01, T_URIKAKE_02);
		// do
		List<TUrikake> actual = service.getUrikakeOfKokyakuRecord("r-KK01");
		// check
		assertEquals(1, actual.size());
		assertEquals("r-KK01-00001", actual.get(0).getRecordId());
	}
}
