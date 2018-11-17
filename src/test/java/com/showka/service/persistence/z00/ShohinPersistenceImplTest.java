package com.showka.service.persistence.z00;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.showka.common.PersistenceTestCase;
import com.showka.domain.z00.Shohin;
import com.showka.service.persistence.z00.ShohinPersistenceImpl;

public class ShohinPersistenceImplTest extends PersistenceTestCase {

	/** 商品01. */
	private static final Object[] M_SHOHIN_V01 = { "SH01", "商品01", 10, "SH01" };

	@Before
	public void before() {
		super.deleteAll(M_SHOHIN);
		super.insert(M_SHOHIN, M_SHOHIN_COLUMN, M_SHOHIN_V01);
	}

	@Autowired
	private ShohinPersistenceImpl service;

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
		Shohin domain = service.getDomain(code);
		// assert
		assertEquals(code, domain.getCode());
		assertEquals("商品01", domain.getName());
	}
}
