package com.showka.service.query.u05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.PersistenceTestCase;
import com.showka.domain.u05.Uriage;
import com.showka.domain.u05.UriageRireki;
import com.showka.domain.z00.Busho;
import com.showka.entity.RUriage;
import com.showka.entity.RUriageKeijo;
import com.showka.entity.RUriageKeijoTeisei;
import com.showka.repository.i.RUriageKeijoRepository;
import com.showka.repository.i.RUriageKeijoTeiseiRepository;
import com.showka.service.persistence.u05.i.UriageRirekiPersistence;
import com.showka.service.query.u05.UriageKeijoQueryImpl;
import com.showka.service.query.u05.i.UriageRirekiQuery;
import com.showka.system.EmptyProxy;
import com.showka.value.EigyoDate;
import com.showka.value.Kakaku;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class UriageKeijoQueryImplTest extends PersistenceTestCase {

	@Tested
	@Injectable
	private UriageKeijoQueryImpl service;

	@Autowired
	@Injectable
	private RUriageKeijoRepository repo;

	@Injectable
	private RUriageKeijoTeiseiRepository repoTeisei;

	@Injectable
	private UriageRirekiPersistence uriageRirekiPersistence;

	@Injectable
	private UriageRirekiQuery uriageRirekiQuery;

	/** 売上計上01. */
	private static final Object[] R_URIAGE_KEIJO_V01 = {
			"r-KK01-00001-20170101",
			"r-BS01",
			"r-KK01-00001-20170101-BS01" };
	/** 売上計上02. */
	private static final Object[] R_URIAGE_KEIJO_V02 = {
			"r-KK02-00002-20170101",
			"r-BS02",
			"r-KK02-00002-20170101-BS02" };

	/**
	 * 売上検索.
	 * 
	 * <pre>
	 * 入力：部署、計上日<br>
	 * 条件：なし
	 * 結果：売上計上が取得できる(無関係のデータは取得しない)。
	 * </pre>
	 */
	@Test
	public void test01_search() throws Exception {
		// database
		super.deleteAndInsert(R_URIAGE_KEIJO, R_URIAGE_KEIJO_COLUMN, R_URIAGE_KEIJO_V01, R_URIAGE_KEIJO_V02);
		// input
		Busho busho = EmptyProxy.domain(Busho.class);
		EigyoDate date = new EigyoDate();
		// uriage rireki entity list
		List<RUriage> rUriageList = new ArrayList<RUriage>();
		RUriage e1 = new RUriage();
		e1.setRecordId("r-KK01-00001-20170101");
		rUriageList.add(e1);
		// expect
		new Expectations() {
			{
				uriageRirekiQuery.search(busho, date);
				result = rUriageList;
			}
		};
		// do
		List<RUriageKeijo> actual = service.search(busho, date);
		// verify
		new Verifications() {
			{
				uriageRirekiQuery.search(busho, date);
				times = 1;
			}
		};
		// check
		assertEquals(1, actual.size());
		assertEquals("r-KK01-00001-20170101", actual.get(0).getUriageRirekiId());
	}

	/**
	 * 計上金額取得.
	 * 
	 * <pre>
	 * 入力：部署、日付
	 * 条件：その日に計上された売上が2件(100, 200円）
	 * 結果：金額が集計される(300円)
	 * </pre>
	 * 
	 */
	@Test
	public void test02_getKeijoKingaku(@Injectable Busho busho, @Injectable EigyoDate date,
			@Injectable RUriageKeijo ke1, @Injectable RUriageKeijo ke2, @Injectable UriageRireki rireki1,
			@Injectable UriageRireki rireki2, @Injectable Uriage uriage1, @Injectable Uriage uriage2) throws Exception {
		// input
		List<RUriageKeijo> keijoEntities = new ArrayList<RUriageKeijo>();
		keijoEntities.add(ke1);
		keijoEntities.add(ke2);
		// 売上ID
		String uriageId1 = "r-KK02-00002";
		String uriageId2 = "r-KK03-00002";
		// 価格
		Kakaku uriageGokeiKingaku1 = new Kakaku(100, 0);
		Kakaku uriageGokeiKingaku2 = new Kakaku(200, 0);
		// expect
		new Expectations() {
			{
				// search 売上計上 entity
				service.search(busho, date);
				result = keijoEntities;
				// get 売上ID
				ke1.getUriageId();
				result = uriageId1;
				ke2.getUriageId();
				result = uriageId2;
				// get 売上履歴
				uriageRirekiPersistence.getUriageRirekiList(uriageId1);
				result = rireki1;
				uriageRirekiPersistence.getUriageRirekiList(uriageId2);
				result = rireki2;
				// get 売上
				rireki1.getUriageOf(date);
				result = Optional.of(uriage1);
				rireki2.getUriageOf(date);
				result = Optional.of(uriage2);
				// get 価格
				uriage1.getUriageGokeiKakaku();
				result = uriageGokeiKingaku1;
				uriage2.getUriageGokeiKakaku();
				result = uriageGokeiKingaku2;
			}
		};
		// do
		int actual = service.getKeijoKingaku(busho, date);
		// verify
		new Verifications() {
			{
				// search 売上計上 entity
				service.search(busho, date);
				times = 1;
				// get 売上ID
				ke1.getUriageId();
				times = 1;
				ke2.getUriageId();
				times = 1;
				// get 売上履歴
				uriageRirekiPersistence.getUriageRirekiList(uriageId1);
				times = 1;
				uriageRirekiPersistence.getUriageRirekiList(uriageId2);
				times = 1;
				// get 売上
				rireki1.getUriageOf(date);
				times = 1;
				rireki2.getUriageOf(date);
				times = 1;
				// get 価格
				uriage1.getUriageGokeiKakaku();
				times = 1;
				uriage2.getUriageGokeiKakaku();
				times = 1;
			}
		};
		// check
		assertEquals(300, actual);
	}

	/**
	 * 売上訂正の計上金額集計.
	 * 
	 * <pre>
	 * 入力：部署、計上日<br>
	 * 条件：100円の売上訂正が存在する
	 * 結果：売上訂正の金額が-100円として集計される
	 * </pre>
	 */
	@Test
	public void test03_getTeiseiKingaku(@Injectable Busho busho, @Injectable EigyoDate date,
			@Injectable RUriageKeijo ke, @Injectable RUriageKeijoTeisei teisei, @Injectable UriageRireki rireki,
			@Injectable Uriage uriage) throws Exception {
		// input
		// 売上計上 entity
		List<RUriageKeijo> keijoEntities = new ArrayList<RUriageKeijo>();
		keijoEntities.add(ke);
		// 計上ID
		String keijoId = "r-KK01-00001-20170101";
		// 売上計上訂正 entity
		List<String> keijoIds = Arrays.asList(new String[] { keijoId });
		List<RUriageKeijoTeisei> teiseiEntities = new ArrayList<RUriageKeijoTeisei>();
		teiseiEntities.add(teisei);
		// 売上ID
		String uriageId = "r-KK02-00002";
		// 過去計上日
		Date pastKeijoDate = new Date();
		// 価格
		Kakaku uriageGokeiKingaku1 = new Kakaku(100, 0);
		// expect
		new Expectations() {
			{
				// search 売上計上 entities
				service.search(busho, date);
				result = keijoEntities;
				// get 計上ID
				ke.getRecordId();
				result = keijoId;
				// find 売上計上訂正 entities
				repoTeisei.findAllById(keijoIds);
				result = teiseiEntities;
				// get 売上ID
				teisei.getUriageId();
				result = uriageId;
				// get 売上履歴
				uriageRirekiPersistence.getUriageRirekiList(uriageId);
				result = rireki;
				// get 過去計上日
				teisei.getTeiseiUriageRirekiKeijoDate();
				result = pastKeijoDate;
				// get 売上
				rireki.getUriageOf(new EigyoDate(pastKeijoDate));
				result = Optional.of(uriage);
				// get 価格
				uriage.getUriageGokeiKakaku();
				result = uriageGokeiKingaku1;
			}
		};
		// do
		int actual = service.getTeiseiKingaku(busho, date);
		// verify
		new Verifications() {
			{
				// search 売上計上 entities
				service.search(busho, date);
				times = 1;
				// get 計上ID
				ke.getRecordId();
				times = 1;
				// find 売上計上訂正 entities
				repoTeisei.findAllById(keijoIds);
				times = 1;
				// get 売上ID
				teisei.getUriageId();
				times = 1;
				// get 売上履歴
				uriageRirekiPersistence.getUriageRirekiList(uriageId);
				times = 1;
				// get 過去計上日
				teisei.getTeiseiUriageRirekiKeijoDate();
				times = 1;
				// get 売上
				rireki.getUriageOf(new EigyoDate(pastKeijoDate));
				times = 1;
				// get 価格
				uriage.getUriageGokeiKakaku();
				times = 1;
			}
		};
		// check
		assertEquals(-100, actual);
	}
}
