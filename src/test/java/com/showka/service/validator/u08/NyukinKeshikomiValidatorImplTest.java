package com.showka.service.validator.u08;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.KeshikomiBuilder;
import com.showka.domain.builder.NyukinBuilder;
import com.showka.domain.builder.UrikakeBuilder;
import com.showka.domain.u06.Urikake;
import com.showka.domain.u08.Keshikomi;
import com.showka.domain.u08.Nyukin;
import com.showka.domain.u08.NyukinKeshikomi;
import com.showka.service.query.u06.i.UrikakeKeshikomiQuery;
import com.showka.system.exception.ValidateException;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;

public class NyukinKeshikomiValidatorImplTest extends SimpleTestCase {

	@Tested
	@Injectable
	private NyukinKeshikomiValidatorImpl service;

	@Injectable
	private UrikakeKeshikomiQuery urikakeKeshikomiPersistence;

	/**
	 * 各整合性検証用メソッドの呼出確認
	 */
	@Test
	public void test00_validate(@Injectable NyukinKeshikomi nyukinKeshikomi) throws Exception {
		// expect
		new Expectations() {
			{
				service.validateKingakuRange(nyukinKeshikomi);
				times = 1;
				service.validateKeshikomiKingakuGokei(nyukinKeshikomi);
				times = 1;
				service.validateKeshikomiKingaku(nyukinKeshikomi);
				times = 1;
				service.validateUrikakeDuplication(nyukinKeshikomi);
				times = 1;
			}
		};
		// do
		service.validate(nyukinKeshikomi);
	}

	/**
	 * 消込.金額合計 > 入金金額 -> エラー
	 */
	@Test(expected = ValidateException.class)
	public void test01_validateKeshikomiKingakuGokei(@Injectable NyukinKeshikomi nyukinKeshikomi) throws Exception {
		// input
		// 消込金額合計
		AmountOfMoney keshikomiKingakuGokei = new AmountOfMoney(1001);
		// 入金
		NyukinBuilder nb = new NyukinBuilder();
		nb.withKingaku(1000);
		Nyukin nyukin = nb.build();
		// expect
		new Expectations() {
			{
				// 消込金額合計取得
				nyukinKeshikomi.getKeshikomiKingakuGokei();
				result = keshikomiKingakuGokei;
				// 入金
				nyukinKeshikomi.getNyukin();
				result = nyukin;
			}
		};
		// do
		service.validateKeshikomiKingakuGokei(nyukinKeshikomi);
	}

	/**
	 * 消込.金額合計 = 入金金額 -> 正常終了
	 */
	@Test
	public void test02_validateKeshikomiKingakuGokei(@Injectable NyukinKeshikomi nyukinKeshikomi) throws Exception {
		// input
		// 消込金額合計
		AmountOfMoney keshikomiKingakuGokei = new AmountOfMoney(1000);
		// 入金
		NyukinBuilder nb = new NyukinBuilder();
		nb.withKingaku(1000);
		Nyukin nyukin = nb.build();
		// expect
		new Expectations() {
			{
				// 消込金額合計取得
				nyukinKeshikomi.getKeshikomiKingakuGokei();
				result = keshikomiKingakuGokei;
				// 入金
				nyukinKeshikomi.getNyukin();
				result = nyukin;
			}
		};
		// do
		service.validateKeshikomiKingakuGokei(nyukinKeshikomi);
		// verify
		new Verifications() {
			{
				// 消込金額合計取得
				nyukinKeshikomi.getKeshikomiKingakuGokei();
				times = 1;
				// 入金
				nyukinKeshikomi.getNyukin();
				times = 1;
			}
		};
	}

	/**
	 * 消込.金額 > 売掛.残高 -> エラー
	 */
	@Test(expected = ValidateException.class)
	public void test03_validateKeshikomiKingaku(@Injectable NyukinKeshikomi nyukinKeshikomi) throws Exception {
		// input
		// 売掛
		UrikakeBuilder ub = new UrikakeBuilder();
		ub.withKingaku(1000);
		Urikake urikake = ub.build();
		// 消込
		KeshikomiBuilder kb = new KeshikomiBuilder();
		kb.withKingaku(1001);
		kb.withUrikake(urikake);
		Keshikomi keshikomi = kb.build();
		// 消込マップ
		Set<Keshikomi> keshikomiSet = new HashSet<Keshikomi>();
		keshikomiSet.add(keshikomi);
		// expect
		new Expectations() {
			{
				// 消込マップ取得
				nyukinKeshikomi.getKeshikomiSet();
				result = keshikomiSet;
			}
		};
		// do
		service.validateKeshikomiKingaku(nyukinKeshikomi);
	}

	/**
	 * 消込.金額 = 売掛.残高 -> 正常終了
	 */
	@Test
	public void test04_validateKeshikomiKingaku(@Injectable NyukinKeshikomi nyukinKeshikomi) throws Exception {
		// input
		// 売掛
		UrikakeBuilder ub = new UrikakeBuilder();
		ub.withKingaku(1000);
		Urikake urikake = ub.build();
		// 消込
		KeshikomiBuilder kb = new KeshikomiBuilder();
		kb.withKingaku(1000);
		kb.withUrikake(urikake);
		Keshikomi keshikomi = kb.build();
		// 消込マップ
		Set<Keshikomi> keshikomiSet = new HashSet<Keshikomi>();
		keshikomiSet.add(keshikomi);
		// expect
		new Expectations() {
			{
				// 消込マップ取得
				nyukinKeshikomi.getKeshikomiSet();
				result = keshikomiSet;
				urikakeKeshikomiPersistence.getAsOf(urikake, keshikomi);
				result = new AmountOfMoney(1000);
			}
		};
		// do
		service.validateKeshikomiKingaku(nyukinKeshikomi);
		// verify
		new Verifications() {
			{
				// 消込マップ取得
				nyukinKeshikomi.getKeshikomiSet();
				times = 1;
				urikakeKeshikomiPersistence.getAsOf(urikake, keshikomi);
				times = 1;
			}
		};
	}

	/**
	 * 同一日に同一の売掛が消し込まれている -> エラー
	 */
	@Test(expected = ValidateException.class)
	public void test05_validateUrikakeDuplication(@Injectable NyukinKeshikomi nyukinKeshikomi, @Mocked Urikake urikake)
			throws Exception {
		// input
		// 消込1
		KeshikomiBuilder kb1 = new KeshikomiBuilder();
		kb1.withKingaku(1000);
		kb1.withRecordId("r-001");
		kb1.withUrikake(urikake);
		kb1.withDate(new EigyoDate(2017, 1, 1));
		Keshikomi keshikomi1 = kb1.build();
		// 消込2
		KeshikomiBuilder kb2 = new KeshikomiBuilder();
		kb2.withKingaku(1000);
		kb2.withRecordId("r-002");
		kb2.withUrikake(urikake);
		kb2.withDate(new EigyoDate(2017, 1, 1));
		Keshikomi keshikomi2 = kb2.build();
		// 売掛
		// 消込マップ
		Set<Keshikomi> keshikomiSet = new HashSet<Keshikomi>();
		keshikomiSet.add(keshikomi1);
		keshikomiSet.add(keshikomi2);
		// expect
		new Expectations() {
			{
				// 消込セット取得
				nyukinKeshikomi.getKeshikomiSet();
				result = keshikomiSet;
			}
		};
		// do
		service.validateUrikakeDuplication(nyukinKeshikomi);
	}

	/**
	 * 同一日に同一売掛が消し込まれていない -> 正常終了
	 */
	@Test
	public void test06_validateUrikakeDuplication(@Injectable NyukinKeshikomi nyukinKeshikomi, @Mocked Urikake urikake1,
			@Mocked Urikake urikake2) throws Exception {
		// input
		// 消込1
		KeshikomiBuilder kb1 = new KeshikomiBuilder();
		kb1.withKingaku(1000);
		kb1.withRecordId("r-001");
		kb1.withUrikake(urikake1);
		kb1.withDate(new EigyoDate(2017, 1, 1));
		Keshikomi keshikomi1 = kb1.build();
		// 消込2
		KeshikomiBuilder kb2 = new KeshikomiBuilder();
		kb2.withKingaku(1000);
		kb2.withUrikake(urikake2);
		kb2.withRecordId("r-002");
		kb2.withDate(new EigyoDate(2017, 1, 1));
		Keshikomi keshikomi2 = kb2.build();
		// 売掛
		// 消込マップ
		Set<Keshikomi> keshikomiSet = new HashSet<Keshikomi>();
		keshikomiSet.add(keshikomi1);
		keshikomiSet.add(keshikomi2);
		// expect
		new Expectations() {
			{
				// 消込マップ取得
				nyukinKeshikomi.getKeshikomiSet();
				result = keshikomiSet;
			}
		};
		// do
		service.validateUrikakeDuplication(nyukinKeshikomi);
		// verify
		new Verifications() {
			{
				// 消込マップ取得
				nyukinKeshikomi.getKeshikomiSet();
				times = 1;
			}
		};
	}

	@Test
	public void test07_validateKingakuRange(@Injectable NyukinKeshikomi nyukinKeshikomi) throws Exception {
		// input
		// 消込
		KeshikomiBuilder kb = new KeshikomiBuilder();
		kb.withKingaku(0);
		Keshikomi keshikomi = kb.build();
		// 消込マップ
		Set<Keshikomi> keshikomiSet = new HashSet<Keshikomi>();
		keshikomiSet.add(keshikomi);
		// expect
		new Expectations() {
			{
				nyukinKeshikomi.getKeshikomiSet();
				result = keshikomiSet;
			}
		};
		// do
		service.validateKingakuRange(nyukinKeshikomi);
		// verify
		new Verifications() {
			{
				nyukinKeshikomi.getKeshikomiSet();
				times = 1;
			}
		};
		assertTrue(true);
	}

	@Test(expected = ValidateException.class)
	public void test08_validateKingakuRange(@Injectable NyukinKeshikomi nyukinKeshikomi) throws Exception {
		// input
		// 消込
		KeshikomiBuilder kb = new KeshikomiBuilder();
		kb.withKingaku(-1);
		Keshikomi keshikomi = kb.build();
		// 消込マップ
		Set<Keshikomi> keshikomiSet = new HashSet<Keshikomi>();
		keshikomiSet.add(keshikomi);
		// expect
		new Expectations() {
			{
				nyukinKeshikomi.getKeshikomiSet();
				result = keshikomiSet;
			}
		};
		// do
		service.validateKingakuRange(nyukinKeshikomi);
	}
}
