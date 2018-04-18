package com.showka.service.specification.u07;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.Kokyaku;
import com.showka.domain.NyukinKakeInfo;
import com.showka.domain.SeikyuMeisai;
import com.showka.domain.Urikake;
import com.showka.domain.builder.KokyakuBuilder;
import com.showka.domain.builder.UrikakeBuilder;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Verifications;

public class SeikyuUrikakeSpecificationImplTest extends SimpleTestCase {

	/**
	 * 支払日取得.
	 * 
	 * <pre>
	 * 入力：なし
	 * 条件：入金売掛情報.入金予定日=2017/2/1
	 * 結果：支払日=2017/2/1
	 * </pre>
	 */
	@Test
	public void test01_getShiharaiDate(@Injectable NyukinKakeInfo nyukinKakeInfo) throws Exception {
		// input
		// 顧客
		KokyakuBuilder kb = new KokyakuBuilder();
		kb.withNyukinKakeInfo(nyukinKakeInfo);
		Kokyaku kokyaku = kb.build();
		// 請求日
		EigyoDate seikyuDate = new EigyoDate(2017, 1, 1);
		// 支払日
		EigyoDate shiharaiDate = new EigyoDate(2017, 2, 1);
		// expect
		new Expectations() {
			{
				nyukinKakeInfo.getNyukinYoteiDate(seikyuDate);
				result = shiharaiDate;
			}
		};
		// do
		SeikyuUrikakeSpecificationImpl spec = new SeikyuUrikakeSpecificationImpl(kokyaku, seikyuDate,
				new ArrayList<Urikake>());
		EigyoDate actual = spec.getShiharaiDate();
		// verify
		new Verifications() {
			{
				nyukinKakeInfo.getNyukinYoteiDate(seikyuDate);
				times = 1;
			}
		};
		// check
		assertEquals(actual, shiharaiDate);
	}

	/**
	 * 請求明細取得.
	 * 
	 * <pre>
	 * 入力：なし
	 * 条件：売掛.金額=1000円
	 * 結果：売掛の請求明細.金額=1000円
	 * </pre>
	 */
	@Test
	public void test02_getSeikyuMeisai() throws Exception {
		// input
		// 顧客
		KokyakuBuilder kb = new KokyakuBuilder();
		Kokyaku kokyaku = kb.build();
		// 請求日
		EigyoDate seikyuDate = new EigyoDate(2017, 1, 1);
		// 売掛
		UrikakeBuilder ub = new UrikakeBuilder();
		ub.withKingaku(new AmountOfMoney(1000));
		Urikake urikake = ub.build();
		List<Urikake> urikakeList = new ArrayList<Urikake>();
		urikakeList.add(urikake);
		// do
		SeikyuUrikakeSpecificationImpl spec = new SeikyuUrikakeSpecificationImpl(kokyaku, seikyuDate, urikakeList);
		List<SeikyuMeisai> actual = spec.getSeikyuMeisai();
		// check
		assertEquals(1, actual.size());
		assertEquals(1000, actual.get(0).getKingaku().intValue());
	}
}
