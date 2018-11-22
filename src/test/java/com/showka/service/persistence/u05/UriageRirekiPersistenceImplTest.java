package com.showka.service.persistence.u05;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.PersistenceTestCase;
import com.showka.domain.builder.UriageBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u05.Uriage;
import com.showka.domain.u05.UriageMeisai;
import com.showka.domain.u05.UriageRireki;
import com.showka.entity.RUriage;
import com.showka.entity.RUriagePK;
import com.showka.kubun.HanbaiKubun;
import com.showka.repository.i.RUriageRepository;
import com.showka.service.crud.u01.i.KokyakuCrud;
import com.showka.service.crud.u05.i.UriageRirekiMeisaiCrud;
import com.showka.service.persistence.u05.UriageRirekiPersistenceImpl;
import com.showka.system.EmptyProxy;
import com.showka.value.EigyoDate;
import com.showka.value.TaxRate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

@SuppressWarnings("deprecation")
public class UriageRirekiPersistenceImplTest extends PersistenceTestCase {

	@Tested
	private UriageRirekiPersistenceImpl service;

	@Injectable
	@Autowired
	private RUriageRepository repo;

	@Injectable
	private KokyakuCrud kokyakuPersistence;

	@Injectable
	private UriageRirekiMeisaiCrud uriageRirekiMeisaiPersistence;

	/** 売上01. */
	private static final Uriage rUriage01;
	static {
		UriageBuilder b = new UriageBuilder();
		ArrayList<UriageMeisai> meisai = new ArrayList<UriageMeisai>();
		rUriage01 = b.withKokyaku(EmptyProxy.domain(Kokyaku.class))
				.withDenpyoNumber("00001")
				.withUriageDate(new EigyoDate(2017, 8, 20))
				.withKeijoDate(new EigyoDate(2017, 8, 20))
				.withHanbaiKubun(HanbaiKubun.現金)
				.withShohizeiritsu(new TaxRate(0.08))
				.withUriageMeisai(meisai)
				.withRecordId("r-KK01-00001-20170820")
				.build();
	}

	/** 売上01. */
	private static final Uriage tUriage01;
	static {
		UriageBuilder b = new UriageBuilder();
		ArrayList<UriageMeisai> meisai = new ArrayList<UriageMeisai>();
		tUriage01 = b.withKokyaku(EmptyProxy.domain(Kokyaku.class))
				.withDenpyoNumber("00001")
				.withUriageDate(new EigyoDate(2017, 8, 20))
				.withKeijoDate(new EigyoDate(2017, 8, 20))
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
				uriageRirekiMeisaiPersistence.overrideList(anyString, (List<UriageMeisai>) any);
			}
		};

		// do
		service.save(rUriage01);

		// get saved
		RUriagePK pk = new RUriagePK();
		pk.setUriageId("r-KK01-00001");
		pk.setKeijoDate(new EigyoDate(2017, 8, 20).toDate());
		RUriage actual = repo.getOne(pk);

		// verify
		new Verifications() {
			{
				uriageRirekiMeisaiPersistence.overrideList(anyString, (List<UriageMeisai>) any);
				times = 1;
			}
		};

		// check
		assertEquals("r-KK01-00001", actual.getPk().getUriageId());
		assertEquals(new EigyoDate(2017, 8, 20).toDate(), actual.getPk().getKeijoDate());
	}

	@Test
	public void test02_GetUriage() throws Exception {
		// insert data
		super.deleteAndInsert(R_URIAGE, R_URIAGE_COLUMN, R_URIAGE_01, R_URIAGE_02);
		super.deleteAndInsert(T_URIAGE, T_URIAGE_COLUMN, T_URIAGE_01);
		super.deleteAndInsert(M_KOKYAKU, M_KOKYAKU_COLUMN, M_KOKYAKU_01);

		// do
		UriageRireki actual = service.getUriageRirekiList("r-KK01-00001");

		// check
		assertEquals(2, actual.getList().size());
		assertEquals(new EigyoDate(2017, 8, 20), actual.getNewest().getKeijoDate());
	}
}
