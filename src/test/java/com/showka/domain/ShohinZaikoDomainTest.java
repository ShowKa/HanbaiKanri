package com.showka.domain;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.ShohinZaikoDomain.ShohinIdoOnDate;
import com.showka.domain.builder.ShohinDomainBuilder;
import com.showka.domain.builder.ShohinIdoDomainBuilder;
import com.showka.domain.builder.ShohinIdoMeisaiDomainBuilder;
import com.showka.domain.builder.ShohinZaikoDomainBuilder;
import com.showka.kubun.ShohinIdoKubun;
import com.showka.value.TheTimestamp;

public class ShohinZaikoDomainTest extends SimpleTestCase {

	/** 商品01. */
	private static final ShohinDomain shohinDomain01;
	static {
		ShohinDomainBuilder b = new ShohinDomainBuilder();
		b.withCode("SH01");
		shohinDomain01 = b.build();
	}

	/** 商品02. */
	private static final ShohinDomain shohinDomain02;
	static {
		ShohinDomainBuilder b = new ShohinDomainBuilder();
		b.withCode("SH02");
		shohinDomain02 = b.build();
	}

	/** 移動明細01. 商品01を1つ移動 */
	private static final ShohinIdoMeisaiDomain idoMeisai01;
	static {
		ShohinIdoMeisaiDomainBuilder b = new ShohinIdoMeisaiDomainBuilder();
		b.withMeisaiNumber(1);
		b.withNumber(1);
		b.withShohinDomain(shohinDomain01);
		idoMeisai01 = b.build();
	}

	/** 移動明細02. 商品01を2つ移動 */
	private static final ShohinIdoMeisaiDomain idoMeisai02;
	static {
		ShohinIdoMeisaiDomainBuilder b = new ShohinIdoMeisaiDomainBuilder();
		b.withMeisaiNumber(2);
		b.withNumber(2);
		b.withShohinDomain(shohinDomain01);
		idoMeisai02 = b.build();
	}

	/** 移動明細03. 商品02を3つ移動 */
	private static final ShohinIdoMeisaiDomain idoMeisai03;
	static {
		ShohinIdoMeisaiDomainBuilder b = new ShohinIdoMeisaiDomainBuilder();
		b.withMeisaiNumber(3);
		b.withNumber(3);
		b.withShohinDomain(shohinDomain02);
		idoMeisai03 = b.build();
	}

	/** 移動01. 2017/1/1 0時 売上による消費 */
	private static final ShohinIdoDomain ido01;
	static {
		List<ShohinIdoMeisaiDomain> meisai = new ArrayList<ShohinIdoMeisaiDomain>();
		meisai.add(idoMeisai01);
		meisai.add(idoMeisai02);
		meisai.add(idoMeisai03);
		ShohinIdoDomainBuilder b = new ShohinIdoDomainBuilder();
		b.withTimestamp(new TheTimestamp(2017, 1, 1, 0, 0, 0, 0));
		b.withKubun(ShohinIdoKubun.売上);
		b.withMeisai(meisai);
		ido01 = b.build();
	}

	/** 移動02. 2017/1/1 3時 売上訂正による戻し */
	private static final ShohinIdoDomain ido02;
	static {
		List<ShohinIdoMeisaiDomain> meisai = new ArrayList<ShohinIdoMeisaiDomain>();
		meisai.add(idoMeisai01);
		meisai.add(idoMeisai02);
		meisai.add(idoMeisai03);
		ShohinIdoDomainBuilder b = new ShohinIdoDomainBuilder();
		b.withTimestamp(new TheTimestamp(2017, 1, 1, 3, 0, 0, 0));
		b.withKubun(ShohinIdoKubun.売上訂正);
		b.withMeisai(meisai);
		ido02 = b.build();
	}

	/** 在庫01 = 商品01.(4 -> 3使う) */
	private static final ShohinZaikoDomain zaiko01;
	static {
		List<ShohinIdoOnDate> shohinIdoList = new ArrayList<ShohinIdoOnDate>();
		shohinIdoList.add(new ShohinIdoOnDate(ido01, shohinDomain01));
		ShohinZaikoDomainBuilder b = new ShohinZaikoDomainBuilder();
		b.withKurikoshiNumber(4);
		b.withShohinIdoList(shohinIdoList);
		zaiko01 = b.build();
	}

	/** 在庫02 = 商品02.(3 -> 3使う) */
	private static final ShohinZaikoDomain zaiko02;
	static {
		List<ShohinIdoOnDate> shohinIdoList = new ArrayList<ShohinIdoOnDate>();
		shohinIdoList.add(new ShohinIdoOnDate(ido01, shohinDomain02));
		ShohinZaikoDomainBuilder b = new ShohinZaikoDomainBuilder();
		b.withKurikoshiNumber(3);
		b.withShohinIdoList(shohinIdoList);
		zaiko02 = b.build();
	}

	/** 在庫03 = 商品02.(2 -> 3使う) */
	private static final ShohinZaikoDomain zaiko03;
	static {
		List<ShohinIdoOnDate> shohinIdoList = new ArrayList<ShohinIdoOnDate>();
		shohinIdoList.add(new ShohinIdoOnDate(ido01, shohinDomain02));
		ShohinZaikoDomainBuilder b = new ShohinZaikoDomainBuilder();
		b.withKurikoshiNumber(2);
		b.withShohinIdoList(shohinIdoList);
		zaiko03 = b.build();
	}

	/** 在庫04 = 商品02.(2 -> 0時に3使う -> 3時に3戻す) */
	private static final ShohinZaikoDomain zaiko04;
	static {
		List<ShohinIdoOnDate> shohinIdoList = new ArrayList<ShohinIdoOnDate>();
		shohinIdoList.add(new ShohinIdoOnDate(ido01, shohinDomain02));
		shohinIdoList.add(new ShohinIdoOnDate(ido02, shohinDomain02));
		ShohinZaikoDomainBuilder b = new ShohinZaikoDomainBuilder();
		b.withKurikoshiNumber(2);
		b.withShohinIdoList(shohinIdoList);
		zaiko04 = b.build();
	}

	@Test
	public void test01_ShohinIdoOnDate() throws Exception {
		ShohinIdoOnDate actual = new ShohinIdoOnDate(ido01, shohinDomain01);
		assertEquals(new TheTimestamp(2017, 1, 1), actual.getTimestamp());
		assertEquals(ShohinIdoKubun.売上, actual.getKubun());
		assertEquals(3, actual.getNumber().intValue());
	}

	@Test
	public void test02_GetIncreaseOrDecreaseNumber() throws Exception {
		ShohinIdoOnDate actual = new ShohinIdoOnDate(ido01, shohinDomain01);
		assertEquals(-3, actual.getIncreaseOrDecreaseNumber().intValue());
	}

	@Test
	public void test03_GetIncreaseOrDecreaseNumber() throws Exception {
		ShohinIdoOnDate actual = new ShohinIdoOnDate(ido02, shohinDomain02);
		assertEquals(3, actual.getIncreaseOrDecreaseNumber().intValue());
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

}
