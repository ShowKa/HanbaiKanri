package com.showka.service.crud.z00;

import javax.persistence.Table;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.showka.common.ServiceCrudTestCase;
import com.showka.domain.ShohinDomain;
import com.showka.entity.MShohin;
import com.showka.service.crud.z00.MShohinCrudServiceImpl;

public class MShohinCrudServiceImplTest extends ServiceCrudTestCase {

	// m_busho
	private static final String M_SHOHIN = MShohin.class.getAnnotation(Table.class).name();
	private static final String[] M_SHOHIN_C = { "code", "name", "hyojun_tanka", "record_id" };
	private static final Object[] M_SHOHIN_V01 = { "SH01", "商品01", 10, "SH01" };

	/**
	 * Before
	 */
	@Override
	@Before
	public void before() {
		super.deleteAll(M_SHOHIN);
		super.insert(M_SHOHIN, M_SHOHIN_C, M_SHOHIN_V01);
	}

	@Autowired
	private MShohinCrudServiceImpl service;

	/**
	 * get domain test
	 * 
	 * <pre>
	 * getOne() 使ってるので@Transactionalないとエラーになる。
	 * </pre>
	 */
	@Test
	@Transactional
	public void test_get_domain() {

		// get
		String code = "SH01";
		ShohinDomain domain = service.getDomain(code);

		// assert
		assertEquals(code, domain.getCode());
		assertEquals("商品01", domain.getName());
	}
}
