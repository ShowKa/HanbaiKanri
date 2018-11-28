package com.showka.service.validator.u01;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.PersistenceTestCase;
import com.showka.domain.builder.NyukinKakeInfoBuilder;
import com.showka.domain.u01.NyukinKakeInfo;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.kubun.NyukinTsukiKubun;
import com.showka.service.validator.u01.NyukinKakeInfoValidatorImpl;
import com.showka.system.exception.ValidateException;

/**
 * 入金掛情報 Validate Service Test
 *
 * @author 25767
 *
 */
public class NyukinKakeInfoValidatorImplTest extends PersistenceTestCase {

	@Autowired
	private NyukinKakeInfoValidatorImpl service;

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
		NyukinKakeInfoBuilder builder = new NyukinKakeInfoBuilder();
		builder.withKokyakuId(id);
		builder.withNyukinHohoKubun(NyukinHohoKubun.振込);
		builder.withNyukinTsukiKubun(NyukinTsukiKubun.翌々月);
		builder.withShimeDate(20);
		builder.withNyukinDate(15);

		builder.withVersion(version);
		builder.withRecordId(record_id);

		// build domain
		NyukinKakeInfo domain = builder.build();

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
		NyukinKakeInfoBuilder builder = new NyukinKakeInfoBuilder();
		builder.withKokyakuId(id);
		builder.withNyukinHohoKubun(NyukinHohoKubun.振込);
		builder.withNyukinTsukiKubun(NyukinTsukiKubun.当月);
		builder.withShimeDate(25);
		builder.withNyukinDate(25);

		builder.withVersion(version);
		builder.withRecordId(record_id);

		// build domain
		NyukinKakeInfo domain = builder.build();

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
		NyukinKakeInfoBuilder builder = new NyukinKakeInfoBuilder();
		builder.withKokyakuId(id);
		builder.withNyukinHohoKubun(NyukinHohoKubun.振込);
		builder.withNyukinTsukiKubun(NyukinTsukiKubun.当月);
		builder.withShimeDate(20);
		builder.withNyukinDate(15);

		builder.withVersion(version);
		builder.withRecordId(record_id);

		// build domain
		NyukinKakeInfo domain = builder.build();

		service.validate(domain);
	}
}
