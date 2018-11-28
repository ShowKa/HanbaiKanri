package com.showka.service.query.u11;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.PersistenceTestCase;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shohin;
import com.showka.entity.TShohinIdoMeisai;
import com.showka.kubun.ShohinIdoKubun;
import com.showka.system.EmptyProxy;
import com.showka.value.EigyoDate;

// 他あり
public class ShohinIdoQueryImplTest extends PersistenceTestCase {

	@Autowired
	private ShohinIdoQueryImpl service;

	/** 商品移動01 */
	private static final Object[] T_SHOHIN_IDO_V1 = {
			"r-001",
			"r-BS01",
			new EigyoDate(2017, 8, 20).toDate(),
			"10",
			new Date(),
			"r-001" };

	/** 商品移動02 */
	private static final Object[] T_SHOHIN_IDO_V2 = {
			"r-002",
			"r-BS01",
			new EigyoDate(2017, 8, 20).toDate(),
			"11",
			new Date(),
			"r-002" };

	/** 商品移動03 */
	private static final Object[] T_SHOHIN_IDO_V3 = {
			"r-003",
			"r-BS01",
			new EigyoDate(2017, 8, 21).toDate(),
			"10",
			new Date(),
			"r-003" };

	/** 商品移動明細01. */
	private static final Object[] T_SHOHIN_IDO_MEISAI_V01 = { "r-001", 1, "r-SH01", 10, "r-001-1" };

	/** 商品移動明細02. */
	private static final Object[] T_SHOHIN_IDO_MEISAI_V02 = { "r-002", 1, "r-SH01", 11, "r-002-1" };

	/** 商品移動明細03. */
	private static final Object[] T_SHOHIN_IDO_MEISAI_V03 = { "r-003", 1, "r-SH01", 12, "r-003-1" };

	/** 部署01. */
	private static final Object[] VALUE01 = { "BS01", "01", "01", "部署01", "r-BS01" };

	@Test
	public void test_findIdoMeisaiEntity_01() throws Exception {
		// table
		super.deleteAndInsert(T_SHOHIN_IDO, T_SHOHIN_IDO_COLUMN, T_SHOHIN_IDO_V1, T_SHOHIN_IDO_V2, T_SHOHIN_IDO_V3);
		super.deleteAndInsert(T_SHOHIN_IDO_MEISAI, T_SHOHIN_IDO_MEISAI_COLUMN, T_SHOHIN_IDO_MEISAI_V01,
				T_SHOHIN_IDO_MEISAI_V02, T_SHOHIN_IDO_MEISAI_V03);
		super.deleteAndInsert(M_BUSHO, M_BUSHO_COLUMN, VALUE01);
		// data
		Shohin shohin = EmptyProxy.domain(Shohin.class);
		shohin.setRecordId("r-SH01");
		Busho busho = EmptyProxy.domain(Busho.class);
		busho.setRecordId("r-BS01");
		// do
		List<TShohinIdoMeisai> actual = service.findIdoMeisaiEntity(busho, new EigyoDate(2017, 8, 20), shohin);
		// check
		assertEquals(2, actual.size());
		actual.stream().filter(ido -> {
			return ido.getRecordId().equals("r-002");
		}).forEach(ido -> {
			String kubun = ido.getShohinIdo().getKubun();
			assertEquals(ShohinIdoKubun.売上訂正, kubun);
		});
	}
}