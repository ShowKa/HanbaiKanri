package com.showka.service.crud.u05;

import java.util.ArrayList;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.ServiceCrudTestCase;
import com.showka.domain.KokyakuDomain;
import com.showka.domain.UriageCancelDomain;
import com.showka.domain.UriageDomain;
import com.showka.domain.UriageMeisaiDomain;
import com.showka.domain.builder.UriageDomainBuilder;
import com.showka.entity.CUriage;
import com.showka.entity.TUriage;
import com.showka.entity.TUriagePK;
import com.showka.kubun.HanbaiKubun;
import com.showka.repository.i.CUriageRepository;
import com.showka.service.crud.u05.i.UriageCrudService;
import com.showka.system.EmptyProxy;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Tested;
import mockit.Verifications;

public class UriageCancelCrudServiceImplTest extends ServiceCrudTestCase {

	@Tested
	private UriageCancelCrudServiceImpl service;

	@Injectable
	@Autowired
	private CUriageRepository repo;

	@Injectable
	private UriageCrudService uriageCrudService;

	/** 売上01. */
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

	/** キャンセル売上01. */
	private static final Object[] C_URIAGE_01 = { "KK01-00001", "r-KK01-00001" };

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

	/**
	 * get domain.
	 */
	@Test
	public void test02_getDomain() throws Exception {
		// table
		deleteAndInsert(C_URIAGE, C_URIAGE_COLUMN, C_URIAGE_01);
		// expect
		TUriagePK pk = new TUriagePK();
		pk.setDenpyoNumber("00001");
		pk.setKokyakuId("r-KK01");
		TUriage uriage = new TUriage();
		uriage.setPk(pk);
		new Expectations() {
			{
				uriageCrudService.getDomain(pk);
				result = new UriageDomainBuilder().build();
			}
		};
		// mock
		new MockUp<CUriage>() {
			@Mock
			public TUriage getUriage() {
				return uriage;
			}
		};
		// do
		UriageCancelDomain actual = service.getDomain("KK01-00001");
		// verify
		new Verifications() {
			{
				uriageCrudService.getDomain(pk);
				times = 1;
			}
		};
		// check
		assertEquals("r-KK01-00001", actual.getRecordId());
	}

}
