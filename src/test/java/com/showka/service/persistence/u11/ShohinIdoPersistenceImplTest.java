package com.showka.service.persistence.u11;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.builder.ShohinIdoBuilder;
import com.showka.domain.u11.ShohinIdo;
import com.showka.domain.u11.ShohinIdoMeisai;
import com.showka.domain.z00.Busho;
import com.showka.kubun.ShohinIdoKubun;
import com.showka.service.crud.u11.i.ShohinIdoCrud;
import com.showka.service.specification.u11.i.ShohinIdoSpecification;
import com.showka.value.EigyoDate;
import com.showka.value.TheTimestamp;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;

public class ShohinIdoPersistenceImplTest extends SimpleTestCase {

	@Tested
	private ShohinIdoPersistenceImpl service;

	@Injectable
	private ShohinIdoCrud shohinIdoCrud;

	/** 移動01. 2017/1/1 0時 売上による消費 */
	private static final ShohinIdoBuilder ido_save;
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
		ido_save = b;
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
		// data
		ShohinIdo shohinIdo = ido_save.build();
		// expect
		new Expectations() {
			{
				// ascertain
				specification.ascertainSatisfaction();
				// save
				specification.getShohinIdo();
				result = shohinIdo;
				shohinIdoCrud.save(shohinIdo);
			}
		};
		// do
		service.shohinIdo(specification);
		// verify
		new Verifications() {
			{
				// ascertain
				specification.ascertainSatisfaction();
				times = 1;
				// save
				specification.getShohinIdo();
				times = 1;
				shohinIdoCrud.save(shohinIdo);
				times = 1;
			}
		};
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
		// data
		ShohinIdo shohinIdo = ido_save.build();
		// expect
		new Expectations() {
			{
				// save
				specification.getShohinIdo();
				result = shohinIdo;
				shohinIdoCrud.save(shohinIdo);
			}
		};
		// do
		service.shohinIdoForcibly(specification);
		// verify
		new Verifications() {
			{
				// ascertain
				specification.ascertainSatisfaction();
				times = 0;
				// save
				specification.getShohinIdo();
				times = 1;
				shohinIdoCrud.save(shohinIdo);
				times = 1;
			}
		};
	}
}