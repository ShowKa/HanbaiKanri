package com.showka.service.crud.u11;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.CrudServiceTestCase;
import com.showka.domain.Busho;
import com.showka.domain.Shohin;
import com.showka.domain.ShohinIdo;
import com.showka.domain.ShohinZaiko;
import com.showka.domain.ShohinZaiko.ShohinIdoOnDate;
import com.showka.entity.MShohin;
import com.showka.entity.TShohinZaiko;
import com.showka.repository.i.TShohinZaikoRepository;
import com.showka.service.crud.u11.i.ShohinIdoCrudService;
import com.showka.service.crud.z00.i.ShohinCrudService;
import com.showka.system.EmptyProxy;
import com.showka.value.TheDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Tested;
import mockit.Verifications;

public class ShohinZaikoCrudServiceImplTest extends CrudServiceTestCase {

	@Tested
	private ShohinZaikoCrudServiceImpl service;

	@Injectable
	@Autowired
	private TShohinZaikoRepository repo;

	@Injectable
	private ShohinIdoCrudService shohinIdoCrudService;

	@Injectable
	private ShohinCrudService shohinCrudService;

	/** 商品. */
	private static final Object[] M_SHOHIN_V01 = { "SH01", "商品01", 10, "r-SH01" };

	/** 商品在庫01. */
	private static final Object[] T_SHOHIN_ZAIKO_V01 = {
			"r-BS01",
			new TheDate(2017, 1, 1).toDate(),
			"r-SH01",
			100,
			"r-BS01-20170101-SH01" };

	/**
	 * 在庫データがなかった場合.
	 */
	@Test
	public void test01_GetShohinZaiko() throws Exception {
		// table
		super.deleteAll(T_SHOHIN_ZAIKO);
		// 部署
		Busho busho = EmptyProxy.domain(Busho.class);
		busho.setRecordId("r-BS01");
		// 日付
		TheDate date = new TheDate(2017, 1, 1);
		// 商品
		Shohin shohin = EmptyProxy.domain(Shohin.class);
		shohin.setRecordId("r-SH01");
		// do
		ShohinZaiko actual = service.getShohinZaiko(busho, date, shohin);
		// check
		assertEquals("r-BS01", actual.getBusho().getRecordId());
		assertEquals("r-SH01", actual.getShohin().getRecordId());
		assertEquals(new TheDate(2017, 1, 1), actual.getDate());
		assertEquals(0, actual.getNumber().intValue());
	}

	/**
	 * 在庫データがすでにある場合.
	 */
	@Test
	public void test02_GetShohinZaiko() throws Exception {
		// table
		super.deleteAndInsert(T_SHOHIN_ZAIKO, T_SHOHIN_ZAIKO_COLUMN, T_SHOHIN_ZAIKO_V01);
		// 部署
		Busho busho = EmptyProxy.domain(Busho.class);
		busho.setRecordId("r-BS01");
		// 日付
		TheDate date = new TheDate(2017, 1, 1);
		// 商品
		Shohin shohin = EmptyProxy.domain(Shohin.class);
		shohin.setRecordId("r-SH01");
		// mock
		new MockUp<ShohinIdoOnDate>() {
			@Mock
			public void $init(ShohinIdo ido, Shohin target) {
				assertEquals("r-001", ido.getRecordId());
				assertEquals("r-SH01", target.getRecordId());
			}
		};
		// expect
		List<ShohinIdo> _idoList = new ArrayList<ShohinIdo>();
		ShohinIdo ido = EmptyProxy.domain(ShohinIdo.class);
		ido.setRecordId("r-001");
		_idoList.add(ido);
		new Expectations() {
			{
				shohinIdoCrudService.getShohinIdoListInDate(busho, date, shohin);
				result = _idoList;
			}
		};
		// do
		ShohinZaiko actual = service.getShohinZaiko(busho, date, shohin);
		// verify
		new Verifications() {
			{
				shohinIdoCrudService.getShohinIdoListInDate(busho, date, shohin);
				times = 1;
			}
		};
		// check
		assertEquals("r-BS01", actual.getBusho().getRecordId());
		assertEquals("r-SH01", actual.getShohin().getRecordId());
		assertEquals(new TheDate(2017, 1, 1), actual.getDate());
		assertEquals(100, actual.getKurikoshiNumber().intValue());
	}

	@Test
	public void test03_GetShohinZaiko() throws Exception {
		// table
		super.deleteAndInsert(T_SHOHIN_ZAIKO, T_SHOHIN_ZAIKO_COLUMN, T_SHOHIN_ZAIKO_V01);
		super.deleteAndInsert(M_SHOHIN, M_SHOHIN_COLUMN, M_SHOHIN_V01);
		// 部署
		Busho busho = EmptyProxy.domain(Busho.class);
		busho.setRecordId("r-BS01");
		// 日付
		TheDate date = new TheDate(2017, 1, 1);
		// 商品
		Shohin shohin = EmptyProxy.domain(Shohin.class);
		shohin.setRecordId("r-SH01");
		// mock
		new MockUp<ShohinIdoOnDate>() {
			@Mock
			public void $init(ShohinIdo ido, Shohin target) {
				assertEquals("r-001", ido.getRecordId());
				assertEquals("r-SH01", target.getRecordId());
			}
		};
		new MockUp<TShohinZaiko>() {
			@Mock
			public MShohin getShohin() {
				MShohin e = new MShohin();
				e.setCode("SH01");
				return e;
			}
		};
		// expect
		List<ShohinIdo> _idoList = new ArrayList<ShohinIdo>();
		ShohinIdo ido = EmptyProxy.domain(ShohinIdo.class);
		ido.setRecordId("r-001");
		_idoList.add(ido);
		new Expectations() {
			{
				shohinCrudService.getDomain("SH01");
				result = shohin;
				shohinIdoCrudService.getShohinIdoListInDate(busho, date, shohin);
				result = _idoList;
			}
		};
		// do
		List<ShohinZaiko> actual = service.getShohinZaiko(busho, date);
		// verify
		new Verifications() {
			{
				shohinCrudService.getDomain("SH01");
				times = 1;
				shohinIdoCrudService.getShohinIdoListInDate(busho, date, shohin);
				times = 1;
			}
		};
		// check
		assertEquals(1, actual.size());
		assertEquals("r-BS01", actual.get(0).getBusho().getRecordId());
		assertEquals("r-SH01", actual.get(0).getShohin().getRecordId());
		assertEquals(new TheDate(2017, 1, 1), actual.get(0).getDate());
		assertEquals(100, actual.get(0).getKurikoshiNumber().intValue());

	}

}
