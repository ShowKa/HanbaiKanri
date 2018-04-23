/*
 * This file is generated by jOOQ.
*/
package com.showka.table.public_.tables.records;


import com.showka.table.public_.tables.T_SEIKYU_MEISAI;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record11;
import org.jooq.Record2;
import org.jooq.Row11;
import org.jooq.impl.UpdatableRecordImpl;


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
public class T_SEIKYU_MEISAI_RECORD extends UpdatableRecordImpl<T_SEIKYU_MEISAI_RECORD> implements Record11<String, String, String, Timestamp, String, String, String, Timestamp, String, Integer, Integer> {

    private static final long serialVersionUID = -1097119817;

    /**
     * Setter for <code>PUBLIC.T_SEIKYU_MEISAI.SEIKYU_ID</code>.
     */
    public void setSeikyuId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>PUBLIC.T_SEIKYU_MEISAI.SEIKYU_ID</code>.
     */
    public String getSeikyuId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>PUBLIC.T_SEIKYU_MEISAI.URIKAKE_ID</code>.
     */
    public void setUrikakeId(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>PUBLIC.T_SEIKYU_MEISAI.URIKAKE_ID</code>.
     */
    public String getUrikakeId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>PUBLIC.T_SEIKYU_MEISAI.CREATE_FUNCTION</code>.
     */
    public void setCreateFunction(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>PUBLIC.T_SEIKYU_MEISAI.CREATE_FUNCTION</code>.
     */
    public String getCreateFunction() {
        return (String) get(2);
    }

    /**
     * Setter for <code>PUBLIC.T_SEIKYU_MEISAI.CREATE_TIMESTAMP</code>.
     */
    public void setCreateTimestamp(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>PUBLIC.T_SEIKYU_MEISAI.CREATE_TIMESTAMP</code>.
     */
    public Timestamp getCreateTimestamp() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>PUBLIC.T_SEIKYU_MEISAI.CREATE_USER_ID</code>.
     */
    public void setCreateUserId(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>PUBLIC.T_SEIKYU_MEISAI.CREATE_USER_ID</code>.
     */
    public String getCreateUserId() {
        return (String) get(4);
    }

    /**
     * Setter for <code>PUBLIC.T_SEIKYU_MEISAI.RECORD_ID</code>.
     */
    public void setRecordId(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>PUBLIC.T_SEIKYU_MEISAI.RECORD_ID</code>.
     */
    public String getRecordId() {
        return (String) get(5);
    }

    /**
     * Setter for <code>PUBLIC.T_SEIKYU_MEISAI.UPDATE_FUNCTION</code>.
     */
    public void setUpdateFunction(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>PUBLIC.T_SEIKYU_MEISAI.UPDATE_FUNCTION</code>.
     */
    public String getUpdateFunction() {
        return (String) get(6);
    }

    /**
     * Setter for <code>PUBLIC.T_SEIKYU_MEISAI.UPDATE_TIMESTAMP</code>.
     */
    public void setUpdateTimestamp(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>PUBLIC.T_SEIKYU_MEISAI.UPDATE_TIMESTAMP</code>.
     */
    public Timestamp getUpdateTimestamp() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>PUBLIC.T_SEIKYU_MEISAI.UPDATE_USER_ID</code>.
     */
    public void setUpdateUserId(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>PUBLIC.T_SEIKYU_MEISAI.UPDATE_USER_ID</code>.
     */
    public String getUpdateUserId() {
        return (String) get(8);
    }

    /**
     * Setter for <code>PUBLIC.T_SEIKYU_MEISAI.VERSION</code>.
     */
    public void setVersion(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>PUBLIC.T_SEIKYU_MEISAI.VERSION</code>.
     */
    public Integer getVersion() {
        return (Integer) get(9);
    }

    /**
     * Setter for <code>PUBLIC.T_SEIKYU_MEISAI.KINGAKU</code>.
     */
    public void setKingaku(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>PUBLIC.T_SEIKYU_MEISAI.KINGAKU</code>.
     */
    public Integer getKingaku() {
        return (Integer) get(10);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record2<String, String> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record11 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<String, String, String, Timestamp, String, String, String, Timestamp, String, Integer, Integer> fieldsRow() {
        return (Row11) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<String, String, String, Timestamp, String, String, String, Timestamp, String, Integer, Integer> valuesRow() {
        return (Row11) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return T_SEIKYU_MEISAI.t_seikyu_meisai.SEIKYU_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return T_SEIKYU_MEISAI.t_seikyu_meisai.URIKAKE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return T_SEIKYU_MEISAI.t_seikyu_meisai.CREATE_FUNCTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return T_SEIKYU_MEISAI.t_seikyu_meisai.CREATE_TIMESTAMP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return T_SEIKYU_MEISAI.t_seikyu_meisai.CREATE_USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return T_SEIKYU_MEISAI.t_seikyu_meisai.RECORD_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return T_SEIKYU_MEISAI.t_seikyu_meisai.UPDATE_FUNCTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return T_SEIKYU_MEISAI.t_seikyu_meisai.UPDATE_TIMESTAMP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return T_SEIKYU_MEISAI.t_seikyu_meisai.UPDATE_USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field10() {
        return T_SEIKYU_MEISAI.t_seikyu_meisai.VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field11() {
        return T_SEIKYU_MEISAI.t_seikyu_meisai.KINGAKU;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getSeikyuId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getUrikakeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getCreateFunction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component4() {
        return getCreateTimestamp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getCreateUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getRecordId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getUpdateFunction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component8() {
        return getUpdateTimestamp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component9() {
        return getUpdateUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component10() {
        return getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component11() {
        return getKingaku();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getSeikyuId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getUrikakeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getCreateFunction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getCreateTimestamp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getCreateUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getRecordId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getUpdateFunction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value8() {
        return getUpdateTimestamp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getUpdateUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value10() {
        return getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value11() {
        return getKingaku();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SEIKYU_MEISAI_RECORD value1(String value) {
        setSeikyuId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SEIKYU_MEISAI_RECORD value2(String value) {
        setUrikakeId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SEIKYU_MEISAI_RECORD value3(String value) {
        setCreateFunction(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SEIKYU_MEISAI_RECORD value4(Timestamp value) {
        setCreateTimestamp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SEIKYU_MEISAI_RECORD value5(String value) {
        setCreateUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SEIKYU_MEISAI_RECORD value6(String value) {
        setRecordId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SEIKYU_MEISAI_RECORD value7(String value) {
        setUpdateFunction(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SEIKYU_MEISAI_RECORD value8(Timestamp value) {
        setUpdateTimestamp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SEIKYU_MEISAI_RECORD value9(String value) {
        setUpdateUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SEIKYU_MEISAI_RECORD value10(Integer value) {
        setVersion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SEIKYU_MEISAI_RECORD value11(Integer value) {
        setKingaku(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SEIKYU_MEISAI_RECORD values(String value1, String value2, String value3, Timestamp value4, String value5, String value6, String value7, Timestamp value8, String value9, Integer value10, Integer value11) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached T_SEIKYU_MEISAI_RECORD
     */
    public T_SEIKYU_MEISAI_RECORD() {
        super(T_SEIKYU_MEISAI.t_seikyu_meisai);
    }

    /**
     * Create a detached, initialised T_SEIKYU_MEISAI_RECORD
     */
    public T_SEIKYU_MEISAI_RECORD(String seikyuId, String urikakeId, String createFunction, Timestamp createTimestamp, String createUserId, String recordId, String updateFunction, Timestamp updateTimestamp, String updateUserId, Integer version, Integer kingaku) {
        super(T_SEIKYU_MEISAI.t_seikyu_meisai);

        set(0, seikyuId);
        set(1, urikakeId);
        set(2, createFunction);
        set(3, createTimestamp);
        set(4, createUserId);
        set(5, recordId);
        set(6, updateFunction);
        set(7, updateTimestamp);
        set(8, updateUserId);
        set(9, version);
        set(10, kingaku);
    }
}
