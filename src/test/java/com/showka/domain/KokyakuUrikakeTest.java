package com.showka.domain;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.KokyakuUrikakeBuilder;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Verifications;

public class KokyakuUrikakeTest extends SimpleTestCase {

	/**
	 * 合計金額取得.
	 * 
	 * <pre>
	 * 入力：なし
	 * 条件：売掛2件（残高=100, 残高=200）
	 * 結果：合計=200
	 * </pre>
	 */
	@Test
	public void test01_getGokeiKingaku(@Injectable Urikake u1, @Injectable Urikake u2) throws Exception {
		// input
		// 売掛リスト
		ArrayList<Urikake> urikakeList = new ArrayList<Urikake>();
		urikakeList.add(u1);
		urikakeList.add(u2);
		// 顧客売上
		KokyakuUrikakeBuilder b = new KokyakuUrikakeBuilder();
		b.withUrikakeList(urikakeList);
		KokyakuUrikake kokyakuUrikake = b.build();
		// expect
		new Expectations() {
			{
				u1.getZandaka();
				result = Integer.valueOf(100);
				u2.getZandaka();
				result = Integer.valueOf(200);
			}
		};
		// do
		AmountOfMoney actual = kokyakuUrikake.getGokeiKingaku();
		// verify
		new Verifications() {
			{
				u1.getZandaka();
				times = 1;
				u2.getZandaka();
				times = 1;
			}
		};
		// check
		assertEquals(300, actual.intValue());
	}

	/**
	 * 要入金売掛取得.
	 * 
	 * <pre>
	 * 入力：日付=2017/1/1
	 * 条件：売掛2件（入金予定日=2017/1/1, 入金予定日=2017/1/2）
	 * 結果：売掛を1件(入金予定日=2017/1/1)だけ取得する
	 * </pre>
	 */
	@Test
	public void test02_getUrikakeListNyukinRequiredBy(@Injectable Urikake u1, @Injectable Urikake u2) throws Exception {
		// input
		// 売掛リスト
		ArrayList<Urikake> urikakeList = new ArrayList<Urikake>();
		urikakeList.add(u1);
		urikakeList.add(u2);
		// 顧客売上
		KokyakuUrikakeBuilder b = new KokyakuUrikakeBuilder();
		b.withUrikakeList(urikakeList);
		KokyakuUrikake kokyakuUrikake = b.build();
		// expect
		new Expectations() {
			{
				u1.getNyukinYoteiDate();
				result = new EigyoDate(2017, 1, 1);
				u2.getNyukinYoteiDate();
				result = new EigyoDate(2017, 1, 2);
			}
		};
		// do
		List<Urikake> actual = kokyakuUrikake.getUrikakeListNyukinRequiredBy(new EigyoDate(2017, 1, 1));
		// verify
		new Verifications() {
			{
				u1.getNyukinYoteiDate();
				times = 1;
				u2.getNyukinYoteiDate();
				times = 1;
			}
		};
		// check
		assertEquals(1, actual.size());
		assertEquals(new EigyoDate(2017, 1, 1), actual.get(0).getNyukinYoteiDate());
	}

	/**
	 * 要入金売掛取得.
	 * 
	 * <pre>
	 * 入力：日付=2017/1/2
	 * 条件：売掛2件（入金予定日=2017/1/1, 入金予定日=2017/1/2）
	 * 結果：売掛を2件とも取得する
	 * </pre>
	 */
	@Test
	public void test03_getUrikakeListNyukinRequiredBy(@Injectable Urikake u1, @Injectable Urikake u2) throws Exception {
		// input
		// 売掛リスト
		ArrayList<Urikake> urikakeList = new ArrayList<Urikake>();
		urikakeList.add(u1);
		urikakeList.add(u2);
		// 顧客売上
		KokyakuUrikakeBuilder b = new KokyakuUrikakeBuilder();
		b.withUrikakeList(urikakeList);
		KokyakuUrikake kokyakuUrikake = b.build();
		// expect
		new Expectations() {
			{
				u1.getNyukinYoteiDate();
				result = new EigyoDate(2017, 1, 1);
				u2.getNyukinYoteiDate();
				result = new EigyoDate(2017, 1, 2);
			}
		};
		// do
		List<Urikake> actual = kokyakuUrikake.getUrikakeListNyukinRequiredBy(new EigyoDate(2017, 1, 2));
		// verify
		new Verifications() {
			{
				u1.getNyukinYoteiDate();
				times = 1;
				u2.getNyukinYoteiDate();
				times = 1;
			}
		};
		// check
		assertEquals(2, actual.size());
	}

	/**
	 * 要入金売掛取得（入金予定日指定）.
	 * 
	 * <pre>
	 * 入力：日付=2017/1/1
	 * 条件：売掛2件（入金予定日=2017/1/1, 入金予定日=2017/1/2）
	 * 結果：入金予定日=2017/1/1の残高を取得する
	 * </pre>
	 */
	@Test
	public void test04_getGokeiKingakuNyukinRequiredBy(@Injectable Urikake u1, @Injectable Urikake u2)
			throws Exception {
		// input
		// 売掛リスト
		ArrayList<Urikake> urikakeList = new ArrayList<Urikake>();
		urikakeList.add(u1);
		urikakeList.add(u2);
		// 顧客売上
		KokyakuUrikakeBuilder b = new KokyakuUrikakeBuilder();
		b.withUrikakeList(urikakeList);
		KokyakuUrikake kokyakuUrikake = b.build();
		// expect
		new Expectations() {
			{
				u1.getNyukinYoteiDate();
				result = new EigyoDate(2017, 1, 1);
				u2.getNyukinYoteiDate();
				result = new EigyoDate(2017, 1, 2);
				u1.getZandaka();
				result = Integer.valueOf(100);
			}
		};
		// do
		AmountOfMoney actual = kokyakuUrikake.getGokeiKingakuNyukinRequiredBy(new EigyoDate(2017, 1, 1));
		// verify
		new Verifications() {
			{
				u1.getNyukinYoteiDate();
				times = 1;
				u2.getNyukinYoteiDate();
				times = 1;
				u1.getZandaka();
				times = 1;
			}
		};
		// check
		assertEquals(100, actual.intValue());
	}

}
