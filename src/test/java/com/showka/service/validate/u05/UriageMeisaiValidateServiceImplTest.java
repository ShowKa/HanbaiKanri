package com.showka.service.validate.u05;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.ServiceCrudTestCase;
import com.showka.domain.ShohinDomain;
import com.showka.domain.UriageMeisaiDomain;
import com.showka.domain.builder.ShohinDomainBuilder;
import com.showka.domain.builder.UriageMeisaiDomainBuilder;
import com.showka.system.exception.NotAllowedNumberException;

public class UriageMeisaiValidateServiceImplTest extends ServiceCrudTestCase {

	@Autowired
	private UriageMeisaiValidateServiceImpl service;

	/**
	 * validate 正常系.
	 *
	 * <pre>
	 * 入力：売上明細domain <br>
	 * 条件：正常 <br>
	 * 結果：成功
	 * 
	 * <pre>
	 */
	@Test
	public void test01_validate() {
		// 商品ドメインダミー
		ShohinDomainBuilder sh = new ShohinDomainBuilder();
		sh.withRecordId("shohin_record_id");
		ShohinDomain shohinDomain = sh.build();

		// 売上明細主キー
		String uriageId = "KK01-TEST001";
		Integer meisaiNumber = 1;
		String recordID = uriageId + "-" + meisaiNumber;

		// 売上明細ドメイン
		UriageMeisaiDomainBuilder b = new UriageMeisaiDomainBuilder();
		b.withHanbaiNumber(10);
		b.withHanbaiTanka(BigDecimal.valueOf(0));
		b.withMeisaiNumber(meisaiNumber);
		b.withRecordId(recordID);
		b.withShohinDomain(shohinDomain);
		b.withUriageId(uriageId);
		b.withVersion(0);
		UriageMeisaiDomain d = b.build();

		// do
		service.validate(d);

	}

	/**
	 * validate 異常系.
	 *
	 * <pre>
	 * 入力：売上明細domain <br>
	 * 条件：商品販売単価がマイナス <br>
	 * 結果：検証例外発生
	 * 
	 * <pre>
	 */
	@Test(expected = NotAllowedNumberException.class)
	public void test02_validate() {
		// 商品ドメインダミー
		ShohinDomainBuilder sh = new ShohinDomainBuilder();
		sh.withRecordId("shohin_record_id");
		ShohinDomain shohinDomain = sh.build();

		// 売上明細主キー
		String uriageId = "KK01-TEST001";
		Integer meisaiNumber = 1;
		String recordID = uriageId + "-" + meisaiNumber;

		// 売上明細ドメイン
		UriageMeisaiDomainBuilder b = new UriageMeisaiDomainBuilder();
		b.withHanbaiNumber(10);
		b.withHanbaiTanka(BigDecimal.valueOf(-1));
		b.withMeisaiNumber(meisaiNumber);
		b.withRecordId(recordID);
		b.withShohinDomain(shohinDomain);
		b.withUriageId(uriageId);
		b.withVersion(0);
		UriageMeisaiDomain d = b.build();

		// do
		service.validate(d);
		fail();
	}

}