/*
 * This file is generated by jOOQ.
*/
package com.showka.table.public_.tables;


import com.showka.table.public_.Keys;
import com.showka.table.public_.Public;
import com.showka.table.public_.tables.records.C_KESHIKOMI_RECORD;

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
public class C_KESHIKOMI extends TableImpl<C_KESHIKOMI_RECORD> {

    private static final long serialVersionUID = 1872422189;

    /**
     * The reference instance of <code>PUBLIC.C_KESHIKOMI</code>
     */
    public static final C_KESHIKOMI c_keshikomi = new C_KESHIKOMI();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<C_KESHIKOMI_RECORD> getRecordType() {
        return C_KESHIKOMI_RECORD.class;
    }

    /**
     * The column <code>PUBLIC.C_KESHIKOMI.ID</code>.
     */
    public final TableField<C_KESHIKOMI_RECORD, String> id = createField("ID", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.C_KESHIKOMI.CREATE_FUNCTION</code>.
     */
    public final TableField<C_KESHIKOMI_RECORD, String> create_function = createField("CREATE_FUNCTION", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.C_KESHIKOMI.CREATE_TIMESTAMP</code>.
     */
    public final TableField<C_KESHIKOMI_RECORD, LocalDateTime> create_timestamp = createField("CREATE_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP()", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>PUBLIC.C_KESHIKOMI.CREATE_USER_ID</code>.
     */
    public final TableField<C_KESHIKOMI_RECORD, String> create_user_id = createField("CREATE_USER_ID", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.C_KESHIKOMI.RECORD_ID</code>.
     */
    public final TableField<C_KESHIKOMI_RECORD, String> record_id = createField("RECORD_ID", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.C_KESHIKOMI.UPDATE_FUNCTION</code>.
     */
    public final TableField<C_KESHIKOMI_RECORD, String> update_function = createField("UPDATE_FUNCTION", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.C_KESHIKOMI.UPDATE_TIMESTAMP</code>.
     */
    public final TableField<C_KESHIKOMI_RECORD, LocalDateTime> update_timestamp = createField("UPDATE_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP()", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>PUBLIC.C_KESHIKOMI.UPDATE_USER_ID</code>.
     */
    public final TableField<C_KESHIKOMI_RECORD, String> update_user_id = createField("UPDATE_USER_ID", org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false).defaultValue(org.jooq.impl.DSL.field("'default'", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>PUBLIC.C_KESHIKOMI.VERSION</code>.
     */
    public final TableField<C_KESHIKOMI_RECORD, Integer> version = createField("VERSION", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("1", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>PUBLIC.C_KESHIKOMI.CANCEL_DATE</code>.
     */
    public final TableField<C_KESHIKOMI_RECORD, LocalDateTime> cancel_date = createField("CANCEL_DATE", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.C_KESHIKOMI.DATE</code>.
     */
    public final TableField<C_KESHIKOMI_RECORD, LocalDateTime> date = createField("DATE", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.C_KESHIKOMI.KINGAKU</code>.
     */
    public final TableField<C_KESHIKOMI_RECORD, Integer> kingaku = createField("KINGAKU", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.C_KESHIKOMI.NYUKIN_ID</code>.
     */
    public final TableField<C_KESHIKOMI_RECORD, String> nyukin_id = createField("NYUKIN_ID", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.C_KESHIKOMI.TIMESTAMP</code>.
     */
    public final TableField<C_KESHIKOMI_RECORD, LocalDateTime> timestamp = createField("TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.C_KESHIKOMI.URIKAKE_ID</code>.
     */
    public final TableField<C_KESHIKOMI_RECORD, String> urikake_id = createField("URIKAKE_ID", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * Create a <code>PUBLIC.C_KESHIKOMI</code> table reference
     */
    public C_KESHIKOMI() {
        this(DSL.name("C_KESHIKOMI"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.C_KESHIKOMI</code> table reference
     */
    public C_KESHIKOMI(String alias) {
        this(DSL.name(alias), c_keshikomi);
    }

    /**
     * Create an aliased <code>PUBLIC.C_KESHIKOMI</code> table reference
     */
    public C_KESHIKOMI(Name alias) {
        this(alias, c_keshikomi);
    }

    private C_KESHIKOMI(Name alias, Table<C_KESHIKOMI_RECORD> aliased) {
        this(alias, aliased, null);
    }

    private C_KESHIKOMI(Name alias, Table<C_KESHIKOMI_RECORD> aliased, Field<?>[] parameters) {
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
    public UniqueKey<C_KESHIKOMI_RECORD> getPrimaryKey() {
        return Keys.CONSTRAINT_B;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<C_KESHIKOMI_RECORD>> getKeys() {
        return Arrays.<UniqueKey<C_KESHIKOMI_RECORD>>asList(Keys.CONSTRAINT_B, Keys.UK_SSOE3IYNBJW5S1LQAL26BKO3O);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<C_KESHIKOMI_RECORD, ?>> getReferences() {
        return Arrays.<ForeignKey<C_KESHIKOMI_RECORD, ?>>asList(Keys.FK76494XLSJXCW1QWO8QLVW4HUI, Keys.FKTBCYL9MLX9OHE7CQEVIVISUF4);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public C_KESHIKOMI as(String alias) {
        return new C_KESHIKOMI(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public C_KESHIKOMI as(Name alias) {
        return new C_KESHIKOMI(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public C_KESHIKOMI rename(String name) {
        return new C_KESHIKOMI(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public C_KESHIKOMI rename(Name name) {
        return new C_KESHIKOMI(name, null);
    }
}
