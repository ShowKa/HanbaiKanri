package com.showka.service.persistence.u17;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.PersistenceTestCase;
import com.showka.domain.z00.Busho;
import com.showka.entity.MBushoDate;
import com.showka.repository.i.MBushoDateRepository;
import com.showka.service.persistence.u17.BushoDatePersistenceImpl;
import com.showka.system.EmptyProxy;

public class BushoDatePersistenceImplTest extends PersistenceTestCase {

	@Autowired
	private BushoDatePersistenceImpl bushoDatePersistenceImpl;

	@Autowired
	private MBushoDateRepository repo;

	/** 部署日付01. */
	private Object[] M_BUSHO_DATE_01 = { "r-BS01", d("20170101"), "r-BS01" };

	/**
	 * 営業日更新.
	 *
	 * <pre>
	 * 入力：部署<br>
	 * 条件：なし <br>
	 * 結果：busho_dateテーブルが次の営業日に更新される。
	 * 
	 * <pre>
	 */
	@Test
	@Transactional
	public void test01_toNextEigyoDate() throws Exception {
		// insert DB
		super.deleteAndInsert(M_BUSHO_DATE, M_BUSHO_DATE_COLUMN, M_BUSHO_DATE_01);
		// data
		Busho busho = EmptyProxy.domain(Busho.class);
		busho.setRecordId("r-BS01");
		// do
		bushoDatePersistenceImpl.toNextEigyoDate(busho);
		// check
		MBushoDate actual = repo.getOne("r-BS01");
		assertEquals(d("20170102"), actual.getEigyoDate());
	}
}
