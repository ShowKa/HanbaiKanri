package com.showka.domain.u11;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.NyukaBuilder;
import com.showka.domain.builder.ShohinBuilder;
import com.showka.domain.builder.ShohinIdoBuilder;
import com.showka.domain.builder.ShohinIdoMeisaiBuilder;
import com.showka.domain.z00.Shohin;
import com.showka.kubun.ShohinIdoKubun;
import com.showka.value.EigyoDate;

public class NyukaTest extends SimpleTestCase {

	// 商品移動数取得(入荷数:4 = 入荷:5 + 訂正:-1)
	@Test
	public void test_GetNumber_01() throws Exception {
		// 商品
		Shohin shohin = new ShohinBuilder().withCode("SH01").build();
		// 商品移動明細
		ShohinIdoMeisaiBuilder simb = new ShohinIdoMeisaiBuilder();
		simb.withNumber(5);
		simb.withShohinDomain(shohin);
		List<ShohinIdoMeisai> meisai = Arrays.asList(simb.build());
		// 商品移動
		ShohinIdoBuilder sib = new ShohinIdoBuilder();
		sib.withKubun(ShohinIdoKubun.入荷);
		sib.withMeisai(meisai);
		ShohinIdo nyukaShohinIdo = sib.build();
		// 商品移動明細 訂正
		ShohinIdoMeisaiBuilder simbt = new ShohinIdoMeisaiBuilder();
		simbt.withNumber(-1);
		simbt.withShohinDomain(shohin);
		List<ShohinIdoMeisai> meisait = Arrays.asList(simbt.build());
		// 商品移動 訂正
		ShohinIdoBuilder sibt = new ShohinIdoBuilder();
		sibt.withKubun(ShohinIdoKubun.入荷訂正);
		sibt.withMeisai(meisait);
		ShohinIdo nyukaShohinIdot = sibt.build();
		// 入荷
		NyukaBuilder nb = new NyukaBuilder();
		nb.withNyukaShohinIdo(nyukaShohinIdo);
		nb.withTeiseiList(Arrays.asList(nyukaShohinIdot));
		Nyuka nyuka = nb.build();
		// do
		Integer actual = nyuka.getNumber(shohin);
		// check
		assertEquals(4, actual.intValue());
	}

	// 商品set取得
	@Test
	public void test_GetShohinSet_01() throws Exception {
		// 商品
		Shohin shohin = new ShohinBuilder().withCode("SH01").build();
		// 商品移動明細
		ShohinIdoMeisaiBuilder simb = new ShohinIdoMeisaiBuilder();
		simb.withNumber(5);
		simb.withShohinDomain(shohin);
		List<ShohinIdoMeisai> meisai = Arrays.asList(simb.build());
		// 商品移動
		ShohinIdoBuilder sib = new ShohinIdoBuilder();
		sib.withKubun(ShohinIdoKubun.入荷);
		sib.withMeisai(meisai);
		ShohinIdo nyukaShohinIdo = sib.build();
		// 商品移動明細 訂正
		ShohinIdoMeisaiBuilder simbt = new ShohinIdoMeisaiBuilder();
		simbt.withNumber(-1);
		simbt.withShohinDomain(shohin);
		List<ShohinIdoMeisai> meisait = Arrays.asList(simbt.build());
		// 商品移動 訂正
		ShohinIdoBuilder sibt = new ShohinIdoBuilder();
		sibt.withKubun(ShohinIdoKubun.入荷訂正);
		sibt.withMeisai(meisait);
		ShohinIdo nyukaShohinIdot = sibt.build();
		// 入荷
		NyukaBuilder nb = new NyukaBuilder();
		nb.withNyukaShohinIdo(nyukaShohinIdo);
		nb.withTeiseiList(Arrays.asList(nyukaShohinIdot));
		Nyuka nyuka = nb.build();
		// do
		Set<Shohin> actual = nyuka.getShohinSet();
		// check
		assertTrue(actual.contains(shohin));
	}

	// 商品set取得、ただし入荷数=0の商品は除外
	@Test
	public void test_GetShohinSet_02() throws Exception {
		// 商品
		Shohin shohin = new ShohinBuilder().withCode("SH01").build();
		// 商品移動明細
		ShohinIdoMeisaiBuilder simb = new ShohinIdoMeisaiBuilder();
		simb.withNumber(5);
		simb.withShohinDomain(shohin);
		List<ShohinIdoMeisai> meisai = Arrays.asList(simb.build());
		// 商品移動
		ShohinIdoBuilder sib = new ShohinIdoBuilder();
		sib.withKubun(ShohinIdoKubun.入荷);
		sib.withMeisai(meisai);
		ShohinIdo nyukaShohinIdo = sib.build();
		// 商品移動明細 訂正
		ShohinIdoMeisaiBuilder simbt = new ShohinIdoMeisaiBuilder();
		simbt.withNumber(-5);
		simbt.withShohinDomain(shohin);
		List<ShohinIdoMeisai> meisait = Arrays.asList(simbt.build());
		// 商品移動 訂正
		ShohinIdoBuilder sibt = new ShohinIdoBuilder();
		sibt.withKubun(ShohinIdoKubun.入荷訂正);
		sibt.withMeisai(meisait);
		ShohinIdo nyukaShohinIdot = sibt.build();
		// 入荷
		NyukaBuilder nb = new NyukaBuilder();
		nb.withNyukaShohinIdo(nyukaShohinIdo);
		nb.withTeiseiList(Arrays.asList(nyukaShohinIdot));
		Nyuka nyuka = nb.build();
		// do
		Set<Shohin> actual = nyuka.getShohinSet();
		// check
		assertEquals(0, actual.size());
	}

	// 訂正済判定: true
	@Test
	public void test_DoneTeisei_01() throws Exception {
		// 商品
		Shohin shohin = new ShohinBuilder().withCode("SH01").build();
		// 商品移動明細
		ShohinIdoMeisaiBuilder simb = new ShohinIdoMeisaiBuilder();
		simb.withNumber(5);
		simb.withShohinDomain(shohin);
		List<ShohinIdoMeisai> meisai = Arrays.asList(simb.build());
		// 商品移動
		ShohinIdoBuilder sib = new ShohinIdoBuilder();
		sib.withKubun(ShohinIdoKubun.入荷);
		sib.withMeisai(meisai);
		ShohinIdo nyukaShohinIdo = sib.build();
		// 商品移動明細 訂正
		ShohinIdoMeisaiBuilder simbt = new ShohinIdoMeisaiBuilder();
		simbt.withNumber(-5);
		simbt.withShohinDomain(shohin);
		List<ShohinIdoMeisai> meisait = Arrays.asList(simbt.build());
		// 商品移動 訂正
		ShohinIdoBuilder sibt = new ShohinIdoBuilder();
		sibt.withKubun(ShohinIdoKubun.入荷訂正);
		sibt.withMeisai(meisait);
		ShohinIdo nyukaShohinIdot = sibt.build();
		// 入荷
		NyukaBuilder nb = new NyukaBuilder();
		nb.withNyukaShohinIdo(nyukaShohinIdo);
		nb.withTeiseiList(Arrays.asList(nyukaShohinIdot));
		Nyuka nyuka = nb.build();
		// do
		boolean actual = nyuka.doneTeisei();
		// check
		assertTrue(actual);
	}

	// 訂正済判定: false
	@Test
	public void test_DoneTeisei_02() throws Exception {
		// 商品
		Shohin shohin = new ShohinBuilder().withCode("SH01").build();
		// 商品移動明細
		ShohinIdoMeisaiBuilder simb = new ShohinIdoMeisaiBuilder();
		simb.withNumber(5);
		simb.withShohinDomain(shohin);
		List<ShohinIdoMeisai> meisai = Arrays.asList(simb.build());
		// 商品移動
		ShohinIdoBuilder sib = new ShohinIdoBuilder();
		sib.withKubun(ShohinIdoKubun.入荷);
		sib.withMeisai(meisai);
		ShohinIdo nyukaShohinIdo = sib.build();
		// 入荷
		NyukaBuilder nb = new NyukaBuilder();
		nb.withNyukaShohinIdo(nyukaShohinIdo);
		nb.withTeiseiList(Arrays.asList());
		Nyuka nyuka = nb.build();
		// do
		boolean actual = nyuka.doneTeisei();
		// check
		assertFalse(actual);
	}

	// 営業日における訂正有無判定
	@Test
	public void test_DoneTeiseiEigyoDate_01() throws Exception {
		// 商品
		Shohin shohin = new ShohinBuilder().withCode("SH01").build();
		// 商品移動明細
		ShohinIdoMeisaiBuilder simb = new ShohinIdoMeisaiBuilder();
		simb.withNumber(5);
		simb.withShohinDomain(shohin);
		List<ShohinIdoMeisai> meisai = Arrays.asList(simb.build());
		// 商品移動
		ShohinIdoBuilder sib = new ShohinIdoBuilder();
		sib.withKubun(ShohinIdoKubun.入荷);
		sib.withMeisai(meisai);
		ShohinIdo nyukaShohinIdo = sib.build();
		// 商品移動明細 訂正
		ShohinIdoMeisaiBuilder simbt = new ShohinIdoMeisaiBuilder();
		simbt.withNumber(-1);
		simbt.withShohinDomain(shohin);
		List<ShohinIdoMeisai> meisait = Arrays.asList(simbt.build());
		// 商品移動 訂正
		ShohinIdoBuilder sibt = new ShohinIdoBuilder();
		sibt.withKubun(ShohinIdoKubun.入荷訂正);
		sibt.withMeisai(meisait);
		sibt.withDate(new EigyoDate(2018, 1, 2));
		ShohinIdo nyukaShohinIdot = sibt.build();
		// 入荷
		NyukaBuilder nb = new NyukaBuilder();
		nb.withNyukaShohinIdo(nyukaShohinIdo);
		nb.withTeiseiList(Arrays.asList(nyukaShohinIdot));
		Nyuka nyuka = nb.build();
		// do
		boolean actual = nyuka.doneTeisei(new EigyoDate(2018, 1, 2));
		// check
		assertTrue(actual);
	}

	// 指定した日付の商品移動を取得
	@Test
	public void test_GetShohinIdoOf_01() throws Exception {
		// 商品
		Shohin shohin = new ShohinBuilder().withCode("SH01").build();
		// 商品移動明細
		ShohinIdoMeisaiBuilder simb = new ShohinIdoMeisaiBuilder();
		simb.withNumber(5);
		simb.withShohinDomain(shohin);
		List<ShohinIdoMeisai> meisai = Arrays.asList(simb.build());
		// 商品移動
		ShohinIdoBuilder sib = new ShohinIdoBuilder();
		sib.withKubun(ShohinIdoKubun.入荷);
		sib.withMeisai(meisai);
		sib.withDate(new EigyoDate(2018, 1, 1));
		ShohinIdo nyukaShohinIdo = sib.build();
		// 商品移動明細 訂正
		ShohinIdoMeisaiBuilder simbt = new ShohinIdoMeisaiBuilder();
		simbt.withNumber(-1);
		simbt.withShohinDomain(shohin);
		List<ShohinIdoMeisai> meisait = Arrays.asList(simbt.build());
		// 商品移動 訂正
		ShohinIdoBuilder sibt = new ShohinIdoBuilder();
		sibt.withKubun(ShohinIdoKubun.入荷訂正);
		sibt.withMeisai(meisait);
		sibt.withDate(new EigyoDate(2018, 1, 2));
		ShohinIdo nyukaShohinIdot = sibt.build();
		// 入荷
		NyukaBuilder nb = new NyukaBuilder();
		nb.withNyukaShohinIdo(nyukaShohinIdo);
		nb.withTeiseiList(Arrays.asList(nyukaShohinIdot));
		Nyuka nyuka = nb.build();
		// do
		Optional<ShohinIdo> actual = nyuka.getShohinIdoOf(new EigyoDate(2018, 1, 1));
		// check
		assertEquals(new EigyoDate(2018, 1, 1), actual.get().getDate());
	}

	// 最新商品移動取得, 未訂正の場合
	@Test
	public void test_GetNewestShohinIdo_01() throws Exception {
		// 商品
		Shohin shohin = new ShohinBuilder().withCode("SH01").build();
		// 商品移動明細
		ShohinIdoMeisaiBuilder simb = new ShohinIdoMeisaiBuilder();
		simb.withNumber(5);
		simb.withShohinDomain(shohin);
		List<ShohinIdoMeisai> meisai = Arrays.asList(simb.build());
		// 商品移動
		ShohinIdoBuilder sib = new ShohinIdoBuilder();
		sib.withKubun(ShohinIdoKubun.入荷);
		sib.withMeisai(meisai);
		sib.withDate(new EigyoDate(2018, 1, 1));
		ShohinIdo nyukaShohinIdo = sib.build();
		// 入荷
		NyukaBuilder nb = new NyukaBuilder();
		nb.withNyukaShohinIdo(nyukaShohinIdo);
		nb.withTeiseiList(Arrays.asList());
		Nyuka nyuka = nb.build();
		// do
		ShohinIdo actual = nyuka.getNewestShohinIdo();
		assertEquals(nyukaShohinIdo, actual);
	}

	// 最新商品移動取得, 訂正ありの場合、移動日=最新の商品移動が対象となる。
	@Test
	public void test_GetNewestShohinIdo_02() throws Exception {
		// 商品移動
		ShohinIdoBuilder sib = new ShohinIdoBuilder();
		sib.withKubun(ShohinIdoKubun.入荷);
		sib.withDate(new EigyoDate(2018, 1, 1));
		ShohinIdo nyukaShohinIdo = sib.build();
		// 商品移動 訂正
		ShohinIdoBuilder sibt = new ShohinIdoBuilder();
		sibt.withKubun(ShohinIdoKubun.入荷訂正);
		sibt.withDate(new EigyoDate(2018, 1, 2));
		ShohinIdo nyukaShohinIdot = sibt.build();
		// 商品移動 再訂正
		ShohinIdoBuilder sibt2 = new ShohinIdoBuilder();
		sibt2.withKubun(ShohinIdoKubun.入荷訂正);
		sibt2.withDate(new EigyoDate(2018, 1, 3));
		ShohinIdo nyukaShohinIdot2 = sibt2.build();
		// 入荷
		NyukaBuilder nb = new NyukaBuilder();
		nb.withNyukaShohinIdo(nyukaShohinIdo);
		nb.withTeiseiList(Arrays.asList(nyukaShohinIdot, nyukaShohinIdot2));
		Nyuka nyuka = nb.build();
		// do
		ShohinIdo actual = nyuka.getNewestShohinIdo();
		// check
		assertEquals(new EigyoDate(2018, 1, 3), actual.getDate());
	}
}
