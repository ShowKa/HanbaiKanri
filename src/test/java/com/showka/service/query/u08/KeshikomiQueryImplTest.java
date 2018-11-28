package com.showka.service.query.u08;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.PersistenceTestCase;
import com.showka.entity.TKeshikomi;

public class KeshikomiQueryImplTest extends PersistenceTestCase {

	@Autowired
	private KeshikomiQueryImpl service;

	private static final Object[] T_KESHIKOMI_01 = { "r-001", d("20170101"), 1000, "r-001", "r-KK01-00001", "r-001" };
	private static final Object[] T_KESHIKOMI_02 = { "r-002", d("20170101"), 2000, "r-001", "r-KK01-00002", "r-002" };

	@Test
	public void test01_getKeshikomiSetOfNyukin() throws Exception {
		// database
		super.deleteAndInsert(T_KESHIKOMI, T_KESHIKOMI_COLUMN, T_KESHIKOMI_01, T_KESHIKOMI_02);
		// do
		List<TKeshikomi> actual = service.findEntityOfNyukin("r-001");
		assertEquals(2, actual.size());
	}

	@Test
	public void test01_getKeshikomiSetOfUrikake() throws Exception {
		// database
		super.deleteAndInsert(T_KESHIKOMI, T_KESHIKOMI_COLUMN, T_KESHIKOMI_01, T_KESHIKOMI_02);
		// do
		List<TKeshikomi> actual = service.findEntityOfUrikake("r-KK01-00001");
		assertEquals(1, actual.size());
	}
}
