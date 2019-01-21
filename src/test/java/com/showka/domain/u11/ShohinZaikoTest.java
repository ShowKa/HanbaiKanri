package com.showka.domain.u11;

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
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shohin;
import com.showka.kubun.ShohinIdoKubun;
import com.showka.value.EigyoDate;
import com.showka.value.TheTimestamp;

public class ShohinZaikoTest extends SimpleTestCase {

	/** 商品01. */
	private static final Shohin shohinDomain01;
	static {
		ShohinBuilder b = new ShohinBuilder();
		b.withCode("SH01");
		shohinDomain01 = b.build();
	}

	/** 商品02. */
	private static final Shohin shohinDomain02;
	static {
		ShohinBuilder b = new ShohinBuilder();
		b.withCode("SH02");
		shohinDomain02 = b.build();
	}

	/** 移動明細01. 商品01を1つ移動 */
	private static final ShohinIdoMeisai idoMeisai01;
	static {
		ShohinIdoMeisaiBuilder b = new ShohinIdoMeisaiBuilder();
		b.withMeisaiNumber(1);
		b.withNumber(1);
		b.withShohinDomain(shohinDomain01);
		idoMeisai01 = b.build();
	}

	/** 移動明細02. 商品01を2つ移動 */
	private static final ShohinIdoMeisai idoMeisai02;
	static {
		ShohinIdoMeisaiBuilder b = new ShohinIdoMeisaiBuilder();
		b.withMeisaiNumber(2);
		b.withNumber(2);
		b.withShohinDomain(shohinDomain01);
		idoMeisai02 = b.build();
	}

	/** 移動明細03. 商品02を3つ移動 */
	private static final ShohinIdoMeisai idoMeisai03;
	static {
		ShohinIdoMeisaiBuilder b = new ShohinIdoMeisaiBuilder();
		b.withMeisaiNumber(3);
		b.withNumber(3);
		b.withShohinDomain(shohinDomain02);
		idoMeisai03 = b.build();
	}

	/** 移動01. 2017/1/1 0時 売上による消費 */
	private static final ShohinIdo ido01;
	static {
		List<ShohinIdoMeisai> meisai = new ArrayList<ShohinIdoMeisai>();
		meisai.add(idoMeisai01);
		meisai.add(idoMeisai02);
		meisai.add(idoMeisai03);
		ShohinIdoBuilder b = new ShohinIdoBuilder();
		b.withDate(new EigyoDate(2017, 1, 1));
		b.withTimestamp(new TheTimestamp(2017, 1, 1, 0, 0, 0, 0));
		b.withKubun(ShohinIdoKubun.売上);
		b.withMeisai(meisai);
		ido01 = b.build();
	}

	/** 移動02. 2017/1/1 3時 売上訂正による戻し */
	private static final ShohinIdo ido02;
	static {
		List<ShohinIdoMeisai> meisai = new ArrayList<ShohinIdoMeisai>();
		meisai.add(idoMeisai01);
		meisai.add(idoMeisai02);
		meisai.add(idoMeisai03);
		ShohinIdoBuilder b = new ShohinIdoBuilder();
		b.withDate(new EigyoDate(2017, 1, 1));
		b.withTimestamp(new TheTimestamp(2017, 1, 1, 3, 0, 0, 0));
		b.withKubun(ShohinIdoKubun.売上訂正);
		b.withMeisai(meisai);
		ido02 = b.build();
	}

	/** 在庫01 = 商品01.(4 -> 3使う) */
	private static final ShohinZaiko zaiko01;
	static {
		ShohinZaikoBuilder b = new ShohinZaikoBuilder();
		b.withKurikoshiNumber(4);
		b.withShohin(shohinDomain01);
		b.withShohinIdoList(Arrays.asList(ido01));
		zaiko01 = b.build();
	}

	/** 在庫02 = 商品02.(3 -> 3使う) */
	private static final ShohinZaiko zaiko02;
	static {
		ShohinZaikoBuilder b = new ShohinZaikoBuilder();
		b.withKurikoshiNumber(3);
		b.withShohin(shohinDomain02);
		b.withShohinIdoList(Arrays.asList(ido01));
		zaiko02 = b.build();
	}

	/** 在庫03 = 商品02.(2 -> 3使う) */
	private static final ShohinZaiko zaiko03;
	static {
		ShohinZaikoBuilder b = new ShohinZaikoBuilder();
		b.withKurikoshiNumber(2);
		b.withShohin(shohinDomain02);
		b.withShohinIdoList(Arrays.asList(ido01));
		zaiko03 = b.build();
	}

	/** 在庫04 = 商品02.(2 -> 0時に3使う -> 3時に3戻す) */
	private static final ShohinZaiko zaiko04;
	static {
		ShohinZaikoBuilder b = new ShohinZaikoBuilder();
		b.withKurikoshiNumber(2);
		b.withShohin(shohinDomain02);
		b.withShohinIdoList(Arrays.asList(ido01, ido02));
		zaiko04 = b.build();
	}

	@Test
	public void test04_Exists() throws Exception {
		boolean actual = zaiko01.exists();
		assertTrue(actual);
	}

	@Test
	public void test05_Exists() throws Exception {
		boolean actual = zaiko02.exists();
		assertEquals(false, actual);
	}

	@Test
	public void test06_isNegative() throws Exception {
		boolean actual = zaiko02.isNegative();
		assertEquals(false, actual);
	}

	@Test
	public void test07_isNegative() throws Exception {
		boolean actual = zaiko03.isNegative();
		assertEquals(true, actual);
	}

	@Test
	public void test08_GetKurikoshiNumber() throws Exception {
		Integer actual = zaiko01.getKurikoshiNumber();
		assertEquals(4, actual.intValue());
	}

	@Test
	public void test09_GetNumber() throws Exception {
		Integer actual = zaiko01.getNumber();
		assertEquals(1, actual.intValue());
	}

	@Test
	public void test10_GetNumber() throws Exception {
		Integer actual = zaiko04.getNumber(new TheTimestamp(2017, 1, 1, 2, 0, 0, 0));
		assertEquals(-1, actual.intValue());
	}

	@Test
	public void test11_GetNumber() throws Exception {
		Integer actual = zaiko04.getNumber(new TheTimestamp(2017, 1, 1, 3, 0, 0, 0));
		assertEquals(2, actual.intValue());
	}

	@Test
	public void test12_CloneForKurikoshi() throws Exception {
		EigyoDate nextDate = new EigyoDate(2017, 1, 2);
		ShohinZaiko actual = zaiko01.cloneForKurikoshi(nextDate);
		assertEquals(1, actual.getKurikoshiNumber().intValue());
		assertEquals(nextDate, actual.getDate());
		assertEquals(0, actual.getShohinIdoList().size());
	}

	@Test
	public void test13_BuildZeroZaiko() throws Exception {
		Busho busho = new BushoBuilder().build();
		EigyoDate date = new EigyoDate();
		Shohin shohin = new ShohinBuilder().build();
		ShohinZaiko actual = ShohinZaiko.buildZeroZaiko(busho, date, shohin);
		assertEquals(0, actual.getKurikoshiNumber().intValue());
		assertEquals(0, actual.getNumber().intValue());
	}

	@Test
	public void test14_Remove() throws Exception {
		ShohinZaiko actual = zaiko01.remove(ido01);
		assertEquals(0, actual.getShohinIdoList().size());
	}

	// merge 商品移動新規追加
	@Test
	public void test15_merge() throws Exception {
		ShohinZaiko actual = zaiko01.merge(ido02);
		assertEquals(2, actual.getShohinIdoList().size());
	}

	// merge 既存の商品移動追加
	@Test
	public void test16_merge() throws Exception {
		ShohinZaiko actual = zaiko01.merge(ido01);
		assertEquals(1, actual.getShohinIdoList().size());
	}
}
