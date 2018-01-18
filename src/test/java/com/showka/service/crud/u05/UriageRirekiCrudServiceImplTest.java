package com.showka.service.crud.u05;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.ServiceCrudTestCase;
import com.showka.domain.KokyakuDomain;
import com.showka.domain.UriageRirekiDomain;
import com.showka.domain.UriageRirekiListDomain;
import com.showka.domain.UriageRirekiMeisaiDomain;
import com.showka.domain.builder.UriageRirekiDomainBuilder;
import com.showka.entity.RUriage;
import com.showka.entity.RUriagePK;
import com.showka.kubun.HanbaiKubun;
import com.showka.repository.i.CUriageRepository;
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
public class UriageRirekiCrudServiceImplTest extends ServiceCrudTestCase {

	@Tested
	private UriageRirekiCrudServiceImpl service;

	@Injectable
	@Autowired
	private RUriageRepository repo;

	@Injectable
	private KokyakuCrudService kokyakuCrudService;

	@Injectable
	private UriageRirekiMeisaiCrudService uriageRirekiMeisaiCrudService;

	@Injectable
	@Autowired
	private CUriageRepository cUriageRepository;

	/** 売上01. */
	private static final UriageRirekiDomain uriage01;
	static {
		UriageRirekiDomainBuilder b = new UriageRirekiDomainBuilder();
		ArrayList<UriageRirekiMeisaiDomain> meisai = new ArrayList<UriageRirekiMeisaiDomain>();
		uriage01 = b.withUriageId("r-KK01-00001")
				.withKokyaku(EmptyProxy.domain(KokyakuDomain.class))
				.withDenpyoNumber("00001")
				.withUriageDate(new TheDate(2017, 8, 20))
				.withKeijoDate(new TheDate(2017, 8, 20))
				.withHanbaiKubun(HanbaiKubun.現金)
				.withShohizeiritsu(new TaxRate(0.08))
				.withUriageMeisai(meisai)
				.withRecordId("r-KK01-00001-20170820")
				.build();
	}

	// 売上履歴テーブル
	private static final Object[] R_URIAGE_01 = {
			"r-KK01-00001",
			new Date("2017/08/19"),
			new Date("2017/08/19"),
			"00",
			0.08,
			false,
			"r-KK01-00001-20170819" };
	private static final Object[] R_URIAGE_02 = {
			"r-KK01-00001",
			new Date("2017/08/19"),
			new Date("2017/08/20"),
			"00",
			0.08,
			false,
			"r-KK01-00001-20170820" };
	private static final Object[] R_URIAGE_02_CANCELD = {
			"r-KK01-00001",
			new Date("2017/08/19"),
			new Date("2017/08/20"),
			"00",
			0.08,
			true,
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

	// 売上キャンセルテーブル
	private static final Object[] C_URIAGE_01 = { "r-KK01", "00001", new Date(2017, 8, 21), "r-KK01-00001" };

	// 顧客
	private static final Object[] M_KOKYAKU_01 = { "KK01", "顧客01", "左京区", "01", "00", "r-BS01", "r-KK01" };

	@Test
	@SuppressWarnings("unchecked")
	public void test01_Save() throws Exception {

		super.deleteAll(R_URIAGE);

		new Expectations() {
			{
				uriageRirekiMeisaiCrudService.overrideList((List<UriageRirekiMeisaiDomain>) any);
			}
		};

		// do
		service.save(uriage01);

		// get saved
		RUriagePK pk = new RUriagePK();
		pk.setUriageId("r-KK01-00001");
		pk.setKeijoDate(new TheDate(2017, 8, 20).toDate());
		RUriage actual = repo.getOne(pk);

		// verify
		new Verifications() {
			{
				uriageRirekiMeisaiCrudService.overrideList((List<UriageRirekiMeisaiDomain>) any);
				times = 1;
			}
		};

		// check
		assertEquals("r-KK01-00001", actual.getPk().getUriageId());
		assertEquals(new TheDate(2017, 8, 20).toDate(), actual.getPk().getKeijoDate());
	}

	@Test
	public void test02_GetUriageRireki() throws Exception {
		// insert data
		super.deleteAndInsert(R_URIAGE, R_URIAGE_COLUMN, R_URIAGE_01, R_URIAGE_02);
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, T_URIAGE_01);
		super.deleteAndInsert(M_KOKYAKU, M_KOKYAKU_COLUMN, M_KOKYAKU_01);

		// do
		UriageRirekiListDomain actual = service.getUriageRirekiList("r-KK01-00001");

		// check
		assertEquals(2, actual.getList().size());
		assertEquals("r-KK01-00001-20170820", actual.getNewest().getRecordId());
	}

	@Test
	public void test03_getUriageRireki() throws Exception {
		// insert data
		super.deleteAndInsert(R_URIAGE, R_URIAGE_COLUMN, R_URIAGE_01, R_URIAGE_02_CANCELD);
		super.deleteAndInsert(C_URIAGE, C_URIAGE_COLUMN, C_URIAGE_01);
		super.deleteAll(T_URIAGE);
		super.deleteAndInsert(M_KOKYAKU, M_KOKYAKU_COLUMN, M_KOKYAKU_01);

		// do
		UriageRirekiListDomain actual = service.getUriageRirekiList("r-KK01-00001");

		// check
		assertEquals(2, actual.getList().size());
		assertEquals("r-KK01-00001-20170820", actual.getNewest().getRecordId());
	}

}
