package com.showka.service.crud.u05;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.ServiceCrudTestCase;
import com.showka.domain.ShohinDomain;
import com.showka.domain.UriageMeisaiDomain;
import com.showka.domain.builder.ShohinDomainBuilder;
import com.showka.domain.builder.UriageMeisaiDomainBuilder;
import com.showka.entity.TUriageMeisai;
import com.showka.entity.TUriageMeisaiPK;
import com.showka.repository.i.TUriageMeisaiRepository;

public class TUriageMeisaiCrudServiceImplTest extends ServiceCrudTestCase {

	@Autowired
	private TUriageMeisaiCrudServiceImpl service;

	@Autowired
	private TUriageMeisaiRepository repo;

	@Test
	public void test01_save() {

		// 商品ドメインダミー
		ShohinDomainBuilder sh = new ShohinDomainBuilder();
		sh.withRecordId("shohin_record_id");
		ShohinDomain shohinDomain = sh.build();

		// 売上明細主キー
		String uriageId = "KK01-TEST001";
		Integer meisaiNumber = 1;
		String recordID = uriageId + "-" + meisaiNumber;

		// 売上明細ドメイン
		UriageMeisaiDomainBuilder b = new UriageMeisaiDomainBuilder();
		b.withHanbaiNumber(10);
		b.withHanbaiTanka(BigDecimal.valueOf(200));
		b.withMeisaiNumber(meisaiNumber);
		b.withRecordId(recordID);
		b.withShohinDomain(shohinDomain);
		b.withUriageId(uriageId);
		b.withVersion(0);
		UriageMeisaiDomain domain = b.build();

		// do
		service.save(domain);

		// check
		TUriageMeisaiPK id = new TUriageMeisaiPK();
		id.setUriageId(uriageId);
		id.setMeisaiNumber(meisaiNumber);
		TUriageMeisai actual = repo.findById(id).get();

		assertEquals(recordID, actual.getRecordId());
	}

	@Test
	public void test02_delete() {

		// set pk
		TUriageMeisaiPK pk = new TUriageMeisaiPK();
		String uriageId = "KK01-00001";
		Integer meisaiNumber = 1;
		pk.setUriageId(uriageId);
		pk.setMeisaiNumber(meisaiNumber);

		// set version
		Integer version = 1;

		// check exists
		TUriageMeisai before = repo.findById(pk).get();
		assertNotNull(before);

		// do
		service.delete(pk, version);

		// check deleted
		Optional<TUriageMeisai> actual = repo.findById(pk);
		assertEquals(false, actual.isPresent());
	}

}
