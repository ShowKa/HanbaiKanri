package com.showka.service.crud.z00;

import javax.persistence.Table;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.ServiceCrudTestCase;
import com.showka.domain.BushoDomain;
import com.showka.domain.ShainDomain;
import com.showka.entity.MShain;
import com.showka.repository.i.MShainRepository;
import com.showka.service.crud.z00.i.BushoCrudService;
import com.showka.system.EmptyProxy;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class ShainCrudServiceImplTest extends ServiceCrudTestCase {

	@Tested
	private ShainCrudServiceImpl service;

	@Injectable
	@Autowired
	private MShainRepository repo;

	@Injectable
	private BushoCrudService bushoCrudService;

	// m_shain
	private static final String M_SHAIN = MShain.class.getAnnotation(Table.class).name();
	private static final String[] M_SHAIN_C = { "code", "name", "shozoku_busho_id", "record_id" };
	private static final Object[] M_SHAIN_V01 = { "user01", "社員01", "BS01", "r-user01" };

	@Before
	public void before() {
		super.deleteAll(M_SHAIN);
	}

	@Test
	public void test01_GetDomain() throws Exception {

		// insert
		super.insert(M_SHAIN, M_SHAIN_C, M_SHAIN_V01);

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
