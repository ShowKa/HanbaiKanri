package com.showka.service.specification.u11;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.builder.KokyakuBuilder;
import com.showka.domain.builder.ShohinBuilder;
import com.showka.domain.builder.ShohinIdoBuilder;
import com.showka.domain.builder.ShohinIdoMeisaiBuilder;
import com.showka.domain.builder.UriageBuilder;
import com.showka.domain.builder.UriageMeisaiBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u05.Uriage;
import com.showka.domain.u05.UriageMeisai;
import com.showka.domain.u11.ShohinIdo;
import com.showka.domain.u11.ShohinIdoMeisai;
import com.showka.domain.u11.ShohinZaiko;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shohin;
import com.showka.kubun.ShohinIdoKubun;
import com.showka.service.crud.u05.i.UriageCrudService;
import com.showka.service.crud.u11.i.ShohinIdoUriageCrudService;
import com.showka.service.crud.u11.i.ShohinZaikoCrudService;
import com.showka.system.EmptyProxy;
import com.showka.system.exception.MinusZaikoException;
import com.showka.value.EigyoDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class ShohinIdoSpecificationAssociatedWithUriageTest extends SimpleTestCase {

	@Tested
	private ShohinIdoSpecificationAssociatedWithUriage shohinIdoSpecificationImpl;

	@Injectable
	private UriageCrudService uriageCrudService;

	@Injectable
	private ShohinZaikoCrudService shohinZaikoCrudService;

	@Injectable
	private ShohinIdoUriageCrudService shohinIdoUriageCrudService;

	/**
	 * 売上設定.
	 *
	 * <pre>
	 * 入力：売上<br>
	 * 条件：売上訂正ではない <br>
	 * 結果：売上による商品移動が生成される
	 * 
	 * <pre>
	 */
	@Test
	public void test01_setUriage() throws Exception {
		// input
		// 商品
		ShohinBuilder sb = new ShohinBuilder();
		Shohin shohin = sb.build();
		// 明細
		ArrayList<UriageMeisai> _meisai = new ArrayList<UriageMeisai>();
		// 明細01
		UriageMeisaiBuilder mb = new UriageMeisaiBuilder();
		mb.withMeisaiNumber(1);
		mb.withShohinDomain(shohin);
		mb.withHanbaiNumber(5);
		mb.withRecordId("KK01-00001-1");
		UriageMeisai uriageMeisai01 = mb.build();
		_meisai.add(uriageMeisai01);
		// 明細02
		_meisai.add(new UriageMeisaiBuilder().build());
		// 売上
		UriageBuilder b = new UriageBuilder();
		b.withKokyaku(EmptyProxy.domain(Kokyaku.class));
		b.withDenpyoNumber("00001");
		b.withKeijoDate(new EigyoDate(2017, 8, 20));
		b.withUriageMeisai(_meisai);
		b.withRecordId("KK01-00001");
		Uriage uriage01 = b.build();
		// expect
		new Expectations() {
			{
				shohinIdoUriageCrudService.getNewestShohinIdo(uriage01.getRecordId());
			}
		};
		// do
		shohinIdoSpecificationImpl.setUriage(uriage01);
		List<ShohinIdo> actual = shohinIdoSpecificationImpl.getShohinIdo();
		// verify
		new Verifications() {
			{
				shohinIdoUriageCrudService.getNewestShohinIdo(uriage01.getRecordId());
				times = 1;
			}
		};
		// check
		assertEquals(1, actual.size());
		ShohinIdo ido = actual.get(0);
		assertEquals(ShohinIdoKubun.売上, ido.getKubun());
		// 明細
		List<ShohinIdoMeisai> meisai = ido.getMeisai();
		assertEquals(2, meisai.size());
		meisai.stream().filter(p -> {
			return p.getMeisaiNumber().intValue() == 1;
		}).forEach(meisai1 -> {
			assertEquals(5, meisai1.getNumber().intValue());
		});
	}

	/**
	 * 売上設定.
	 *
	 * <pre>
	 * 入力：売上<br>
	 * 条件：前日の売上の訂正を伴う <br>
	 * 結果：売上による商品移動、および訂正伝票が生成される
	 * 
	 * <pre>
	 */
	@Test
	public void test02_setUriage(@Injectable Kokyaku kokyaku, @Injectable Busho busho) throws Exception {
		// input
		// 商品
		ShohinBuilder sb = new ShohinBuilder();
		Shohin shohin = sb.build();
		// 明細
		ArrayList<UriageMeisai> _meisai = new ArrayList<UriageMeisai>();
		// 明細01
		UriageMeisaiBuilder mb = new UriageMeisaiBuilder();
		mb.withMeisaiNumber(1);
		mb.withShohinDomain(shohin);
		mb.withHanbaiNumber(5);
		mb.withRecordId("KK01-00001-1");
		UriageMeisai uriageMeisai01 = mb.build();
		_meisai.add(uriageMeisai01);
		// 売上
		UriageBuilder b = new UriageBuilder();
		b.withKokyaku(kokyaku);
		b.withDenpyoNumber("00001");
		b.withKeijoDate(new EigyoDate(2017, 8, 20));
		b.withUriageMeisai(_meisai);
		b.withRecordId("KK01-00001");
		Uriage uriage01 = b.build();
		// 過去商品移動明細
		List<ShohinIdoMeisai> meisai = new ArrayList<ShohinIdoMeisai>();
		ShohinIdoMeisaiBuilder simb = new ShohinIdoMeisaiBuilder();
		simb.withMeisaiNumber(1);
		meisai.add(simb.build());
		// 過去商品移動
		ShohinIdoBuilder sib = new ShohinIdoBuilder();
		sib.withDate(new EigyoDate(2017, 8, 19));
		sib.withMeisai(meisai);
		ShohinIdo shohinIdo01 = sib.build();
		// expect
		new Expectations() {
			{
				busho.getEigyoDate();
				result = new EigyoDate(2017, 8, 20);
				shohinIdoUriageCrudService.getNewestShohinIdo(uriage01.getRecordId());
				result = Optional.of(shohinIdo01);
			}
		};
		// do
		shohinIdoSpecificationImpl.setUriage(uriage01);
		List<ShohinIdo> actual = shohinIdoSpecificationImpl.getShohinIdo();
		// verify
		new Verifications() {
			{
				shohinIdoUriageCrudService.getNewestShohinIdo(uriage01.getRecordId());
				times = 1;
			}
		};
		// check
		assertEquals(2, actual.size());
		ShohinIdo ido = actual.get(1);
		assertEquals(ShohinIdoKubun.売上訂正, ido.getKubun());
	}

	/**
	 * 仕様確認.
	 *
	 * <pre>
	 * 入力：売上<br>
	 * 条件：在庫10個に対し、商品売上10個 <br>
	 * 結果：エラーが発生しない。
	 * 
	 * <pre>
	 */
	@Test
	public void test03_ascertainSatisfaction(@Injectable ShohinZaiko zaiko) throws Exception {
		// input
		// 商品
		ShohinBuilder sb = new ShohinBuilder();
		sb.withCode("SH01");
		Shohin shohin = sb.build();
		// 明細
		ArrayList<UriageMeisai> meisai = new ArrayList<UriageMeisai>();
		// 明細01
		UriageMeisaiBuilder umb = new UriageMeisaiBuilder();
		umb.withHanbaiNumber(10);
		umb.withShohinDomain(shohin);
		UriageMeisai uriageMeisai = umb.build();
		meisai.add(uriageMeisai);
		// 部署
		BushoBuilder bb = new BushoBuilder();
		bb.withCode("BS01");
		EigyoDate eigyoDate = new EigyoDate(2017, 8, 20);
		bb.withEigyoDate(eigyoDate);
		Busho busho = bb.build();
		// 顧客
		KokyakuBuilder kb = new KokyakuBuilder();
		kb.withCode("KK01");
		kb.withShukanBusho(busho);
		Kokyaku kokyaku = kb.build();
		// 売上
		UriageBuilder ub = new UriageBuilder();
		ub.withKokyaku(kokyaku);
		ub.withDenpyoNumber("00001");
		ub.withUriageMeisai(meisai);
		Uriage uriage = ub.build();
		// expect
		new Expectations() {
			{
				zaiko.getNumber();
				result = 10;
			}
		};
		// do
		shohinIdoSpecificationImpl.setUriage(uriage);
		shohinIdoSpecificationImpl.ascertainSatisfaction();
		// check
		assertTrue(true);
	}

	/**
	 * 仕様確認.
	 *
	 * <pre>
	 * 入力：売上<br>
	 * 条件：在庫10個に対し、商品売上11個 <br>
	 * 結果：マイナス在庫例外
	 * 
	 * <pre>
	 */
	@Test(expected = MinusZaikoException.class)
	public void test04_ascertainSatisfaction(@Injectable ShohinZaiko zaiko) throws Exception {
		// input
		// 商品
		ShohinBuilder sb = new ShohinBuilder();
		sb.withCode("SH01");
		Shohin shohin = sb.build();
		// 明細
		ArrayList<UriageMeisai> meisai = new ArrayList<UriageMeisai>();
		// 明細01
		UriageMeisaiBuilder umb = new UriageMeisaiBuilder();
		umb.withHanbaiNumber(11);
		umb.withShohinDomain(shohin);
		UriageMeisai uriageMeisai = umb.build();
		meisai.add(uriageMeisai);
		// 部署
		BushoBuilder bb = new BushoBuilder();
		bb.withCode("BS01");
		EigyoDate eigyoDate = new EigyoDate(2017, 8, 20);
		bb.withEigyoDate(eigyoDate);
		Busho busho = bb.build();
		// 顧客
		KokyakuBuilder kb = new KokyakuBuilder();
		kb.withCode("KK01");
		kb.withShukanBusho(busho);
		Kokyaku kokyaku = kb.build();
		// 売上
		UriageBuilder ub = new UriageBuilder();
		ub.withKokyaku(kokyaku);
		ub.withDenpyoNumber("00001");
		ub.withUriageMeisai(meisai);
		Uriage uriage = ub.build();
		// expect
		new Expectations() {
			{
				zaiko.getNumber();
				result = 10;
			}
		};
		// do
		shohinIdoSpecificationImpl.setUriage(uriage);
		shohinIdoSpecificationImpl.ascertainSatisfaction();
	}

	/**
	 * 仕様確認.
	 *
	 * <pre>
	 * 入力：売上<br>
	 * 条件：在庫10個に対し、商品売上11個 。ただし訂正分で1個在庫がもどる。<br>
	 * 結果：エラーなし
	 * 
	 * <pre>
	 */
	@Test
	public void test05_ascertainSatisfaction(@Injectable ShohinZaiko zaiko, @Injectable ShohinIdo pastShohinIdo)
			throws Exception {
		// input
		// 商品
		ShohinBuilder sb = new ShohinBuilder();
		sb.withCode("SH01");
		Shohin shohin = sb.build();
		// 明細
		ArrayList<UriageMeisai> meisai = new ArrayList<UriageMeisai>();
		// 明細01
		UriageMeisaiBuilder umb = new UriageMeisaiBuilder();
		umb.withHanbaiNumber(11);
		umb.withShohinDomain(shohin);
		UriageMeisai uriageMeisai = umb.build();
		meisai.add(uriageMeisai);
		// 部署
		BushoBuilder bb = new BushoBuilder();
		bb.withCode("BS01");
		EigyoDate eigyoDate = new EigyoDate(2017, 8, 20);
		bb.withEigyoDate(eigyoDate);
		Busho busho = bb.build();
		// 顧客
		KokyakuBuilder kb = new KokyakuBuilder();
		kb.withCode("KK01");
		kb.withShukanBusho(busho);
		Kokyaku kokyaku = kb.build();
		// 売上
		UriageBuilder ub = new UriageBuilder();
		ub.withKokyaku(kokyaku);
		ub.withDenpyoNumber("00001");
		ub.withUriageMeisai(meisai);
		ub.withRecordId("r-KK01-00001");
		Uriage uriage = ub.build();
		// 過去の商品移動モック
		HashSet<Shohin> shohinSet = new HashSet<Shohin>();
		shohinSet.add(shohin);
		// expect
		new Expectations() {
			{
				// 訂正対象の過去商品移動
				shohinIdoUriageCrudService.getNewestShohinIdo(uriage.getRecordId());
				result = Optional.of(pastShohinIdo);
				// 当日営業日に移動した
				pastShohinIdo.getDate();
				result = new EigyoDate(2017, 8, 20);
				// 対象商品商品。1つ移動した（部署->顧客）
				pastShohinIdo.getShohinSet();
				result = shohinSet;
				pastShohinIdo.getAbusoluteIdoNumberForBushoZaiko(shohin);
				result = -1;
				// 在庫は10個残っている
				zaiko.getNumber();
				result = 10;
			}
		};
		// do
		shohinIdoSpecificationImpl.setUriage(uriage);
		shohinIdoSpecificationImpl.ascertainSatisfaction();
		// verify
		new Verifications() {
			{
				shohinIdoUriageCrudService.getNewestShohinIdo(uriage.getRecordId());
				times = 1;
			}
		};
		// check
		List<ShohinIdo> actual = shohinIdoSpecificationImpl.getShohinIdoForDelete();
		assertEquals(shohinSet, actual.get(0).getShohinSet());
	}
}
