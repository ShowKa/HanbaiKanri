/*
 * This file is generated by jOOQ.
*/
package com.showka.table.public_.tables;


import com.showka.table.public_.Keys;
import com.showka.table.public_.Public;
import com.showka.table.public_.tables.records.T_SHOHIN_IDO_MEISAI_RECORD;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
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
public class T_SHOHIN_IDO_MEISAI extends TableImpl<T_SHOHIN_IDO_MEISAI_RECORD> {

    private static final long serialVersionUID = -1139505175;

    /**
     * The reference instance of <code>PUBLIC.T_SHOHIN_IDO_MEISAI</code>
     */
    public static final T_SHOHIN_IDO_MEISAI t_shohin_ido_meisai = new T_SHOHIN_IDO_MEISAI();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<T_SHOHIN_IDO_MEISAI_RECORD> getRecordType() {
        return T_SHOHIN_IDO_MEISAI_RECORD.class;
    }

    /**
     * The column <code>PUBLIC.T_SHOHIN_IDO_MEISAI.MEISAI_NUMBER</code>.
     */
    public final TableField<T_SHOHIN_IDO_MEISAI_RECORD, Integer> meisai_number = createField("MEISAI_NUMBER", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.T_SHOHIN_IDO_MEISAI.SHOHIN_IDO_ID</code>.
     */
    public final TableField<T_SHOHIN_IDO_MEISAI_RECORD, String> shohin_ido_id = createField("SHOHIN_IDO_ID", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.T_SHOHIN_IDO_MEISAI.CREATE_FUNCTION</code>.
     */
    public final TableField<T_SHOHIN_IDO_MEISAI_RECORD, String> create_function = createField("CREATE_FUNCTION", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.T_SHOHIN_IDO_MEISAI.CREATE_TIMESTAMP</code>.
     */
    public final TableField<T_SHOHIN_IDO_MEISAI_RECORD, LocalDateTime> create_timestamp = createField("CREATE_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP()", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>PUBLIC.T_SHOHIN_IDO_MEISAI.CREATE_USER_ID</code>.
     */
    public final TableField<T_SHOHIN_IDO_MEISAI_RECORD, String> create_user_id = createField("CREATE_USER_ID", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.T_SHOHIN_IDO_MEISAI.RECORD_ID</code>.
     */
    public final TableField<T_SHOHIN_IDO_MEISAI_RECORD, String> record_id = createField("RECORD_ID", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.T_SHOHIN_IDO_MEISAI.UPDATE_FUNCTION</code>.
     */
    public final TableField<T_SHOHIN_IDO_MEISAI_RECORD, String> update_function = createField("UPDATE_FUNCTION", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.T_SHOHIN_IDO_MEISAI.UPDATE_TIMESTAMP</code>.
     */
    public final TableField<T_SHOHIN_IDO_MEISAI_RECORD, LocalDateTime> update_timestamp = createField("UPDATE_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP()", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>PUBLIC.T_SHOHIN_IDO_MEISAI.UPDATE_USER_ID</code>.
     */
    public final TableField<T_SHOHIN_IDO_MEISAI_RECORD, String> update_user_id = createField("UPDATE_USER_ID", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.T_SHOHIN_IDO_MEISAI.VERSION</code>.
     */
    public final TableField<T_SHOHIN_IDO_MEISAI_RECORD, Integer> version = createField("VERSION", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("1", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>PUBLIC.T_SHOHIN_IDO_MEISAI.NUMBER</code>.
     */
    public final TableField<T_SHOHIN_IDO_MEISAI_RECORD, Integer> number = createField("NUMBER", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.T_SHOHIN_IDO_MEISAI.SHOHIN_ID</code>.
     */
    public final TableField<T_SHOHIN_IDO_MEISAI_RECORD, String> shohin_id = createField("SHOHIN_ID", org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * Create a <code>PUBLIC.T_SHOHIN_IDO_MEISAI</code> table reference
     */
    public T_SHOHIN_IDO_MEISAI() {
        this(DSL.name("T_SHOHIN_IDO_MEISAI"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.T_SHOHIN_IDO_MEISAI</code> table reference
     */
    public T_SHOHIN_IDO_MEISAI(String alias) {
        this(DSL.name(alias), t_shohin_ido_meisai);
    }

    /**
     * Create an aliased <code>PUBLIC.T_SHOHIN_IDO_MEISAI</code> table reference
     */
    public T_SHOHIN_IDO_MEISAI(Name alias) {
        this(alias, t_shohin_ido_meisai);
    }

    private T_SHOHIN_IDO_MEISAI(Name alias, Table<T_SHOHIN_IDO_MEISAI_RECORD> aliased) {
        this(alias, aliased, null);
    }

    private T_SHOHIN_IDO_MEISAI(Name alias, Table<T_SHOHIN_IDO_MEISAI_RECORD> aliased, Field<?>[] parameters) {
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
    public UniqueKey<T_SHOHIN_IDO_MEISAI_RECORD> getPrimaryKey() {
        return Keys.CONSTRAINT_25;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<T_SHOHIN_IDO_MEISAI_RECORD>> getKeys() {
        return Arrays.<UniqueKey<T_SHOHIN_IDO_MEISAI_RECORD>>asList(Keys.CONSTRAINT_25, Keys.UK_QMKOJB8N58COAAOK60IQVBJ5B);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<T_SHOHIN_IDO_MEISAI_RECORD, ?>> getReferences() {
        return Arrays.<ForeignKey<T_SHOHIN_IDO_MEISAI_RECORD, ?>>asList(Keys.FKP0LRETW08695Q1FOIS2EK6Q6B, Keys.FKNYESTD5U0GAEDM2AUNTF943D1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SHOHIN_IDO_MEISAI as(String alias) {
        return new T_SHOHIN_IDO_MEISAI(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SHOHIN_IDO_MEISAI as(Name alias) {
        return new T_SHOHIN_IDO_MEISAI(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public T_SHOHIN_IDO_MEISAI rename(String name) {
        return new T_SHOHIN_IDO_MEISAI(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public T_SHOHIN_IDO_MEISAI rename(Name name) {
        return new T_SHOHIN_IDO_MEISAI(name, null);
    }
}
