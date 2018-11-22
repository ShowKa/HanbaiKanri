package com.showka.service.crud.z00;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.PersistenceTestCase;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shain;
import com.showka.repository.i.MShainRepository;
import com.showka.service.crud.z00.ShainCrudImpl;
import com.showka.service.crud.z00.i.BushoCrud;
import com.showka.system.EmptyProxy;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class ShainCrudImplTest extends PersistenceTestCase {

	@Tested
	private ShainCrudImpl service;

	@Injectable
	@Autowired
	private MShainRepository repo;

	@Injectable
	private BushoCrud bushoCrud;

	/** 社員01. */
	private static final Object[] M_SHAIN_V01 = { "user01", "社員01", "r-BS01", "r-user01" };

	/** 部署01. */
	private static final Object[] M_BUSHO_V01 = { "BS01", "01", "01", "部署01", "r-BS01" };

	@Test
	public void test01_GetDomain() throws Exception {
		// insert
		super.deleteAndInsert(M_SHAIN, M_SHAIN_COLUMN, M_SHAIN_V01);
		super.deleteAndInsert(M_BUSHO, M_BUSHO_COLUMN, M_BUSHO_V01);
		// data
		String code = "user01";
		Busho bushoMock = EmptyProxy.domain(Busho.class);
		// expectation
		new Expectations() {
			{
				bushoCrud.getDomain(anyString);
				result = bushoMock;
			}
		};
		// do
		Shain actual = service.getDomain(code);
		// verification
		new Verifications() {
			{
				bushoCrud.getDomain(anyString);
				times = 1;
			}
		};
		// check
		assertEquals("r-user01", actual.getRecordId());
		assertEquals(true, actual.getShozokuBusho().isEmpty());
	}
}
