package com.showka.service.query.u08;

import java.util.List;

import org.jooq.Record;
import org.jooq.Result;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.CrudByJooqServiceTestCase;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.z00.Busho;
import com.showka.service.query.u08.FirmBankFurikomiQueryImpl;
import com.showka.value.TheDate;

public class FirmBankFurikomiQueryImplTest2 extends CrudByJooqServiceTestCase {

	@Autowired
	private FirmBankFurikomiQueryImpl service;

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
	private static final Object[] fb02 = {
			d("20170820"),
			2,
			"001",
			"0000001",
			d("20170820"),
			1,
			"依頼人1",
			"r-20170820-02" };
	private static final Object[] fb03 = {
			d("20170820"),
			3,
			"001",
			"0000001",
			d("20170820"),
			1,
			"依頼人1",
			"r-20170820-03" };
	// 部署銀行口座
	private static final Object[] ba01 = { "r-BS01", "001", "0000001", "r-BS01" };
	// 振分
	private static final Object[] fw01 = { "r-KK01-01", "r-KK01-20170101", 1, "r-KK01-01-001" };
	// 請求
	private static final Object[] ts01 = { "r-KK01", "r-BS01", "10", d("20170101"), d("20170201"), "r-KK01-20170101" };
	// 振込依頼人
	private static final Object[] fi01 = { "r-KK01", "依頼人1", "r-KK01-01" };
	// マッチング
	private static final Object[] mt01 = { "r-20170820-01", "r-KK01-01-001", "r-20170820-01^KK01-01-001" };
	// マッチングエラー
	private static final Object[] me01 = { "r-20170820-02", "01", "r-20170820-01" };

	@Test
	public void test_searchOf_01() throws Exception {
		// database
		super.deleteAndInsert(M_BUSHO_BANK_ACCOUNT, M_BUSHO_BANK_ACCOUNT_COLUMN, ba01);
		super.deleteAndInsert(T_FIRM_BANK_FURIKOMI, T_FIRM_BANK_FURIKOMI_COLUMN, fb01);
		super.deleteAndInsert(W_FIRM_BANK_FURIWAKE, W_FIRM_BANK_FURIWAKE_COLUMN, fw01);
		super.deleteAndInsert(T_SEIKYU, T_SEIKYU_COLUMN, ts01);
		super.deleteAndInsert(M_FURIKOMI_IRANIN, M_FURIKOMI_IRANIN_COLUMN, fi01);
		// input
		BushoBuilder bb = new BushoBuilder();
		bb.withRecordId("r-BS01");
		Busho busho = bb.build();
		TheDate date = new TheDate(2017, 8, 20);
		Result<Record> actual = service.searchOf(busho, date);
		assertEquals(1, actual.size());
	}

	@Test
	public void test_searchUnmathed_01() throws Exception {
		// database
		super.deleteAndInsert(T_FIRM_BANK_FURIKOMI, T_FIRM_BANK_FURIKOMI_COLUMN, fb01, fb02, fb03);
		super.deleteAndInsert(W_FIRM_BANK_FURIKOMI_MATCHING, W_FIRM_BANK_FURIKOMI_MATCHING_COLUMN, mt01);
		super.deleteAndInsert(W_FIRM_BANK_FURIKOMI_MATCHING_ERROR, W_FIRM_BANK_FURIKOMI_MATCHING_ERROR_COLUMN, me01);
		// input
		TheDate date = new TheDate(2017, 8, 20);
		List<String> actual = service.getUnmatched(date);
		assertEquals(1, actual.size());
		assertEquals("r-20170820-03", actual.get(0));
	}
}
