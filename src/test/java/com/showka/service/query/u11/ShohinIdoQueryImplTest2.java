package com.showka.service.query.u11;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.builder.ShohinBuilder;
import com.showka.domain.builder.ShohinIdoBuilder;
import com.showka.domain.u11.ShohinIdo;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shohin;
import com.showka.entity.TShohinIdoMeisai;
import com.showka.entity.TShohinIdoMeisaiPK;
import com.showka.repository.i.TShohinIdoMeisaiRepository;
import com.showka.service.crud.u11.i.ShohinIdoCrud;
import com.showka.value.TheDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class ShohinIdoQueryImplTest2 extends SimpleTestCase {

	@Tested
	@Injectable
	private ShohinIdoQueryImpl service;

	@Injectable
	private ShohinIdoCrud shohinIdoCrud;

	@Injectable
	private TShohinIdoMeisaiRepository tShohinIdoMeisaiRepository;

	@Test
	public void test() {
		// input
		// 部署
		BushoBuilder bb = new BushoBuilder();
		bb.withCode("BS01");
		Busho busho = bb.build();
		// 移動日
		TheDate date = new TheDate(2017, 1, 1);
		// 商品
		ShohinBuilder sb = new ShohinBuilder();
		sb.withCode("SH01");
		Shohin shohin = sb.build();
		// mock
		// 移動1
		TShohinIdoMeisai im1 = new TShohinIdoMeisai();
		TShohinIdoMeisaiPK pk1 = new TShohinIdoMeisaiPK();
		pk1.setShohinIdoId("r-001");
		im1.setPk(pk1);
		// 移動2_1
		TShohinIdoMeisai im2_1 = new TShohinIdoMeisai();
		TShohinIdoMeisaiPK pk2_1 = new TShohinIdoMeisaiPK();
		pk2_1.setShohinIdoId("r-002");
		im2_1.setPk(pk2_1);
		// 移動2_2
		TShohinIdoMeisai im2_2 = new TShohinIdoMeisai();
		TShohinIdoMeisaiPK pk2_2 = new TShohinIdoMeisaiPK();
		pk2_2.setShohinIdoId("r-002");
		im2_2.setPk(pk2_2);
		// 移動list
		List<TShohinIdoMeisai> list = new ArrayList<>();
		list.add(im1);
		list.add(im2_1);
		list.add(im2_2);
		// expect
		new Expectations() {
			{
				service.findIdoMeisaiEntity(busho, date, shohin);
				result = list;
				shohinIdoCrud.getDomain("r-001");
				result = new ShohinIdoBuilder().build();
				shohinIdoCrud.getDomain("r-002");
				result = new ShohinIdoBuilder().build();
			}
		};
		// do
		List<ShohinIdo> actual = service.getShohinIdoListInDate(busho, date, shohin);
		// verify
		new Verifications() {
			{
				service.findIdoMeisaiEntity(busho, date, shohin);
				times = 1;
				shohinIdoCrud.getDomain("r-001");
				times = 1;
				shohinIdoCrud.getDomain("r-002");
				times = 1;
			}
		};
		// check
		assertEquals(2, actual.size());
	}

}
