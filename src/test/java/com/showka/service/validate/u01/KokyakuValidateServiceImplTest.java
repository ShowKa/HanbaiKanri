package com.showka.service.validate.u01;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.showka.common.CrudServiceTestCase;
import com.showka.domain.BushoDomain;
import com.showka.domain.KokyakuDomain;
import com.showka.domain.NyukinKakeInfoDomain;
import com.showka.domain.builder.KokyakuDomainBuilder;
import com.showka.domain.builder.NyukinKakeInfoDomainBuilder;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.KokyakuKubun;
import com.showka.service.crud.z00.i.BushoCrudService;
import com.showka.system.exception.NotExistException;
import com.showka.system.exception.ValidateException;

/**
 * 顧客 Validate Service Test
 *
 * @author 25767
 *
 */
public class KokyakuValidateServiceImplTest extends CrudServiceTestCase {

	@Autowired
	private KokyakuValidateServiceImpl service;

	@Autowired
	private BushoCrudService bushoCRUDService;

	/**
	 * test data
	 */
	private static final Object[] VALUE01 = { "KK01", "aaaa", "左京区", "01", "00", "BS01", "KK01" };
	private static final Object[] VALUE02 = { "KK02", "aaaa", "右京区", "01", "00", "BS02", "KK02" };
	private static final Object[] VALUE03 = { "KK03", "bbbb", "上京区", "01", "00", "BS02", "KK03" };

	/**
	 * Before
	 */
	@Before
	public void before() {
		super.deleteAll(M_KOKYAKU);
		super.insert(M_KOKYAKU, M_KOKYAKU_COLUMN, VALUE01, VALUE02, VALUE03);
	}

	@Test(expected = NotExistException.class)
	public void test_validateForRefer1() {
		String id = "KK05";
		service.validateForRefer(id);
		fail();
	}

	@Test
	public void test_validateForRefer2() {
		String id = "KK01";
		service.validateForRefer(id);
		assertTrue(true);
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
	@Transactional
	public void test_validateForRegister1() {

		String id = "KK05";
		Integer version = 0;
		String record_id = "this is inserted record";
		String bushoId = "BS01";

		// build nyukinKakeInfo domain
		NyukinKakeInfoDomainBuilder nyukinBuilder = new NyukinKakeInfoDomainBuilder();
		nyukinBuilder.withKokyakuId(id);
		NyukinKakeInfoDomain nyukinDomain = nyukinBuilder.build();

		// get busho domain
		BushoDomain bushoDomain = bushoCRUDService.getDomain(bushoId);

		// build kokyaku domain
		KokyakuDomainBuilder builder = new KokyakuDomainBuilder();
		builder.withCode(id);
		builder.withShukanBusho(bushoDomain);
		builder.withNyukinKakeInfo(nyukinDomain);

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
	@Transactional
	public void test_validateForRegister2() {

		String id = "KK01";
		Integer version = 0;
		String record_id = "this is inserted record";
		String bushoId = "BS01";

		// build nyukinKakeInfo domain
		NyukinKakeInfoDomainBuilder nyukinBuilder = new NyukinKakeInfoDomainBuilder();
		nyukinBuilder.withKokyakuId(id);
		NyukinKakeInfoDomain nyukinDomain = nyukinBuilder.build();

		// get busho domain
		BushoDomain bushoDomain = bushoCRUDService.getDomain(bushoId);

		// build kokyaku domain
		KokyakuDomainBuilder builder = new KokyakuDomainBuilder();
		builder.withCode(id);
		builder.withShukanBusho(bushoDomain);
		builder.withNyukinKakeInfo(nyukinDomain);

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
	@Transactional
	public void test_validate1() {

		String id = "KK01";
		Integer version = 0;
		String bushoId = "BS01";
		String record_id = "this is inserted record";
		KokyakuKubun kokyakuKubun = KokyakuKubun.個人;
		HanbaiKubun hanbaiKubun = HanbaiKubun.現金;

		// build nyukinKakeInfo domain
		NyukinKakeInfoDomainBuilder nyukinBuilder = new NyukinKakeInfoDomainBuilder();
		nyukinBuilder.withKokyakuId(id);
		NyukinKakeInfoDomain nyukinDomain = nyukinBuilder.build();

		// get busho domain
		BushoDomain bushoDomain = bushoCRUDService.getDomain(bushoId);

		// build kokyaku domain
		KokyakuDomainBuilder builder = new KokyakuDomainBuilder();
		builder.withCode(id);
		builder.withKokyakuKubun(kokyakuKubun);
		builder.withHanbaiKubun(hanbaiKubun);
		builder.withShukanBusho(bushoDomain);
		builder.withNyukinKakeInfo(nyukinDomain);

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
	@Transactional
	public void test_validate2() {

		String id = "KK01";
		Integer version = 0;
		String record_id = "this is inserted record";
		String bushoId = "BS01";
		KokyakuKubun kokyakuKubun = KokyakuKubun.法人;
		HanbaiKubun hanbaiKubun = HanbaiKubun.現金;

		// build nyukinKakeInfo domain
		NyukinKakeInfoDomainBuilder nyukinBuilder = new NyukinKakeInfoDomainBuilder();
		nyukinBuilder.withKokyakuId(id);
		NyukinKakeInfoDomain nyukinDomain = nyukinBuilder.build();

		// get busho domain
		BushoDomain bushoDomain = bushoCRUDService.getDomain(bushoId);

		// build kokyaku domain
		KokyakuDomainBuilder builder = new KokyakuDomainBuilder();
		builder.withCode(id);
		builder.withKokyakuKubun(kokyakuKubun);
		builder.withHanbaiKubun(hanbaiKubun);
		builder.withShukanBusho(bushoDomain);
		builder.withNyukinKakeInfo(nyukinDomain);

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
	@Transactional
	public void test_validate3() {

		String id = "KK01";
		Integer version = 0;
		String record_id = "this is inserted record";
		String bushoId = "BS01";
		KokyakuKubun kokyakuKubun = KokyakuKubun.個人;
		HanbaiKubun hanbaiKubun = HanbaiKubun.掛売;

		// build nyukinKakeInfo domain
		NyukinKakeInfoDomainBuilder nyukinBuilder = new NyukinKakeInfoDomainBuilder();
		nyukinBuilder.withKokyakuId(id);
		NyukinKakeInfoDomain nyukinDomain = nyukinBuilder.build();

		// get busho domain
		BushoDomain bushoDomain = bushoCRUDService.getDomain(bushoId);

		// build kokyaku domain
		KokyakuDomainBuilder builder = new KokyakuDomainBuilder();
		builder.withCode(id);
		builder.withKokyakuKubun(kokyakuKubun);
		builder.withHanbaiKubun(hanbaiKubun);
		builder.withShukanBusho(bushoDomain);
		builder.withNyukinKakeInfo(nyukinDomain);

		builder.withVersion(version);
		builder.withRecordId(record_id);

		// build domain
		KokyakuDomain domain = builder.build();

		service.validate(domain);
	}
}
