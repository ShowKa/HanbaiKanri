package com.showka.service.crud.u05;

import java.util.ArrayList;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.ServiceCrudTestCase;
import com.showka.domain.KokyakuDomain;
import com.showka.domain.UriageDomain;
import com.showka.domain.UriageMeisaiDomain;
import com.showka.domain.builder.UriageDomainBuilder;
import com.showka.entity.CUriage;
import com.showka.kubun.HanbaiKubun;
import com.showka.repository.i.CUriageRepository;
import com.showka.system.EmptyProxy;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;

public class UriageCancelCrudServiceImplTest extends ServiceCrudTestCase {

	@Autowired
	private UriageCancelCrudServiceImpl service;

	@Autowired
	private CUriageRepository repo;

	// 売上
	public static final UriageDomain uriage01;
	static {
		UriageDomainBuilder b = new UriageDomainBuilder();
		ArrayList<UriageMeisaiDomain> meisai = new ArrayList<UriageMeisaiDomain>();
		KokyakuDomain kokyaku = EmptyProxy.domain(KokyakuDomain.class);
		kokyaku.setRecordId("dummy");
		uriage01 = b.withKokyaku(kokyaku)
				.withDenpyoNumber("00001")
				.withUriageDate(new TheDate(2017, 8, 20))
				.withKeijoDate(new TheDate(2017, 8, 20))
				.withHanbaiKubun(HanbaiKubun.現金)
				.withShohizeiritsu(new TaxRate(0.08))
				.withUriageMeisai(meisai)
				.withRecordId("KK01-00001")
				.build();
	}

	@Test
	public void test01_save() throws Exception {

		// do
		service.save(uriage01);

		// check
		CUriage actual = repo.getOne("KK01-00001");
		assertNotNull(actual);
	}

}
