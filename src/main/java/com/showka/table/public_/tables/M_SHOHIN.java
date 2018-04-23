/*
 * This file is generated by jOOQ.
*/
package com.showka.table.public_.tables;


import com.showka.table.public_.Indexes;
import com.showka.table.public_.Keys;
import com.showka.table.public_.Public;
import com.showka.table.public_.tables.records.M_SHOHIN_RECORD;

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
public class M_SHOHIN extends TableImpl<M_SHOHIN_RECORD> {

    private static final long serialVersionUID = -474395787;

    /**
     * The reference instance of <code>PUBLIC.M_SHOHIN</code>
     */
    public static final M_SHOHIN m_shohin = new M_SHOHIN();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<M_SHOHIN_RECORD> getRecordType() {
        return M_SHOHIN_RECORD.class;
    }

    /**
     * The column <code>PUBLIC.M_SHOHIN.CODE</code>.
     */
    public final TableField<M_SHOHIN_RECORD, String> CODE = createField("CODE", org.jooq.impl.SQLDataType.VARCHAR(4).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.M_SHOHIN.CREATE_FUNCTION</code>.
     */
    public final TableField<M_SHOHIN_RECORD, String> CREATE_FUNCTION = createField("CREATE_FUNCTION", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.M_SHOHIN.CREATE_TIMESTAMP</code>.
     */
    public final TableField<M_SHOHIN_RECORD, Timestamp> CREATE_TIMESTAMP = createField("CREATE_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>PUBLIC.M_SHOHIN.CREATE_USER_ID</code>.
     */
    public final TableField<M_SHOHIN_RECORD, String> CREATE_USER_ID = createField("CREATE_USER_ID", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.M_SHOHIN.RECORD_ID</code>.
     */
    public final TableField<M_SHOHIN_RECORD, String> RECORD_ID = createField("RECORD_ID", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.M_SHOHIN.UPDATE_FUNCTION</code>.
     */
    public final TableField<M_SHOHIN_RECORD, String> UPDATE_FUNCTION = createField("UPDATE_FUNCTION", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.M_SHOHIN.UPDATE_TIMESTAMP</code>.
     */
    public final TableField<M_SHOHIN_RECORD, Timestamp> UPDATE_TIMESTAMP = createField("UPDATE_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>PUBLIC.M_SHOHIN.UPDATE_USER_ID</code>.
     */
    public final TableField<M_SHOHIN_RECORD, String> UPDATE_USER_ID = createField("UPDATE_USER_ID", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.M_SHOHIN.VERSION</code>.
     */
    public final TableField<M_SHOHIN_RECORD, Integer> VERSION = createField("VERSION", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("1", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>PUBLIC.M_SHOHIN.HYOJUN_TANKA</code>.
     */
    public final TableField<M_SHOHIN_RECORD, Integer> HYOJUN_TANKA = createField("HYOJUN_TANKA", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.M_SHOHIN.NAME</code>.
     */
    public final TableField<M_SHOHIN_RECORD, String> NAME = createField("NAME", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * Create a <code>PUBLIC.M_SHOHIN</code> table reference
     */
    public M_SHOHIN() {
        this(DSL.name("M_SHOHIN"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.M_SHOHIN</code> table reference
     */
    public M_SHOHIN(String alias) {
        this(DSL.name(alias), m_shohin);
    }

    /**
     * Create an aliased <code>PUBLIC.M_SHOHIN</code> table reference
     */
    public M_SHOHIN(Name alias) {
        this(alias, m_shohin);
    }

    private M_SHOHIN(Name alias, Table<M_SHOHIN_RECORD> aliased) {
        this(alias, aliased, null);
    }

    private M_SHOHIN(Name alias, Table<M_SHOHIN_RECORD> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.PRIMARY_KEY_7, Indexes.UK_QQ0UP8I0X3HO6U8CL0QEMQ4QJ_INDEX_7);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<M_SHOHIN_RECORD> getPrimaryKey() {
        return Keys.CONSTRAINT_7;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<M_SHOHIN_RECORD>> getKeys() {
        return Arrays.<UniqueKey<M_SHOHIN_RECORD>>asList(Keys.CONSTRAINT_7, Keys.UK_QQ0UP8I0X3HO6U8CL0QEMQ4QJ);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_SHOHIN as(String alias) {
        return new M_SHOHIN(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_SHOHIN as(Name alias) {
        return new M_SHOHIN(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public M_SHOHIN rename(String name) {
        return new M_SHOHIN(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public M_SHOHIN rename(Name name) {
        return new M_SHOHIN(name, null);
    }
}