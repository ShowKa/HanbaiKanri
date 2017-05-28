package com.showka.common;

import java.sql.Timestamp;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.commons.lang3.ArrayUtils;
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
import com.ninja_squad.dbsetup.operation.Insert.Builder;
import com.ninja_squad.dbsetup.operation.Operation;

import junit.framework.TestCase;

/**
 * Base Test Case Class for Crud1 Service Class.
 * 
 * @author ShowKa
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class ServiceCrudTestCase extends TestCase {

	/**
	 * data source.
	 */
	@Autowired
	protected DataSource dataSource;

	protected Destination dest;

	/**
	 * common columns name
	 */
	public static final String[] COMMON_COLUMN = { "create_user_id", "create_user_name", "create_timestamp",
			"update_user_id", "update_user_name", "update_timestamp", "version", "id" };

	/**
	 * common columns value
	 */
	public static final Object[] COMMON_VALUE = { "test_create", "test_create", new Timestamp(0), "test_update",
			"test_update", new Timestamp(0), 0 };

	/**
	 * db setup.
	 * 
	 * @param operations
	 */
	public void dbSetUp(Operation... operations) {
		Destination dest = new DataSourceDestination(dataSource);
		Operation ops = Operations.sequenceOf(operations);
		DbSetup dbSetup = new DbSetup(dest, ops);
		dbSetup.launch();
	}

	/**
	 * delete all records from table.
	 * 
	 * @param tableName
	 */
	public void deleteAll(String tableName) {
		Destination dest = new DataSourceDestination(dataSource);
		DeleteAll deleteAll = Operations.deleteAllFrom(tableName);
		Operation ops = Operations.sequenceOf(deleteAll);
		DbSetup dbSetup = new DbSetup(dest, ops);
		dbSetup.launch();
	}

	/**
	 * insert records;
	 * 
	 * @param tableName
	 * @param columns
	 * @param valuses
	 */
	public void insert(String tableName, String[] columns, Object[]... values) {
		Destination dest = new DataSourceDestination(dataSource);

		Builder builder = Operations.insertInto(tableName).columns(ArrayUtils.addAll(columns, COMMON_COLUMN));
		for (Object[] v : values) {
			builder.values(ArrayUtils.addAll(v, ArrayUtils.addAll(COMMON_VALUE, UUID.randomUUID().toString())));
		}

		Insert inserts = builder.build();

		Operation ops = Operations.sequenceOf(inserts);
		DbSetup dbSetup = new DbSetup(dest, ops);
		dbSetup.launch();
	}
}
