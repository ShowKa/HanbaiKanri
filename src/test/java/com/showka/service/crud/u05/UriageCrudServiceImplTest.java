package com.showka.service.crud.u05;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.ServiceCrudTestCase;
import com.showka.domain.KokyakuDomain;
import com.showka.domain.UriageDomain;
import com.showka.domain.UriageMeisaiDomain;
import com.showka.domain.builder.KokyakuDomainBuilder;
import com.showka.domain.builder.UriageDomainBuilder;
import com.showka.domain.builder.UriageMeisaiDomainBuilder;
import com.showka.domain.mock.Domains;
import com.showka.entity.TUriage;
import com.showka.entity.TUriageMeisaiPK;
import com.showka.entity.TUriagePK;
import com.showka.kubun.HanbaiKubun;
import com.showka.repository.i.MKokyakuRepository;
import com.showka.repository.i.TUriageMeisaiRepository;
import com.showka.repository.i.TUriageRepository;
import com.showka.service.crud.u01.i.KokyakuCrudService;
import com.showka.service.crud.u05.i.UriageMeisaiCrudService;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class UriageCrudServiceImplTest extends ServiceCrudTestCase {

	@Tested
	private UriageCrudServiceImpl service;

	@Injectable
	@Autowired
	private TUriageRepository repo;

	@Injectable
	private MKokyakuRepository kokyakuRepo;

	@Injectable
	private TUriageMeisaiRepository meisaiRepository;

	@Injectable
	private UriageMeisaiCrudService uriageMeisaiCrudService;

	@Injectable
	private KokyakuCrudService kokyakuCrudService;

	// test data

	/** 売上01 */
	private static final Object[] URIAGE_01 = { "r-KK01", "00001", new Date(), "00", 0.08, "r-KK01-00001" };

	/** 売上明細01 */
	private static final Object[] URIAGE_MEISAI_01 = { "r-KK01-00001", 1, "r-SH01", 5, 1000, "r-KK01-00001-1" };

	/** 顧客01 */
	private static final Object[] KOKYAKU_01 = { "KK01", "顧客01", "左京区", "01", "00", "r-BS01", "r-KK01" };

	/**
	 * save for register
	 * 
	 * @throws Exception
	 */
	@Test
	public void test01_Save_Register() throws Exception {

		// data
		super.deleteAll(Domains.T_URIAGE_TABLE);
		assertEquals(0, repo.findAll().size());

		// 売上明細01
		UriageMeisaiDomainBuilder bm1 = new UriageMeisaiDomainBuilder();
		bm1.withUriageId("r-KK99-99999");
		bm1.withMeisaiNumber(1);
		UriageMeisaiDomain uriageMeisai01 = bm1.build();

		// 売上明細02
		UriageMeisaiDomainBuilder bm2 = new UriageMeisaiDomainBuilder();
		bm2.withUriageId("r-KK99-99999");
		bm2.withMeisaiNumber(2);
		UriageMeisaiDomain uriageMeisai02 = bm2.build();

		ArrayList<UriageMeisaiDomain> meisai = new ArrayList<UriageMeisaiDomain>();
		meisai.add(uriageMeisai01);
		meisai.add(uriageMeisai02);

		// 顧客
		KokyakuDomainBuilder bk = new KokyakuDomainBuilder();
		bk.withRecordId("r-KK99");
		KokyakuDomain kokyaku01 = bk.build();

		// 売上
		UriageDomainBuilder b = new UriageDomainBuilder();
		b.withKokyaku(kokyaku01)
				.withDenpyoNumber("99999")
				.withUriageDate(new TheDate(2017, 8, 20))
				.withHanbaiKubun(HanbaiKubun.現金)
				.withShohizeiritsu(new TaxRate(0.08))
				.withUriageMeisai(meisai)
				.withRecordId("r-KK99-99999");
		UriageDomain uriage = b.build();

		// 売上明細03 削除対象
		UriageMeisaiDomainBuilder bm3 = new UriageMeisaiDomainBuilder();
		bm3.withUriageId("r-KK99-99999");
		bm3.withMeisaiNumber(3);
		UriageMeisaiDomain uriageMeisai03 = bm3.build();

		// old
		List<UriageMeisaiDomain> oldList = new ArrayList<UriageMeisaiDomain>();
		oldList.add(uriageMeisai01);
		oldList.add(uriageMeisai02);
		oldList.add(uriageMeisai03);

		// expectation
		new Expectations() {
			{
				uriageMeisaiCrudService.save(uriageMeisai01);
				uriageMeisaiCrudService.save(uriageMeisai02);
				uriageMeisaiCrudService.getDomainList("r-KK99-99999");
				result = oldList;
				uriageMeisaiCrudService.delete(uriageMeisai03);
			}
		};

		// save
		service.save(uriage);

		// verification
		new Verifications() {
			{
				uriageMeisaiCrudService.save(uriageMeisai01);
				times = 1;
				uriageMeisaiCrudService.save(uriageMeisai02);
				times = 1;
				uriageMeisaiCrudService.delete(uriageMeisai03);
				times = 1;
				uriageMeisaiCrudService.delete(uriageMeisai01);
				times = 0;
			}
		};

		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId("r-KK99");
		pk.setDenpyoNumber("99999");
		TUriage actual = repo.getOne(pk);
		assertEquals(0.08, actual.getShohizeiritsu());
		assertEquals(0, actual.getVersion().intValue());
	}

	/**
	 * save for update
	 * 
	 * @throws Exception
	 */
	@Test
	public void test02_Save_Update() throws Exception {

		// data
		super.deleteAndInsert(Domains.T_URIAGE_TABLE, Domains.T_URIAGE_COLUMN, URIAGE_01);
		// repo 呼ばないとエラーになる....
		assertEquals(1, repo.findAll().size());

		// 売上明細01
		UriageMeisaiDomainBuilder bm1 = new UriageMeisaiDomainBuilder();
		bm1.withUriageId("r-KK01-00001");
		bm1.withMeisaiNumber(1);
		UriageMeisaiDomain uriageMeisai01 = bm1.build();

		// 売上明細02
		UriageMeisaiDomainBuilder bm2 = new UriageMeisaiDomainBuilder();
		bm2.withUriageId("r-KK01-00001");
		bm2.withMeisaiNumber(2);
		UriageMeisaiDomain uriageMeisai02 = bm2.build();

		ArrayList<UriageMeisaiDomain> meisai = new ArrayList<UriageMeisaiDomain>();
		meisai.add(uriageMeisai01);
		meisai.add(uriageMeisai02);

		// 顧客
		KokyakuDomainBuilder bk = new KokyakuDomainBuilder();
		bk.withRecordId("r-KK01");
		KokyakuDomain kokyaku01 = bk.build();

		// 売上
		UriageDomainBuilder b = new UriageDomainBuilder();
		b.withKokyaku(kokyaku01)
				.withDenpyoNumber("00001")
				.withUriageDate(new TheDate(2017, 8, 20))
				.withHanbaiKubun(HanbaiKubun.現金)
				.withShohizeiritsu(new TaxRate(0.99))
				.withUriageMeisai(meisai)
				.withVersion(0)
				.withRecordId("KK01-00001");
		UriageDomain uriage01 = b.build();

		// expectation
		new Expectations() {
			{
				uriageMeisaiCrudService.save(uriageMeisai01);
				uriageMeisaiCrudService.save(uriageMeisai02);
			}
		};

		// save
		service.save(uriage01);

		// verification
		new Verifications() {
			{
				uriageMeisaiCrudService.save(uriageMeisai01);
				times = 1;
				uriageMeisaiCrudService.save(uriageMeisai02);
				times = 1;
			}
		};

		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId("r-KK01");
		pk.setDenpyoNumber("00001");
		TUriage actual = repo.getOne(pk);
		assertEquals(0.99, actual.getShohizeiritsu());
		assertEquals(1, actual.getVersion().intValue());
	}

	/**
	 * delete
	 * 
	 * @throws Exception
	 */
	@Test
	public void test03_deleteByPK() throws Exception {
		// data
		super.deleteAndInsert(Domains.T_URIAGE_TABLE, Domains.T_URIAGE_COLUMN, URIAGE_01);
		assertEquals(1, repo.findAll().size());

		// do
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId("r-KK01");
		pk.setDenpyoNumber("00001");
		service.delete(pk, 0);

		// check
		assertEquals(0, repo.findAll().size());
	}

	/**
	 * delete domain
	 * 
	 * @throws Exception
	 */
	@Test
	public void test04_deleteDomain() throws Exception {
		// data
		super.deleteAndInsert(Domains.T_URIAGE_TABLE, Domains.T_URIAGE_COLUMN, URIAGE_01);
		assertEquals(1, repo.findAll().size());

		// 顧客
		KokyakuDomainBuilder bk = new KokyakuDomainBuilder();
		bk.withRecordId("r-KK01");
		KokyakuDomain kokyaku01 = bk.build();

		// 売上
		UriageDomainBuilder b = new UriageDomainBuilder();
		b.withKokyaku(kokyaku01).withDenpyoNumber("00001").withVersion(0);
		UriageDomain uriage01 = b.build();

		// do
		service.delete(uriage01);

		// check
		assertEquals(0, repo.findAll().size());
	}

	/**
	 * exists
	 * 
	 * @throws Exception
	 */
	@Test
	public void test05_exists() throws Exception {
		// data
		super.deleteAndInsert(Domains.T_URIAGE_TABLE, Domains.T_URIAGE_COLUMN, URIAGE_01);
		assertEquals(1, repo.findAll().size());

		// do
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId("r-KK01");
		pk.setDenpyoNumber("00001");
		boolean actual = service.exsists(pk);

		// check
		assertEquals(true, actual);
	}

	/**
	 * exists
	 * 
	 * @throws Exception
	 */
	@Test
	public void test06_exists() throws Exception {
		// data
		super.deleteAndInsert(Domains.T_URIAGE_TABLE, Domains.T_URIAGE_COLUMN, URIAGE_01);
		assertEquals(1, repo.findAll().size());

		// do
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId("r-KK99");
		pk.setDenpyoNumber("99999");
		boolean actual = service.exsists(pk);

		// check
		assertEquals(false, actual);
	}

	@Test
	public void test07_getDomain() throws Exception {
		// data
		super.deleteAndInsert(Domains.T_URIAGE_TABLE, Domains.T_URIAGE_COLUMN, URIAGE_01);
		super.deleteAndInsert(Domains.M_KOKYAKU_TABLE, Domains.M_KOKYAKU_COLUMN, KOKYAKU_01);
		super.deleteAndInsert(Domains.T_URIAGE_MEISAI_TABLE, Domains.T_URIAGE_MEISAI_COLUMN, URIAGE_MEISAI_01);
		assertEquals(1, repo.findAll().size());

		// 顧客
		KokyakuDomainBuilder bk = new KokyakuDomainBuilder();
		bk.withRecordId("r-KK01");
		KokyakuDomain kokyaku01 = bk.build();

		// 売上明細01
		UriageMeisaiDomainBuilder bm1 = new UriageMeisaiDomainBuilder();
		bm1.withRecordId("r-KK01-00001-1");
		UriageMeisaiDomain uriageMeisai01 = bm1.build();

		// expect
		new Expectations() {
			{
				kokyakuCrudService.getDomain("KK01");
				result = kokyaku01;
				uriageMeisaiCrudService.getDomain(new TUriageMeisaiPK("r-KK01-00001", 1));
				result = uriageMeisai01;
			}
		};

		// do
		TUriagePK pk = new TUriagePK("r-KK01", "00001");
		UriageDomain d = service.getDomain(pk);

		// verification
		new Verifications() {
			{
				kokyakuCrudService.getDomain("KK01");
				times = 1;
				uriageMeisaiCrudService.getDomain(new TUriageMeisaiPK("r-KK01-00001", 1));
				times = 1;
			}
		};

		// check
		assertEquals("r-KK01-00001", d.getRecordId());
		assertEquals("r-KK01", d.getKokyaku().getRecordId());
		assertEquals(1, d.getUriageMeisai().size());
		assertEquals("r-KK01-00001-1", d.getUriageMeisai().get(0).getRecordId());
	}

}
