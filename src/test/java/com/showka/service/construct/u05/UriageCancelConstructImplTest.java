package com.showka.service.construct.u05;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.builder.KokyakuBuilder;
import com.showka.domain.builder.UriageBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u05.Uriage;
import com.showka.domain.z00.Busho;
import com.showka.entity.TUriagePK;
import com.showka.service.crud.u05.i.UriageCrud;
import com.showka.value.EigyoDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class UriageCancelConstructImplTest extends SimpleTestCase {

	@Tested
	private UriageCancelConstructImpl service;

	@Injectable
	private UriageCrud uriageCrud;

	@Test
	public void test_By_01() throws Exception {
		// input
		TUriagePK pk = new TUriagePK();
		// mock
		// 部署
		BushoBuilder bb = new BushoBuilder();
		EigyoDate eigyoDate = new EigyoDate(2018, 9, 20);
		bb.withEigyoDate(eigyoDate);
		Busho busho = bb.build();
		// 顧客
		KokyakuBuilder bk = new KokyakuBuilder();
		bk.withShukanBusho(busho);
		Kokyaku kokyaku = bk.build();
		// 売上
		UriageBuilder ub = new UriageBuilder();
		ub.withKokyaku(kokyaku);
		Uriage uriage = ub.build();
		// expect
		new Expectations() {
			{
				uriageCrud.getDomain(pk);
				result = uriage;
			}
		};
		// do
		Uriage actual = service.by(pk);
		// verify
		new Verifications() {
			{
				uriageCrud.getDomain(pk);
				times = 1;
			}
		};
		// check
		assertEquals(0, actual.getUriageMeisai().size());
		assertEquals(eigyoDate, actual.getKeijoDate());
	}

}
