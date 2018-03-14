package com.showka.service.specification.u11;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.Busho;
import com.showka.domain.Kokyaku;
import com.showka.domain.Shohin;
import com.showka.domain.ShohinIdo;
import com.showka.domain.ShohinIdoMeisai;
import com.showka.domain.ShohinZaiko;
import com.showka.domain.Uriage;
import com.showka.domain.UriageMeisai;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.builder.KokyakuBuilder;
import com.showka.domain.builder.ShohinBuilder;
import com.showka.domain.builder.ShohinIdoBuilder;
import com.showka.domain.builder.UriageBuilder;
import com.showka.domain.builder.UriageMeisaiBuilder;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.ShohinIdoKubun;
import com.showka.service.crud.u05.i.UriageCrudService;
import com.showka.service.crud.u11.i.ShohinIdoUriageCrudService;
import com.showka.service.crud.u11.i.ShohinZaikoCrudService;
import com.showka.system.EmptyProxy;
import com.showka.system.exception.MinusZaikoException;
import com.showka.value.EigyoDate;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;

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

	/** 売上明細01. */
	public static final UriageMeisai uriageMeisai01;
	static {
		Shohin shohin = EmptyProxy.domain(Shohin.class);
		UriageMeisaiBuilder b = new UriageMeisaiBuilder();
		b.withMeisaiNumber(1)
				.withShohinDomain(shohin)
				.withHanbaiNumber(5)
				.withHanbaiTanka(BigDecimal.valueOf(1000))
				.withRecordId("KK01-00001-1");
		uriageMeisai01 = b.build();
	}

	/** 売上明細02. */
	public static final UriageMeisai uriageMeisai02;
	static {
		Shohin shohin = EmptyProxy.domain(Shohin.class);
		UriageMeisaiBuilder b = new UriageMeisaiBuilder();
		b.withMeisaiNumber(2)
				.withShohinDomain(shohin)
				.withHanbaiNumber(6)
				.withHanbaiTanka(BigDecimal.valueOf(1001))
				.withRecordId("KK01-00001-2");
		uriageMeisai02 = b.build();
	}
	/** 売上明細03. */
	public static final UriageMeisai uriageMeisai03;
	static {
		Shohin shohin = EmptyProxy.domain(Shohin.class);
		UriageMeisaiBuilder b = new UriageMeisaiBuilder();
		b.withMeisaiNumber(3)
				.withShohinDomain(shohin)
				.withHanbaiNumber(6)
				.withHanbaiTanka(BigDecimal.valueOf(1001))
				.withRecordId("KK01-00001-2");
		uriageMeisai03 = b.build();
	}
	/** 売上00. */
	public static final Uriage uriage00;
	static {
		UriageBuilder b = new UriageBuilder();
		ArrayList<UriageMeisai> meisai = new ArrayList<UriageMeisai>();
		uriage00 = b.withKokyaku(EmptyProxy.domain(Kokyaku.class))
				.withDenpyoNumber("00001")
				.withUriageDate(new TheDate(2017, 8, 20))
				.withKeijoDate(new TheDate(2017, 8, 20))
				.withHanbaiKubun(HanbaiKubun.現金)
				.withShohizeiritsu(new TaxRate(0.08))
				.withUriageMeisai(meisai)
				.withRecordId("KK01-00001")
				.build();
	}
	/** 売上01. */
	public static final Uriage uriage01;
	static {
		UriageBuilder b = new UriageBuilder();
		ArrayList<UriageMeisai> meisai = new ArrayList<UriageMeisai>();
		meisai.add(uriageMeisai01);
		meisai.add(uriageMeisai02);
		uriage01 = b.withKokyaku(EmptyProxy.domain(Kokyaku.class))
				.withDenpyoNumber("00001")
				.withUriageDate(new TheDate(2017, 8, 20))
				.withKeijoDate(new TheDate(2017, 8, 20))
				.withHanbaiKubun(HanbaiKubun.現金)
				.withShohizeiritsu(new TaxRate(0.08))
				.withUriageMeisai(meisai)
				.withRecordId("KK01-00001")
				.build();
	}
	/** 売上02. */
	public static final Uriage uriage02;
	static {
		UriageBuilder b = new UriageBuilder();
		ArrayList<UriageMeisai> meisai = new ArrayList<UriageMeisai>();
		meisai.add(uriageMeisai03);
		uriage02 = b.withKokyaku(EmptyProxy.domain(Kokyaku.class))
				.withDenpyoNumber("00001")
				.withUriageDate(new TheDate(2017, 8, 20))
				.withKeijoDate(new TheDate(2017, 8, 20))
				.withHanbaiKubun(HanbaiKubun.現金)
				.withShohizeiritsu(new TaxRate(0.08))
				.withUriageMeisai(meisai)
				.withRecordId("KK01-00001")
				.build();
	}

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
		// data 売上
		Uriage _uriage01 = new UriageBuilder().withKokyaku(kokyaku).apply(uriage01);
		// data 過去商品移動
		ShohinIdoBuilder b = new ShohinIdoBuilder();
		b.withDate(new TheDate(2017, 9, 20));
		List<ShohinIdoMeisai> meisai = new ArrayList<ShohinIdoMeisai>();
		b.withMeisai(meisai);
		ShohinIdo shohinIdo01 = b.build();
		// expect
		new Expectations() {
			{
				busho.getEigyoDate();
				result = new EigyoDate(2017, 8, 20);
				shohinIdoUriageCrudService.getNewestShohinIdo(_uriage01.getRecordId());
				result = Optional.of(shohinIdo01);
			}
		};
		// do
		shohinIdoSpecificationImpl.setUriage(_uriage01);
		List<ShohinIdo> actual = shohinIdoSpecificationImpl.getShohinIdo();
		// verify
		new Verifications() {
			{
				shohinIdoUriageCrudService.getNewestShohinIdo(_uriage01.getRecordId());
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
				result = new TheDate(2017, 8, 20);
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
	}
}
