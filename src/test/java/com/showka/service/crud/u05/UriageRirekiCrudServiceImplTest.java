package com.showka.service.crud.u05;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.CrudServiceTestCase;
import com.showka.domain.KokyakuDomain;
import com.showka.domain.UriageDomain;
import com.showka.domain.UriageMeisaiDomain;
import com.showka.domain.UriageRirekiDomain;
import com.showka.domain.builder.UriageDomainBuilder;
import com.showka.entity.RUriage;
import com.showka.entity.RUriagePK;
import com.showka.kubun.HanbaiKubun;
import com.showka.repository.i.RUriageRepository;
import com.showka.service.crud.u01.i.KokyakuCrudService;
import com.showka.service.crud.u05.i.UriageRirekiMeisaiCrudService;
import com.showka.system.EmptyProxy;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

@SuppressWarnings("deprecation")
public class UriageRirekiCrudServiceImplTest extends CrudServiceTestCase {

	@Tested
	private UriageRirekiCrudServiceImpl service;

	@Injectable
	@Autowired
	private RUriageRepository repo;

	@Injectable
	private KokyakuCrudService kokyakuCrudService;

	@Injectable
	private UriageRirekiMeisaiCrudService uriageRirekiMeisaiCrudService;

	/** 売上01. */
	private static final UriageDomain rUriage01;
	static {
		UriageDomainBuilder b = new UriageDomainBuilder();
		ArrayList<UriageMeisaiDomain> meisai = new ArrayList<UriageMeisaiDomain>();
		rUriage01 = b.withKokyaku(EmptyProxy.domain(KokyakuDomain.class))
				.withDenpyoNumber("00001")
				.withUriageDate(new TheDate(2017, 8, 20))
				.withKeijoDate(new TheDate(2017, 8, 20))
				.withHanbaiKubun(HanbaiKubun.現金)
				.withShohizeiritsu(new TaxRate(0.08))
				.withUriageMeisai(meisai)
				.withRecordId("r-KK01-00001-20170820")
				.build();
	}

	/** 売上01. */
	private static final UriageDomain tUriage01;
	static {
		UriageDomainBuilder b = new UriageDomainBuilder();
		ArrayList<UriageMeisaiDomain> meisai = new ArrayList<UriageMeisaiDomain>();
		tUriage01 = b.withKokyaku(EmptyProxy.domain(KokyakuDomain.class))
				.withDenpyoNumber("00001")
				.withUriageDate(new TheDate(2017, 8, 20))
				.withKeijoDate(new TheDate(2017, 8, 20))
				.withHanbaiKubun(HanbaiKubun.現金)
				.withShohizeiritsu(new TaxRate(0.08))
				.withUriageMeisai(meisai)
				.withRecordId("r-KK01-00001")
				.build();
	}

	// 売上履歴テーブル
	private static final Object[] R_URIAGE_01 = {
			"r-KK01-00001",
			new Date("2017/08/19"),
			new Date("2017/08/19"),
			"00",
			0.08,
			"r-KK01-00001-20170819" };
	private static final Object[] R_URIAGE_02 = {
			"r-KK01-00001",
			new Date("2017/08/19"),
			new Date("2017/08/20"),
			"00",
			0.08,
			"r-KK01-00001-20170820" };

	// 売上テーブル
	private static final Object[] T_URIAGE_01 = {
			"r-KK01",
			"00001",
			new Date(2017, 8, 19),
			new Date(2017, 8, 20),
			"00",
			0.08,
			"r-KK01-00001" };

	// 顧客
	private static final Object[] M_KOKYAKU_01 = { "KK01", "顧客01", "左京区", "01", "00", "r-BS01", "r-KK01" };

	@Test
	@SuppressWarnings("unchecked")
	public void test01_Save() throws Exception {

		super.deleteAll(R_URIAGE);

		new Expectations() {
			{
				uriageRirekiMeisaiCrudService.overrideList((List<UriageMeisaiDomain>) any);
			}
		};

		// do
		service.save(rUriage01);

		// get saved
		RUriagePK pk = new RUriagePK();
		pk.setUriageId("r-KK01-00001");
		pk.setKeijoDate(new TheDate(2017, 8, 20).toDate());
		RUriage actual = repo.getOne(pk);

		// verify
		new Verifications() {
			{
				uriageRirekiMeisaiCrudService.overrideList((List<UriageMeisaiDomain>) any);
				times = 1;
			}
		};

		// check
		assertEquals("r-KK01-00001", actual.getPk().getUriageId());
		assertEquals(new TheDate(2017, 8, 20).toDate(), actual.getPk().getKeijoDate());
	}

	@Test
	public void test02_GetUriage() throws Exception {
		// insert data
		super.deleteAndInsert(R_URIAGE, R_URIAGE_COLUMN, R_URIAGE_01, R_URIAGE_02);
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, T_URIAGE_01);
		super.deleteAndInsert(M_KOKYAKU, M_KOKYAKU_COLUMN, M_KOKYAKU_01);

		// do
		UriageRirekiDomain actual = service.getUriageRirekiList("r-KK01-00001");

		// check
		assertEquals(2, actual.getList().size());
		assertEquals(new TheDate(2017, 8, 20), actual.getNewest().getKeijoDate());
	}

	@Test
	public void test03_cancel_with_new_record() throws Exception {
		// data
		String uriageId = "r-KK01-00001";
		super.deleteAll(R_URIAGE);

		// do
		service.cancel(tUriage01);

		// check
		RUriagePK pk = new RUriagePK();
		pk.setKeijoDate(new TheDate(2017, 8, 20).toDate());
		pk.setUriageId(uriageId);
		RUriage actual = repo.findById(pk).get();
		assertEquals(uriageId, actual.getPk().getUriageId());

		new Verifications() {
			{
				uriageRirekiMeisaiCrudService.deleteAll(anyString);
				times = 0;
			}
		};
	}

	@Test
	public void test04_cancel_with_existing_record() throws Exception {
		// data
		String uriageId = "r-KK01-00001";
		String rUriageId = "r-KK01-00001-20170820";
		super.deleteAndInsert(R_URIAGE, R_URIAGE_COLUMN, R_URIAGE_01, R_URIAGE_02);

		// expect
		new Expectations() {
			{
				uriageRirekiMeisaiCrudService.deleteAll(rUriageId);
			}
		};

		// do
		service.cancel(tUriage01);

		// check
		RUriagePK pk = new RUriagePK();
		pk.setKeijoDate(new TheDate(2017, 8, 20).toDate());
		pk.setUriageId(uriageId);
		RUriage actual = repo.findById(pk).get();
		assertEquals("r-KK01-00001-20170820", actual.getRecordId());

		// verify
		new Verifications() {
			{
				uriageRirekiMeisaiCrudService.deleteAll(rUriageId);
				times = 1;
			}
		};
	}

}
