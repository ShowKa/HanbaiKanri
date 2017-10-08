package com.showka.service.crud.u01;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.showka.common.ServiceCrudTestCase;
import com.showka.domain.BushoDomain;
import com.showka.domain.KokyakuDomain;
import com.showka.domain.NyukinKakeInfoDomain;
import com.showka.domain.builder.KokyakuDomainBuilder;
import com.showka.domain.builder.NyukinKakeInfoDomainBuilder;
import com.showka.entity.MKokyaku;
import com.showka.entity.MNyukinKakeInfo;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.KokyakuKubun;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.kubun.NyukinTsukiKubun;
import com.showka.repository.i.MKokyakuRepository;
import com.showka.repository.i.MNyukinKakeInfoRepository;
import com.showka.service.crud.z00.i.BushoCrudService;

/**
 * 顧客 CRUD Service Test
 *
 * @author 25767
 *
 */
public class KokyakuCrudServiceImplTest extends ServiceCrudTestCase {

	@Autowired
	private KokyakuCrudServiceImpl service;

	@Autowired
	private MKokyakuRepository repo;

	@Autowired
	private MNyukinKakeInfoRepository nyukinRepo;

	@Autowired
	private BushoCrudService bushoService;

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
	private static final Object[] VALUE03 = { "KK03", "bbbb", "上京区", "10", "01", "BS02", "KK03" };

	/**
	 * table name
	 */
	private static final String BUSHO_TABLE_NAME = "m_busho";

	/**
	 * columns
	 */
	private static final String[] BUSHO_COLUMN = { "code", "busho_kubun", "jigyo_kubun", "name", "record_id" };

	/**
	 * test data
	 */
	private static final Object[] BUSHO_VALUE01 = { "BS01", "01", "01", "部署01", "BS01" };
	private static final Object[] BUSHO_VALUE02 = { "BS02", "99", "02", "部署02", "BS02" };

	/**
	 * table name
	 */
	private static final String NYUKIN_KAKE_TABLE_NAME = "m_nyukin_kake_info";

	/**
	 * columns
	 */
	private static final String[] NYUKIN_KAKE_COLUMN = { "kokyaku_id", "nyukin_hoho_kubun", "nyukin_tsuki_kubun",
			"shimebi", "nyukin_date", "record_id" };

	/**
	 * test data
	 */
	private static final Object[] NYUKIN_KAKE_VALUE01 = { "KK03", "00", "10", "20", "30", "KK03" };

	@Before
	public void deletTable() {
		super.deleteAll(TABLE_NAME);
		super.deleteAll(BUSHO_TABLE_NAME);
		super.deleteAll(NYUKIN_KAKE_TABLE_NAME);

		super.insert(BUSHO_TABLE_NAME, BUSHO_COLUMN, BUSHO_VALUE01, BUSHO_VALUE02);
	}

	/**
	 * insert 新規レコードの挿入テスト
	 *
	 * <pre>
	 * 入力：顧客ドメイン <br>
	 * 条件：該当顧客レコードなし <br>
	 * 結果：成功
	 *
	 * <pre>
	 */
	@Test
	@Transactional
	public void test_insert() {

		// kokyaku
		final String id = "NEW!";
		final String name = "aaaa";
		final String address = "北区";
		final KokyakuKubun kokyakuKubun = KokyakuKubun.法人;
		final HanbaiKubun hanbaiKubun = HanbaiKubun.掛売;
		final String bushoId = "BS01";

		final String record_id = "this is inserted record";

		// nyukinKakeInfo
		final Integer nyukinDate = 11;
		final NyukinHohoKubun nyukinHohoKubun = NyukinHohoKubun.振込;
		final NyukinTsukiKubun nyukinTukiKubun = NyukinTsukiKubun.当月;
		final Integer shimeDate = 12;

		// build nyukinKakeInfo domain
		NyukinKakeInfoDomainBuilder nyukinBuilder = new NyukinKakeInfoDomainBuilder();
		nyukinBuilder.withKokyakuId(id);
		nyukinBuilder.withNyukinDate(nyukinDate);
		nyukinBuilder.withNyukinHohoKubun(nyukinHohoKubun);
		nyukinBuilder.withNyukinTsukiKubun(nyukinTukiKubun);
		nyukinBuilder.withShimeDate(shimeDate);
		nyukinBuilder.withRecordId(record_id);
		NyukinKakeInfoDomain nyukinDomain = nyukinBuilder.build();

		// get busho domain
		BushoDomain bushoDomain = bushoService.getDomain(bushoId);

		// build kokyaku domain
		KokyakuDomainBuilder builder = new KokyakuDomainBuilder();
		builder.withCode(id);
		builder.withName(name);
		builder.withAddress(address);
		builder.withKokyakuKubun(kokyakuKubun);
		builder.withHanbaiKubun(hanbaiKubun);
		builder.withShukanBusho(bushoDomain);
		builder.withNyukinKakeInfo(nyukinDomain);
		builder.withRecordId(record_id);
		KokyakuDomain domain = builder.build();

		// save = insert
		service.save(domain);

		// assert
		MKokyaku actual = repo.findById(id).get();
		assertEquals(name, actual.getName());
		assertEquals(address, actual.getAddress());
		assertEquals(kokyakuKubun.getCode(), actual.getKokyakuKubun());
		assertEquals(hanbaiKubun.getCode(), actual.getHanbaiKubun());
		assertEquals(record_id, actual.getRecordId());

		MNyukinKakeInfo actualNyukin = nyukinRepo.findById(id).get();
		assertEquals(nyukinHohoKubun.getCode(), actualNyukin.getNyukinHohoKubun());
		assertEquals(nyukinTukiKubun.getCode(), actualNyukin.getNyukinTsukiKubun());
		assertEquals(shimeDate.intValue(), actualNyukin.getShimebi());
		assertEquals(nyukinDate.intValue(), actualNyukin.getNyukinDate());

	}

	/**
	 * update 既存レコードの更新テスト
	 *
	 * <pre>
	 * 入力：顧客ID <br>
	 * 条件：該当顧客レコードあり。販売区分を掛売→現金へ更新。 <br>
	 * 結果：成功
	 *
	 * <pre>
	 */
	@Test
	@Transactional
	public void test_update() {

		super.insert(TABLE_NAME, COLUMN, VALUE01, VALUE02, VALUE03);
		super.insert(NYUKIN_KAKE_TABLE_NAME, NYUKIN_KAKE_COLUMN, NYUKIN_KAKE_VALUE01);

		// kokyaku
		final String id = "KK03";
		final String name = "ccbb";
		final String address = "南区";
		final KokyakuKubun kokyakuKubun = KokyakuKubun.法人;
		final HanbaiKubun hanbaiKubun = HanbaiKubun.現金;
		final String bushoId = "BS01";

		final Integer version = 0;
		final String record_id = "KK03";

		// build nyukinKakeInfo domain
		NyukinKakeInfoDomainBuilder nyukinBuilder = new NyukinKakeInfoDomainBuilder();
		nyukinBuilder.withKokyakuId(id);
		NyukinKakeInfoDomain nyukinDomain = nyukinBuilder.build();

		// get busho domain
		BushoDomain bushoDomain = bushoService.getDomain(bushoId);

		// build kokyaku domain
		KokyakuDomainBuilder builder = new KokyakuDomainBuilder();
		builder.withCode(id);
		builder.withName(name);
		builder.withAddress(address);
		builder.withKokyakuKubun(kokyakuKubun);
		builder.withHanbaiKubun(hanbaiKubun);
		builder.withShukanBusho(bushoDomain);
		builder.withNyukinKakeInfo(nyukinDomain);
		builder.withVersion(version);
		builder.withRecordId(record_id);
		KokyakuDomain domain = builder.build();

		// save = insert
		service.save(domain);

		// assert
		MKokyaku actual = repo.findById(id).get();
		assertEquals(name, actual.getName());
		assertEquals(address, actual.getAddress());
		assertEquals(kokyakuKubun.getCode(), actual.getKokyakuKubun());
		assertEquals(hanbaiKubun.getCode(), actual.getHanbaiKubun());
		assertEquals(record_id, actual.getRecordId());

		Optional<MNyukinKakeInfo> actualNyukin = nyukinRepo.findById(id);
		assertFalse(actualNyukin.isPresent());

	}

	/**
	 * delete 既存レコードの削除テスト
	 *
	 * <pre>
	 * 入力：顧客ID <br>
	 * 条件：該当顧客レコードが存在する。該当入金掛情報レコードが存在する。 <br>
	 * 結果：成功
	 *
	 * <pre>
	 */
	@Test
	@Transactional
	public void test_delete1() {

		super.insert(TABLE_NAME, COLUMN, VALUE01, VALUE02, VALUE03);
		super.insert(NYUKIN_KAKE_TABLE_NAME, NYUKIN_KAKE_COLUMN, NYUKIN_KAKE_VALUE01);

		final String id = "KK03";
		final String bushoId = "BS01";
		Integer version = 0;
		Integer nyukinVersion = 0;
		final String record_id = "KK03";

		// build nyukinKakeInfo domain
		NyukinKakeInfoDomainBuilder nyukinBuilder = new NyukinKakeInfoDomainBuilder();
		nyukinBuilder.withKokyakuId(id);
		nyukinBuilder.withVersion(nyukinVersion);
		NyukinKakeInfoDomain nyukinDomain = nyukinBuilder.build();

		// get busho domain
		BushoDomain bushoDomain = bushoService.getDomain(bushoId);

		// build kokyaku domain
		KokyakuDomainBuilder builder = new KokyakuDomainBuilder();
		builder.withCode(id);
		builder.withShukanBusho(bushoDomain);
		builder.withNyukinKakeInfo(nyukinDomain);
		builder.withVersion(version);
		builder.withRecordId(record_id);
		KokyakuDomain domain = builder.build();
		// delete

		service.delete(domain);

		// assert
		Optional<MKokyaku> result = repo.findById(id);
		assertFalse(result.isPresent());
		Optional<MNyukinKakeInfo> resultNyukin = nyukinRepo.findById(record_id);
		assertFalse(resultNyukin.isPresent());
	}

	/**
	 * exsists 存在しないレコードの存在確認テスト
	 *
	 * <pre>
	 * 入力：顧客ID <br>
	 * 条件：顧客レコードが存在する <br>
	 * 結果：成功
	 *
	 * <pre>
	 */

	@Test
	public void test_exsists1() {

		super.insert(TABLE_NAME, COLUMN, VALUE01, VALUE02, VALUE03);

		String id = "KK03";

		boolean result = service.exsists(id);
		assertTrue(result);

	}

	/**
	 * exsists 存在しないレコードの存在確認テスト
	 *
	 * <pre>
	 * 入力：顧客ID <br>
	 * 条件：顧客レコードが存在しない <br>
	 * 結果：失敗
	 *
	 * <pre>
	 */
	@Test
	public void test_exsists2() {

		String id = "KK03";

		boolean result = service.exsists(id);
		assertFalse(result);
	}

	/**
	 * getDomain 既存レコードを取得できるか確認する
	 *
	 * <pre>
	 * 入力：入金掛情報を持つ顧客のID <br>
	 * 条件：顧客レコード、入金掛情報が存在 <br>
	 * 結果：成功
	 *
	 * <pre>
	 */
	@Test
	@Transactional
	public void test_getDomain1() {

		super.insert(TABLE_NAME, COLUMN, VALUE01, VALUE02, VALUE03);
		super.insert(NYUKIN_KAKE_TABLE_NAME, NYUKIN_KAKE_COLUMN, NYUKIN_KAKE_VALUE01);

		String id = "KK03";

		// getDomain
		KokyakuDomain result = service.getDomain(id);

		// assert
		assertEquals("KK03", result.getCode());
		assertEquals("bbbb", result.getName());
		assertEquals("上京区", result.getAddress());
		assertEquals("10", result.getHanbaiKubun().getCode());
		assertEquals("01", result.getKokyakuKubun().getCode());
		assertEquals("BS02", result.getShukanBusho().getRecordId());
		assertEquals("KK03", result.getRecordId());

		NyukinKakeInfoDomain resultNyukin = result.getNyukinKakeInfo();
		assertEquals("00", resultNyukin.getNyukinHohoKubun().getCode());
		assertEquals("10", resultNyukin.getNyukinTsukiKubun().getCode());
		assertEquals(20, resultNyukin.getShimeDate().intValue());
		assertEquals(30, resultNyukin.getNyukinDate().intValue());
	}

	/**
	 * getDomain 既存レコードを取得できるか確認する
	 *
	 * <pre>
	 * 入力：入金掛情報を持たない顧客のID <br>
	 * 条件：顧客レコードが存在 <br>
	 * 結果：成功
	 *
	 * <pre>
	 */
	@Test
	@Transactional
	public void test_getDomain2() {
		super.insert(TABLE_NAME, COLUMN, VALUE01, VALUE02, VALUE03);
		super.insert(NYUKIN_KAKE_TABLE_NAME, NYUKIN_KAKE_COLUMN, NYUKIN_KAKE_VALUE01);

		String id = "KK01";

		// getDomain
		KokyakuDomain result = service.getDomain(id);

		// assert
		assertEquals("KK01", result.getCode());
		assertEquals("aaaa", result.getName());
		assertEquals("左京区", result.getAddress());
		assertEquals("00", result.getHanbaiKubun().getCode());
		assertEquals("01", result.getKokyakuKubun().getCode());
		assertEquals("BS01", result.getShukanBusho().getRecordId());
		assertEquals("KK01", result.getRecordId());
	}
}
