package com.showka.service.crud.u05;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.CrudServiceTestCase;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.builder.KokyakuBuilder;
import com.showka.domain.builder.UriageBuilder;
import com.showka.domain.builder.UriageMeisaiBuilder;
import com.showka.domain.builder.UriageRirekiBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u05.Uriage;
import com.showka.domain.u05.UriageMeisai;
import com.showka.domain.u05.UriageRireki;
import com.showka.domain.z00.Busho;
import com.showka.entity.TUriage;
import com.showka.entity.TUriageMeisaiPK;
import com.showka.entity.TUriagePK;
import com.showka.kubun.HanbaiKubun;
import com.showka.repository.i.TUriageRepository;
import com.showka.service.crud.u01.i.KokyakuCrudService;
import com.showka.service.crud.u05.i.UriageCancelCrudService;
import com.showka.service.crud.u05.i.UriageMeisaiCrudService;
import com.showka.service.crud.u05.i.UriageRirekiCrudService;
import com.showka.value.EigyoDate;
import com.showka.value.TaxRate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Tested;
import mockit.Verifications;

public class UriageCrudServiceImplTest extends CrudServiceTestCase {

	@Tested
	@Injectable
	private UriageCrudServiceImpl service;

	@Injectable
	@Autowired
	private TUriageRepository repo;

	@Injectable
	private UriageMeisaiCrudService uriageMeisaiCrudService;

	@Injectable
	private KokyakuCrudService kokyakuCrudService;

	@Injectable
	private UriageRirekiCrudService uriageRirekiCrudService;

	@Injectable
	private UriageCancelCrudService uriageCancelCrudService;

	/** 売上01 */
	private static final Object[] URIAGE_01 = { "r-KK01", "00001", new Date(), new Date(), "00", 0.08, "r-KK01-00001" };
	private static final Object[] URIAGE_02 = { "r-KK01", "00002", new Date(), new Date(), "00", 0.08, "r-KK01-00002" };

	/** 売上明細01 */
	private static final Object[] URIAGE_MEISAI_01 = { "r-KK01-00001", 1, "r-SH01", 5, 1000, "r-KK01-00001-1" };

	/** 顧客01 */
	private static final Object[] KOKYAKU_01 = { "KK01", "顧客01", "左京区", "01", "00", "r-BS01", "r-KK01" };

	/** 部署01. */
	private static final Object[] M_BUSHO_01 = { "BS01", "01", "01", "部署01", "r-BS01" };

	/**
	 * save for register
	 * 
	 * @throws Exception
	 */
	@Test
	public void test01_Save_Register() throws Exception {
		// data
		super.deleteAll(T_URIAGE);
		assertEquals(0, repo.findAll().size());
		// 売上明細01
		UriageMeisaiBuilder bm1 = new UriageMeisaiBuilder();
		bm1.withMeisaiNumber(1);
		UriageMeisai uriageMeisai01 = bm1.build();
		// 売上明細02
		UriageMeisaiBuilder bm2 = new UriageMeisaiBuilder();
		bm2.withMeisaiNumber(2);
		UriageMeisai uriageMeisai02 = bm2.build();
		ArrayList<UriageMeisai> meisai = new ArrayList<UriageMeisai>();
		meisai.add(uriageMeisai01);
		meisai.add(uriageMeisai02);
		// 顧客
		KokyakuBuilder bk = new KokyakuBuilder();
		bk.withRecordId("r-KK99");
		Kokyaku kokyaku01 = bk.build();
		// 売上
		UriageBuilder b = new UriageBuilder();
		b.withKokyaku(kokyaku01)
				.withDenpyoNumber("99999")
				.withUriageDate(new EigyoDate(2017, 8, 20))
				.withKeijoDate(new EigyoDate(2017, 8, 20))
				.withHanbaiKubun(HanbaiKubun.現金)
				.withShohizeiritsu(new TaxRate(0.08))
				.withUriageMeisai(meisai);
		Uriage uriage = b.build();
		// expectation
		new Expectations() {
			{
				// 売上履歴の保存
				uriageRirekiCrudService.save(uriage);
				// 明細上書き
				uriageMeisaiCrudService.overrideList(anyString, uriage.getUriageMeisai());
			}
		};
		// save
		service.save(uriage);
		// verification
		new Verifications() {
			{
				uriageRirekiCrudService.save(uriage);
				times = 1;
				uriageMeisaiCrudService.overrideList(anyString, uriage.getUriageMeisai());
				times = 1;
			}
		};
		// check
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
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, URIAGE_01);
		// repo 呼ばないとエラーになる....
		assertEquals(1, repo.findAll().size());
		// 売上明細01
		UriageMeisaiBuilder bm1 = new UriageMeisaiBuilder();
		bm1.withMeisaiNumber(1);
		UriageMeisai uriageMeisai01 = bm1.build();
		// 売上明細02
		UriageMeisaiBuilder bm2 = new UriageMeisaiBuilder();
		bm2.withMeisaiNumber(2);
		UriageMeisai uriageMeisai02 = bm2.build();
		ArrayList<UriageMeisai> meisai = new ArrayList<UriageMeisai>();
		meisai.add(uriageMeisai01);
		meisai.add(uriageMeisai02);
		// 顧客
		KokyakuBuilder bk = new KokyakuBuilder();
		bk.withRecordId("r-KK01");
		Kokyaku kokyaku01 = bk.build();
		// 売上
		UriageBuilder b = new UriageBuilder();
		b.withKokyaku(kokyaku01)
				.withDenpyoNumber("00001")
				.withUriageDate(new EigyoDate(2017, 8, 20))
				.withKeijoDate(new EigyoDate(2017, 8, 20))
				.withHanbaiKubun(HanbaiKubun.現金)
				.withShohizeiritsu(new TaxRate(0.99))
				.withUriageMeisai(meisai)
				.withVersion(0);
		Uriage uriage01 = b.build();
		// expectation
		new Expectations() {
			{
				uriageRirekiCrudService.save(uriage01);
				uriageMeisaiCrudService.overrideList("r-KK01-00001", uriage01.getUriageMeisai());
			}
		};
		// save
		service.save(uriage01);
		// verification
		new Verifications() {
			{
				uriageRirekiCrudService.save(uriage01);
				times = 1;
				uriageMeisaiCrudService.overrideList("r-KK01-00001", uriage01.getUriageMeisai());
				times = 1;
			}
		};
		// check
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId("r-KK01");
		pk.setDenpyoNumber("00001");
		TUriage actual = repo.getOne(pk);
		assertEquals(0.99, actual.getShohizeiritsu());
		assertEquals(1, actual.getVersion().intValue());
	}

	/**
	 * delete 単純に削除.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test03_delete() throws Exception {
		// data
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, URIAGE_01);
		super.deleteAndInsert(M_KOKYAKU, M_KOKYAKU_COLUMN, KOKYAKU_01);
		super.deleteAndInsert(M_BUSHO, M_BUSHO_COLUMN, M_BUSHO_01);
		assertEquals(1, repo.findAll().size());
		// input
		// 顧客
		KokyakuBuilder kb = new KokyakuBuilder();
		kb.withRecordId("r-KK01");
		Kokyaku kokyaku = kb.build();
		// 売上
		UriageBuilder ub = new UriageBuilder();
		ub.withKokyaku(kokyaku);
		ub.withDenpyoNumber("00001");
		ub.withVersion(0);
		ub.withUriageMeisai(new ArrayList<>());
		Uriage uriage = ub.build();
		// do
		service.delete(uriage);
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
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, URIAGE_01);
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
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, URIAGE_01);
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
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, URIAGE_01);
		super.deleteAndInsert(M_KOKYAKU, M_KOKYAKU_COLUMN, KOKYAKU_01);
		super.deleteAndInsert(T_URIAGE_MEISAI, T_URIAGE_MEISAI_COLUMN, URIAGE_MEISAI_01);
		assertEquals(1, repo.findAll().size());
		// 顧客
		KokyakuBuilder bk = new KokyakuBuilder();
		bk.withRecordId("r-KK01");
		Kokyaku kokyaku01 = bk.build();
		// 売上明細01
		UriageMeisaiBuilder bm1 = new UriageMeisaiBuilder();
		bm1.withRecordId("r-KK01-00001-1");
		UriageMeisai uriageMeisai01 = bm1.build();
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
		Uriage d = service.getDomain(pk);
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

	@Test
	@Transactional
	public void test08_cancel() throws Exception {
		// data
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, URIAGE_01);
		super.deleteAndInsert(T_URIAGE_MEISAI, T_URIAGE_MEISAI_COLUMN, URIAGE_MEISAI_01);
		super.deleteAndInsert(M_KOKYAKU, M_KOKYAKU_COLUMN, KOKYAKU_01);
		super.deleteAndInsert(M_BUSHO, M_BUSHO_COLUMN, M_BUSHO_01);
		// 部署
		BushoBuilder bb = new BushoBuilder();
		bb.withEigyoDate(new EigyoDate());
		bb.withCode("BS01");
		bb.withRecordId("r-BS01");
		Busho busho = bb.build();
		// 顧客
		KokyakuBuilder bk = new KokyakuBuilder();
		bk.withShukanBusho(busho);
		bk.withRecordId("r-KK01");
		Kokyaku kokyaku01 = bk.build();
		// expect
		new Expectations() {
			{
				// 顧客ドメイン取得
				kokyakuCrudService.getDomain("KK01");
				result = kokyaku01;
				// 明細削除
				uriageMeisaiCrudService.overrideList("r-KK01-00001", (List<UriageMeisai>) any);
				// cancel 履歴
				uriageRirekiCrudService.save((Uriage) any);
				// cancel
				uriageCancelCrudService.save((Uriage) any);
			}
		};
		// do
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId("r-KK01");
		pk.setDenpyoNumber("00001");
		service.cancel(pk, 0);
		// check
		TUriage actual = repo.findByRecordId("r-KK01-00001");
		assertNotNull(actual);
		// verify
		new Verifications() {
			{
				// 顧客ドメイン取得
				kokyakuCrudService.getDomain("KK01");
				times = 1;
				// 明細削除
				uriageMeisaiCrudService.overrideList("r-KK01-00001", (List<UriageMeisai>) any);
				times = 1;
				// cancel 履歴
				uriageRirekiCrudService.save((Uriage) any);
				times = 1;
				// cancel
				uriageCancelCrudService.save((Uriage) any);
				times = 1;
			}
		};
	}

	@Test
	public void test09_GetDomain() throws Exception {
		// data
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, URIAGE_01);
		super.deleteAndInsert(M_KOKYAKU, M_KOKYAKU_COLUMN, KOKYAKU_01);
		super.deleteAndInsert(T_URIAGE_MEISAI, T_URIAGE_MEISAI_COLUMN, URIAGE_MEISAI_01);
		// 顧客
		KokyakuBuilder bk = new KokyakuBuilder();
		bk.withRecordId("r-KK01");
		Kokyaku kokyaku01 = bk.build();
		// 売上明細01
		UriageMeisaiBuilder bm1 = new UriageMeisaiBuilder();
		bm1.withRecordId("r-KK01-00001-1");
		UriageMeisai uriageMeisai01 = bm1.build();
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
		Uriage actual = service.getDomain("KK01", "00001");
		// verification
		new Verifications() {
			{
				kokyakuCrudService.getDomain("KK01");
				times = 2;
				uriageMeisaiCrudService.getDomain(new TUriageMeisaiPK("r-KK01-00001", 1));
				times = 1;
			}
		};
		// check
		assertEquals("r-KK01-00001", actual.getRecordId());
	}

	@Test
	public void test10_getUriageOfKokyaku() throws Exception {
		// data
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, URIAGE_01, URIAGE_02);
		super.deleteAndInsert(M_KOKYAKU, M_KOKYAKU_COLUMN, KOKYAKU_01);
		super.deleteAndInsert(T_URIAGE_MEISAI, T_URIAGE_MEISAI_COLUMN, URIAGE_MEISAI_01);
		super.deleteAndInsert(M_BUSHO, M_BUSHO_COLUMN, M_BUSHO_01);
		List<Uriage> actual = service.getUriageOfKokyaku("KK01");
		assertEquals(2, actual.size());
	}

	/**
	 * revert 計上済み売上に戻す.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test11_revert() throws Exception {
		// data
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, URIAGE_01);
		super.deleteAndInsert(M_KOKYAKU, M_KOKYAKU_COLUMN, KOKYAKU_01);
		super.deleteAndInsert(M_BUSHO, M_BUSHO_COLUMN, M_BUSHO_01);
		assertEquals(1, repo.findAll().size());
		// expect
		List<Uriage> u = new ArrayList<Uriage>();
		UriageRireki rireki = new UriageRirekiBuilder().withList(u).build();
		new Expectations() {
			{
				uriageRirekiCrudService.getUriageRirekiList("r-KK01-00001");
				result = rireki;
				service.save((Uriage) any);
			}
		};
		// mock
		new MockUp<UriageRireki>() {
			@Mock
			public Uriage getNewest() {
				return new UriageBuilder().build();
			}
		};
		// do
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId("r-KK01");
		pk.setDenpyoNumber("00001");
		service.revert(pk, 0);
		// verify
		new Verifications() {
			{
				uriageRirekiCrudService.getUriageRirekiList("r-KK01-00001");
				times = 1;
				service.save((Uriage) any);
				times = 1;
			}
		};
	}
}
