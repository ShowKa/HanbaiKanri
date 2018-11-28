package com.showka.service.query.u08;

import org.jooq.Record;
import org.jooq.Result;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.CrudByJooqServiceTestCase;
import com.showka.service.query.u08.MatchedFBFurikomiQueryImpl;
import com.showka.value.TheDate;

public class MatchedFBFurikomiQueryImplTest2 extends CrudByJooqServiceTestCase {

	@Autowired
	private MatchedFBFurikomiQueryImpl service;

	// FB振込
	private static final Object[] fb01 = {
			d("20170820"),
			1,
			"001",
			"0000001",
			d("20170820"),
			1,
			"依頼人1",
			"r-20170820-01" };
	// 振分
	private static final Object[] fw01 = { "r-KK01-01", "r-KK01-20170101", 1, "r-KK01-01-001" };
	// マッチング
	private static final Object[] mt01 = { "r-20170820-01", "r-KK01-01-001", "r-20170820-01^KK01-01-001" };

	@Test
	public void test_query_01() throws Exception {
		// database
		super.deleteAndInsert(T_FIRM_BANK_FURIKOMI, T_FIRM_BANK_FURIKOMI_COLUMN, fb01);
		super.deleteAndInsert(W_FIRM_BANK_FURIWAKE, W_FIRM_BANK_FURIWAKE_COLUMN, fw01);
		super.deleteAndInsert(W_FIRM_BANK_FURIKOMI_MATCHING, W_FIRM_BANK_FURIKOMI_MATCHING_COLUMN, mt01);
		// input
		TheDate transmissionDate = new TheDate(2017, 8, 20);
		// do
		Result<Record> actual = service.query(transmissionDate);
		// check
		assertEquals(1, actual.size());
	}

}
