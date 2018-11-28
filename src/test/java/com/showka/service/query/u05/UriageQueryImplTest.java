package com.showka.service.query.u05;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.PersistenceTestCase;
import com.showka.domain.u05.Uriage;

public class UriageQueryImplTest extends PersistenceTestCase {

	@Autowired
	private UriageQueryImpl service;

	/** 売上01 */
	private static final Object[] URIAGE_01 = { "r-KK01", "00001", new Date(), new Date(), "00", 0.08, "r-KK01-00001" };
	private static final Object[] URIAGE_02 = { "r-KK01", "00002", new Date(), new Date(), "00", 0.08, "r-KK01-00002" };

	/** 売上明細01 */
	private static final Object[] URIAGE_MEISAI_01 = { "r-KK01-00001", 1, "r-SH01", 5, 1000, "r-KK01-00001-1" };
	private static final Object[] URIAGE_MEISAI_02 = { "r-KK01-00002", 1, "r-SH01", 5, 1000, "r-KK01-00002-1" };

	/** 顧客01 */
	private static final Object[] KOKYAKU_01 = { "KK01", "顧客01", "左京区", "01", "00", "r-BS01", "r-KK01" };

	/** 部署01. */
	private static final Object[] M_BUSHO_01 = { "BS01", "01", "01", "部署01", "r-BS01" };

	/** 商品 */
	private static final Object[] M_SHOHIN_01 = { "SH01", "商品01", 10, "r-SH01" };

	@Test
	public void test10_getUriageOfKokyaku() throws Exception {
		// data
		super.deleteAndInsert(M_SHOHIN, M_SHOHIN_COLUMN, M_SHOHIN_01);
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, URIAGE_01, URIAGE_02);
		super.deleteAndInsert(M_KOKYAKU, M_KOKYAKU_COLUMN, KOKYAKU_01);
		super.deleteAndInsert(T_URIAGE_MEISAI, T_URIAGE_MEISAI_COLUMN, URIAGE_MEISAI_01, URIAGE_MEISAI_02);
		super.deleteAndInsert(M_BUSHO, M_BUSHO_COLUMN, M_BUSHO_01);
		List<Uriage> actual = service.get("KK01");
		assertEquals(2, actual.size());
	}

}
