package com.showka.service.search.u07;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.CrudServiceTestCase;
import com.showka.domain.Kokyaku;
import com.showka.domain.Seikyu;
import com.showka.domain.builder.KokyakuBuilder;
import com.showka.domain.builder.SeikyuBuilder;
import com.showka.entity.TSeikyu;
import com.showka.entity.TSeikyuPK;
import com.showka.repository.i.TSeikyuRepository;
import com.showka.service.crud.u07.i.SeikyuCrudService;
import com.showka.value.EigyoDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class SeikyuSearchServiceImplTest extends CrudServiceTestCase {

	@Tested
	@Injectable
	private SeikyuSearchServiceImpl service;

	@Autowired
	@Injectable
	private TSeikyuRepository repo;

	@Injectable
	private SeikyuCrudService seikyuCrudService;

	/** 請求. */
	private static final Object[] T_SEIKYU_01 = { "r-KK01", d("20170101"), d("20170201"), "r-KK01-20170101" };
	private static final Object[] T_SEIKYU_02 = { "r-KK01", d("20170201"), d("20170301"), "r-KK01-20170201" };

	@Test
	public void test01_getAllOf() throws Exception {
		// input
		// 請求日
		EigyoDate seikyuDate = new EigyoDate();
		// 請求 pk
		TSeikyuPK pk = new TSeikyuPK();
		pk.setKokyakuId("r-KK01");
		pk.setSeikyuDate(seikyuDate.toDate());
		// 請求 entity
		TSeikyu seikyu = new TSeikyu();
		seikyu.setPk(pk);
		// 請求 entity list
		List<TSeikyu> seikyuList = new ArrayList<TSeikyu>();
		seikyuList.add(seikyu);
		// 顧客
		KokyakuBuilder kb = new KokyakuBuilder();
		kb.withCode("KK01");
		Kokyaku kokyaku = kb.build();
		// 請求 domain
		SeikyuBuilder sb = new SeikyuBuilder();
		sb.withKokyaku(kokyaku);
		sb.withSeikyuDate(seikyuDate);
		Seikyu seikyuDomain = sb.build();
		// expect
		new Expectations() {
			{
				service.getAllEntitiesOf(kokyaku);
				result = seikyuList;
				seikyuCrudService.getDomain(pk);
				result = seikyuDomain;
			}
		};
		// do
		List<Seikyu> actual = service.getAllOf(kokyaku);
		// verify
		new Verifications() {
			{
				seikyuCrudService.getDomain(pk);
				times = 1;
			}
		};
		// check
		assertEquals(1, actual.size());
		assertEquals(seikyuDomain, actual.get(0));
	}

	@Test
	public void test02_getAllEntitiesOf() throws Exception {
		// database
		super.deleteAndInsert(T_SEIKYU, T_SEIKYU_COLUMN, T_SEIKYU_01, T_SEIKYU_02);
		// input
		KokyakuBuilder kb = new KokyakuBuilder();
		kb.withRecordId("r-KK01");
		Kokyaku kokyaku = kb.build();
		// do
		List<TSeikyu> actual = service.getAllEntitiesOf(kokyaku);
		// check
		assertEquals(2, actual.size());
	}

}