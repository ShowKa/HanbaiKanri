/*
 * This file is generated by jOOQ.
*/
package com.showka.table.public_.tables;


import com.showka.table.public_.Keys;
import com.showka.table.public_.Public;
import com.showka.table.public_.tables.records.M_SHAIN_RECORD;

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
public class M_SHAIN extends TableImpl<M_SHAIN_RECORD> {

    private static final long serialVersionUID = 1587552766;

    /**
     * The reference instance of <code>PUBLIC.M_SHAIN</code>
     */
    public static final M_SHAIN m_shain = new M_SHAIN();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<M_SHAIN_RECORD> getRecordType() {
        return M_SHAIN_RECORD.class;
    }

    /**
     * The column <code>PUBLIC.M_SHAIN.CODE</code>.
     */
    public final TableField<M_SHAIN_RECORD, String> code = createField("CODE", org.jooq.impl.SQLDataType.VARCHAR(6).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.M_SHAIN.CREATE_FUNCTION</code>.
     */
    public final TableField<M_SHAIN_RECORD, String> create_function = createField("CREATE_FUNCTION", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.M_SHAIN.CREATE_TIMESTAMP</code>.
     */
    public final TableField<M_SHAIN_RECORD, LocalDateTime> create_timestamp = createField("CREATE_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP()", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>PUBLIC.M_SHAIN.CREATE_USER_ID</code>.
     */
    public final TableField<M_SHAIN_RECORD, String> create_user_id = createField("CREATE_USER_ID", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.M_SHAIN.RECORD_ID</code>.
     */
    public final TableField<M_SHAIN_RECORD, String> record_id = createField("RECORD_ID", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.M_SHAIN.UPDATE_FUNCTION</code>.
     */
    public final TableField<M_SHAIN_RECORD, String> update_function = createField("UPDATE_FUNCTION", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.M_SHAIN.UPDATE_TIMESTAMP</code>.
     */
    public final TableField<M_SHAIN_RECORD, LocalDateTime> update_timestamp = createField("UPDATE_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP()", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>PUBLIC.M_SHAIN.UPDATE_USER_ID</code>.
     */
    public final TableField<M_SHAIN_RECORD, String> update_user_id = createField("UPDATE_USER_ID", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.M_SHAIN.VERSION</code>.
     */
    public final TableField<M_SHAIN_RECORD, Integer> version = createField("VERSION", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("1", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>PUBLIC.M_SHAIN.SHOZOKU_BUSHO_ID</code>.
     */
    public final TableField<M_SHAIN_RECORD, String> shozoku_busho_id = createField("SHOZOKU_BUSHO_ID", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.M_SHAIN.NAME</code>.
     */
    public final TableField<M_SHAIN_RECORD, String> name = createField("NAME", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * Create a <code>PUBLIC.M_SHAIN</code> table reference
     */
    public M_SHAIN() {
        this(DSL.name("M_SHAIN"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.M_SHAIN</code> table reference
     */
    public M_SHAIN(String alias) {
        this(DSL.name(alias), m_shain);
    }

    /**
     * Create an aliased <code>PUBLIC.M_SHAIN</code> table reference
     */
    public M_SHAIN(Name alias) {
        this(alias, m_shain);
    }

    private M_SHAIN(Name alias, Table<M_SHAIN_RECORD> aliased) {
        this(alias, aliased, null);
    }

    private M_SHAIN(Name alias, Table<M_SHAIN_RECORD> aliased, Field<?>[] parameters) {
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
    public UniqueKey<M_SHAIN_RECORD> getPrimaryKey() {
        return Keys.CONSTRAINT_90;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<M_SHAIN_RECORD>> getKeys() {
        return Arrays.<UniqueKey<M_SHAIN_RECORD>>asList(Keys.CONSTRAINT_90, Keys.UK_81PKBX9M7PBAFUMTCUKHTOOBH);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<M_SHAIN_RECORD, ?>> getReferences() {
        return Arrays.<ForeignKey<M_SHAIN_RECORD, ?>>asList(Keys.FK6EXWC57AN76H9BS794TJGTUGN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_SHAIN as(String alias) {
        return new M_SHAIN(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_SHAIN as(Name alias) {
        return new M_SHAIN(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public M_SHAIN rename(String name) {
        return new M_SHAIN(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public M_SHAIN rename(Name name) {
        return new M_SHAIN(name, null);
    }
}
