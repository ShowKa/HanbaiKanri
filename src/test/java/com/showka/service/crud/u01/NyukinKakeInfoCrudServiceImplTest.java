package com.showka.service.crud.u01;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import com.showka.common.ServiceCrudTestCase;
import com.showka.domain.NyukinKakeInfoDomain;
import com.showka.domain.builder.NyukinKakeInfoDomainBuilder;
import com.showka.entity.MNyukinKakeInfo;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.kubun.NyukinTsukiKubun;
import com.showka.repository.i.MNyukinKakeInfoRepository;

public class NyukinKakeInfoCrudServiceImplTest extends ServiceCrudTestCase {

	@Autowired
	private NyukinKakeInfoCrudServiceImpl service;

	@Autowired
	private MNyukinKakeInfoRepository repo;

	/**
	 * 新しいレコードのINERTテスト
	 */
	@Test
	public void test_insert() {

		String id = "NEW!";
		Integer version = 0;
		String record_id = "this is inserted record";

		// set up builder
		NyukinKakeInfoDomainBuilder builder = new NyukinKakeInfoDomainBuilder();
		builder.withKokyakuId(id);
		builder.withNyukinDate(11);
		builder.withNyukinHohoKubun(NyukinHohoKubun.振込);
		builder.withNyukinTsukiKubun(NyukinTsukiKubun.当月);
		builder.withShimeDate(12);
		builder.withVersion(version);
		builder.withRecordId(record_id);

		// build domain
		NyukinKakeInfoDomain domain = builder.build();

		// save = insert
		service.save(domain);

		// assert
		MNyukinKakeInfo actual = repo.findById(id).get();
		assertEquals(record_id, actual.getRecordId());

	}

	/**
	 * 既存レコードの更新テスト
	 */
	@Test
	public void test_update() {

		String id = "KK03";
		Integer version = 1;
		String record_id = "this is updated record";

		// set up builder
		NyukinKakeInfoDomainBuilder builder = new NyukinKakeInfoDomainBuilder();
		builder.withKokyakuId(id);
		builder.withNyukinDate(11);
		builder.withNyukinHohoKubun(NyukinHohoKubun.振込);
		builder.withNyukinTsukiKubun(NyukinTsukiKubun.当月);
		builder.withShimeDate(12);
		builder.withVersion(version);
		builder.withRecordId(record_id);

		// build domain
		NyukinKakeInfoDomain domain = builder.build();

		// save = update
		service.save(domain);

		// assert
		MNyukinKakeInfo actual = repo.findById(id).get();
		assertEquals(record_id, actual.getRecordId());

	}

	/**
	 * 排他制御テスト
	 * 
	 * <pre>
	 * 不正なversion番号を設定する。更新エラーとなればよい。
	 * </pre>
	 */
	@Test(expected = ObjectOptimisticLockingFailureException.class)
	public void test_optimistic_lock_error() {

		String id = "KK03";
		Integer version = 999;
		String record_id = "this is updated record";

		// set up builder
		NyukinKakeInfoDomainBuilder builder = new NyukinKakeInfoDomainBuilder();
		builder.withKokyakuId(id);
		builder.withNyukinDate(11);
		builder.withNyukinHohoKubun(NyukinHohoKubun.振込);
		builder.withNyukinTsukiKubun(NyukinTsukiKubun.当月);
		builder.withShimeDate(12);
		builder.withVersion(version);
		builder.withRecordId(record_id);

		// build domain
		NyukinKakeInfoDomain domain = builder.build();

		// save = update
		service.save(domain);
		fail();
	}
}
