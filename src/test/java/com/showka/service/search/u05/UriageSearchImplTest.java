package com.showka.service.search.u05;

import static com.showka.table.public_.tables.T_URIAGE.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jooq.DSLContext;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.CrudByJooqServiceTestCase;
import com.showka.domain.builder.KokyakuBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u05.Uriage;
import com.showka.entity.TUriagePK;
import com.showka.service.persistence.u05.i.UriagePersistence;
import com.showka.service.search.u05.UriageSearchImpl;
import com.showka.service.search.u05.i.UriageSearchCriteria;
import com.showka.table.public_.tables.records.T_URIAGE_RECORD;
import com.showka.value.EigyoDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class UriageSearchImplTest extends CrudByJooqServiceTestCase {

	@Tested
	@Injectable
	private UriageSearchImpl service;

	@Autowired
	@Injectable
	private DSLContext create;

	@Injectable
	private UriagePersistence uriagePersistence;

	/** 売上01 */
	private static final Object[] T_URIAGE_01 = {
			"r-KK01",
			"00001",
			d("20170819"),
			d("20170819"),
			"00",
			0.08,
			"r-KK01-00001" };

	/** 売上02 */
	private static final Object[] T_URIAGE_02 = {
			"r-KK02",
			"00002",
			d("20170820"),
			d("20170820"),
			"00",
			0.08,
			"r-KK02-00002" };

	/** 売掛. */
	private static final Object[] T_URIKAKE_01 = { "r-KK01-00001", 1000, d("20180101"), "r-KK01-00001" };
	// private static final Object[] T_URIKAKE_02 = { "r-KK02-00002", 1000,
	// d("20180101"), "r-KK02-00002" };

	/**
	 * 検索.
	 * 
	 * <pre>
	 * 入力：検索条件
	 * 条件：顧客指定する, 売掛のみ=false
	 * 結果：指定した顧客の売上レコードのみ
	 * </pre>
	 * 
	 * @throws Exception
	 */
	@Test
	public void test01_searchRecords() throws Exception {
		// database
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, T_URIAGE_01, T_URIAGE_02);
		super.deleteAndInsert(T_URIKAKE, T_URIKAKE_COLUMN, T_URIKAKE_01);
		// input
		UriageSearchCriteria criteria = new UriageSearchCriteria();
		Kokyaku kokyaku = new KokyakuBuilder().withRecordId("r-KK01").build();
		criteria.setKokyaku(Optional.of(kokyaku));
		criteria.setFrom(new EigyoDate(2017, 8, 19));
		criteria.setTo(new EigyoDate(2017, 8, 20));
		criteria.setOnlyUrikake(false);
		// do
		List<T_URIAGE_RECORD> actual = service.searchRecords(criteria);
		// check
		assertEquals(1, actual.size());
	}

	/**
	 * 検索.
	 * 
	 * <pre>
	 * 入力：検索条件
	 * 条件：顧客指定しない, 売掛のみ=false
	 * 結果：顧客関係なくレコードを取得
	 * </pre>
	 * 
	 * @throws Exception
	 */
	@Test
	public void test02_searchRecords() throws Exception {
		// database
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, T_URIAGE_01, T_URIAGE_02);
		super.deleteAndInsert(T_URIKAKE, T_URIKAKE_COLUMN, T_URIKAKE_01);
		// input
		UriageSearchCriteria criteria = new UriageSearchCriteria();
		criteria.setKokyaku(Optional.empty());
		criteria.setFrom(new EigyoDate(2017, 8, 19));
		criteria.setTo(new EigyoDate(2017, 8, 20));
		criteria.setOnlyUrikake(false);
		// do
		List<T_URIAGE_RECORD> actual = service.searchRecords(criteria);
		// check
		assertEquals(2, actual.size());
	}

	/**
	 * 検索.
	 * 
	 * <pre>
	 * 入力：検索条件
	 * 条件：顧客指定しない, 売掛のみ=true
	 * 結果：売掛した売上のみ取得
	 * </pre>
	 * 
	 * @throws Exception
	 */
	@Test
	public void test03_searchRecords() throws Exception {
		// database
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, T_URIAGE_01, T_URIAGE_02);
		super.deleteAndInsert(T_URIKAKE, T_URIKAKE_COLUMN, T_URIKAKE_01);
		// input
		UriageSearchCriteria criteria = new UriageSearchCriteria();
		criteria.setKokyaku(Optional.empty());
		criteria.setFrom(new EigyoDate(2017, 8, 19));
		criteria.setTo(new EigyoDate(2017, 8, 20));
		criteria.setOnlyUrikake(true);
		// do
		List<T_URIAGE_RECORD> actual = service.searchRecords(criteria);
		// check
		assertEquals(1, actual.size());
	}

	/**
	 * 検索.
	 * 
	 * <pre>
	 * 入力：検索条件
	 * 条件：顧客指定しない, 売掛のみ=false, 売上日from=2017/08/20, to=2017/08/21
	 * 結果：売上日=2017/08/20のレコードのみ
	 * </pre>
	 * 
	 * @throws Exception
	 */
	@Test
	public void test04_searchRecords() throws Exception {
		// database
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, T_URIAGE_01, T_URIAGE_02);
		super.deleteAndInsert(T_URIKAKE, T_URIKAKE_COLUMN, T_URIKAKE_01);
		// input
		UriageSearchCriteria criteria = new UriageSearchCriteria();
		criteria.setKokyaku(Optional.empty());
		criteria.setFrom(new EigyoDate(2017, 8, 20));
		criteria.setTo(new EigyoDate(2017, 8, 21));
		criteria.setOnlyUrikake(false);
		// do
		List<T_URIAGE_RECORD> actual = service.searchRecords(criteria);
		// check
		assertEquals(1, actual.size());
	}

	@Test
	public void test05_buildFromRecord(@Injectable Uriage uriage) throws Exception {
		// input
		// records
		T_URIAGE_RECORD record = new T_URIAGE_RECORD();
		record.set(t_uriage.kokyaku_id, "r-KK01");
		record.set(t_uriage.denpyo_number, "00001");
		List<T_URIAGE_RECORD> records = new ArrayList<T_URIAGE_RECORD>();
		records.add(record);
		// pk
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId("r-KK01");
		pk.setDenpyoNumber("00001");
		// except
		new Expectations() {
			{
				uriagePersistence.getDomain(pk);
				result = uriage;
			}
		};
		// do
		List<Uriage> actual = service.buildFromRecord(records);
		// verify
		new Verifications() {
			{
				uriagePersistence.getDomain(pk);
				times = 1;
			}
		};
		// check
		assertEquals(uriage, actual.get(0));
	}

	@Test
	public void test06_search(@Injectable T_URIAGE_RECORD r, @Injectable Uriage u) throws Exception {
		// input
		UriageSearchCriteria criteria = new UriageSearchCriteria();
		List<T_URIAGE_RECORD> records = new ArrayList<T_URIAGE_RECORD>();
		records.add(r);
		List<Uriage> domains = new ArrayList<Uriage>();
		domains.add(u);
		// expect
		new Expectations() {
			{
				service.searchRecords(criteria);
				result = records;
				times = 1;
				service.buildFromRecord(records);
				result = domains;
				times = 1;
			}
		};
		// do
		List<Uriage> actual = service.search(criteria);
		// check
		assertEquals(u, actual.get(0));
	}
}
