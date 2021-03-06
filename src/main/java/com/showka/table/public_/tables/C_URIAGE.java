/*
 * This file is generated by jOOQ.
*/
package com.showka.table.public_.tables;


import com.showka.table.public_.Keys;
import com.showka.table.public_.Public;
import com.showka.table.public_.tables.records.C_URIAGE_RECORD;

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
public class C_URIAGE extends TableImpl<C_URIAGE_RECORD> {

    private static final long serialVersionUID = -147667648;

    /**
     * The reference instance of <code>PUBLIC.C_URIAGE</code>
     */
    public static final C_URIAGE c_uriage = new C_URIAGE();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<C_URIAGE_RECORD> getRecordType() {
        return C_URIAGE_RECORD.class;
    }

    /**
     * The column <code>PUBLIC.C_URIAGE.URIAGE_ID</code>.
     */
    public final TableField<C_URIAGE_RECORD, String> uriage_id = createField("URIAGE_ID", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.C_URIAGE.CREATE_FUNCTION</code>.
     */
    public final TableField<C_URIAGE_RECORD, String> create_function = createField("CREATE_FUNCTION", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.C_URIAGE.CREATE_TIMESTAMP</code>.
     */
    public final TableField<C_URIAGE_RECORD, LocalDateTime> create_timestamp = createField("CREATE_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP()", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>PUBLIC.C_URIAGE.CREATE_USER_ID</code>.
     */
    public final TableField<C_URIAGE_RECORD, String> create_user_id = createField("CREATE_USER_ID", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.C_URIAGE.RECORD_ID</code>.
     */
    public final TableField<C_URIAGE_RECORD, String> record_id = createField("RECORD_ID", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.C_URIAGE.UPDATE_FUNCTION</code>.
     */
    public final TableField<C_URIAGE_RECORD, String> update_function = createField("UPDATE_FUNCTION", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.C_URIAGE.UPDATE_TIMESTAMP</code>.
     */
    public final TableField<C_URIAGE_RECORD, LocalDateTime> update_timestamp = createField("UPDATE_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP()", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>PUBLIC.C_URIAGE.UPDATE_USER_ID</code>.
     */
    public final TableField<C_URIAGE_RECORD, String> update_user_id = createField("UPDATE_USER_ID", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.C_URIAGE.VERSION</code>.
     */
    public final TableField<C_URIAGE_RECORD, Integer> version = createField("VERSION", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("1", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * Create a <code>PUBLIC.C_URIAGE</code> table reference
     */
    public C_URIAGE() {
        this(DSL.name("C_URIAGE"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.C_URIAGE</code> table reference
     */
    public C_URIAGE(String alias) {
        this(DSL.name(alias), c_uriage);
    }

    /**
     * Create an aliased <code>PUBLIC.C_URIAGE</code> table reference
     */
    public C_URIAGE(Name alias) {
        this(alias, c_uriage);
    }

    private C_URIAGE(Name alias, Table<C_URIAGE_RECORD> aliased) {
        this(alias, aliased, null);
    }

    private C_URIAGE(Name alias, Table<C_URIAGE_RECORD> aliased, Field<?>[] parameters) {
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
    public UniqueKey<C_URIAGE_RECORD> getPrimaryKey() {
        return Keys.CONSTRAINT_6;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<C_URIAGE_RECORD>> getKeys() {
        return Arrays.<UniqueKey<C_URIAGE_RECORD>>asList(Keys.CONSTRAINT_6, Keys.UK_C90UBJW54JK7MVI3FGQ9NA2QU);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<C_URIAGE_RECORD, ?>> getReferences() {
        return Arrays.<ForeignKey<C_URIAGE_RECORD, ?>>asList(Keys.FKEDPVU5W0BXSMRNXCHN19IA4KK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public C_URIAGE as(String alias) {
        return new C_URIAGE(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public C_URIAGE as(Name alias) {
        return new C_URIAGE(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public C_URIAGE rename(String name) {
        return new C_URIAGE(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public C_URIAGE rename(Name name) {
        return new C_URIAGE(name, null);
    }
}
