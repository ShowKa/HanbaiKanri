package com.showka.service.search.u07;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.CrudByJooqServiceTestCase;
import com.showka.domain.Busho;
import com.showka.domain.builder.BushoBuilder;
import com.showka.table.public_.tables.records.T_SEIKYU_RECORD;

public class SeikyuSearchServiceImplTest2 extends CrudByJooqServiceTestCase {

	@Autowired
	private SeikyuSearchServiceImpl service;

	/** 請求. */
	private static final Object[] T_SEIKYU_01 = {
			"r-KK01",
			"r-BS01",
			"10",
			d("20170101"),
			d("20170201"),
			"r-KK01-20170101" };
	/** 請求が有効でない（売掛の消込完了） */
	private static final Object[] T_SEIKYU_02 = {
			"r-KK01",
			"r-BS01",
			"10",
			d("20170201"),
			d("20170301"),
			"r-KK01-20170201" };
	/** 別部署の請求 */
	private static final Object[] T_SEIKYU_03 = {
			"r-KK02",
			"r-BS02",
			"10",
			d("20170201"),
			d("20170301"),
			"r-KK02-20170201" };
	/** 請求売掛 */
	private static final Object[] J_SEIKYU_URIKAKE_01 = { "r-001", "r-KK01-20170101", "r-001-KK01-20170101" };
	private static final Object[] J_SEIKYU_URIKAKE_03 = { "r-002", "r-KK02-20170201", "r-002-KK02-20170201" };

	@Test
	public void test_getAllRecordsOf_01() {
		// database
		super.deleteAndInsert(T_SEIKYU, T_SEIKYU_COLUMN, T_SEIKYU_01, T_SEIKYU_02, T_SEIKYU_03);
		super.deleteAndInsert(J_SEIKYU_URIKAKE, J_SEIKYU_URIKAKE_COLUMN, J_SEIKYU_URIKAKE_01, J_SEIKYU_URIKAKE_03);
		// input
		BushoBuilder bb = new BushoBuilder();
		bb.withRecordId("r-BS01");
		Busho busho = bb.build();
		// do
		List<T_SEIKYU_RECORD> actual = service.getAllRecordsOf(busho);
		// assert
		assertEquals(1, actual.size());
		assertEquals("r-KK01-20170101", actual.get(0).getRecordId());
	}
}
