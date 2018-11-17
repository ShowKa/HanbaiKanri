package com.showka.service.search.u06;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.PersistenceTestCase;
import com.showka.domain.builder.KokyakuBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.entity.TUrikake;
import com.showka.value.EigyoDate;

public class UrikakeSearchServiceImplTest extends PersistenceTestCase {

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
	/** 売掛未請求状態 */
	private static final Object[] S_URIKAKE_NOT_YET_01 = { "r-KK01-00001", "r-KK01-00001" };
	/** 売掛請求済状態 */
	private static final Object[] S_URIKAKE_SEIKYU_DONE_01 = { "r-KK01-00001", "r-KK01-20180820", "r-KK01-00001" };
	/** 請求. */
	// 支払予定=2018/9/20
	private static final Object[] T_SEIKYU_01 = {
			"r-KK01",
			"r-BS01",
			"10",
			d("20180820"),
			d("20180920"),
			"r-KK01-20180820" };

	// 別顧客の売掛
	private static final Object[] T_URIKAKE_02 = { "r-KK02-00002", 1, d("20170101"), "r-KK02-00002" };
	private static final Object[] T_URIAGE_02 = {
			"r-KK02",
			"00002",
			new Date(),
			new Date(),
			"00",
			0.08,
			"r-KK02-00002" };
	private static final Object[] S_URIKAKE_NOT_YET_02 = { "r-KK02-00002", "r-KK02-00002" };
	private static final Object[] S_URIKAKE_SEIKYU_DONE_02 = { "r-KK02-00002", "r-KK02-20180820", "r-KK02-00002" };

	// 特定顧客の未請求状態の売掛のみが抽出できる。
	@Test
	public void test_getSeikyuNotYetEntity_01() throws Exception {
		// database
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, T_URIAGE_01, T_URIAGE_02);
		super.deleteAndInsert(T_URIKAKE, T_URIKAKE_COLUMN, T_URIKAKE_01, T_URIKAKE_02);
		super.deleteAndInsert(S_URIKAKE_SEIKYU_NOT_YET, S_URIKAKE_SEIKYU_NOT_YET_COLUMN, S_URIKAKE_NOT_YET_01,
				S_URIKAKE_NOT_YET_02);
		// input
		KokyakuBuilder kb = new KokyakuBuilder();
		kb.withRecordId("r-KK01");
		Kokyaku kokyaku = kb.build();
		// do
		List<TUrikake> actual = service.getSeikyuNotYetEntity(kokyaku);
		// check
		assertEquals(1, actual.size());
		assertEquals("r-KK01-00001", actual.get(0).getRecordId());
	}

	// 特定顧客の請求済状態の売掛のみが抽出できる。
	@Test
	public void test_getSeikyuDoneEntity_01() throws Exception {
		// database
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, T_URIAGE_01, T_URIAGE_02);
		super.deleteAndInsert(T_URIKAKE, T_URIKAKE_COLUMN, T_URIKAKE_01, T_URIKAKE_02);
		super.deleteAndInsert(S_URIKAKE_SEIKYU_DONE, S_URIKAKE_SEIKYU_DONE_COLUMN, S_URIKAKE_SEIKYU_DONE_01,
				S_URIKAKE_SEIKYU_DONE_02);
		// input
		KokyakuBuilder kb = new KokyakuBuilder();
		kb.withRecordId("r-KK01");
		Kokyaku kokyaku = kb.build();
		// do
		List<TUrikake> actual = service.getSeikyuDoneEntity(kokyaku);
		// check
		assertEquals(1, actual.size());
		assertEquals("r-KK01-00001", actual.get(0).getRecordId());
	}

	// 特定顧客の請求済状態&請求支払日が過ぎたものを抽出できる。
	@Test
	public void test_getSeikyuDoneEntity_delay_01() throws Exception {
		// database
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, T_URIAGE_01);
		super.deleteAndInsert(T_URIKAKE, T_URIKAKE_COLUMN, T_URIKAKE_01);
		super.deleteAndInsert(S_URIKAKE_SEIKYU_DONE, S_URIKAKE_SEIKYU_DONE_COLUMN, S_URIKAKE_SEIKYU_DONE_01);
		super.deleteAndInsert(T_SEIKYU, T_SEIKYU_COLUMN, T_SEIKYU_01);
		// input
		KokyakuBuilder kb = new KokyakuBuilder();
		kb.withRecordId("r-KK01");
		Kokyaku kokyaku = kb.build();
		// do
		List<TUrikake> actual = service.getSeikyuDoneButDelayedEntity(kokyaku, new EigyoDate(2018, 9, 20));
		// check
		assertEquals(1, actual.size());
		assertEquals("r-KK01-00001", actual.get(0).getRecordId());
	}

	// 特定顧客の請求済状態&請求支払日未踏のものは抽出対象とならない。
	@Test
	public void test_getSeikyuDoneEntity_delay_02() throws Exception {
		// database
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, T_URIAGE_01);
		super.deleteAndInsert(T_URIKAKE, T_URIKAKE_COLUMN, T_URIKAKE_01);
		super.deleteAndInsert(S_URIKAKE_SEIKYU_DONE, S_URIKAKE_SEIKYU_DONE_COLUMN, S_URIKAKE_SEIKYU_DONE_01);
		super.deleteAndInsert(T_SEIKYU, T_SEIKYU_COLUMN, T_SEIKYU_01);
		// input
		KokyakuBuilder kb = new KokyakuBuilder();
		kb.withRecordId("r-KK01");
		Kokyaku kokyaku = kb.build();
		// do
		List<TUrikake> actual = service.getSeikyuDoneButDelayedEntity(kokyaku, new EigyoDate(2018, 9, 19));
		// check
		assertEquals(0, actual.size());
	}
}
