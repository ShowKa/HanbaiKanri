package com.showka.service.validator.u11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.builder.ShohinBuilder;
import com.showka.domain.builder.ShohinIdoBuilder;
import com.showka.domain.builder.ShohinIdoMeisaiBuilder;
import com.showka.domain.builder.ShohinZaikoBuilder;
import com.showka.domain.u11.ShohinIdo;
import com.showka.domain.u11.ShohinIdoMeisai;
import com.showka.domain.u11.ShohinZaiko;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shohin;
import com.showka.kubun.ShohinIdoKubun;
import com.showka.service.query.u11.i.ShohinZaikoQuery;
import com.showka.system.exception.specification.MinusZaikoException;
import com.showka.value.EigyoDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;

/** 商品在庫検証テスト */
public class ShohinZaikoValidatorImplTest extends SimpleTestCase {

	@Tested
	private ShohinZaikoValidatorImpl service;

	@Injectable
	private ShohinZaikoQuery shohinZaikoQuery;

	/** マイナス在庫発生しない場合、正常終了する */
	@Test
	public void test_ValidateMinusZaiko_01() throws Exception {
		// input
		// 部署
		BushoBuilder bb = new BushoBuilder();
		bb.withCode("BB01");
		Busho busho = bb.build();
		// 移動日
		EigyoDate date = new EigyoDate(2019, 1, 1);
		// 商品
		ShohinBuilder sb = new ShohinBuilder();
		sb.withCode("SH01");
		Shohin shohin = sb.build();
		// 商品移動明細
		ShohinIdoMeisaiBuilder simb = new ShohinIdoMeisaiBuilder();
		simb.withNumber(1);
		simb.withShohinDomain(shohin);
		List<ShohinIdoMeisai> meisai = Arrays.asList(simb.build());
		// 商品移動
		ShohinIdoBuilder sib = new ShohinIdoBuilder();
		sib.withBusho(busho);
		sib.withDate(date);
		sib.withMeisai(meisai);
		sib.withKubun(ShohinIdoKubun.売上);
		ShohinIdo shohinIdo = sib.build();
		// 商品在庫
		ShohinZaikoBuilder szb = new ShohinZaikoBuilder();
		szb.withKurikoshiNumber(1);
		szb.withShohin(shohin);
		szb.withShohinIdoList(new ArrayList<>());
		ShohinZaiko zaiko = szb.build();
		// expect
		new Expectations() {
			{
				shohinZaikoQuery.get(busho, date, shohin);
				result = zaiko;
			}
		};
		// do
		service.validateMinusZaiko(shohinIdo);
	}

	/** マイナス在庫発生時、エラーとなる */
	@Test(expected = MinusZaikoException.class)
	public void test_ValidateMinusZaiko_02() throws Exception {
		// input
		// 部署
		BushoBuilder bb = new BushoBuilder();
		bb.withCode("BB01");
		Busho busho = bb.build();
		// 移動日
		EigyoDate date = new EigyoDate(2019, 1, 1);
		// 商品
		ShohinBuilder sb = new ShohinBuilder();
		sb.withCode("SH01");
		Shohin shohin = sb.build();
		// 商品移動明細
		ShohinIdoMeisaiBuilder simb = new ShohinIdoMeisaiBuilder();
		simb.withNumber(2);
		simb.withShohinDomain(shohin);
		List<ShohinIdoMeisai> meisai = Arrays.asList(simb.build());
		// 商品移動
		ShohinIdoBuilder sib = new ShohinIdoBuilder();
		sib.withBusho(busho);
		sib.withDate(date);
		sib.withMeisai(meisai);
		sib.withKubun(ShohinIdoKubun.売上);
		ShohinIdo shohinIdo = sib.build();
		// 商品在庫
		ShohinZaikoBuilder szb = new ShohinZaikoBuilder();
		szb.withKurikoshiNumber(1);
		szb.withShohin(shohin);
		szb.withShohinIdoList(new ArrayList<>());
		ShohinZaiko zaiko = szb.build();
		// expect
		new Expectations() {
			{
				shohinZaikoQuery.get(busho, date, shohin);
				result = zaiko;
			}
		};
		// do
		service.validateMinusZaiko(shohinIdo);
	}

	/** 商品移動削除時、マイナス在庫発生しない */
	@Test
	public void test_ValidateMinusZaikoForDelete_01() throws Exception {
		// input
		// 部署
		BushoBuilder bb = new BushoBuilder();
		bb.withCode("BB01");
		Busho busho = bb.build();
		// 移動日
		EigyoDate date = new EigyoDate(2019, 1, 1);
		// 商品
		ShohinBuilder sb = new ShohinBuilder();
		sb.withCode("SH01");
		Shohin shohin = sb.build();
		// 商品移動明細
		ShohinIdoMeisaiBuilder simb = new ShohinIdoMeisaiBuilder();
		simb.withNumber(2);
		simb.withShohinDomain(shohin);
		List<ShohinIdoMeisai> meisai = Arrays.asList(simb.build());
		// 商品移動
		ShohinIdoBuilder sib = new ShohinIdoBuilder();
		sib.withBusho(busho);
		sib.withDate(date);
		sib.withMeisai(meisai);
		sib.withKubun(ShohinIdoKubun.入荷);
		ShohinIdo shohinIdo = sib.build();
		// 商品在庫
		ShohinZaikoBuilder szb = new ShohinZaikoBuilder();
		szb.withKurikoshiNumber(0);
		szb.withShohin(shohin);
		szb.withShohinIdoList(Arrays.asList(shohinIdo));
		ShohinZaiko zaiko = szb.build();
		// expect
		new Expectations() {
			{
				shohinZaikoQuery.get(busho, date, shohin);
				result = zaiko;
			}
		};
		// do
		service.validateMinusZaikoForDelete(shohinIdo);
	}

	/** 商品移動削除時、マイナス在庫発生 */
	@Test(expected = MinusZaikoException.class)
	public void test_ValidateMinusZaikoForDelete_02() throws Exception {
		// input
		// 部署
		BushoBuilder bb = new BushoBuilder();
		bb.withCode("BB01");
		Busho busho = bb.build();
		// 移動日
		EigyoDate date = new EigyoDate(2019, 1, 1);
		// 商品
		ShohinBuilder sb = new ShohinBuilder();
		sb.withCode("SH01");
		Shohin shohin = sb.build();
		// 商品移動明細
		ShohinIdoMeisaiBuilder simb = new ShohinIdoMeisaiBuilder();
		simb.withNumber(2);
		simb.withShohinDomain(shohin);
		List<ShohinIdoMeisai> meisai = Arrays.asList(simb.build());
		// 商品移動
		ShohinIdoBuilder sib = new ShohinIdoBuilder();
		sib.withBusho(busho);
		sib.withDate(date);
		sib.withMeisai(meisai);
		sib.withKubun(ShohinIdoKubun.入荷);
		ShohinIdo shohinIdo = sib.build();
		// 商品在庫
		ShohinZaikoBuilder szb = new ShohinZaikoBuilder();
		szb.withKurikoshiNumber(-1);
		szb.withShohin(shohin);
		szb.withShohinIdoList(Arrays.asList(shohinIdo));
		ShohinZaiko zaiko = szb.build();
		// expect
		new Expectations() {
			{
				shohinZaikoQuery.get(busho, date, shohin);
				result = zaiko;
			}
		};
		// do
		service.validateMinusZaikoForDelete(shohinIdo);
	}
}
