package com.showka.service.query.u05;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.u05.Uriage;
import com.showka.entity.MKokyaku;
import com.showka.entity.RUriage;
import com.showka.entity.RUriagePK;
import com.showka.entity.TUriage;
import com.showka.entity.TUriagePK;
import com.showka.repository.i.RUriageRepository;
import com.showka.service.crud.u01.i.KokyakuCrud;
import com.showka.service.crud.u05.i.UriageRirekiMeisaiCrud;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class UriageRirekiQueryImplTest2 extends SimpleTestCase {

	@Tested
	private UriageRirekiQueryImpl service;

	@Injectable
	private RUriageRepository repo;

	@Injectable
	private KokyakuCrud kokyakuCrud;

	@Injectable
	private UriageRirekiMeisaiCrud uriageRirekiMeisaiCrud;

	@Test
	public void test_buildBy_01() {
		// input
		// 売上履歴
		RUriage e = new RUriage();
		e.setHanbaiKubun("00");
		e.setRecordId("r-KK01-00001-20180820");
		e.setShohizeiritsu(0.08);
		e.setUriageDate(new Date());
		RUriagePK rpk = new RUriagePK();
		rpk.setKeijoDate(new Date());
		e.setPk(rpk);
		// 顧客
		MKokyaku kokyaku = new MKokyaku();
		kokyaku.setCode("KK01");
		// 売上
		TUriage u = new TUriage();
		u.setKokyaku(kokyaku);
		TUriagePK upk = new TUriagePK();
		upk.setDenpyoNumber("00001");
		u.setPk(upk);
		e.setUriage(u);
		// expect
		new Expectations() {
			{
				kokyakuCrud.getDomain("KK01");
				uriageRirekiMeisaiCrud.getDomainList(e.getRecordId());
			}
		};
		// do
		List<Uriage> actual = service.buildBy(Arrays.asList(e));
		// verify
		new Verifications() {
			{
				kokyakuCrud.getDomain("KK01");
				times = 1;
				uriageRirekiMeisaiCrud.getDomainList(e.getRecordId());
				times = 1;
			}
		};
		// check
		assertEquals(1, actual.size());
		assertEquals("r-KK01-00001-20180820", actual.get(0).getRecordId());
	}
}
