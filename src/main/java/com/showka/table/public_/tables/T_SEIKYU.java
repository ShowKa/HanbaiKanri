/*
 * This file is generated by jOOQ.
*/
package com.showka.table.public_.tables;


import com.showka.table.public_.Keys;
import com.showka.table.public_.Public;
import com.showka.table.public_.tables.records.T_SEIKYU_RECORD;

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
public class T_SEIKYU extends TableImpl<T_SEIKYU_RECORD> {

    private static final long serialVersionUID = 298329565;

    /**
     * The reference instance of <code>PUBLIC.T_SEIKYU</code>
     */
    public static final T_SEIKYU t_seikyu = new T_SEIKYU();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<T_SEIKYU_RECORD> getRecordType() {
        return T_SEIKYU_RECORD.class;
    }

    /**
     * The column <code>PUBLIC.T_SEIKYU.KOKYAKU_ID</code>.
     */
    public final TableField<T_SEIKYU_RECORD, String> kokyaku_id = createField("KOKYAKU_ID", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.T_SEIKYU.SEIKYU_DATE</code>.
     */
    public final TableField<T_SEIKYU_RECORD, LocalDateTime> seikyu_date = createField("SEIKYU_DATE", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.T_SEIKYU.CREATE_FUNCTION</code>.
     */
    public final TableField<T_SEIKYU_RECORD, String> create_function = createField("CREATE_FUNCTION", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.T_SEIKYU.CREATE_TIMESTAMP</code>.
     */
    public final TableField<T_SEIKYU_RECORD, LocalDateTime> create_timestamp = createField("CREATE_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP()", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>PUBLIC.T_SEIKYU.CREATE_USER_ID</code>.
     */
    public final TableField<T_SEIKYU_RECORD, String> create_user_id = createField("CREATE_USER_ID", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.T_SEIKYU.RECORD_ID</code>.
     */
    public final TableField<T_SEIKYU_RECORD, String> record_id = createField("RECORD_ID", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.T_SEIKYU.UPDATE_FUNCTION</code>.
     */
    public final TableField<T_SEIKYU_RECORD, String> update_function = createField("UPDATE_FUNCTION", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.T_SEIKYU.UPDATE_TIMESTAMP</code>.
     */
    public final TableField<T_SEIKYU_RECORD, LocalDateTime> update_timestamp = createField("UPDATE_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP()", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>PUBLIC.T_SEIKYU.UPDATE_USER_ID</code>.
     */
    public final TableField<T_SEIKYU_RECORD, String> update_user_id = createField("UPDATE_USER_ID", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.T_SEIKYU.VERSION</code>.
     */
    public final TableField<T_SEIKYU_RECORD, Integer> version = createField("VERSION", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("1", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>PUBLIC.T_SEIKYU.SHIHARAI_DATE</code>.
     */
    public final TableField<T_SEIKYU_RECORD, LocalDateTime> shiharai_date = createField("SHIHARAI_DATE", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false), this, "");

    /**
     * Create a <code>PUBLIC.T_SEIKYU</code> table reference
     */
    public T_SEIKYU() {
        this(DSL.name("T_SEIKYU"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.T_SEIKYU</code> table reference
     */
    public T_SEIKYU(String alias) {
        this(DSL.name(alias), t_seikyu);
    }

    /**
     * Create an aliased <code>PUBLIC.T_SEIKYU</code> table reference
     */
    public T_SEIKYU(Name alias) {
        this(alias, t_seikyu);
    }

    private T_SEIKYU(Name alias, Table<T_SEIKYU_RECORD> aliased) {
        this(alias, aliased, null);
    }

    private T_SEIKYU(Name alias, Table<T_SEIKYU_RECORD> aliased, Field<?>[] parameters) {
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
    public UniqueKey<T_SEIKYU_RECORD> getPrimaryKey() {
        return Keys.CONSTRAINT_4;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<T_SEIKYU_RECORD>> getKeys() {
        return Arrays.<UniqueKey<T_SEIKYU_RECORD>>asList(Keys.CONSTRAINT_4, Keys.UK_4SCPAF4QMJRAM1VXMKUYJ269J);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<T_SEIKYU_RECORD, ?>> getReferences() {
        return Arrays.<ForeignKey<T_SEIKYU_RECORD, ?>>asList(Keys.FKS8KT4C3Q2N4GLUH51SN6LLX73);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SEIKYU as(String alias) {
        return new T_SEIKYU(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SEIKYU as(Name alias) {
        return new T_SEIKYU(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public T_SEIKYU rename(String name) {
        return new T_SEIKYU(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public T_SEIKYU rename(Name name) {
        return new T_SEIKYU(name, null);
    }
}
