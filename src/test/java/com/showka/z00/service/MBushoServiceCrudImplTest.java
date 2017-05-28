package com.showka.z00.service;

import java.sql.Timestamp;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.operation.DeleteAll;
import com.ninja_squad.dbsetup.operation.Insert;
import com.ninja_squad.dbsetup.operation.Operation;
import com.showka.entity.MBusho;

import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MBushoServiceCrudImplTest extends TestCase {

	@Autowired
	MBushoServiceCrudImpl service;

	@Autowired
	DataSource dataSource;

	public DeleteAll DELETE_ALL = Operations.deleteAllFrom("m_busho");

	public static final String[] COLUMN = { "code", "busho_kubun", "jigyo_kubun", "name" };

	public static final String[] COMMON_COLUMN = { "id", "create_user_id", "create_user_name", "create_timestamp",
			"update_user_id", "update_user_name", "update_timestamp", "version" };

	public static final Object[] COMMON_COLUMN_VALUE = { UUID.randomUUID().toString(), "test_create", "test_create",
			new Timestamp(0), "test_update", "test_update", new Timestamp(0), 0 };

	public static final Object[] VALUE = { "BS01", "00", "00", "部署01" };

	public static final Insert INSERT = Operations.insertInto("m_busho")
			.columns(ArrayUtils.addAll(COLUMN, COMMON_COLUMN))
			.values(ArrayUtils.addAll(VALUE, COMMON_COLUMN_VALUE))
			.build();

	@Before
	public void before() {
		Destination dest = new DataSourceDestination(dataSource);
		Operation ops = Operations.sequenceOf(DELETE_ALL, INSERT);
		DbSetup dbSetup = new DbSetup(dest, ops);
		dbSetup.launch();
	}

	@Test
	public void testFindById_01() throws Exception {
		MBusho actual = service.findById("BS01");
		assertEquals("部署01", actual.getName());
	}

}
