package com.showka.service.persistence.u11;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.PersistenceTestCase;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.builder.ShohinIdoBuilder;
import com.showka.domain.u11.ShohinIdo;
import com.showka.domain.u11.ShohinIdoMeisai;
import com.showka.domain.z00.Busho;
import com.showka.entity.TShohinIdo;
import com.showka.kubun.ShohinIdoKubun;
import com.showka.repository.i.TShohinIdoRepository;
import com.showka.service.crud.u11.i.ShohinIdoCrud;
import com.showka.service.specification.u11.i.ShohinIdoSpecification;
import com.showka.system.exception.MinusZaikoException;
import com.showka.system.exception.MinusZaikoException.MinusZaiko;
import com.showka.value.EigyoDate;
import com.showka.value.TheTimestamp;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;

public class ShohinIdPersistenceImplTest extends PersistenceTestCase {

	@Tested
	private ShohinIdoPersistenceImpl service;

	@Injectable
	private ShohinIdoCrud shohinIdoCrud;

	@Autowired
	private TShohinIdoRepository repo;

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
	 * 商品移動仕様による商品移動.
	 *
	 * <pre>
	 * 入力：商品移動仕様 <br>
	 * 条件：エラーなし <br>
	 * 結果：登録成功
	 * 
	 * <pre>
	 */
	@Test
	public void test_ShohinIdo_01(@Mocked ShohinIdoSpecification specification) throws Exception {
		// table
		super.deleteAll(T_SHOHIN_IDO);
		// data
		ShohinIdo shohinIdo = ido01.build();
		// expect
		new Expectations() {
			{
				specification.getShohinIdo();
				result = shohinIdo;
			}
		};
		// do
		service.shohinIdo(specification);
		// check
		TShohinIdo actual = repo.getOne(shohinIdo.getRecordId());
		assertNotNull(actual);
	}

	/**
	 * 商品移動仕様による商品移動.
	 *
	 * <pre>
	 * 入力：商品移動仕様 <br>
	 * 条件：エラーあり <br>
	 * 結果：登録失敗
	 * 
	 * <pre>
	 */
	@Test(expected = MinusZaikoException.class)
	public void test_ShohinIdo_02(@Mocked ShohinIdoSpecification specification) throws Exception {
		// table
		super.deleteAll(T_SHOHIN_IDO);
		// data
		List<MinusZaiko> mze = new ArrayList<MinusZaiko>();
		// expect
		new Expectations() {
			{
				specification.ascertainSatisfaction();
				result = new MinusZaikoException(mze);
			}
		};
		// do
		service.shohinIdo(specification);
	}

	/**
	 * 商品移動仕様による商品移動（強制登録）.
	 *
	 * <pre>
	 * 入力：商品移動仕様 <br>
	 * 条件：エラーなし <br>
	 * 結果：登録成功
	 * 
	 * <pre>
	 */
	@Test
	public void test_shohinIdoForcibly_01(@Mocked ShohinIdoSpecification specification) throws Exception {
		// table
		super.deleteAll(T_SHOHIN_IDO);
		// data
		ShohinIdo shohinIdo = ido01.build();
		// expect
		new Expectations() {
			{
				specification.getShohinIdo();
				result = shohinIdo;
			}
		};
		// do
		service.shohinIdoForcibly(specification);
		// check
		TShohinIdo actual = repo.getOne(shohinIdo.getRecordId());
		assertNotNull(actual);
	}

}