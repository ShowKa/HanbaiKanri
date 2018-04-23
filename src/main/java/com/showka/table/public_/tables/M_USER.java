/*
 * This file is generated by jOOQ.
*/
package com.showka.table.public_.tables;


import com.showka.table.public_.Indexes;
import com.showka.table.public_.Keys;
import com.showka.table.public_.Public;
import com.showka.table.public_.tables.records.M_USER_RECORD;

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
public class M_USER extends TableImpl<M_USER_RECORD> {

    private static final long serialVersionUID = -675592809;

    /**
     * The reference instance of <code>PUBLIC.M_USER</code>
     */
    public static final M_USER m_user = new M_USER();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<M_USER_RECORD> getRecordType() {
        return M_USER_RECORD.class;
    }

    /**
     * The column <code>PUBLIC.M_USER.ID</code>.
     */
    public final TableField<M_USER_RECORD, Long> ID = createField("ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.M_USER.CREATE_FUNCTION</code>.
     */
    public final TableField<M_USER_RECORD, String> CREATE_FUNCTION = createField("CREATE_FUNCTION", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.M_USER.CREATE_TIMESTAMP</code>.
     */
    public final TableField<M_USER_RECORD, Timestamp> CREATE_TIMESTAMP = createField("CREATE_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>PUBLIC.M_USER.CREATE_USER_ID</code>.
     */
    public final TableField<M_USER_RECORD, String> CREATE_USER_ID = createField("CREATE_USER_ID", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.M_USER.RECORD_ID</code>.
     */
    public final TableField<M_USER_RECORD, String> RECORD_ID = createField("RECORD_ID", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.M_USER.UPDATE_FUNCTION</code>.
     */
    public final TableField<M_USER_RECORD, String> UPDATE_FUNCTION = createField("UPDATE_FUNCTION", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.M_USER.UPDATE_TIMESTAMP</code>.
     */
    public final TableField<M_USER_RECORD, Timestamp> UPDATE_TIMESTAMP = createField("UPDATE_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>PUBLIC.M_USER.UPDATE_USER_ID</code>.
     */
    public final TableField<M_USER_RECORD, String> UPDATE_USER_ID = createField("UPDATE_USER_ID", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.M_USER.VERSION</code>.
     */
    public final TableField<M_USER_RECORD, Integer> VERSION = createField("VERSION", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("1", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>PUBLIC.M_USER.PASSWORD</code>.
     */
    public final TableField<M_USER_RECORD, String> PASSWORD = createField("PASSWORD", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.M_USER.USERNAME</code>.
     */
    public final TableField<M_USER_RECORD, String> USERNAME = createField("USERNAME", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * Create a <code>PUBLIC.M_USER</code> table reference
     */
    public M_USER() {
        this(DSL.name("M_USER"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.M_USER</code> table reference
     */
    public M_USER(String alias) {
        this(DSL.name(alias), m_user);
    }

    /**
     * Create an aliased <code>PUBLIC.M_USER</code> table reference
     */
    public M_USER(Name alias) {
        this(alias, m_user);
    }

    private M_USER(Name alias, Table<M_USER_RECORD> aliased) {
        this(alias, aliased, null);
    }

    private M_USER(Name alias, Table<M_USER_RECORD> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.PRIMARY_KEY_88, Indexes.UK_BRMB54LD0GQSTOGMB812XW951_INDEX_8, Indexes.UK_E0AD43H00N5AJYKDHEM6ODJ79_INDEX_8);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<M_USER_RECORD> getPrimaryKey() {
        return Keys.CONSTRAINT_88;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<M_USER_RECORD>> getKeys() {
        return Arrays.<UniqueKey<M_USER_RECORD>>asList(Keys.CONSTRAINT_88, Keys.UK_E0AD43H00N5AJYKDHEM6ODJ79, Keys.UK_BRMB54LD0GQSTOGMB812XW951);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_USER as(String alias) {
        return new M_USER(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_USER as(Name alias) {
        return new M_USER(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public M_USER rename(String name) {
        return new M_USER(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public M_USER rename(Name name) {
        return new M_USER(name, null);
    }
}