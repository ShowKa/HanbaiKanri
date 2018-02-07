package com.showka.service.crud.u17;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.ServiceCrudTestCase;
import com.showka.domain.BushoDomain;
import com.showka.entity.MBushoDate;
import com.showka.repository.i.MBushoDateRepository;
import com.showka.system.EmptyProxy;

public class BushoDateCrudServiceImplTest extends ServiceCrudTestCase {

	@Autowired
	private BushoDateCrudServiceImpl bushoDateCrudServiceImpl;

	@Autowired
	private MBushoDateRepository repo;

	/** 部署日付01. */
	private Object[] M_BUSHO_DATE_01 = { "r-BS01", d("20170101"), "r-BS01" };

	@Test
	public void test01_toNextEigyoDate() throws Exception {
		// insert DB
		super.deleteAndInsert(M_BUSHO_DATE, M_BUSHO_DATE_COLUMN, M_BUSHO_DATE_01);
		// data
		BushoDomain busho = EmptyProxy.domain(BushoDomain.class);
		busho.setRecordId("r-BS01");
		// do
		bushoDateCrudServiceImpl.toNextEigyoDate(busho);
		// check
		MBushoDate actual = repo.getOne("r-BS01");
		assertEquals(d("20170102"), actual.getEigyoDate());
	}
}
