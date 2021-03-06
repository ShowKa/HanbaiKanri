/*
 * This file is generated by jOOQ.
*/
package com.showka.table.public_.tables.records;


import com.showka.table.public_.tables.M_BUSHO;

import java.time.LocalDateTime;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record12;
import org.jooq.Row12;
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
public class M_BUSHO_RECORD extends UpdatableRecordImpl<M_BUSHO_RECORD> implements Record12<String, String, LocalDateTime, String, String, String, LocalDateTime, String, Integer, String, String, String> {

    private static final long serialVersionUID = 1428515252;

    /**
     * Setter for <code>PUBLIC.M_BUSHO.CODE</code>.
     */
    public void setCode(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>PUBLIC.M_BUSHO.CODE</code>.
     */
    public String getCode() {
        return (String) get(0);
    }

    /**
     * Setter for <code>PUBLIC.M_BUSHO.CREATE_FUNCTION</code>.
     */
    public void setCreateFunction(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>PUBLIC.M_BUSHO.CREATE_FUNCTION</code>.
     */
    public String getCreateFunction() {
        return (String) get(1);
    }

    /**
     * Setter for <code>PUBLIC.M_BUSHO.CREATE_TIMESTAMP</code>.
     */
    public void setCreateTimestamp(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>PUBLIC.M_BUSHO.CREATE_TIMESTAMP</code>.
     */
    public LocalDateTime getCreateTimestamp() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>PUBLIC.M_BUSHO.CREATE_USER_ID</code>.
     */
    public void setCreateUserId(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>PUBLIC.M_BUSHO.CREATE_USER_ID</code>.
     */
    public String getCreateUserId() {
        return (String) get(3);
    }

    /**
     * Setter for <code>PUBLIC.M_BUSHO.RECORD_ID</code>.
     */
    public void setRecordId(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>PUBLIC.M_BUSHO.RECORD_ID</code>.
     */
    public String getRecordId() {
        return (String) get(4);
    }

    /**
     * Setter for <code>PUBLIC.M_BUSHO.UPDATE_FUNCTION</code>.
     */
    public void setUpdateFunction(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>PUBLIC.M_BUSHO.UPDATE_FUNCTION</code>.
     */
    public String getUpdateFunction() {
        return (String) get(5);
    }

    /**
     * Setter for <code>PUBLIC.M_BUSHO.UPDATE_TIMESTAMP</code>.
     */
    public void setUpdateTimestamp(LocalDateTime value) {
        set(6, value);
    }

    /**
     * Getter for <code>PUBLIC.M_BUSHO.UPDATE_TIMESTAMP</code>.
     */
    public LocalDateTime getUpdateTimestamp() {
        return (LocalDateTime) get(6);
    }

    /**
     * Setter for <code>PUBLIC.M_BUSHO.UPDATE_USER_ID</code>.
     */
    public void setUpdateUserId(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>PUBLIC.M_BUSHO.UPDATE_USER_ID</code>.
     */
    public String getUpdateUserId() {
        return (String) get(7);
    }

    /**
     * Setter for <code>PUBLIC.M_BUSHO.VERSION</code>.
     */
    public void setVersion(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>PUBLIC.M_BUSHO.VERSION</code>.
     */
    public Integer getVersion() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>PUBLIC.M_BUSHO.BUSHO_KUBUN</code>.
     */
    public void setBushoKubun(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>PUBLIC.M_BUSHO.BUSHO_KUBUN</code>.
     */
    public String getBushoKubun() {
        return (String) get(9);
    }

    /**
     * Setter for <code>PUBLIC.M_BUSHO.JIGYO_KUBUN</code>.
     */
    public void setJigyoKubun(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>PUBLIC.M_BUSHO.JIGYO_KUBUN</code>.
     */
    public String getJigyoKubun() {
        return (String) get(10);
    }

    /**
     * Setter for <code>PUBLIC.M_BUSHO.NAME</code>.
     */
    public void setName(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>PUBLIC.M_BUSHO.NAME</code>.
     */
    public String getName() {
        return (String) get(11);
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
    // Record12 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row12<String, String, LocalDateTime, String, String, String, LocalDateTime, String, Integer, String, String, String> fieldsRow() {
        return (Row12) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row12<String, String, LocalDateTime, String, String, String, LocalDateTime, String, Integer, String, String, String> valuesRow() {
        return (Row12) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return M_BUSHO.m_busho.code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return M_BUSHO.m_busho.create_function;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field3() {
        return M_BUSHO.m_busho.create_timestamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return M_BUSHO.m_busho.create_user_id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return M_BUSHO.m_busho.record_id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return M_BUSHO.m_busho.update_function;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field7() {
        return M_BUSHO.m_busho.update_timestamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return M_BUSHO.m_busho.update_user_id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return M_BUSHO.m_busho.version;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return M_BUSHO.m_busho.busho_kubun;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return M_BUSHO.m_busho.jigyo_kubun;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return M_BUSHO.m_busho.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getCode();
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
    public LocalDateTime component3() {
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
    public LocalDateTime component7() {
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
        return getBushoKubun();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component11() {
        return getJigyoKubun();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component12() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getCode();
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
    public LocalDateTime value3() {
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
    public LocalDateTime value7() {
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
        return getBushoKubun();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getJigyoKubun();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_BUSHO_RECORD value1(String value) {
        setCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_BUSHO_RECORD value2(String value) {
        setCreateFunction(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_BUSHO_RECORD value3(LocalDateTime value) {
        setCreateTimestamp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_BUSHO_RECORD value4(String value) {
        setCreateUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_BUSHO_RECORD value5(String value) {
        setRecordId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_BUSHO_RECORD value6(String value) {
        setUpdateFunction(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_BUSHO_RECORD value7(LocalDateTime value) {
        setUpdateTimestamp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_BUSHO_RECORD value8(String value) {
        setUpdateUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_BUSHO_RECORD value9(Integer value) {
        setVersion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_BUSHO_RECORD value10(String value) {
        setBushoKubun(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_BUSHO_RECORD value11(String value) {
        setJigyoKubun(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_BUSHO_RECORD value12(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_BUSHO_RECORD values(String value1, String value2, LocalDateTime value3, String value4, String value5, String value6, LocalDateTime value7, String value8, Integer value9, String value10, String value11, String value12) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached M_BUSHO_RECORD
     */
    public M_BUSHO_RECORD() {
        super(M_BUSHO.m_busho);
    }

    /**
     * Create a detached, initialised M_BUSHO_RECORD
     */
    public M_BUSHO_RECORD(String code, String create_function, LocalDateTime create_timestamp, String create_user_id, String record_id, String update_function, LocalDateTime update_timestamp, String update_user_id, Integer version, String busho_kubun, String jigyo_kubun, String name) {
        super(M_BUSHO.m_busho);

        set(0, code);
        set(1, create_function);
        set(2, create_timestamp);
        set(3, create_user_id);
        set(4, record_id);
        set(5, update_function);
        set(6, update_timestamp);
        set(7, update_user_id);
        set(8, version);
        set(9, busho_kubun);
        set(10, jigyo_kubun);
        set(11, name);
    }
}
