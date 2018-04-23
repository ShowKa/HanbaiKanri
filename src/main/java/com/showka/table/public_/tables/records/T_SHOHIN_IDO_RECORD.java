/*
 * This file is generated by jOOQ.
*/
package com.showka.table.public_.tables.records;


import com.showka.table.public_.tables.T_SHOHIN_IDO;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record13;
import org.jooq.Row13;
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
public class T_SHOHIN_IDO_RECORD extends UpdatableRecordImpl<T_SHOHIN_IDO_RECORD> implements Record13<String, String, Timestamp, String, String, String, Timestamp, String, Integer, String, Timestamp, String, Timestamp> {

    private static final long serialVersionUID = -1420469144;

    /**
     * Setter for <code>PUBLIC.T_SHOHIN_IDO.ID</code>.
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>PUBLIC.T_SHOHIN_IDO.ID</code>.
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>PUBLIC.T_SHOHIN_IDO.CREATE_FUNCTION</code>.
     */
    public void setCreateFunction(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>PUBLIC.T_SHOHIN_IDO.CREATE_FUNCTION</code>.
     */
    public String getCreateFunction() {
        return (String) get(1);
    }

    /**
     * Setter for <code>PUBLIC.T_SHOHIN_IDO.CREATE_TIMESTAMP</code>.
     */
    public void setCreateTimestamp(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>PUBLIC.T_SHOHIN_IDO.CREATE_TIMESTAMP</code>.
     */
    public Timestamp getCreateTimestamp() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>PUBLIC.T_SHOHIN_IDO.CREATE_USER_ID</code>.
     */
    public void setCreateUserId(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>PUBLIC.T_SHOHIN_IDO.CREATE_USER_ID</code>.
     */
    public String getCreateUserId() {
        return (String) get(3);
    }

    /**
     * Setter for <code>PUBLIC.T_SHOHIN_IDO.RECORD_ID</code>.
     */
    public void setRecordId(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>PUBLIC.T_SHOHIN_IDO.RECORD_ID</code>.
     */
    public String getRecordId() {
        return (String) get(4);
    }

    /**
     * Setter for <code>PUBLIC.T_SHOHIN_IDO.UPDATE_FUNCTION</code>.
     */
    public void setUpdateFunction(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>PUBLIC.T_SHOHIN_IDO.UPDATE_FUNCTION</code>.
     */
    public String getUpdateFunction() {
        return (String) get(5);
    }

    /**
     * Setter for <code>PUBLIC.T_SHOHIN_IDO.UPDATE_TIMESTAMP</code>.
     */
    public void setUpdateTimestamp(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>PUBLIC.T_SHOHIN_IDO.UPDATE_TIMESTAMP</code>.
     */
    public Timestamp getUpdateTimestamp() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>PUBLIC.T_SHOHIN_IDO.UPDATE_USER_ID</code>.
     */
    public void setUpdateUserId(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>PUBLIC.T_SHOHIN_IDO.UPDATE_USER_ID</code>.
     */
    public String getUpdateUserId() {
        return (String) get(7);
    }

    /**
     * Setter for <code>PUBLIC.T_SHOHIN_IDO.VERSION</code>.
     */
    public void setVersion(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>PUBLIC.T_SHOHIN_IDO.VERSION</code>.
     */
    public Integer getVersion() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>PUBLIC.T_SHOHIN_IDO.BUSHO_ID</code>.
     */
    public void setBushoId(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>PUBLIC.T_SHOHIN_IDO.BUSHO_ID</code>.
     */
    public String getBushoId() {
        return (String) get(9);
    }

    /**
     * Setter for <code>PUBLIC.T_SHOHIN_IDO.DATE</code>.
     */
    public void setDate(Timestamp value) {
        set(10, value);
    }

    /**
     * Getter for <code>PUBLIC.T_SHOHIN_IDO.DATE</code>.
     */
    public Timestamp getDate() {
        return (Timestamp) get(10);
    }

    /**
     * Setter for <code>PUBLIC.T_SHOHIN_IDO.KUBUN</code>.
     */
    public void setKubun(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>PUBLIC.T_SHOHIN_IDO.KUBUN</code>.
     */
    public String getKubun() {
        return (String) get(11);
    }

    /**
     * Setter for <code>PUBLIC.T_SHOHIN_IDO.SAGYO_TIMESTAMP</code>.
     */
    public void setSagyoTimestamp(Timestamp value) {
        set(12, value);
    }

    /**
     * Getter for <code>PUBLIC.T_SHOHIN_IDO.SAGYO_TIMESTAMP</code>.
     */
    public Timestamp getSagyoTimestamp() {
        return (Timestamp) get(12);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record13 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row13<String, String, Timestamp, String, String, String, Timestamp, String, Integer, String, Timestamp, String, Timestamp> fieldsRow() {
        return (Row13) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row13<String, String, Timestamp, String, String, String, Timestamp, String, Integer, String, Timestamp, String, Timestamp> valuesRow() {
        return (Row13) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return T_SHOHIN_IDO.t_shohin_ido.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return T_SHOHIN_IDO.t_shohin_ido.CREATE_FUNCTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return T_SHOHIN_IDO.t_shohin_ido.CREATE_TIMESTAMP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return T_SHOHIN_IDO.t_shohin_ido.CREATE_USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return T_SHOHIN_IDO.t_shohin_ido.RECORD_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return T_SHOHIN_IDO.t_shohin_ido.UPDATE_FUNCTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return T_SHOHIN_IDO.t_shohin_ido.UPDATE_TIMESTAMP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return T_SHOHIN_IDO.t_shohin_ido.UPDATE_USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return T_SHOHIN_IDO.t_shohin_ido.VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return T_SHOHIN_IDO.t_shohin_ido.BUSHO_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field11() {
        return T_SHOHIN_IDO.t_shohin_ido.DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return T_SHOHIN_IDO.t_shohin_ido.KUBUN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field13() {
        return T_SHOHIN_IDO.t_shohin_ido.SAGYO_TIMESTAMP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getCreateFunction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component3() {
        return getCreateTimestamp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getCreateUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getRecordId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getUpdateFunction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component7() {
        return getUpdateTimestamp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getUpdateUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component9() {
        return getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component10() {
        return getBushoId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component11() {
        return getDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component12() {
        return getKubun();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component13() {
        return getSagyoTimestamp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getCreateFunction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value3() {
        return getCreateTimestamp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getCreateUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getRecordId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getUpdateFunction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getUpdateTimestamp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getUpdateUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value9() {
        return getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getBushoId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value11() {
        return getDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getKubun();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value13() {
        return getSagyoTimestamp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SHOHIN_IDO_RECORD value1(String value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SHOHIN_IDO_RECORD value2(String value) {
        setCreateFunction(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SHOHIN_IDO_RECORD value3(Timestamp value) {
        setCreateTimestamp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SHOHIN_IDO_RECORD value4(String value) {
        setCreateUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SHOHIN_IDO_RECORD value5(String value) {
        setRecordId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SHOHIN_IDO_RECORD value6(String value) {
        setUpdateFunction(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SHOHIN_IDO_RECORD value7(Timestamp value) {
        setUpdateTimestamp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SHOHIN_IDO_RECORD value8(String value) {
        setUpdateUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SHOHIN_IDO_RECORD value9(Integer value) {
        setVersion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SHOHIN_IDO_RECORD value10(String value) {
        setBushoId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SHOHIN_IDO_RECORD value11(Timestamp value) {
        setDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SHOHIN_IDO_RECORD value12(String value) {
        setKubun(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SHOHIN_IDO_RECORD value13(Timestamp value) {
        setSagyoTimestamp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_SHOHIN_IDO_RECORD values(String value1, String value2, Timestamp value3, String value4, String value5, String value6, Timestamp value7, String value8, Integer value9, String value10, Timestamp value11, String value12, Timestamp value13) {
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
        value12(value12);
        value13(value13);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached T_SHOHIN_IDO_RECORD
     */
    public T_SHOHIN_IDO_RECORD() {
        super(T_SHOHIN_IDO.t_shohin_ido);
    }

    /**
     * Create a detached, initialised T_SHOHIN_IDO_RECORD
     */
    public T_SHOHIN_IDO_RECORD(String id, String createFunction, Timestamp createTimestamp, String createUserId, String recordId, String updateFunction, Timestamp updateTimestamp, String updateUserId, Integer version, String bushoId, Timestamp date, String kubun, Timestamp sagyoTimestamp) {
        super(T_SHOHIN_IDO.t_shohin_ido);

        set(0, id);
        set(1, createFunction);
        set(2, createTimestamp);
        set(3, createUserId);
        set(4, recordId);
        set(5, updateFunction);
        set(6, updateTimestamp);
        set(7, updateUserId);
        set(8, version);
        set(9, bushoId);
        set(10, date);
        set(11, kubun);
        set(12, sagyoTimestamp);
    }
}
