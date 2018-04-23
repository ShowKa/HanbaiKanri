/*
 * This file is generated by jOOQ.
*/
package com.showka.table.public_.tables;


import com.showka.table.public_.Indexes;
import com.showka.table.public_.Keys;
import com.showka.table.public_.Public;
import com.showka.table.public_.tables.records.T_URIKAKE_RECORD;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
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
public class T_URIKAKE extends TableImpl<T_URIKAKE_RECORD> {

    private static final long serialVersionUID = -1436011918;

    /**
     * The reference instance of <code>PUBLIC.T_URIKAKE</code>
     */
    public static final T_URIKAKE t_urikake = new T_URIKAKE();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<T_URIKAKE_RECORD> getRecordType() {
        return T_URIKAKE_RECORD.class;
    }

    /**
     * The column <code>PUBLIC.T_URIKAKE.URIAGE_ID</code>.
     */
    public final TableField<T_URIKAKE_RECORD, String> URIAGE_ID = createField("URIAGE_ID", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.T_URIKAKE.CREATE_FUNCTION</code>.
     */
    public final TableField<T_URIKAKE_RECORD, String> CREATE_FUNCTION = createField("CREATE_FUNCTION", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.T_URIKAKE.CREATE_TIMESTAMP</code>.
     */
    public final TableField<T_URIKAKE_RECORD, Timestamp> CREATE_TIMESTAMP = createField("CREATE_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>PUBLIC.T_URIKAKE.CREATE_USER_ID</code>.
     */
    public final TableField<T_URIKAKE_RECORD, String> CREATE_USER_ID = createField("CREATE_USER_ID", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.T_URIKAKE.RECORD_ID</code>.
     */
    public final TableField<T_URIKAKE_RECORD, String> RECORD_ID = createField("RECORD_ID", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.T_URIKAKE.UPDATE_FUNCTION</code>.
     */
    public final TableField<T_URIKAKE_RECORD, String> UPDATE_FUNCTION = createField("UPDATE_FUNCTION", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.T_URIKAKE.UPDATE_TIMESTAMP</code>.
     */
    public final TableField<T_URIKAKE_RECORD, Timestamp> UPDATE_TIMESTAMP = createField("UPDATE_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>PUBLIC.T_URIKAKE.UPDATE_USER_ID</code>.
     */
    public final TableField<T_URIKAKE_RECORD, String> UPDATE_USER_ID = createField("UPDATE_USER_ID", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.T_URIKAKE.VERSION</code>.
     */
    public final TableField<T_URIKAKE_RECORD, Integer> VERSION = createField("VERSION", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("1", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>PUBLIC.T_URIKAKE.NYUKIN_YOTEI_DATE</code>.
     */
    public final TableField<T_URIKAKE_RECORD, Timestamp> NYUKIN_YOTEI_DATE = createField("NYUKIN_YOTEI_DATE", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.T_URIKAKE.ZANDAKA</code>.
     */
    public final TableField<T_URIKAKE_RECORD, Integer> ZANDAKA = createField("ZANDAKA", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>PUBLIC.T_URIKAKE</code> table reference
     */
    public T_URIKAKE() {
        this(DSL.name("T_URIKAKE"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.T_URIKAKE</code> table reference
     */
    public T_URIKAKE(String alias) {
        this(DSL.name(alias), t_urikake);
    }

    /**
     * Create an aliased <code>PUBLIC.T_URIKAKE</code> table reference
     */
    public T_URIKAKE(Name alias) {
        this(alias, t_urikake);
    }

    private T_URIKAKE(Name alias, Table<T_URIKAKE_RECORD> aliased) {
        this(alias, aliased, null);
    }

    private T_URIKAKE(Name alias, Table<T_URIKAKE_RECORD> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.PRIMARY_KEY_53, Indexes.UK_3RCPSMXXRKKRO42FWTDIPIAXB_INDEX_5);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<T_URIKAKE_RECORD> getPrimaryKey() {
        return Keys.CONSTRAINT_53;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<T_URIKAKE_RECORD>> getKeys() {
        return Arrays.<UniqueKey<T_URIKAKE_RECORD>>asList(Keys.CONSTRAINT_53, Keys.UK_3RCPSMXXRKKRO42FWTDIPIAXB);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<T_URIKAKE_RECORD, ?>> getReferences() {
        return Arrays.<ForeignKey<T_URIKAKE_RECORD, ?>>asList(Keys.FK5QEL4CGVEMJKPVIBO5647PES1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_URIKAKE as(String alias) {
        return new T_URIKAKE(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_URIKAKE as(Name alias) {
        return new T_URIKAKE(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public T_URIKAKE rename(String name) {
        return new T_URIKAKE(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public T_URIKAKE rename(Name name) {
        return new T_URIKAKE(name, null);
    }
}
