package com.showka.service.validate.u01;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.ServiceCrudTestCase;
import com.showka.domain.NyukinKakeInfoDomain;
import com.showka.domain.builder.NyukinKakeInfoDomainBuilder;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.kubun.NyukinTsukiKubun;
import com.showka.system.exception.ValidateException;

public class NyukinKakeInfoValidateServiceImplTest extends ServiceCrudTestCase {

	/**
	 * 入金掛情報 Validate Service Test
	 *
	 * @author 25767
	 *
	 */
	@Autowired
	private NyukinKakeInfoValidateServiceImpl service;

	/**
	 * validate 入金日が締日より後になっているか検証する
	 *
	 * <pre>
	 * 入力：入金掛情報domain <br>
	 * 条件：入金日が締日より後 <br>
	 * 結果：成功
	 *
	 * <pre>
	 */
	@Test
	public void test_validate1() {

		String id = "KK01";
		Integer version = 0;
		String record_id = "this is inserted record";

		// set up builder
		NyukinKakeInfoDomainBuilder builder = new NyukinKakeInfoDomainBuilder();
		builder.withKokyakuId(id);
		builder.withNyukinHohoKubun(NyukinHohoKubun.振込);
		builder.withNyukinTsukiKubun(NyukinTsukiKubun.翌々月);
		builder.withShimeDate(20);
		builder.withNyukinDate(15);

		builder.withVersion(version);
		builder.withRecordId(record_id);

		// build domain
		NyukinKakeInfoDomain domain = builder.build();

		service.validate(domain);
	}

	/**
	 * validate 入金日が締日より後になっているか検証する
	 *
	 * <pre>
	 * 入力：入金掛情報domain <br>
	 * 条件：入金日と締日が一致 <br>
	 * 結果：失敗
	 *
	 * <pre>
	 */
	@Test(expected = ValidateException.class)
	public void test_validate2() {

		String id = "KK01";
		Integer version = 0;
		String record_id = "this is inserted record";

		// set up builder
		NyukinKakeInfoDomainBuilder builder = new NyukinKakeInfoDomainBuilder();
		builder.withKokyakuId(id);
		builder.withNyukinHohoKubun(NyukinHohoKubun.振込);
		builder.withNyukinTsukiKubun(NyukinTsukiKubun.当月);
		builder.withShimeDate(25);
		builder.withNyukinDate(25);

		builder.withVersion(version);
		builder.withRecordId(record_id);

		// build domain
		NyukinKakeInfoDomain domain = builder.build();

		service.validate(domain);
	}

	/**
	 * validate 入金日が締日より後になっているか検証する
	 *
	 * <pre>
	 * 入力：入金掛情報domain <br>
	 * 条件：入金日が締日より前 <br>
	 * 結果：失敗
	 *
	 * <pre>
	 */
	@Test(expected = ValidateException.class)
	public void test_validate3() {

		String id = "KK01";
		Integer version = 0;
		String record_id = "this is inserted record";

		// set up builder
		NyukinKakeInfoDomainBuilder builder = new NyukinKakeInfoDomainBuilder();
		builder.withKokyakuId(id);
		builder.withNyukinHohoKubun(NyukinHohoKubun.振込);
		builder.withNyukinTsukiKubun(NyukinTsukiKubun.当月);
		builder.withShimeDate(20);
		builder.withNyukinDate(15);

		builder.withVersion(version);
		builder.withRecordId(record_id);

		// build domain
		NyukinKakeInfoDomain domain = builder.build();

		service.validate(domain);
	}
}
