package com.showka.service.query.u11;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.u11.ShohinZaiko;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shohin;
import com.showka.entity.TShohinZaikoPK;
import com.showka.repository.i.TShohinZaikoRepository;
import com.showka.service.crud.u11.i.ShohinZaikoCrud;
import com.showka.system.EmptyProxy;
import com.showka.value.EigyoDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class ShohinZaikoQueryImplTest2 extends SimpleTestCase {

	@Tested
	private ShohinZaikoQueryImpl service;

	@Injectable
	private TShohinZaikoRepository repo;

	@Injectable
	private ShohinZaikoCrud shohinZaikoCrud;

	/**
	 * 在庫データがなかった場合.
	 */
	@Test
	public void test01_get_01() throws Exception {
		// input
		// 部署
		Busho busho = EmptyProxy.domain(Busho.class);
		busho.setRecordId("r-BS01");
		// 日付
		EigyoDate date = new EigyoDate(2017, 1, 1);
		// 商品
		Shohin shohin = EmptyProxy.domain(Shohin.class);
		shohin.setRecordId("r-SH01");
		// pk
		TShohinZaikoPK pk = new TShohinZaikoPK();
		pk.setBushoId("r-BS01");
		pk.setEigyoDate(date.toDate());
		pk.setShohinId("r-SH01");
		// expect
		new Expectations() {
			{
				shohinZaikoCrud.exists(pk);
				result = false;
			}
		};
		// do
		ShohinZaiko actual = service.get(busho, date, shohin);
		// verify
		new Verifications() {
			{
				shohinZaikoCrud.exists(pk);
				times = 1;
				shohinZaikoCrud.getDomain((TShohinZaikoPK) any);
				times = 0;
			}
		};
		// check
		assertEquals("r-BS01", actual.getBusho().getRecordId());
		assertEquals("r-SH01", actual.getShohin().getRecordId());
		assertEquals(new EigyoDate(2017, 1, 1), actual.getDate());
		assertEquals(0, actual.getNumber().intValue());
	}

	/**
	 * 在庫データがすでにある場合.
	 */
	@Test
	public void test01_get_02() throws Exception {
		// input
		// 部署
		Busho busho = EmptyProxy.domain(Busho.class);
		busho.setRecordId("r-BS01");
		// 日付
		EigyoDate date = new EigyoDate(2017, 1, 1);
		// 商品
		Shohin shohin = EmptyProxy.domain(Shohin.class);
		shohin.setRecordId("r-SH01");
		// pk
		TShohinZaikoPK pk = new TShohinZaikoPK();
		pk.setBushoId("r-BS01");
		pk.setEigyoDate(date.toDate());
		pk.setShohinId("r-SH01");
		// mock
		ShohinZaiko shohinZaiko = EmptyProxy.domain(ShohinZaiko.class);
		// expect
		new Expectations() {
			{
				shohinZaikoCrud.exists(pk);
				result = true;
				shohinZaikoCrud.getDomain(pk);
				result = shohinZaiko;
			}
		};
		// do
		ShohinZaiko actual = service.get(busho, date, shohin);
		// verify
		new Verifications() {
			{
				shohinZaikoCrud.exists(pk);
				times = 1;
				shohinZaikoCrud.getDomain(pk);
				times = 1;
			}
		};
		// check
		assertEquals(shohinZaiko, actual);
	}
}
