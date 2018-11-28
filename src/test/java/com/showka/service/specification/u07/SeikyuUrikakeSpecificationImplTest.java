package com.showka.service.specification.u07;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.KokyakuBuilder;
import com.showka.domain.builder.UrikakeBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u01.NyukinKakeInfo;
import com.showka.domain.u06.Urikake;
import com.showka.domain.u06.UrikakeKeshikomi;
import com.showka.domain.u07.SeikyuMeisai;
import com.showka.service.query.u06.i.UrikakeKeshikomiQuery;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;
import com.showka.value.TheDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Verifications;

public class SeikyuUrikakeSpecificationImplTest extends SimpleTestCase {

	@Injectable
	private UrikakeKeshikomiQuery urikakeKeshikomiQuery;

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
		TheDate actual = spec.getShiharaiDate();
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
	public void test02_getSeikyuMeisai(@Injectable UrikakeKeshikomi urikakeKeshikomi) throws Exception {
		// input
		// 顧客
		KokyakuBuilder kb = new KokyakuBuilder();
		Kokyaku kokyaku = kb.build();
		// 請求日
		EigyoDate seikyuDate = new EigyoDate(2017, 1, 1);
		// 売掛
		UrikakeBuilder ub = new UrikakeBuilder();
		ub.withRecordId("r-001");
		Urikake urikake = ub.build();
		List<Urikake> urikakeList = new ArrayList<Urikake>();
		urikakeList.add(urikake);
		// expect
		new Expectations() {
			{
				urikakeKeshikomiQuery.get("r-001");
				result = urikakeKeshikomi;
				urikakeKeshikomi.getZandaka();
				result = new AmountOfMoney(1000);
			}
		};
		// construct
		SeikyuUrikakeSpecificationImpl spec = new SeikyuUrikakeSpecificationImpl(kokyaku, seikyuDate, urikakeList);
		spec.setUrikakeKeshikomiQuery(urikakeKeshikomiQuery);
		// do
		List<SeikyuMeisai> actual = spec.getSeikyuMeisai();
		// check
		assertEquals(1, actual.size());
		assertEquals(1000, actual.get(0).getKingaku().intValue());
	}
}
