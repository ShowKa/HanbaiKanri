package com.showka.service.persistence.u05;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.PersistenceTestCase;
import com.showka.domain.u05.Uriage;
import com.showka.domain.u05.UriageRireki;
import com.showka.domain.u17.BushoUriage;
import com.showka.domain.z00.Busho;
import com.showka.entity.RUriage;
import com.showka.entity.RUriageKeijo;
import com.showka.entity.RUriageKeijoTeisei;
import com.showka.entity.RUriagePK;
import com.showka.repository.i.RUriageKeijoRepository;
import com.showka.repository.i.RUriageKeijoTeiseiRepository;
import com.showka.service.persistence.u05.UriageKeijoPersistenceImpl;
import com.showka.service.persistence.u05.i.UriageRirekiPersistence;
import com.showka.service.query.u05.i.UriageKeijoQuery;
import com.showka.service.query.u05.i.UriageRirekiQuery;
import com.showka.value.EigyoDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class UriageKeijoPersistenceImplTest extends PersistenceTestCase {

	@Tested
	private UriageKeijoPersistenceImpl uriageKeijoPersistenceImpl;

	@Injectable
	@Autowired
	private RUriageKeijoRepository repo;

	@Injectable
	@Autowired
	private RUriageKeijoTeiseiRepository repoTeisei;

	@Injectable
	private UriageRirekiQuery uriageRirekiQuery;

	@Injectable
	private UriageRirekiPersistence uriageRirekiPersistence;

	@Injectable
	private UriageKeijoQuery uriageKeijoQuery;

	/**
	 * 計上.
	 * 
	 * <pre>
	 * 入力：部署、計上日<br>
	 * 条件:計上対象の売上履歴が取得できる<br>
	 * 結果:登録される
	 * </pre>
	 */
	@Test
	public void test01_keijo(@Injectable Busho busho, @Injectable EigyoDate date) throws Exception {
		// database
		super.deleteAll(R_URIAGE_KEIJO);
		// input
		// pk
		RUriagePK pk = new RUriagePK();
		pk.setUriageId("r-KK01-00001");
		pk.setKeijoDate(date.toDate());
		// entity
		RUriage e = new RUriage();
		e.setPk(pk);
		e.setRecordId("r-KK01-00001-20170101");
		// entity -> add to list
		List<RUriage> uriageRirekiList = new ArrayList<RUriage>();
		uriageRirekiList.add(e);
		// expect
		new Expectations() {
			{
				uriageRirekiQuery.get(busho, date);
				result = uriageRirekiList;
				busho.getRecordId();
				result = "r-BS01";
			}
		};
		// do
		uriageKeijoPersistenceImpl.keijo(busho, date);
		// verify
		new Verifications() {
			{

			}
		};
		// check
		Optional<RUriageKeijo> actual = repo.findById("r-KK01-00001-20170101");
		assertTrue(actual.isPresent());
	}

	/**
	 * 計上.
	 * 
	 * <pre>
	 * 入力：部署、計上日<br>
	 * 条件:訂正対象の売上を取得しようとしたが存在しない<br>
	 * 結果:訂正売上が計上されない
	 * </pre>
	 */
	@Test
	public void test02_keijo(@Injectable Busho busho, @Injectable EigyoDate date, @Injectable UriageRireki rirekiDomain)
			throws Exception {
		// database
		super.deleteAll(R_URIAGE_KEIJO);
		super.deleteAll(R_URIAGE_KEIJO_TEISEI);
		// input
		// pk
		RUriagePK pk = new RUriagePK();
		String uriageId = "r-KK01-00001";
		pk.setUriageId(uriageId);
		pk.setKeijoDate(date.toDate());
		// entity
		RUriage e = new RUriage();
		e.setPk(pk);
		e.setRecordId("r-KK01-00001-20170101");
		// entity -> add to list
		List<RUriage> uriageRirekiList = new ArrayList<RUriage>();
		uriageRirekiList.add(e);
		// expect
		new Expectations() {
			{
				uriageRirekiQuery.get(busho, date);
				result = uriageRirekiList;
				busho.getRecordId();
				result = "r-BS01";
				uriageRirekiPersistence.getUriageRirekiList(uriageId);
				rirekiDomain.getTeiseiUriage(date);
			}
		};
		// do
		uriageKeijoPersistenceImpl.keijo(busho, date);
		// verify
		new Verifications() {
			{
				uriageRirekiQuery.get(busho, date);
				times = 1;
				busho.getRecordId();
				times = 1;
				uriageRirekiPersistence.getUriageRirekiList(uriageId);
				times = 1;
				rirekiDomain.getTeiseiUriage(date);
				times = 1;
			}
		};
		// check
		List<RUriageKeijoTeisei> actual = repoTeisei.findAll();
		assertTrue(actual.isEmpty());
	}

	/**
	 * 計上(売上訂正).
	 * 
	 * <pre>
	 * 入力：部署、計上日<br>
	 * 条件:訂正対象の売上を取得できた<br>
	 * 結果:売上訂正が計上される
	 * </pre>
	 */
	@Test
	public void test03_keijo(@Injectable Busho busho, @Injectable EigyoDate date, @Injectable UriageRireki rirekiDomain,
			@Injectable Uriage teiseiUriage) throws Exception {
		// database
		super.deleteAll(R_URIAGE_KEIJO);
		super.deleteAll(R_URIAGE_KEIJO_TEISEI);
		// input
		// pk
		RUriagePK pk = new RUriagePK();
		String uriageId = "r-KK01-00001";
		pk.setUriageId(uriageId);
		pk.setKeijoDate(date.toDate());
		// entity
		RUriage e = new RUriage();
		e.setPk(pk);
		String uriageRirekiId = "r-KK01-00001-20170101";
		e.setRecordId(uriageRirekiId);
		// entity -> add to list
		List<RUriage> uriageRirekiList = new ArrayList<RUriage>();
		uriageRirekiList.add(e);
		// 訂正
		String teiseiUriageId = "r-KK01-00001-20161231";
		Optional<Uriage> teisei = Optional.of(teiseiUriage);
		// expect
		new Expectations() {
			{
				uriageRirekiQuery.get(busho, date);
				result = uriageRirekiList;
				busho.getRecordId();
				result = "r-BS01";
				uriageRirekiPersistence.getUriageRirekiList(uriageId);
				rirekiDomain.getTeiseiUriage(date);
				result = teisei;
				teiseiUriage.getRecordId();
				result = teiseiUriageId;
			}
		};
		// do
		uriageKeijoPersistenceImpl.keijo(busho, date);
		// verify
		new Verifications() {
			{
				uriageRirekiQuery.get(busho, date);
				times = 1;
				busho.getRecordId();
				times = 1;
				uriageRirekiPersistence.getUriageRirekiList(uriageId);
				times = 1;
				rirekiDomain.getTeiseiUriage(date);
				times = 1;
				teiseiUriage.getRecordId();
				times = 1;
			}
		};
		// check
		RUriageKeijo keijo = repo.getOne(uriageRirekiId);
		RUriageKeijoTeisei actual = repoTeisei.getOne(keijo.getRecordId());
		assertEquals(teiseiUriageId, actual.getUriageRirekiId());
	}

	/**
	 * 売上計上金額集計
	 */
	@Test
	public void test04_getBushoUriage(@Injectable Busho busho, @Injectable EigyoDate date) throws Exception {
		// expect
		new Expectations() {
			{
				uriageKeijoQuery.getKeijoKingaku(busho, date);
				result = 300;
				uriageKeijoQuery.getTeiseiKingaku(busho, date);
				result = -100;
			}
		};
		// do
		BushoUriage actual = uriageKeijoPersistenceImpl.getBushoUriage(busho, date);
		// verify
		new Verifications() {
			{
				uriageKeijoQuery.getKeijoKingaku(busho, date);
				times = 1;
				uriageKeijoQuery.getTeiseiKingaku(busho, date);
				times = 1;
			}
		};
		// check
		assertEquals(300, actual.getKeijoKingaku().intValue());
		assertEquals(-100, actual.getTeiseiKingaku().intValue());
		assertEquals(200, actual.getKeijoKingakuGokei().intValue());
	}

}
