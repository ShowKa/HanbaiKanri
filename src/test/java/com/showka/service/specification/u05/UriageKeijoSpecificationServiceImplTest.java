package com.showka.service.specification.u05;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.CrudServiceTestCase;
import com.showka.domain.BushoDomain;
import com.showka.domain.KokyakuDomain;
import com.showka.domain.UriageDomain;
import com.showka.domain.builder.BushoDomainBuilder;
import com.showka.domain.builder.KokyakuDomainBuilder;
import com.showka.domain.builder.UriageDomainBuilder;
import com.showka.value.EigyoDate;
import com.showka.value.TheDate;

public class UriageKeijoSpecificationServiceImplTest extends CrudServiceTestCase {

	@Autowired
	private UriageKeijoSpecificationServiceImpl service;

	@Test
	public void test01_isKeijoZumi() throws Exception {

		// input busho
		BushoDomainBuilder bb = new BushoDomainBuilder();
		bb.withEigyoDate(new EigyoDate(2017, 8, 20));
		BushoDomain shukanBusho = bb.build();

		// input kokyaku
		KokyakuDomainBuilder kb = new KokyakuDomainBuilder();
		kb.withShukanBusho(shukanBusho);
		KokyakuDomain kokyaku = kb.build();

		// input uriage
		UriageDomainBuilder ub = new UriageDomainBuilder();
		ub.withKeijoDate(new TheDate(2017, 8, 19));
		ub.withKokyaku(kokyaku);
		UriageDomain uriage = ub.build();

		// do
		boolean actual = service.isKeijoZumi(uriage);

		// check
		assertEquals(true, actual);
	}

	@Test
	public void test02_isKeijoZumi() throws Exception {

		// input busho
		BushoDomainBuilder bb = new BushoDomainBuilder();
		bb.withEigyoDate(new EigyoDate(2017, 8, 19));
		BushoDomain shukanBusho = bb.build();

		// input kokyaku
		KokyakuDomainBuilder kb = new KokyakuDomainBuilder();
		kb.withShukanBusho(shukanBusho);
		KokyakuDomain kokyaku = kb.build();

		// input uriage
		UriageDomainBuilder ub = new UriageDomainBuilder();
		ub.withKeijoDate(new TheDate(2017, 8, 19));
		ub.withKokyaku(kokyaku);
		UriageDomain uriage = ub.build();

		// do
		boolean actual = service.isKeijoZumi(uriage);

		// check
		assertEquals(false, actual);
	}

}
