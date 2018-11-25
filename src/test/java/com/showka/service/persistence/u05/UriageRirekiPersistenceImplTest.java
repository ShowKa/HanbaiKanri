package com.showka.service.persistence.u05;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.PersistenceTestCase;
import com.showka.domain.builder.UriageBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u05.Uriage;
import com.showka.domain.u05.UriageMeisai;
import com.showka.entity.RUriage;
import com.showka.entity.RUriagePK;
import com.showka.kubun.HanbaiKubun;
import com.showka.repository.i.RUriageRepository;
import com.showka.service.crud.u01.i.KokyakuCrud;
import com.showka.service.crud.u05.i.UriageRirekiMeisaiCrud;
import com.showka.system.EmptyProxy;
import com.showka.value.EigyoDate;
import com.showka.value.TaxRate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

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

	@Test
	@SuppressWarnings("unchecked")
	public void test01_Save() throws Exception {
		// database
		super.deleteAll(R_URIAGE);
		// expect
		new Expectations() {
			{
				uriageRirekiMeisaiPersistence.overrideList(anyString, (List<UriageMeisai>) any);
			}
		};
		// do
		service.save(rUriage01);
		// verify
		new Verifications() {
			{
				uriageRirekiMeisaiPersistence.overrideList(anyString, (List<UriageMeisai>) any);
				times = 1;
			}
		};
		// check
		RUriagePK pk = new RUriagePK();
		pk.setUriageId("r-KK01-00001");
		pk.setKeijoDate(new EigyoDate(2017, 8, 20).toDate());
		RUriage actual = repo.getOne(pk);
		assertEquals("r-KK01-00001", actual.getPk().getUriageId());
		assertEquals(new EigyoDate(2017, 8, 20).toDate(), actual.getPk().getKeijoDate());
	}
}
