package com.showka.service.query.u11;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.PersistenceTestCase;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.builder.ShohinBuilder;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shohin;
import com.showka.entity.TShohinZaiko;
import com.showka.value.EigyoDate;

// 2あり
public class ShohinZaikoQueryImplTest extends PersistenceTestCase {

	@Autowired
	private ShohinZaikoQueryImpl service;

	/** 部署. */
	private static final Object[] M_BUSHO_01 = { "BS01", "01", "01", "部署01", "r-BS01" };

	/** 商品. */
	private static final Object[] M_SHOHIN_01 = { "SH01", "商品01", 10, "r-SH01" };

	/** 商品在庫01. */
	private static final Object[] T_SHOHIN_ZAIKO_01 = {
			"r-BS01",
			new EigyoDate(2017, 1, 1).toDate(),
			"r-SH01",
			100,
			"r-BS01-20170101-SH01" };

	@Test
	public void test_findEntity_01() throws Exception {
		// table
		super.deleteAndInsert(T_SHOHIN_ZAIKO, T_SHOHIN_ZAIKO_COLUMN, T_SHOHIN_ZAIKO_01);
		super.deleteAndInsert(M_SHOHIN, M_SHOHIN_COLUMN, M_SHOHIN_01);
		super.deleteAndInsert(M_BUSHO, M_BUSHO_COLUMN, M_BUSHO_01);
		// 部署
		Busho busho = new BushoBuilder().build();
		busho.setRecordId("r-BS01");
		// 日付
		EigyoDate date = new EigyoDate(2017, 1, 1);
		// 商品
		Shohin shohin = new ShohinBuilder().build();
		shohin.setRecordId("r-SH01");
		// do
		List<TShohinZaiko> actual = service.findEntity(busho, date);
		// check
		assertEquals(1, actual.size());
		assertEquals("r-BS01", actual.get(0).getBusho().getRecordId());
		assertEquals("r-SH01", actual.get(0).getShohin().getRecordId());
		assertEquals(new EigyoDate(2017, 1, 1).toDate(), actual.get(0).getEigyoDate());
		assertEquals(100, actual.get(0).getNumber().intValue());
	}
}
