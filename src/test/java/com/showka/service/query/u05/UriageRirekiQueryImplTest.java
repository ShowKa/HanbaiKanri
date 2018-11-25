package com.showka.service.query.u05;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.PersistenceTestCase;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.z00.Busho;
import com.showka.entity.RUriage;
import com.showka.value.EigyoDate;

// 2あり
@SuppressWarnings("deprecation")
public class UriageRirekiQueryImplTest extends PersistenceTestCase {

	@Autowired
	private UriageRirekiQueryImpl service;

	// 部署
	private static final Object[] M_BUSHO_01 = { "BS01", "01", "01", "部署01", "r-BS01" };
	// 顧客
	private static final Object[] M_KOKYAKU_01 = { "KK01", "顧客01", "左京区", "01", "00", "r-BS01", "r-KK01" };
	// 売上テーブル
	private static final Object[] T_URIAGE_01 = {
			"r-KK01",
			"00001",
			new Date(2017, 8, 19),
			new Date(2017, 8, 20),
			"00",
			0.08,
			"r-KK01-00001" };
	// 売上履歴テーブル
	private static final Object[] R_URIAGE_01 = {
			"r-KK01-00001",
			new Date("2017/08/19"),
			new Date("2017/08/19"),
			"00",
			0.08,
			"r-KK01-00001-20170819" };
	private static final Object[] R_URIAGE_02 = {
			"r-KK01-00001",
			new Date("2017/08/19"),
			new Date("2017/08/20"),
			"00",
			0.08,
			"r-KK01-00001-20170820" };

	@Test
	@Transactional
	public void test01_search() throws Exception {
		// database
		super.deleteAndInsert(R_URIAGE, R_URIAGE_COLUMN, R_URIAGE_01, R_URIAGE_02);
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, T_URIAGE_01);
		super.deleteAndInsert(M_KOKYAKU, M_KOKYAKU_COLUMN, M_KOKYAKU_01);
		super.deleteAndInsert(M_BUSHO, M_BUSHO_COLUMN, M_BUSHO_01);
		// input
		// 部署
		BushoBuilder bb = new BushoBuilder();
		bb.withRecordId("r-BS01");
		Busho busho = bb.build();
		// 計上日
		EigyoDate date = new EigyoDate(2017, 8, 19);
		// do
		List<RUriage> actual = service.getEntityList(busho, date);
		// check
		assertEquals(1, actual.size());
	}

	@Test
	public void test02_GetUriage() throws Exception {
		// insert data
		super.deleteAndInsert(R_URIAGE, R_URIAGE_COLUMN, R_URIAGE_01, R_URIAGE_02);
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, T_URIAGE_01);
		super.deleteAndInsert(M_KOKYAKU, M_KOKYAKU_COLUMN, M_KOKYAKU_01);
		// do
		List<RUriage> actual = service.findAllById("r-KK01-00001");
		// check
		assertEquals(2, actual.size());
	}
}
