/*
 * This file is generated by jOOQ.
*/
package com.showka.table.public_.tables;


import com.showka.table.public_.Keys;
import com.showka.table.public_.Public;
import com.showka.table.public_.tables.records.J_NYUKIN_FB_FURIKOMI_RECORD;

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
public class J_NYUKIN_FB_FURIKOMI extends TableImpl<J_NYUKIN_FB_FURIKOMI_RECORD> {

    private static final long serialVersionUID = 211442943;

    /**
     * The reference instance of <code>PUBLIC.J_NYUKIN_FB_FURIKOMI</code>
     */
    public static final J_NYUKIN_FB_FURIKOMI j_nyukin_fb_furikomi = new J_NYUKIN_FB_FURIKOMI();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<J_NYUKIN_FB_FURIKOMI_RECORD> getRecordType() {
        return J_NYUKIN_FB_FURIKOMI_RECORD.class;
    }

    /**
     * The column <code>PUBLIC.J_NYUKIN_FB_FURIKOMI.NYUKIN_ID</code>.
     */
    public final TableField<J_NYUKIN_FB_FURIKOMI_RECORD, String> nyukin_id = createField("NYUKIN_ID", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.J_NYUKIN_FB_FURIKOMI.CREATE_FUNCTION</code>.
     */
    public final TableField<J_NYUKIN_FB_FURIKOMI_RECORD, String> create_function = createField("CREATE_FUNCTION", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.J_NYUKIN_FB_FURIKOMI.CREATE_TIMESTAMP</code>.
     */
    public final TableField<J_NYUKIN_FB_FURIKOMI_RECORD, LocalDateTime> create_timestamp = createField("CREATE_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP()", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>PUBLIC.J_NYUKIN_FB_FURIKOMI.CREATE_USER_ID</code>.
     */
    public final TableField<J_NYUKIN_FB_FURIKOMI_RECORD, String> create_user_id = createField("CREATE_USER_ID", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.J_NYUKIN_FB_FURIKOMI.RECORD_ID</code>.
     */
    public final TableField<J_NYUKIN_FB_FURIKOMI_RECORD, String> record_id = createField("RECORD_ID", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.J_NYUKIN_FB_FURIKOMI.UPDATE_FUNCTION</code>.
     */
    public final TableField<J_NYUKIN_FB_FURIKOMI_RECORD, String> update_function = createField("UPDATE_FUNCTION", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.J_NYUKIN_FB_FURIKOMI.UPDATE_TIMESTAMP</code>.
     */
    public final TableField<J_NYUKIN_FB_FURIKOMI_RECORD, LocalDateTime> update_timestamp = createField("UPDATE_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP()", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>PUBLIC.J_NYUKIN_FB_FURIKOMI.UPDATE_USER_ID</code>.
     */
    public final TableField<J_NYUKIN_FB_FURIKOMI_RECORD, String> update_user_id = createField("UPDATE_USER_ID", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.J_NYUKIN_FB_FURIKOMI.VERSION</code>.
     */
    public final TableField<J_NYUKIN_FB_FURIKOMI_RECORD, Integer> version = createField("VERSION", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("1", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>PUBLIC.J_NYUKIN_FB_FURIKOMI.FB_FURIKOMI_ID</code>.
     */
    public final TableField<J_NYUKIN_FB_FURIKOMI_RECORD, String> fb_furikomi_id = createField("FB_FURIKOMI_ID", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * Create a <code>PUBLIC.J_NYUKIN_FB_FURIKOMI</code> table reference
     */
    public J_NYUKIN_FB_FURIKOMI() {
        this(DSL.name("J_NYUKIN_FB_FURIKOMI"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.J_NYUKIN_FB_FURIKOMI</code> table reference
     */
    public J_NYUKIN_FB_FURIKOMI(String alias) {
        this(DSL.name(alias), j_nyukin_fb_furikomi);
    }

    /**
     * Create an aliased <code>PUBLIC.J_NYUKIN_FB_FURIKOMI</code> table reference
     */
    public J_NYUKIN_FB_FURIKOMI(Name alias) {
        this(alias, j_nyukin_fb_furikomi);
    }

    private J_NYUKIN_FB_FURIKOMI(Name alias, Table<J_NYUKIN_FB_FURIKOMI_RECORD> aliased) {
        this(alias, aliased, null);
    }

    private J_NYUKIN_FB_FURIKOMI(Name alias, Table<J_NYUKIN_FB_FURIKOMI_RECORD> aliased, Field<?>[] parameters) {
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
    public UniqueKey<J_NYUKIN_FB_FURIKOMI_RECORD> getPrimaryKey() {
        return Keys.CONSTRAINT_9;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<J_NYUKIN_FB_FURIKOMI_RECORD>> getKeys() {
        return Arrays.<UniqueKey<J_NYUKIN_FB_FURIKOMI_RECORD>>asList(Keys.CONSTRAINT_9, Keys.UK_F8MQIMUQJCTJ5BMEAEO9FWEA);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<J_NYUKIN_FB_FURIKOMI_RECORD, ?>> getReferences() {
        return Arrays.<ForeignKey<J_NYUKIN_FB_FURIKOMI_RECORD, ?>>asList(Keys.FKHRFFE6GO8CVLLUJ9PICOHY25I, Keys.FK5W0E0A9BMTLLXGI6VLU3EYFY5);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public J_NYUKIN_FB_FURIKOMI as(String alias) {
        return new J_NYUKIN_FB_FURIKOMI(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public J_NYUKIN_FB_FURIKOMI as(Name alias) {
        return new J_NYUKIN_FB_FURIKOMI(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public J_NYUKIN_FB_FURIKOMI rename(String name) {
        return new J_NYUKIN_FB_FURIKOMI(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public J_NYUKIN_FB_FURIKOMI rename(Name name) {
        return new J_NYUKIN_FB_FURIKOMI(name, null);
    }
}
