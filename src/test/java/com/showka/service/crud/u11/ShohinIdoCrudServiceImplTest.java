package com.showka.service.crud.u11;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.ServiceCrudTestCase;
import com.showka.domain.BushoDomain;
import com.showka.domain.ShohinDomain;
import com.showka.domain.ShohinIdoDomain;
import com.showka.domain.ShohinIdoMeisaiDomain;
import com.showka.domain.builder.ShohinIdoDomainBuilder;
import com.showka.entity.MBusho;
import com.showka.entity.TShohinIdo;
import com.showka.kubun.ShohinIdoKubun;
import com.showka.repository.i.TShohinIdoMeisaiRepository;
import com.showka.repository.i.TShohinIdoRepository;
import com.showka.service.crud.u11.i.ShohinIdoMeisaiCrudService;
import com.showka.service.crud.z00.i.BushoCrudService;
import com.showka.system.EmptyProxy;
import com.showka.value.TheDate;
import com.showka.value.TheTimestamp;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Tested;
import mockit.Verifications;

public class ShohinIdoCrudServiceImplTest extends ServiceCrudTestCase {

	@Tested
	private ShohinIdoCrudServiceImpl service;

	@Injectable
	@Autowired
	private TShohinIdoRepository repo;

	@Injectable
	@Autowired
	private TShohinIdoMeisaiRepository tShohinIdoMeisaiRepository;

	@Injectable
	private ShohinIdoMeisaiCrudService shohinIdoMeisaiCrudService;

	@Injectable
	private BushoCrudService bushoCrudService;

	/** 商品移動01 */
	private static final Object[] T_SHOHIN_IDO_V1 = {
			"r-001",
			"r-BS01",
			new TheDate(2017, 8, 20).toDate(),
			"10",
			new Date(),
			"r-001" };

	/** 商品移動02 */
	private static final Object[] T_SHOHIN_IDO_V2 = {
			"r-002",
			"r-BS01",
			new TheDate(2017, 8, 20).toDate(),
			"11",
			new Date(),
			"r-002" };

	/** 商品移動03 */
	private static final Object[] T_SHOHIN_IDO_V3 = {
			"r-003",
			"r-BS01",
			new TheDate(2017, 8, 21).toDate(),
			"10",
			new Date(),
			"r-003" };

	/** 商品移動明細01. */
	private static final Object[] T_SHOHIN_IDO_MEISAI_V01 = { "r-001", 1, "r-SH01", 10, "r-001-1" };

	/** 商品移動明細02. */
	private static final Object[] T_SHOHIN_IDO_MEISAI_V02 = { "r-002", 1, "r-SH01", 11, "r-002-1" };

	/** 商品移動明細03. */
	private static final Object[] T_SHOHIN_IDO_MEISAI_V03 = { "r-003", 1, "r-SH01", 12, "r-003-1" };

	/** 移動01. 2017/1/1 0時 売上による消費 */
	private static final ShohinIdoDomainBuilder ido01;
	static {
		List<ShohinIdoMeisaiDomain> meisai = new ArrayList<ShohinIdoMeisaiDomain>();
		ShohinIdoDomainBuilder b = new ShohinIdoDomainBuilder();
		b.withTimestamp(new TheTimestamp(2017, 1, 1, 0, 0, 0, 0));
		b.withKubun(ShohinIdoKubun.売上);
		b.withMeisai(meisai);
		b.withRecordId("r-001");
		b.withBusho(EmptyProxy.domain(BushoDomain.class));
		b.withDate(new TheDate(2017, 1, 1));
		ido01 = b;
	}

	// 部署 FIXME
	protected static final String M_BUSHO = "m_busho";
	protected static final String[] M_BUSHO_COLUMN = { "code", "busho_kubun", "jigyo_kubun", "name", "record_id" };
	private static final Object[] VALUE01 = { "BS01", "01", "01", "部署01", "r-BS01" };

	/**
	 * 新規登録
	 */
	@Test
	public void test01_Save() throws Exception {
		// table
		super.deleteAll(T_SHOHIN_IDO);
		// data
		ShohinIdoDomain shohinIdo = ido01.build();
		new Expectations() {
			{
				shohinIdoMeisaiCrudService.overrideList(anyString, shohinIdo.getMeisai());
			}
		};
		// do
		service.save(shohinIdo);
		// verify
		new Verifications() {
			{
				shohinIdoMeisaiCrudService.overrideList(anyString, shohinIdo.getMeisai());
				times = 1;
			}
		};
		// check
		TShohinIdo actual = repo.getOne(shohinIdo.getRecordId());
		assertEquals(shohinIdo.getRecordId(), actual.getRecordId());
	}

	/**
	 * 更新.
	 */
	@Test
	public void test02_Save() throws Exception {
		// table
		super.deleteAndInsert(T_SHOHIN_IDO, T_SHOHIN_IDO_COLUMN, T_SHOHIN_IDO_V1);
		// data
		ShohinIdoDomain shohinIdo = ido01.build();
		shohinIdo.setVersion(0);
		new Expectations() {
			{
				shohinIdoMeisaiCrudService.overrideList(anyString, shohinIdo.getMeisai());
			}
		};
		// do
		service.save(shohinIdo);
		// verify
		new Verifications() {
			{
				shohinIdoMeisaiCrudService.overrideList(anyString, shohinIdo.getMeisai());
				times = 1;
			}
		};
		// check
		TShohinIdo actual = repo.getOne(shohinIdo.getRecordId());
		assertEquals(shohinIdo.getRecordId(), actual.getRecordId());
	}

	@Test
	public void test03_delete() throws Exception {
		// table
		super.deleteAndInsert(T_SHOHIN_IDO, T_SHOHIN_IDO_COLUMN, T_SHOHIN_IDO_V1);
		// do
		service.delete("r-001", 0);
		// check
		assertFalse(repo.existsById("r-001"));
	}

	@Test
	public void test04_GetDomain() throws Exception {
		// table
		super.deleteAndInsert(T_SHOHIN_IDO, T_SHOHIN_IDO_COLUMN, T_SHOHIN_IDO_V1);
		// expect
		new Expectations() {
			{
				bushoCrudService.getDomain(anyString);
				result = EmptyProxy.domain(BushoDomain.class);
			}
		};
		// mock
		new MockUp<TShohinIdo>() {
			@Mock
			MBusho getBusho() {
				MBusho m = new MBusho();
				m.setCode("BS01");
				return m;
			}
		};
		// do
		ShohinIdoDomain actual = service.getDomain("r-001");
		// verify
		new Verifications() {
			{
				bushoCrudService.getDomain(anyString);
				times = 1;
			}
		};
		assertEquals("r-001", actual.getRecordId());
	}

	@Test
	public void test05_Exsists() throws Exception {
		// table
		super.deleteAndInsert(T_SHOHIN_IDO, T_SHOHIN_IDO_COLUMN, T_SHOHIN_IDO_V1);
		// do
		boolean actual = service.exsists("r-001");
		assertTrue(actual);
	}

	@Test
	public void test06_GetShohinIdoListInDate() throws Exception {
		// table
		super.deleteAndInsert(T_SHOHIN_IDO, T_SHOHIN_IDO_COLUMN, T_SHOHIN_IDO_V1, T_SHOHIN_IDO_V2, T_SHOHIN_IDO_V3);
		super.deleteAndInsert(T_SHOHIN_IDO_MEISAI, T_SHOHIN_IDO_MEISAI_COLUMN, T_SHOHIN_IDO_MEISAI_V01,
				T_SHOHIN_IDO_MEISAI_V02, T_SHOHIN_IDO_MEISAI_V03);
		super.deleteAndInsert(M_BUSHO, M_BUSHO_COLUMN, VALUE01);
		// data
		ShohinDomain shohin = EmptyProxy.domain(ShohinDomain.class);
		shohin.setRecordId("r-SH01");
		BushoDomain busho = EmptyProxy.domain(BushoDomain.class);
		busho.setRecordId("r-BS01");
		// expect
		new Expectations() {
			{
				bushoCrudService.getDomain(anyString);
				result = EmptyProxy.domain(BushoDomain.class);
			}
		};
		// mock
		new MockUp<TShohinIdo>() {
			@Mock
			MBusho getBusho() {
				MBusho m = new MBusho();
				m.setCode("BS01");
				return m;
			}
		};
		// do
		List<ShohinIdoDomain> actual = service.getShohinIdoListInDate(busho, new TheDate(2017, 8, 20), shohin);
		// verify
		new Verifications() {
			{
				bushoCrudService.getDomain(anyString);
				times = 2;
			}
		};
		// check
		assertEquals(2, actual.size());
		actual.stream().filter(ido -> {
			return ido.getRecordId().equals("r-002");
		}).forEach(ido -> {
			assertEquals(ShohinIdoKubun.売上訂正, ido.getKubun());
		});
	}

}