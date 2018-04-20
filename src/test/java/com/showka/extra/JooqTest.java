package com.showka.extra;

import static org.jooq.impl.DSL.*;

import org.jooq.Record1;
import org.jooq.Result;
import org.junit.Test;

import com.showka.common.CrudByJooqServiceTestCase;

public class JooqTest extends CrudByJooqServiceTestCase {

	/** 部署01. */
	private static final Object[] M_BUSHO_01 = { "BS01", "01", "01", "部署01", "BS01" };

	/** 部署02. */
	private static final Object[] M_BUSHO_02 = { "BS02", "99", "02", "部署02", "BS02" };

	@Test
	public void test00_connection() throws Exception {
		System.out.println(datasource.getConnection().getMetaData());
		boolean actual = datasource.getConnection().isClosed();
		assertFalse(actual);
	}

	@Test
	public void test01_connection() throws Exception {
		Result<Record1<Integer>> r = create.selectOne().fetch();
		Integer actual = r.get(0).get("one", Integer.class);
		assertEquals(1, actual.intValue());
	}

	@Test
	public void test02_select_with_string() throws Exception {
		super.deleteAndInsert(M_BUSHO, M_BUSHO_COLUMN, M_BUSHO_01, M_BUSHO_02);
		Result<Record1<Object>> actual = create.select(field("m_busho.name")).from(table("m_busho")).fetch();
		assertEquals(2, actual.size());
	}
}
