package com.showka.service.persistence.u05;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.KokyakuBuilder;
import com.showka.domain.builder.UriageBuilder;
import com.showka.domain.builder.UriageRirekiBuilder;
import com.showka.domain.u05.Uriage;
import com.showka.domain.u05.UriageRireki;
import com.showka.entity.RUriagePK;
import com.showka.entity.TUriagePK;
import com.showka.service.construct.u05.i.UriageCancelConstruct;
import com.showka.service.crud.u05.i.UriageCrud;
import com.showka.service.persistence.u05.i.UriageCancelPersistence;
import com.showka.service.persistence.u05.i.UriageRirekiPersistence;
import com.showka.service.query.u05.i.UriageRirekiQuery;
import com.showka.value.EigyoDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class UriagePersistenceImplTest extends SimpleTestCase {

	@Tested
	@Injectable
	private UriagePersistenceImpl service;

	@Injectable
	private UriageCrud uriageCrud;

	@Injectable
	private UriageCancelConstruct UriageCancelConstruct;

	@Injectable
	private UriageRirekiPersistence uriageRirekiPersistence;

	@Injectable
	private UriageCancelPersistence uriageCancelPersistence;

	@Injectable
	private UriageRirekiQuery uriageRirekiQuery;

	@Test
	public void test_cancel_01() throws Exception {
		// input
		TUriagePK pk = new TUriagePK();
		// mock
		// 売上
		UriageBuilder ub = new UriageBuilder();
		Uriage uriage = ub.build();
		// expect
		new Expectations() {
			{
				UriageCancelConstruct.by(pk);
				result = uriage;
				times = 1;
				// save
				uriageCrud.save(uriage);
				// cancel
				uriageCancelPersistence.save(uriage);
			}
		};
		// do
		service.cancel(pk, 0);
		// check
		// verify
		new Verifications() {
			{
				// save
				uriageCrud.save(uriage);
				times = 1;
				// cancel
				uriageCancelPersistence.save(uriage);
				times = 1;
			}
		};
	}

	/**
	 * revert 計上済み売上に戻す.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_revert_01() throws Exception {
		// input
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId("r-KK01");
		pk.setDenpyoNumber("00001");
		// mock
		String uriageId = "r-KK01-00001";
		// 売上（削除用)
		UriageBuilder ub1 = new UriageBuilder();
		ub1.withKeijoDate(new EigyoDate(2018, 8, 19));
		ub1.withRecordId(uriageId);
		Uriage uriage = ub1.build();
		// 売上（復帰用)
		UriageBuilder ub2 = new UriageBuilder();
		ub2.withKeijoDate(new EigyoDate(2018, 9, 20));
		ub2.withKokyaku(new KokyakuBuilder().build());
		ub2.withDenpyoNumber("00001");
		Uriage newest = ub2.build();
		// 売上履歴
		List<Uriage> u = new ArrayList<Uriage>();
		u.add(newest);
		UriageRireki rireki = new UriageRirekiBuilder().withList(u).build();
		// 履歴pk
		RUriagePK rpk = new RUriagePK();
		rpk.setUriageId(uriageId);
		rpk.setKeijoDate(new EigyoDate(2018, 8, 19).toDate());
		// expect
		new Expectations() {
			{
				uriageCrud.getDomain(pk);
				result = uriage;
				uriageRirekiPersistence.delete(rpk);
				uriageRirekiQuery.get(uriageId);
				result = rireki;
				uriageCrud.save(newest);
			}
		};
		service.revert(pk, 0);
		// verify
		new Verifications() {
			{
				uriageCrud.getDomain(pk);
				times = 1;
				uriageRirekiPersistence.delete(rpk);
				times = 1;
				uriageRirekiQuery.get(uriageId);
				times = 1;
				uriageCrud.save(newest);
				times = 1;
			}
		};
	}
}
