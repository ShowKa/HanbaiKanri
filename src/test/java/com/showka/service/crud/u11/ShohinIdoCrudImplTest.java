package com.showka.service.crud.u11;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.PersistenceTestCase;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.builder.ShohinBuilder;
import com.showka.domain.builder.ShohinIdoBuilder;
import com.showka.domain.u11.ShohinIdo;
import com.showka.domain.u11.ShohinIdoMeisai;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shohin;
import com.showka.entity.TShohinIdo;
import com.showka.kubun.ShohinIdoKubun;
import com.showka.repository.i.TShohinIdoRepository;
import com.showka.service.crud.u11.i.ShohinIdoMeisaiCrud;
import com.showka.service.crud.z00.i.BushoCrud;
import com.showka.service.persistence.u11.i.ShohinZaikoPersistence;
import com.showka.value.EigyoDate;
import com.showka.value.TheTimestamp;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class ShohinIdoCrudImplTest extends PersistenceTestCase {

	@Tested
	private ShohinIdoCrudImpl service;

	@Autowired
	@Injectable
	private TShohinIdoRepository repo;

	@Injectable
	private ShohinIdoMeisaiCrud shohinIdoMeisaiCrud;

	@Injectable
	private BushoCrud bushoPersistence;

	@Injectable
	private ShohinZaikoPersistence shohinZaikoPersistence;

	/** 商品移動01 */
	private static final Object[] T_SHOHIN_IDO_V1 = {
			"r-001",
			"r-BS01",
			new EigyoDate(2017, 8, 20).toDate(),
			"10",
			new Date(),
			"r-001" };

	/** 部署 */
	protected static final Object[] M_BUSHO_01 = { "BS01", "00", "00", "部署01", "r-BS01" };

	/** 移動01. 2017/1/1 0時 売上による消費 */
	private static final ShohinIdoBuilder ido01;
	static {
		List<ShohinIdoMeisai> meisai = new ArrayList<ShohinIdoMeisai>();
		ShohinIdoBuilder b = new ShohinIdoBuilder();
		b.withTimestamp(new TheTimestamp(2017, 1, 1, 0, 0, 0, 0));
		b.withKubun(ShohinIdoKubun.売上);
		b.withMeisai(meisai);
		b.withRecordId("r-001");
		BushoBuilder bb = new BushoBuilder();
		bb.withRecordId("r-BS01");
		Busho busho = bb.build();
		b.withBusho(busho);
		b.withDate(new EigyoDate(2017, 1, 1));
		ido01 = b;
	}

	/**
	 * 新規登録
	 */
	@Test
	public void test_Save_01() throws Exception {
		// table
		super.deleteAll(T_SHOHIN_IDO);
		// data
		ShohinIdo shohinIdo = ido01.build();
		// do
		service.save(shohinIdo);
		// check
		TShohinIdo actual = repo.getOne(shohinIdo.getRecordId());
		assertEquals(shohinIdo.getRecordId(), actual.getRecordId());
	}

	/**
	 * 更新.
	 */
	@Test
	public void test_Save_02() throws Exception {
		// table
		super.deleteAndInsert(T_SHOHIN_IDO, T_SHOHIN_IDO_COLUMN, T_SHOHIN_IDO_V1);
		// data
		ShohinIdo shohinIdo = ido01.build();
		shohinIdo.setVersion(0);
		// do
		service.save(shohinIdo);
		// check
		TShohinIdo actual = repo.getOne(shohinIdo.getRecordId());
		assertEquals(shohinIdo.getRecordId(), actual.getRecordId());
	}

	/**
	 * ゼロ在庫登録呼び出し.
	 * 
	 * <pre>
	 * 入力：商品移動
	 * 条件：明細1つ含む
	 * 結果：ゼロ在庫呼び出しが1度呼び出される。
	 * </pre>
	 */
	@Test
	public void test_Save_03_ZeroZaiko(@Injectable ShohinIdoMeisai meisai) throws Exception {
		// table
		super.deleteAll(T_SHOHIN_IDO);
		// data
		List<ShohinIdoMeisai> list = new ArrayList<ShohinIdoMeisai>();
		list.add(meisai);
		ido01.withMeisai(list);
		ShohinIdo shohinIdo = ido01.build();
		// mock
		ShohinBuilder sb = new ShohinBuilder();
		sb.withCode("SH01");
		Shohin shohin = sb.build();
		new Expectations() {
			{
				meisai.getShohinDomain();
				result = shohin;
				shohinZaikoPersistence.saveZeroIfEmpty((Busho) any, (EigyoDate) any, shohin);
			}
		};
		// do
		service.save(shohinIdo);
		// verify
		new Verifications() {
			{
				shohinZaikoPersistence.saveZeroIfEmpty((Busho) any, (EigyoDate) any, shohin);
				times = 1;
			}
		};
	}

	@Test
	public void test_delete_01() throws Exception {
		// table
		super.deleteAndInsert(T_SHOHIN_IDO, T_SHOHIN_IDO_COLUMN, T_SHOHIN_IDO_V1);
		// input
		ShohinIdoBuilder sb = new ShohinIdoBuilder();
		sb.withRecordId("r-001");
		sb.withVersion(0);
		ShohinIdo shohinIdo = sb.build();
		// do
		assertTrue(repo.existsById("r-001"));
		service.delete(shohinIdo);
		// check
		assertFalse(repo.existsById("r-001"));
	}

	@Test
	public void test_GetDomain_01() throws Exception {
		// table
		super.deleteAndInsert(T_SHOHIN_IDO, T_SHOHIN_IDO_COLUMN, T_SHOHIN_IDO_V1);
		super.deleteAndInsert(M_BUSHO, M_BUSHO_COLUMN, M_BUSHO_01);
		ShohinIdo actual = service.getDomain("r-001");
		assertEquals("r-001", actual.getRecordId());
	}

	@Test
	public void test05_Exsists() throws Exception {
		// table
		super.deleteAndInsert(T_SHOHIN_IDO, T_SHOHIN_IDO_COLUMN, T_SHOHIN_IDO_V1);
		// do
		boolean actual = service.exists("r-001");
		assertTrue(actual);
	}
}