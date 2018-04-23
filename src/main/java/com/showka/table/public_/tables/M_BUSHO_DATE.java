/*
 * This file is generated by jOOQ.
*/
package com.showka.table.public_.tables;


import com.showka.table.public_.Indexes;
import com.showka.table.public_.Keys;
import com.showka.table.public_.Public;
import com.showka.table.public_.tables.records.M_BUSHO_DATE_RECORD;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class M_BUSHO_DATE extends TableImpl<M_BUSHO_DATE_RECORD> {

    private static final long serialVersionUID = -1966425618;

    /**
     * The reference instance of <code>PUBLIC.M_BUSHO_DATE</code>
     */
    public static final M_BUSHO_DATE m_busho_date = new M_BUSHO_DATE();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<M_BUSHO_DATE_RECORD> getRecordType() {
        return M_BUSHO_DATE_RECORD.class;
    }

    /**
     * The column <code>PUBLIC.M_BUSHO_DATE.BUSHO_ID</code>.
     */
    public final TableField<M_BUSHO_DATE_RECORD, String> BUSHO_ID = createField("BUSHO_ID", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.M_BUSHO_DATE.CREATE_FUNCTION</code>.
     */
    public final TableField<M_BUSHO_DATE_RECORD, String> CREATE_FUNCTION = createField("CREATE_FUNCTION", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.M_BUSHO_DATE.CREATE_TIMESTAMP</code>.
     */
    public final TableField<M_BUSHO_DATE_RECORD, Timestamp> CREATE_TIMESTAMP = createField("CREATE_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>PUBLIC.M_BUSHO_DATE.CREATE_USER_ID</code>.
     */
    public final TableField<M_BUSHO_DATE_RECORD, String> CREATE_USER_ID = createField("CREATE_USER_ID", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.M_BUSHO_DATE.RECORD_ID</code>.
     */
    public final TableField<M_BUSHO_DATE_RECORD, String> RECORD_ID = createField("RECORD_ID", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.M_BUSHO_DATE.UPDATE_FUNCTION</code>.
     */
    public final TableField<M_BUSHO_DATE_RECORD, String> UPDATE_FUNCTION = createField("UPDATE_FUNCTION", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.M_BUSHO_DATE.UPDATE_TIMESTAMP</code>.
     */
    public final TableField<M_BUSHO_DATE_RECORD, Timestamp> UPDATE_TIMESTAMP = createField("UPDATE_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>PUBLIC.M_BUSHO_DATE.UPDATE_USER_ID</code>.
     */
    public final TableField<M_BUSHO_DATE_RECORD, String> UPDATE_USER_ID = createField("UPDATE_USER_ID", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.M_BUSHO_DATE.VERSION</code>.
     */
    public final TableField<M_BUSHO_DATE_RECORD, Integer> VERSION = createField("VERSION", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("1", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>PUBLIC.M_BUSHO_DATE.EIGYO_DATE</code>.
     */
    public final TableField<M_BUSHO_DATE_RECORD, Timestamp> EIGYO_DATE = createField("EIGYO_DATE", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * Create a <code>PUBLIC.M_BUSHO_DATE</code> table reference
     */
    public M_BUSHO_DATE() {
        this(DSL.name("M_BUSHO_DATE"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.M_BUSHO_DATE</code> table reference
     */
    public M_BUSHO_DATE(String alias) {
        this(DSL.name(alias), m_busho_date);
    }

    /**
     * Create an aliased <code>PUBLIC.M_BUSHO_DATE</code> table reference
     */
    public M_BUSHO_DATE(Name alias) {
        this(alias, m_busho_date);
    }

    private M_BUSHO_DATE(Name alias, Table<M_BUSHO_DATE_RECORD> aliased) {
        this(alias, aliased, null);
    }

    private M_BUSHO_DATE(Name alias, Table<M_BUSHO_DATE_RECORD> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.PRIMARY_KEY_E, Indexes.UK_KGBIV56WTAT03W6I4V383FU7A_INDEX_E);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<M_BUSHO_DATE_RECORD> getPrimaryKey() {
        return Keys.CONSTRAINT_E;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<M_BUSHO_DATE_RECORD>> getKeys() {
        return Arrays.<UniqueKey<M_BUSHO_DATE_RECORD>>asList(Keys.CONSTRAINT_E, Keys.UK_KGBIV56WTAT03W6I4V383FU7A);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_BUSHO_DATE as(String alias) {
        return new M_BUSHO_DATE(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_BUSHO_DATE as(Name alias) {
        return new M_BUSHO_DATE(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public M_BUSHO_DATE rename(String name) {
        return new M_BUSHO_DATE(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public M_BUSHO_DATE rename(Name name) {
        return new M_BUSHO_DATE(name, null);
    }
}