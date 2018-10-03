package com.showka.service.search.u07;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jooq.DSLContext;
import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.Busho;
import com.showka.domain.Kokyaku;
import com.showka.domain.Seikyu;
import com.showka.domain.builder.KokyakuBuilder;
import com.showka.domain.builder.SeikyuBuilder;
import com.showka.entity.TSeikyu;
import com.showka.entity.TSeikyuMeisai;
import com.showka.entity.TSeikyuMeisaiPK;
import com.showka.entity.TSeikyuPK;
import com.showka.repository.i.TSeikyuMeisaiRepository;
import com.showka.repository.i.TSeikyuRepository;
import com.showka.service.crud.u07.i.SeikyuCrudService;
import com.showka.table.public_.tables.records.T_SEIKYU_RECORD;
import com.showka.value.EigyoDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;

/**
 * 請求SearchService Test.
 * 
 * <pre>
 * テストの一部は「SeikyuSearchServiceImplTest2」に移譲。
 * </pre>
 */
public class SeikyuSearchServiceImplTest extends SimpleTestCase {

	@Tested
	@Injectable
	private SeikyuSearchServiceImpl service;

	@Injectable
	private TSeikyuRepository repo;

	@Injectable
	private TSeikyuMeisaiRepository meisaiRepo;

	@Injectable
	private SeikyuCrudService seikyuCrudService;

	@Injectable
	private DSLContext create;

	@Test
	public void test_getAllOf_01() throws Exception {
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
	public void test_GetAllOfBusho_01(@Mocked Busho busho, @Mocked T_SEIKYU_RECORD seikyuRecord, @Mocked Seikyu seikyu)
			throws Exception {
		// input
		List<T_SEIKYU_RECORD> records = new ArrayList<>();
		records.add(seikyuRecord);
		// expect
		new Expectations() {
			{
				service.getAllRecordsOf(busho);
				result = records;
				seikyuRecord.getRecordId();
				result = "r-001";
				seikyuCrudService.getDomain("r-001");
				result = seikyu;
			}
		};
		// do
		List<Seikyu> actual = service.getAllOf(busho);
		// assert
		assertEquals(1, actual.size());
		assertEquals(seikyu, actual.get(0));
	}

	@Test
	public void test_GetHistoryOf_01() throws Exception {
		// input
		// 売掛ID
		String urikakeId = "001";
		// mock
		// 請求明細 entity
		TSeikyuMeisai e = new TSeikyuMeisai();
		String seikyuId = "r-KK01-20170820-001";
		TSeikyuMeisaiPK pk = new TSeikyuMeisaiPK();
		pk.setSeikyuId(seikyuId);
		e.setPk(pk);
		List<TSeikyuMeisai> entities = new ArrayList<>();
		entities.add(e);
		// 請求
		SeikyuBuilder sb = new SeikyuBuilder();
		Seikyu seikyu = sb.build();
		List<Seikyu> seikyuList = new ArrayList<>();
		seikyuList.add(seikyu);
		// expect
		new Expectations() {
			{
				service.getHistoryEntitiesOf(urikakeId);
				result = entities;
				seikyuCrudService.getDomain(seikyuId);
				result = seikyuList;
			}
		};
		// do
		List<Seikyu> actual = service.getHistoryOf(urikakeId);
		// verify
		new Verifications() {
			{
				service.getHistoryEntitiesOf(urikakeId);
				times = 1;
				seikyuCrudService.getDomain(seikyuId);
				times = 1;
			}
		};
		// check
		assertEquals(1, actual.size());
		assertEquals(seikyu, actual.get(0));
	}

	@Test
	public void test_GetNewestOf_01() throws Exception {
		// input
		String urikakeId = "r-001";
		// mock
		// 請求1
		SeikyuBuilder sb1 = new SeikyuBuilder();
		sb1.withSeikyuDate(new EigyoDate(2017, 8, 20));
		Seikyu seikyu1 = sb1.build();
		SeikyuBuilder sb2 = new SeikyuBuilder();
		sb2.withSeikyuDate(new EigyoDate(2017, 8, 21));
		Seikyu seikyu2 = sb2.build();
		List<Seikyu> seikyuList = new ArrayList<>();
		seikyuList.add(seikyu1);
		seikyuList.add(seikyu2);
		// expect
		new Expectations() {
			{
				service.getHistoryOf(urikakeId);
				result = seikyuList;
			}
		};
		// do
		Optional<Seikyu> actual = service.getNewestOf(urikakeId);
		// verify
		new Verifications() {
			{
				service.getHistoryOf(urikakeId);
				times = 1;
			}
		};
		// check
		assertEquals(seikyu2, actual.get());
	}
}
