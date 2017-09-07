package com.showka.service.validate.u01;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.ServiceCrudTestCase;
import com.showka.domain.KokyakuDomain;
import com.showka.domain.builder.KokyakuDomainBuilder;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.KokyakuKubun;
import com.showka.system.exception.ValidateException;

public class KokyakuValidateServiceImplTest extends ServiceCrudTestCase {

	/**
	 * 顧客 Validate Service Test
	 *
	 * @author 25767
	 *
	 */
	@Autowired
	private KokyakuValidateServiceImpl service;

	/**
	 * table name
	 */
	private static final String TABLE_NAME = "m_kokyaku";

	/**
	 * columns
	 */
	private static final String[] COLUMN = { "code", "name", "address", "hanbai_kubun", "kokyaku_kubun",
			"shukan_busho_id", "record_id" };

	/**
	 * test data
	 */
	private static final Object[] VALUE01 = { "KK01", "aaaa", "左京区", "00", "01", "BS01", "KK01" };
	private static final Object[] VALUE02 = { "KK02", "aaaa", "右京区", "00", "01", "BS02", "KK02" };
	private static final Object[] VALUE03 = { "KK03", "bbbb", "上京区", "00", "01", "BS02", "KK03" };

	/**
	 * Before
	 */
	@Before
	public void before() {
		super.deleteAll(TABLE_NAME);
		super.insert(TABLE_NAME, COLUMN, VALUE01, VALUE02, VALUE03);
	}

	/**
	 * validateForRegister 顧客コードが既に使われているか検証する
	 *
	 * <pre>
	 * 入力：顧客domain <br>
	 * 条件：顧客コードが使われていない <br>
	 * 結果：成功
	 *
	 * <pre>
	 */
	@Test
	public void test_validateForRegister1() {

		String id = "KK05";
		Integer version = 0;
		String record_id = "this is inserted record";

		// set up builder
		KokyakuDomainBuilder builder = new KokyakuDomainBuilder();
		builder.withCode(id);
		builder.withName("aaaa");
		builder.withAddress("北区");
		builder.withKokyakuKubun(KokyakuKubun.法人);
		builder.withHanbaiKubun(HanbaiKubun.掛売);
		builder.withShukanBushoId("BS01");

		builder.withVersion(version);
		builder.withRecordId(record_id);

		// build domain
		KokyakuDomain domain = builder.build();

		service.validateForRegister(domain);
	}

	/**
	 * validateForRegister 顧客コードが既に使われているか検証する
	 *
	 * <pre>
	 * 入力：顧客domain <br>
	 * 条件：顧客コードが既に使われている <br>
	 * 結果：失敗
	 *
	 * <pre>
	 */
	@Test(expected = ValidateException.class)
	public void test_validateForRegister2() {

		String id = "KK01";
		Integer version = 0;
		String record_id = "this is inserted record";

		// set up builder
		KokyakuDomainBuilder builder = new KokyakuDomainBuilder();
		builder.withCode(id);
		builder.withName("aaaa");
		builder.withAddress("北区");
		builder.withKokyakuKubun(KokyakuKubun.法人);
		builder.withHanbaiKubun(HanbaiKubun.掛売);
		builder.withShukanBushoId("BS01");

		builder.withVersion(version);
		builder.withRecordId(record_id);

		// build domain
		KokyakuDomain domain = builder.build();

		service.validateForRegister(domain);

		fail();
	}

	/**
	 * validate 個人顧客に掛売が設定されていないか検証する
	 *
	 * <pre>
	 * 入力：顧客domain <br>
	 * 条件：個人顧客に現金が設定されている <br>
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
		KokyakuDomainBuilder builder = new KokyakuDomainBuilder();
		builder.withCode(id);
		builder.withName("aaaa");
		builder.withAddress("北区");
		builder.withKokyakuKubun(KokyakuKubun.個人);
		builder.withHanbaiKubun(HanbaiKubun.現金);
		builder.withShukanBushoId("BS01");

		builder.withVersion(version);
		builder.withRecordId(record_id);

		// build domain
		KokyakuDomain domain = builder.build();

		service.validate(domain);
	}

	/**
	 * validate 個人顧客に掛売が設定されていないか検証する
	 *
	 * <pre>
	 * 入力：顧客domain <br>
	 * 条件：法人顧客 <br>
	 * 結果：成功
	 *
	 * <pre>
	 */
	@Test
	public void test_validate2() {

		String id = "KK01";
		Integer version = 0;
		String record_id = "this is inserted record";

		// set up builder
		KokyakuDomainBuilder builder = new KokyakuDomainBuilder();
		builder.withCode(id);
		builder.withName("aaaa");
		builder.withAddress("北区");
		builder.withKokyakuKubun(KokyakuKubun.法人);
		builder.withHanbaiKubun(HanbaiKubun.現金);
		builder.withShukanBushoId("BS01");

		builder.withVersion(version);
		builder.withRecordId(record_id);

		// build domain
		KokyakuDomain domain = builder.build();

		service.validate(domain);
	}

	/**
	 * validate 個人顧客に掛売が設定されていないか検証する
	 *
	 * <pre>
	 * 入力：顧客domain <br>
	 * 条件：個人顧客に掛売が設定されている <br>
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
		KokyakuDomainBuilder builder = new KokyakuDomainBuilder();
		builder.withCode(id);
		builder.withName("aaaa");
		builder.withAddress("北区");
		builder.withKokyakuKubun(KokyakuKubun.個人);
		builder.withHanbaiKubun(HanbaiKubun.掛売);
		builder.withShukanBushoId("BS01");

		builder.withVersion(version);
		builder.withRecordId(record_id);

		// build domain
		KokyakuDomain domain = builder.build();

		service.validate(domain);
	}
}
