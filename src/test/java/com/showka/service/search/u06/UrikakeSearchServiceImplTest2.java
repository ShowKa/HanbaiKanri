package com.showka.service.search.u06;

import java.util.List;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.builder.KokyakuBuilder;
import com.showka.domain.builder.UrikakeBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u06.Urikake;
import com.showka.domain.z00.Busho;
import com.showka.entity.TUrikake;
import com.showka.repository.i.SUrikakeSeikyuDoneRepository;
import com.showka.repository.i.SUrikakeSeikyuNotYetRepository;
import com.showka.service.persistence.u06.i.UrikakePersistence;
import com.showka.value.EigyoDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class UrikakeSearchServiceImplTest2 extends SimpleTestCase {

	@Tested
	@Injectable
	private UrikakeSearchServiceImpl service;

	@Injectable
	private SUrikakeSeikyuNotYetRepository sUrikakeSeikyuNotYetRepository;

	@Injectable
	private SUrikakeSeikyuDoneRepository sUrikakeSeikyuDoneRepository;

	@Injectable
	private UrikakePersistence urikakePersistence;

	@Test
	public void teset_getUrikakeForSeikyu_01() {
		// input
		// 営業日
		EigyoDate date = new EigyoDate(2018, 8, 20);
		// 部署
		BushoBuilder bb = new BushoBuilder();
		bb.withEigyoDate(date);
		Busho busho = bb.build();
		// 顧客
		KokyakuBuilder kb = new KokyakuBuilder();
		kb.withShukanBusho(busho);
		Kokyaku kokyaku = kb.build();
		// mock
		// 売掛レコード1
		TUrikake u1 = new TUrikake();
		u1.setUriageId("r-001");
		// 売掛レコード2
		TUrikake u2 = new TUrikake();
		u2.setUriageId("r-002");
		// 売掛1
		UrikakeBuilder ub1 = new UrikakeBuilder();
		Urikake urikake1 = ub1.build();
		// 売掛2
		UrikakeBuilder ub2 = new UrikakeBuilder();
		Urikake urikake2 = ub2.build();
		// expect
		new Expectations() {
			{
				service.getSeikyuNotYetEntity(kokyaku);
				result = u1;
				service.getSeikyuDoneButDelayedEntity(kokyaku, date);
				result = u2;
				urikakePersistence.getDomain(u1.getUriageId());
				result = urikake1;
				urikakePersistence.getDomain(u2.getUriageId());
				result = urikake2;
			}
		};
		// do
		List<Urikake> actual = service.getUrikakeForSeikyu(kokyaku);
		// verify
		new Verifications() {
			{
				service.getSeikyuNotYetEntity(kokyaku);
				times = 1;
				service.getSeikyuDoneButDelayedEntity(kokyaku, date);
				times = 1;
				urikakePersistence.getDomain(u1.getUriageId());
				times = 1;
				urikakePersistence.getDomain(u2.getUriageId());
				times = 1;
			}
		};
		// check
		assertEquals(2, actual.size());
	}

	@Test
	public void teset_getUrikakeNotSettled_01() {
		// input
		// 顧客
		KokyakuBuilder kb = new KokyakuBuilder();
		Kokyaku kokyaku = kb.build();
		// mock
		// 売掛レコード1
		TUrikake u1 = new TUrikake();
		u1.setUriageId("r-001");
		// 売掛レコード2
		TUrikake u2 = new TUrikake();
		u2.setUriageId("r-002");
		// 売掛1
		UrikakeBuilder ub1 = new UrikakeBuilder();
		Urikake urikake1 = ub1.build();
		// 売掛2
		UrikakeBuilder ub2 = new UrikakeBuilder();
		Urikake urikake2 = ub2.build();
		// expect
		new Expectations() {
			{
				service.getSeikyuNotYetEntity(kokyaku);
				result = u1;
				service.getSeikyuDoneEntity(kokyaku);
				result = u2;
				urikakePersistence.getDomain(u1.getUriageId());
				result = urikake1;
				urikakePersistence.getDomain(u2.getUriageId());
				result = urikake2;
			}
		};
		// do
		List<Urikake> actual = service.getUrikakeNotSettled(kokyaku);
		// verify
		new Verifications() {
			{
				service.getSeikyuNotYetEntity(kokyaku);
				times = 1;
				service.getSeikyuDoneEntity(kokyaku);
				times = 1;
				urikakePersistence.getDomain(u1.getUriageId());
				times = 1;
				urikakePersistence.getDomain(u2.getUriageId());
				times = 1;
			}
		};
		// check
		assertEquals(2, actual.size());
	}

}
