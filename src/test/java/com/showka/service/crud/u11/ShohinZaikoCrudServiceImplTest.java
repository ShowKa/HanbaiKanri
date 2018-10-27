package com.showka.service.crud.u11;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import com.showka.common.CrudServiceTestCase;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.builder.ShohinBuilder;
import com.showka.domain.u11.ShohinIdo;
import com.showka.domain.u11.ShohinZaiko;
import com.showka.domain.u11.ShohinZaiko.ShohinIdoOnDate;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shohin;
import com.showka.entity.MShohin;
import com.showka.entity.TShohinZaiko;
import com.showka.entity.TShohinZaikoPK;
import com.showka.repository.i.TShohinZaikoRepository;
import com.showka.service.crud.u11.i.ShohinIdoCrudService;
import com.showka.service.crud.z00.i.ShohinCrudService;
import com.showka.service.specification.z00.i.BushoDateBusinessService;
import com.showka.system.EmptyProxy;
import com.showka.value.EigyoDate;

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

	@Injectable
	private BushoDateBusinessService bushoDateBusinessService;

	/** 商品. */
	private static final Object[] M_SHOHIN_V01 = { "SH01", "商品01", 10, "r-SH01" };

	/** 商品在庫01. */
	private static final Object[] T_SHOHIN_ZAIKO_V01 = {
			"r-BS01",
			new EigyoDate(2017, 1, 1).toDate(),
			"r-SH01",
			100,
			"r-BS01-20170101-SH01" };

	/** 商品在庫02(ゼロ在庫). */
	private static final Object[] T_SHOHIN_ZAIKO_V02 = {
			"r-BS01",
			new EigyoDate(2017, 1, 1).toDate(),
			"r-SH01",
			0,
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
		EigyoDate date = new EigyoDate(2017, 1, 1);
		// 商品
		Shohin shohin = EmptyProxy.domain(Shohin.class);
		shohin.setRecordId("r-SH01");
		// do
		ShohinZaiko actual = service.getShohinZaiko(busho, date, shohin);
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
	public void test02_GetShohinZaiko() throws Exception {
		// table
		super.deleteAndInsert(T_SHOHIN_ZAIKO, T_SHOHIN_ZAIKO_COLUMN, T_SHOHIN_ZAIKO_V01);
		// 部署
		Busho busho = EmptyProxy.domain(Busho.class);
		busho.setRecordId("r-BS01");
		// 日付
		EigyoDate date = new EigyoDate(2017, 1, 1);
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
		assertEquals(new EigyoDate(2017, 1, 1), actual.getDate());
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
		EigyoDate date = new EigyoDate(2017, 1, 1);
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
		assertEquals(new EigyoDate(2017, 1, 1), actual.get(0).getDate());
		assertEquals(100, actual.get(0).getKurikoshiNumber().intValue());
	}

	/**
	 * 繰越.
	 * 
	 * <pre>
	 * 入力：商品在庫<br>
	 * 条件：在庫あり<br>
	 * 結果：次営業日の在庫データができる。
	 * 
	 * <pre>
	 */
	@Test
	public void test04_kurikoshi() throws Exception {
		// table
		super.deleteAndInsert(T_SHOHIN_ZAIKO, T_SHOHIN_ZAIKO_COLUMN, T_SHOHIN_ZAIKO_V01);
		super.deleteAndInsert(M_SHOHIN, M_SHOHIN_COLUMN, M_SHOHIN_V01);
		// input
		// 部署
		BushoBuilder bb = new BushoBuilder();
		bb.withRecordId("r-BS01");
		Busho busho = bb.build();
		// 営業日
		EigyoDate eigyoDate = new EigyoDate(2017, 1, 1);
		EigyoDate nextEigyoDate = new EigyoDate(2017, 1, 2);
		// 商品
		ShohinBuilder sb = new ShohinBuilder();
		sb.withCode("SH01");
		sb.withRecordId("r-SH01");
		Shohin shohin = sb.build();
		new Expectations() {
			{
				bushoDateBusinessService.getNext(busho, eigyoDate);
				result = nextEigyoDate;
				shohinCrudService.getDomain("SH01");
				result = shohin;
			}
		};
		// do
		service.kurikoshi(busho, eigyoDate);
		// verify
		new Verifications() {
			{
				bushoDateBusinessService.getNext(busho, eigyoDate);
				times = 1;
				shohinCrudService.getDomain("SH01");
				times = 1;
			}
		};
		// check
		TShohinZaiko e = new TShohinZaiko();
		TShohinZaikoPK pk = new TShohinZaikoPK();
		pk.setBushoId("r-BS01");
		pk.setEigyoDate(nextEigyoDate.toDate());
		e.setPk(pk);
		Example<TShohinZaiko> example = Example.of(e);
		List<TShohinZaiko> actual = repo.findAll(example);
		assertEquals(1, actual.size());
		assertEquals(100, actual.get(0).getNumber().intValue());
	}

	/**
	 * 繰越.
	 * 
	 * <pre>
	 * 入力：商品在庫<br>
	 * 条件：在庫なし<br>
	 * 結果：次営業日の在庫データができない。
	 * 
	 * <pre>
	 */
	@Test
	public void test05_kurikoshi() throws Exception {
		// table
		super.deleteAndInsert(T_SHOHIN_ZAIKO, T_SHOHIN_ZAIKO_COLUMN, T_SHOHIN_ZAIKO_V02);
		super.deleteAndInsert(M_SHOHIN, M_SHOHIN_COLUMN, M_SHOHIN_V01);
		// input
		// 部署
		BushoBuilder bb = new BushoBuilder();
		bb.withRecordId("r-BS01");
		Busho busho = bb.build();
		// 営業日
		EigyoDate eigyoDate = new EigyoDate(2017, 1, 1);
		EigyoDate nextEigyoDate = new EigyoDate(2017, 1, 2);
		// 商品
		ShohinBuilder sb = new ShohinBuilder();
		sb.withCode("SH01");
		sb.withRecordId("r-SH01");
		Shohin shohin = sb.build();
		new Expectations() {
			{
				bushoDateBusinessService.getNext(busho, eigyoDate);
				result = nextEigyoDate;
				shohinCrudService.getDomain("SH01");
				result = shohin;
			}
		};
		// do
		service.kurikoshi(busho, eigyoDate);
		// verify
		new Verifications() {
			{
				bushoDateBusinessService.getNext(busho, eigyoDate);
				times = 1;
				shohinCrudService.getDomain("SH01");
				times = 1;
			}
		};
		// check
		TShohinZaiko e = new TShohinZaiko();
		TShohinZaikoPK pk = new TShohinZaikoPK();
		pk.setBushoId("r-BS01");
		pk.setEigyoDate(nextEigyoDate.toDate());
		e.setPk(pk);
		Example<TShohinZaiko> example = Example.of(e);
		List<TShohinZaiko> actual = repo.findAll(example);
		assertTrue(actual.isEmpty());
	}

	/**
	 * ゼロ在庫レコード登録.
	 * 
	 * <pre>
	 * 入力：部署、日付、商品
	 * 条件：在庫レコード未登録
	 * 結果：ゼロの在庫レコードが登録される
	 * </pre>
	 */
	@Test
	public void test06_saveZeroIfEmpty() throws Exception {
		// database
		super.deleteAll(T_SHOHIN_ZAIKO);
		// input
		// 部署
		Busho busho = new BushoBuilder().build();
		busho.setRecordId("r-BS01");
		// 日付
		EigyoDate date = new EigyoDate(2017, 1, 1);
		// 商品
		Shohin shohin = new ShohinBuilder().build();
		shohin.setRecordId("r-SH01");
		// do
		service.saveZeroIfEmpty(busho, date, shohin);
		// check
		TShohinZaikoPK zaikoId = new TShohinZaikoPK();
		zaikoId.setBushoId("r-BS01");
		zaikoId.setEigyoDate(date.toDate());
		zaikoId.setShohinId("r-SH01");
		TShohinZaiko actual = repo.getOne(zaikoId);
		assertEquals(0, actual.getNumber().intValue());
	}

	/**
	 * ゼロ在庫レコード登録.
	 * 
	 * <pre>
	 * 入力：部署、日付、商品
	 * 条件：在庫レコード登録済
	 * 結果：ゼロの在庫レコードが登録されない（既存のまま）
	 * </pre>
	 */
	@Test
	public void test07_saveZeroIfEmpty() throws Exception {
		// database
		super.deleteAndInsert(T_SHOHIN_ZAIKO, T_SHOHIN_ZAIKO_COLUMN, T_SHOHIN_ZAIKO_V01);
		// input
		// 部署
		Busho busho = new BushoBuilder().build();
		busho.setRecordId("r-BS01");
		// 日付
		EigyoDate date = new EigyoDate(2017, 1, 1);
		// 商品
		Shohin shohin = new ShohinBuilder().build();
		shohin.setRecordId("r-SH01");
		// do
		service.saveZeroIfEmpty(busho, date, shohin);
		// check
		TShohinZaikoPK zaikoId = new TShohinZaikoPK();
		zaikoId.setBushoId("r-BS01");
		zaikoId.setEigyoDate(date.toDate());
		zaikoId.setShohinId("r-SH01");
		TShohinZaiko actual = repo.getOne(zaikoId);
		assertEquals(100, actual.getNumber().intValue());
	}
}
