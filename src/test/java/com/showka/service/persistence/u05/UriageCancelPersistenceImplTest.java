package com.showka.service.persistence.u05;

import java.util.ArrayList;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.PersistenceTestCase;
import com.showka.domain.builder.UriageBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u05.Uriage;
import com.showka.domain.u05.UriageMeisai;
import com.showka.entity.CUriage;
import com.showka.kubun.HanbaiKubun;
import com.showka.repository.i.CUriageRepository;
import com.showka.system.EmptyProxy;
import com.showka.value.EigyoDate;
import com.showka.value.TaxRate;

public class UriageCancelPersistenceImplTest extends PersistenceTestCase {

	@Autowired
	private UriageCancelPersistenceImpl service;

	@Autowired
	private CUriageRepository repo;

	/** 売上01. */
	public static final Uriage uriage01;
	static {
		UriageBuilder b = new UriageBuilder();
		ArrayList<UriageMeisai> meisai = new ArrayList<UriageMeisai>();
		Kokyaku kokyaku = EmptyProxy.domain(Kokyaku.class);
		kokyaku.setRecordId("dummy");
		uriage01 = b.withKokyaku(kokyaku)
				.withDenpyoNumber("00001")
				.withUriageDate(new EigyoDate(2017, 8, 20))
				.withKeijoDate(new EigyoDate(2017, 8, 20))
				.withHanbaiKubun(HanbaiKubun.現金)
				.withShohizeiritsu(new TaxRate(0.08))
				.withUriageMeisai(meisai)
				.withRecordId("KK01-00001")
				.build();
	}

	/**
	 * save.
	 */
	@Test
	public void test01_save() throws Exception {
		// table
		super.deleteAll(C_URIAGE);
		// do
		service.save(uriage01);
		// check
		CUriage actual = repo.getOne("KK01-00001");
		assertNotNull(actual);
	}
}
