package com.showka.service.crud.u05;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.ServiceCrudTestCase;
import com.showka.domain.ShohinDomain;
import com.showka.domain.UriageRirekiMeisaiDomain;
import com.showka.domain.builder.UriageRirekiMeisaiDomainBuilder;
import com.showka.entity.RUriageMeisai;
import com.showka.entity.RUriageMeisaiPK;
import com.showka.repository.i.RUriageMeisaiRepository;
import com.showka.service.crud.z00.i.MShohinCrudService;
import com.showka.system.EmptyProxy;

import mockit.Injectable;
import mockit.Tested;

public class UriageRirekiMeisaiCrudServiceImplTest extends ServiceCrudTestCase {

	@Tested
	private UriageRirekiMeisaiCrudServiceImpl service;

	@Injectable
	@Autowired
	private RUriageMeisaiRepository repo;

	@Injectable
	private MShohinCrudService shohinCrudService;

	/** 売上履歴明細01 */
	private static final Object[] R_URIAGE_MEISAI_01 = {
			"r-KK01-00001-20170820",
			1,
			"SH01",
			5,
			1000,
			"r-KK01-00001-20170820-1" };
	private static final Object[] R_URIAGE_MEISAI_02 = {
			"r-KK01-00001-20170820",
			2,
			"SH02",
			5,
			1001,
			"r-KK01-00001-20170820-2" };

	private static final UriageRirekiMeisaiDomain meisai01;
	static {
		// 商品ドメインダミー
		ShohinDomain shohinDomain = EmptyProxy.domain(ShohinDomain.class);

		// 売上明細主キー
		String uriageId = "r-KK01-00001-20170820";
		Integer meisaiNumber = 1;
		String recordID = uriageId + "-" + meisaiNumber;

		// 売上明細ドメイン
		UriageRirekiMeisaiDomainBuilder b = new UriageRirekiMeisaiDomainBuilder();
		b.withHanbaiNumber(10);
		b.withHanbaiTanka(BigDecimal.valueOf(200));
		b.withMeisaiNumber(meisaiNumber);
		b.withRecordId(recordID);
		b.withShohinDomain(shohinDomain);
		b.withUriageId(uriageId);
		meisai01 = b.build();
	}

	@Test
	public void test01_save_insert() throws Exception {
		// data
		deleteAll(R_URIAGE_MEISAI);

		// do
		meisai01.setVersion(null);
		service.save(meisai01);

		// check
		RUriageMeisaiPK id = new RUriageMeisaiPK();
		id.setUriageId(meisai01.getUriageId());
		id.setMeisaiNumber(meisai01.getMeisaiNumber());
		RUriageMeisai actual = repo.findById(id).get();

		assertEquals(meisai01.getRecordId(), actual.getRecordId());
	}

	@Test
	public void test02_save_update() throws Exception {
		// data
		deleteAndInsert(R_URIAGE_MEISAI, R_URIAGE_MEISAI_COLUMN, R_URIAGE_MEISAI_01);

		// do
		meisai01.setVersion(0);
		service.save(meisai01);

		// check
		RUriageMeisaiPK id = new RUriageMeisaiPK();
		id.setUriageId(meisai01.getUriageId());
		id.setMeisaiNumber(meisai01.getMeisaiNumber());
		RUriageMeisai actual = repo.findById(id).get();

		assertEquals(meisai01.getRecordId(), actual.getRecordId());
	}

	@Test
	public void test03_delete() throws Exception {
		// data
		deleteAndInsert(R_URIAGE_MEISAI, R_URIAGE_MEISAI_COLUMN, R_URIAGE_MEISAI_01);

		// do
		meisai01.setVersion(0);
		service.delete(meisai01);

		// check
		RUriageMeisaiPK id = new RUriageMeisaiPK();
		id.setUriageId(meisai01.getUriageId());
		id.setMeisaiNumber(meisai01.getMeisaiNumber());
		assertEquals(false, repo.existsById(id));
	}

	@Test
	public void test04_delete() throws Exception {
		// data
		deleteAndInsert(R_URIAGE_MEISAI, R_URIAGE_MEISAI_COLUMN, R_URIAGE_MEISAI_01);

		// do
		RUriageMeisaiPK id = new RUriageMeisaiPK();
		id.setUriageId(meisai01.getUriageId());
		id.setMeisaiNumber(meisai01.getMeisaiNumber());

		service.delete(id, 0);

		// check
		assertEquals(false, repo.existsById(id));
	}

	@Test
	public void test05_getDomain() throws Exception {
		// data
		deleteAndInsert(R_URIAGE_MEISAI, R_URIAGE_MEISAI_COLUMN, R_URIAGE_MEISAI_01);

		// do
		RUriageMeisaiPK id = new RUriageMeisaiPK();
		id.setUriageId(meisai01.getUriageId());
		id.setMeisaiNumber(meisai01.getMeisaiNumber());

		UriageRirekiMeisaiDomain actual = service.getDomain(id);

		// check
		assertEquals(meisai01, actual);
	}

	@Test
	public void test06_getDomainList() throws Exception {
		// data
		deleteAndInsert(R_URIAGE_MEISAI, R_URIAGE_MEISAI_COLUMN, R_URIAGE_MEISAI_01, R_URIAGE_MEISAI_02);

		// do
		List<UriageRirekiMeisaiDomain> actual = service.getDomainList("r-KK01-00001-20170820");

		// check
		assertEquals(2, actual.size());
	}

	@Test
	public void test07_getDomainList() throws Exception {
		// data
		deleteAndInsert(R_URIAGE_MEISAI, R_URIAGE_MEISAI_COLUMN, R_URIAGE_MEISAI_01);

		// do
		RUriageMeisaiPK id = new RUriageMeisaiPK();
		id.setUriageId(meisai01.getUriageId());
		id.setMeisaiNumber(meisai01.getMeisaiNumber());

		// do
		boolean actual = service.exsists(id);

		// check
		assertEquals(true, actual);
	}

	@Test
	public void test08_overrideList() throws Exception {
		// data
		deleteAndInsert(R_URIAGE_MEISAI, R_URIAGE_MEISAI_COLUMN, R_URIAGE_MEISAI_01, R_URIAGE_MEISAI_02);

		// do
		List<UriageRirekiMeisaiDomain> meisaiList = new ArrayList<UriageRirekiMeisaiDomain>();
		UriageRirekiMeisaiDomainBuilder b = new UriageRirekiMeisaiDomainBuilder();
		b.withHanbaiNumber(999);
		UriageRirekiMeisaiDomain updated = b.apply(meisai01);
		meisaiList.add(updated);
		service.overrideList(meisaiList);

		// check
		List<UriageRirekiMeisaiDomain> actual = service.getDomainList("r-KK01-00001-20170820");
		assertEquals(1, actual.size());
		assertEquals(new Integer(999), actual.get(0).getHanbaiNumber());
	}

	@Test
	public void test09_deleteAll() throws Exception {
		// data
		deleteAndInsert(R_URIAGE_MEISAI, R_URIAGE_MEISAI_COLUMN, R_URIAGE_MEISAI_01, R_URIAGE_MEISAI_02);

		// do
		String uriageId = "r-KK01-00001-20170820";
		service.deleteAll(uriageId);

		// check
		List<UriageRirekiMeisaiDomain> actual = service.getDomainList(uriageId);
		assertTrue(actual.isEmpty());
	}

}
