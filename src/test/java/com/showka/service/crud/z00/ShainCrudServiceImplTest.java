package com.showka.service.crud.z00;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.CrudServiceTestCase;
import com.showka.domain.BushoDomain;
import com.showka.domain.ShainDomain;
import com.showka.repository.i.MShainRepository;
import com.showka.service.crud.z00.i.BushoCrudService;
import com.showka.system.EmptyProxy;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class ShainCrudServiceImplTest extends CrudServiceTestCase {

	@Tested
	private ShainCrudServiceImpl service;

	@Injectable
	@Autowired
	private MShainRepository repo;

	@Injectable
	private BushoCrudService bushoCrudService;

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
		BushoDomain bushoMock = EmptyProxy.domain(BushoDomain.class);
		// expectation
		new Expectations() {
			{
				bushoCrudService.getDomain(anyString);
				result = bushoMock;
			}
		};
		// do
		ShainDomain actual = service.getDomain(code);
		// verification
		new Verifications() {
			{
				bushoCrudService.getDomain(anyString);
				times = 1;
			}
		};
		// check
		assertEquals("r-user01", actual.getRecordId());
		assertEquals(true, actual.getShozokuBusho().isEmpty());
	}
}
