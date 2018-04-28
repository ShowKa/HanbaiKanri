package com.showka.service.validate.u08;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.Keshikomi;
import com.showka.domain.Nyukin;
import com.showka.domain.NyukinKeshikomi;
import com.showka.domain.Urikake;
import com.showka.domain.builder.KeshikomiBuilder;
import com.showka.domain.builder.NyukinBuilder;
import com.showka.domain.builder.UrikakeBuilder;
import com.showka.service.specification.u06.i.UrikakeKeshikomiSpecificationService;
import com.showka.system.exception.ValidateException;
import com.showka.value.AmountOfMoney;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;

public class NyukinKeshikomiValidateServiceImplTest extends SimpleTestCase {

	@Tested
	@Injectable
	private NyukinKeshikomiValidateServiceImpl service;

	@Injectable
	private UrikakeKeshikomiSpecificationService urikakeKeshikomiSpecificationService;

	/**
	 * 各整合性検証用メソッドの呼出確認
	 */
	@Test
	public void test00_validate(@Injectable NyukinKeshikomi nyukinKeshikomi) throws Exception {
		// expect
		new Expectations() {
			{
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
				urikakeKeshikomiSpecificationService.getZandakaOf(urikake);
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
				urikakeKeshikomiSpecificationService.getZandakaOf(urikake);
				times = 1;
			}
		};
	}

	/**
	 * 同一の売掛が消し込まれている -> エラー
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
		Keshikomi keshikomi1 = kb1.build();
		// 消込2
		KeshikomiBuilder kb2 = new KeshikomiBuilder();
		kb2.withKingaku(1000);
		kb2.withRecordId("r-002");
		kb2.withUrikake(urikake);
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
	}

	/**
	 * 同一の売掛が消し込まれていない -> 正常終了
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
		Keshikomi keshikomi1 = kb1.build();
		// 消込2
		KeshikomiBuilder kb2 = new KeshikomiBuilder();
		kb2.withKingaku(1000);
		kb2.withUrikake(urikake2);
		kb2.withRecordId("r-002");
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
}
