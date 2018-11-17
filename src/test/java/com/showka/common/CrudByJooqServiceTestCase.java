package com.showka.common;

import javax.sql.DataSource;

import org.jooq.DSLContext;
import org.jooq.conf.ParamType;
import org.jooq.conf.RenderKeywordStyle;
import org.jooq.conf.Settings;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.system.ExecuteListenerExtend;

public abstract class CrudByJooqServiceTestCase extends PersistenceTestCase {

	@Autowired
	protected DSLContext create;

	@Autowired
	protected DataSource datasource;

	@Before
	public void before() {
		// set event listener
		create.configuration().set(new DefaultExecuteListenerProvider(new ExecuteListenerExtend()));
		// settings
		Settings settings = create.settings();
		settings.withParamType(ParamType.INLINED);
		settings.withRenderKeywordStyle(RenderKeywordStyle.UPPER);
	}
}
