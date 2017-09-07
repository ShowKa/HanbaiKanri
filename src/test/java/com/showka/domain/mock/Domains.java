package com.showka.domain.mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import com.showka.domain.BushoDomain;
import com.showka.domain.KokyakuDomain;
import com.showka.domain.ShohinDomain;
import com.showka.domain.UriageDomain;
import com.showka.domain.UriageMeisaiDomain;
import com.showka.domain.builder.BushoDomainBuilder;
import com.showka.domain.builder.KokyakuDomainBuilder;
import com.showka.domain.builder.ShohinDomainBuilder;
import com.showka.domain.builder.UriageDomainBuilder;
import com.showka.domain.builder.UriageMeisaiDomainBuilder;
import com.showka.kubun.BushoKubun;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.JigyoKubun;
import com.showka.kubun.KokyakuKubun;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;

import lombok.Getter;

@Getter
public class Domains {

	/** 部署01. */
	public static final BushoDomain busho01;
	static {
		BushoDomainBuilder b = new BushoDomainBuilder();
		busho01 = b.withCode("BS01")
				.withBushoKubun(BushoKubun.営業所)
				.withJigyoKubun(JigyoKubun.販売)
				.withName("部署01")
				.withRecordId("r-BS01")
				.build();
	}

	/** 部署02. */
	public static final BushoDomain busho02;
	static {
		BushoDomainBuilder b = new BushoDomainBuilder();
		busho02 = b.withCode("BS02")
				.withBushoKubun(BushoKubun.営業所)
				.withJigyoKubun(JigyoKubun.販売)
				.withName("部署02")
				.withRecordId("BS02")
				.build();
	}

	// 顧客
	/**
	 * table name
	 */
	public static final String M_KOKYAKU_TABLE = "m_kokyaku";

	/**
	 * columns
	 */
	public static final String[] M_KOKYAKU_COLUMN = { "code", "name", "address", "kokyaku_kubun", "hanbai_kubun",
			"shukan_busho_id", "record_id" };

	/** 顧客01. */
	public static final Object[] KOKYAKU01 = { "KK01", "顧客01", "左京区", "01", "00", "r-BS01", "r-KK01" };
	public static final KokyakuDomain kokyaku01;
	static {
		KokyakuDomainBuilder b = new KokyakuDomainBuilder();
		kokyaku01 = b.withCode("KK01")
				.withName("顧客01")
				.withAddress("左京区")
				.withKokyakuKubun(KokyakuKubun.法人)
				.withHanbaiKubun(HanbaiKubun.現金)
				.withRecordId("r-KK01")
				.build();
	}

	/** 顧客02. */
	public static final KokyakuDomain kokyaku02;
	static {
		KokyakuDomainBuilder b = new KokyakuDomainBuilder();
		kokyaku02 = b.withCode("KK02")
				.withName("顧客02")
				.withCode("右京区")
				.withKokyakuKubun(KokyakuKubun.法人)
				.withHanbaiKubun(HanbaiKubun.現金)
				.withRecordId("KK02")
				.build();
	}

	/** 顧客03. */
	public static final KokyakuDomain kokyaku03;
	static {
		KokyakuDomainBuilder b = new KokyakuDomainBuilder();
		kokyaku03 = b.withCode("KK03")
				.withName("顧客03")
				.withCode("上京区")
				.withKokyakuKubun(KokyakuKubun.個人)
				.withHanbaiKubun(HanbaiKubun.現金)
				.withRecordId("KK03")
				.build();
	}

	/** 商品01. */
	public static final ShohinDomain shohin01;
	static {
		ShohinDomainBuilder b = new ShohinDomainBuilder();
		b.withCode("SH01").withName("商品SH01").withHyojunTanka(BigDecimal.valueOf(1000)).withRecordId("SH01");
		shohin01 = b.build();
	}

	/** 商品02. */
	public static final ShohinDomain shohin02;
	static {
		ShohinDomainBuilder b = new ShohinDomainBuilder();
		shohin02 = b.withCode("SH02")
				.withName("商品SH02")
				.withHyojunTanka(BigDecimal.valueOf(1001))
				.withRecordId("SH02")
				.build();
	}

	// 売上明細ドメイン
	/**
	 * table name
	 */
	public static final String T_URIAGE_MEISAI_TABLE = "t_uriage_meisai";

	/**
	 * columns
	 */
	public static final String[] T_URIAGE_MEISAI_COLUMN = { "uriage_id", "meisai_number", "shohin_id", "hanbai_number",
			"hanbai_tanka", "record_id" };

	/** 売上明細01. */
	public static final Object[] URIAGE_MEISAI_01 = { "r-KK01-00001", 1, "r-SH01", 5, 1000, "r-KK01-00001-1" };
	public static final UriageMeisaiDomain uriageMeisai01;
	static {
		UriageMeisaiDomainBuilder b = new UriageMeisaiDomainBuilder();
		b.withUriageId("KK01-00001")
				.withMeisaiNumber(1)
				.withShohinDomain(shohin01)
				.withHanbaiNumber(5)
				.withHanbaiTanka(BigDecimal.valueOf(1000))
				.withRecordId("KK01-00001-1");
		uriageMeisai01 = b.build();
	}

	/** 売上明細02. */
	public static final Object[] URIAGE_MEISAI_02 = { "r-KK01-00001", 2, "r-SH02", 5, 1001, "r-KK01-00001-2" };
	public static final UriageMeisaiDomain uriageMeisai02;
	static {
		UriageMeisaiDomainBuilder b = new UriageMeisaiDomainBuilder();
		b.withMeisaiNumber(1)
				.withShohinDomain(shohin02)
				.withHanbaiNumber(5)
				.withHanbaiTanka(BigDecimal.valueOf(1000))
				.withRecordId("KK01-00001-1");
		uriageMeisai02 = b.build();
	}

	/** 売上明細03. */
	public static final UriageMeisaiDomain uriageMeisai03;
	static {
		UriageMeisaiDomainBuilder b = new UriageMeisaiDomainBuilder();
		uriageMeisai03 = b.withUriageId("KK01-00002")
				.withMeisaiNumber(1)
				.withShohinDomain(shohin01)
				.withHanbaiNumber(10)
				.withHanbaiTanka(BigDecimal.valueOf(1000))
				.withRecordId("KK01-00002-1")
				.build();
	}

	/** 売上明細04. */
	public static final UriageMeisaiDomain uriageMeisai04;
	static {
		UriageMeisaiDomainBuilder b = new UriageMeisaiDomainBuilder();
		uriageMeisai04 = b.withUriageId("KK01-00002")
				.withMeisaiNumber(2)
				.withShohinDomain(shohin02)
				.withHanbaiNumber(10)
				.withHanbaiTanka(BigDecimal.valueOf(1001))
				.withRecordId("KK01-00002-2")
				.build();
	}

	// 売上ドメイン
	/**
	 * table name
	 */
	public static final String T_URIAGE_TABLE = "t_uriage";

	/**
	 * columns
	 */
	public static final String[] T_URIAGE_COLUMN = { "kokyaku_id", "denpyo_number", "uriage_date", "hanbai_kubun",
			"shohizeiritsu", "record_id" };

	/** 売上01. */
	private static final Object[] URIAGE_01 = { "r-KK01", "00001", new Date(2017, 8, 20), "00", 0.08, "r-KK01-00001" };
	public static final UriageDomain uriage01;
	static {
		UriageDomainBuilder b = new UriageDomainBuilder();
		ArrayList<UriageMeisaiDomain> meisai = new ArrayList<UriageMeisaiDomain>();
		meisai.add(uriageMeisai01);
		meisai.add(uriageMeisai02);
		uriage01 = b.withKokyaku(kokyaku01)
				.withDenpyoNumber("00001")
				.withUriageDate(new TheDate(2017, 8, 20))
				.withHanbaiKubun(HanbaiKubun.現金)
				.withShohizeiritsu(new TaxRate(0.08))
				.withUriageMeisai(meisai)
				.withRecordId("KK01-00001")
				.build();
	}

	/** 売上02. */
	public static final UriageDomain uriage02;
	static {
		UriageDomainBuilder b = new UriageDomainBuilder();
		ArrayList<UriageMeisaiDomain> meisai = new ArrayList<UriageMeisaiDomain>();
		meisai.add(uriageMeisai03);
		meisai.add(uriageMeisai04);
		uriage02 = b.withKokyaku(kokyaku01)
				.withDenpyoNumber("00002")
				.withUriageDate(new TheDate(2017, 8, 20))
				.withHanbaiKubun(HanbaiKubun.掛売)
				.withShohizeiritsu(new TaxRate(0.08))
				.withUriageMeisai(meisai)
				.withRecordId("KK01-00002")
				.build();
	}

}
