package com.showka.service.crud.u05;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.CrudServiceTestCase;
import com.showka.domain.Busho;
import com.showka.domain.Uriage;
import com.showka.domain.UriageRireki;
import com.showka.entity.RUriage;
import com.showka.entity.RUriageKeijo;
import com.showka.entity.RUriageKeijoTeisei;
import com.showka.entity.RUriagePK;
import com.showka.repository.i.RUriageKeijoRepository;
import com.showka.repository.i.RUriageKeijoTeiseiRepository;
import com.showka.service.crud.u05.i.UriageRirekiCrudService;
import com.showka.service.search.u05.i.UriageRirekiSearchService;
import com.showka.value.TheDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class UriageKeijoCrudServiceImplTest extends CrudServiceTestCase {

	@Tested
	private UriageKeijoCrudServiceImpl uriageKeijoCrudServiceImpl;

	@Injectable
	@Autowired
	private RUriageKeijoRepository repo;

	@Injectable
	@Autowired
	private RUriageKeijoTeiseiRepository repoTeisei;

	@Injectable
	private UriageRirekiSearchService uriageRirekiSearchService;

	@Injectable
	private UriageRirekiCrudService uriageRirekiCrudService;

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
	public void test01_keijo(@Injectable Busho busho, @Injectable TheDate date) throws Exception {
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
				uriageRirekiSearchService.search(busho, date);
				result = uriageRirekiList;
				busho.getRecordId();
				result = "r-BS01";
			}
		};
		// do
		uriageKeijoCrudServiceImpl.keijo(busho, date);
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
	public void test02_keijo(@Injectable Busho busho, @Injectable TheDate date, @Injectable UriageRireki rirekiDomain)
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
				uriageRirekiSearchService.search(busho, date);
				result = uriageRirekiList;
				busho.getRecordId();
				result = "r-BS01";
				uriageRirekiCrudService.getUriageRirekiList(uriageId);
				rirekiDomain.getTeiseiUriage(date);
			}
		};
		// do
		uriageKeijoCrudServiceImpl.keijo(busho, date);
		// verify
		new Verifications() {
			{
				uriageRirekiSearchService.search(busho, date);
				times = 1;
				busho.getRecordId();
				times = 1;
				uriageRirekiCrudService.getUriageRirekiList(uriageId);
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
	public void test03_keijo(@Injectable Busho busho, @Injectable TheDate date, @Injectable UriageRireki rirekiDomain,
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
				uriageRirekiSearchService.search(busho, date);
				result = uriageRirekiList;
				busho.getRecordId();
				result = "r-BS01";
				uriageRirekiCrudService.getUriageRirekiList(uriageId);
				rirekiDomain.getTeiseiUriage(date);
				result = teisei;
				teiseiUriage.getRecordId();
				result = teiseiUriageId;
			}
		};
		// do
		uriageKeijoCrudServiceImpl.keijo(busho, date);
		// verify
		new Verifications() {
			{
				uriageRirekiSearchService.search(busho, date);
				times = 1;
				busho.getRecordId();
				times = 1;
				uriageRirekiCrudService.getUriageRirekiList(uriageId);
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
}
