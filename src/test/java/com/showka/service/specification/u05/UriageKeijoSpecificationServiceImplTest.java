package com.showka.service.specification.u05;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.CrudServiceTestCase;
import com.showka.domain.Busho;
import com.showka.domain.Kokyaku;
import com.showka.domain.Uriage;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.builder.KokyakuBuilder;
import com.showka.domain.builder.UriageBuilder;
import com.showka.value.EigyoDate;
import com.showka.value.TheDate;

public class UriageKeijoSpecificationServiceImplTest extends CrudServiceTestCase {

	@Autowired
	private UriageKeijoSpecificationServiceImpl service;

	@Test
	public void test01_isKeijoZumi() throws Exception {

		// input busho
		BushoBuilder bb = new BushoBuilder();
		bb.withEigyoDate(new EigyoDate(2017, 8, 20));
		Busho shukanBusho = bb.build();

		// input kokyaku
		KokyakuBuilder kb = new KokyakuBuilder();
		kb.withShukanBusho(shukanBusho);
		Kokyaku kokyaku = kb.build();

		// input uriage
		UriageBuilder ub = new UriageBuilder();
		ub.withKeijoDate(new TheDate(2017, 8, 19));
		ub.withKokyaku(kokyaku);
		Uriage uriage = ub.build();

		// do
		boolean actual = service.isKeijoZumi(uriage);

		// check
		assertEquals(true, actual);
	}

	@Test
	public void test02_isKeijoZumi() throws Exception {

		// input busho
		BushoBuilder bb = new BushoBuilder();
		bb.withEigyoDate(new EigyoDate(2017, 8, 19));
		Busho shukanBusho = bb.build();

		// input kokyaku
		KokyakuBuilder kb = new KokyakuBuilder();
		kb.withShukanBusho(shukanBusho);
		Kokyaku kokyaku = kb.build();

		// input uriage
		UriageBuilder ub = new UriageBuilder();
		ub.withKeijoDate(new TheDate(2017, 8, 19));
		ub.withKokyaku(kokyaku);
		Uriage uriage = ub.build();

		// do
		boolean actual = service.isKeijoZumi(uriage);

		// check
		assertEquals(false, actual);
	}

}
