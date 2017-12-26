package com.showka.service.crud.u05;

import java.math.BigDecimal;

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
	private static UriageRirekiMeisaiDomain meisai01;
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
		service.save(meisai01);

		// check
		RUriageMeisaiPK id = new RUriageMeisaiPK();
		id.setUriageId(meisai01.getUriageId());
		id.setMeisaiNumber(meisai01.getMeisaiNumber());
		RUriageMeisai actual = repo.findById(id).get();

		assertEquals(meisai01.getRecordId(), actual.getRecordId());
	}

}
