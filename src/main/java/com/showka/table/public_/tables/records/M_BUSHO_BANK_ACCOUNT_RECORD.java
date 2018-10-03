/*
 * This file is generated by jOOQ.
*/
package com.showka.table.public_.tables.records;


import com.showka.table.public_.tables.M_BUSHO_BANK_ACCOUNT;

import java.time.LocalDateTime;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record11;
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
public class M_BUSHO_BANK_ACCOUNT_RECORD extends UpdatableRecordImpl<M_BUSHO_BANK_ACCOUNT_RECORD> implements Record11<String, String, LocalDateTime, String, String, String, LocalDateTime, String, Integer, String, String> {

    private static final long serialVersionUID = -236729509;

    /**
     * Setter for <code>PUBLIC.M_BUSHO_BANK_ACCOUNT.BUSHO_ID</code>.
     */
    public void setBushoId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>PUBLIC.M_BUSHO_BANK_ACCOUNT.BUSHO_ID</code>.
     */
    public String getBushoId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>PUBLIC.M_BUSHO_BANK_ACCOUNT.CREATE_FUNCTION</code>.
     */
    public void setCreateFunction(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>PUBLIC.M_BUSHO_BANK_ACCOUNT.CREATE_FUNCTION</code>.
     */
    public String getCreateFunction() {
        return (String) get(1);
    }

    /**
     * Setter for <code>PUBLIC.M_BUSHO_BANK_ACCOUNT.CREATE_TIMESTAMP</code>.
     */
    public void setCreateTimestamp(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>PUBLIC.M_BUSHO_BANK_ACCOUNT.CREATE_TIMESTAMP</code>.
     */
    public LocalDateTime getCreateTimestamp() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>PUBLIC.M_BUSHO_BANK_ACCOUNT.CREATE_USER_ID</code>.
     */
    public void setCreateUserId(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>PUBLIC.M_BUSHO_BANK_ACCOUNT.CREATE_USER_ID</code>.
     */
    public String getCreateUserId() {
        return (String) get(3);
    }

    /**
     * Setter for <code>PUBLIC.M_BUSHO_BANK_ACCOUNT.RECORD_ID</code>.
     */
    public void setRecordId(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>PUBLIC.M_BUSHO_BANK_ACCOUNT.RECORD_ID</code>.
     */
    public String getRecordId() {
        return (String) get(4);
    }

    /**
     * Setter for <code>PUBLIC.M_BUSHO_BANK_ACCOUNT.UPDATE_FUNCTION</code>.
     */
    public void setUpdateFunction(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>PUBLIC.M_BUSHO_BANK_ACCOUNT.UPDATE_FUNCTION</code>.
     */
    public String getUpdateFunction() {
        return (String) get(5);
    }

    /**
     * Setter for <code>PUBLIC.M_BUSHO_BANK_ACCOUNT.UPDATE_TIMESTAMP</code>.
     */
    public void setUpdateTimestamp(LocalDateTime value) {
        set(6, value);
    }

    /**
     * Getter for <code>PUBLIC.M_BUSHO_BANK_ACCOUNT.UPDATE_TIMESTAMP</code>.
     */
    public LocalDateTime getUpdateTimestamp() {
        return (LocalDateTime) get(6);
    }

    /**
     * Setter for <code>PUBLIC.M_BUSHO_BANK_ACCOUNT.UPDATE_USER_ID</code>.
     */
    public void setUpdateUserId(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>PUBLIC.M_BUSHO_BANK_ACCOUNT.UPDATE_USER_ID</code>.
     */
    public String getUpdateUserId() {
        return (String) get(7);
    }

    /**
     * Setter for <code>PUBLIC.M_BUSHO_BANK_ACCOUNT.VERSION</code>.
     */
    public void setVersion(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>PUBLIC.M_BUSHO_BANK_ACCOUNT.VERSION</code>.
     */
    public Integer getVersion() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>PUBLIC.M_BUSHO_BANK_ACCOUNT.ACCOUNT_NUMBER</code>.
     */
    public void setAccountNumber(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>PUBLIC.M_BUSHO_BANK_ACCOUNT.ACCOUNT_NUMBER</code>.
     */
    public String getAccountNumber() {
        return (String) get(9);
    }

    /**
     * Setter for <code>PUBLIC.M_BUSHO_BANK_ACCOUNT.BANK_CODE</code>.
     */
    public void setBankCode(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>PUBLIC.M_BUSHO_BANK_ACCOUNT.BANK_CODE</code>.
     */
    public String getBankCode() {
        return (String) get(10);
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
    // Record11 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<String, String, LocalDateTime, String, String, String, LocalDateTime, String, Integer, String, String> fieldsRow() {
        return (Row11) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<String, String, LocalDateTime, String, String, String, LocalDateTime, String, Integer, String, String> valuesRow() {
        return (Row11) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return M_BUSHO_BANK_ACCOUNT.m_busho_bank_account.busho_id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return M_BUSHO_BANK_ACCOUNT.m_busho_bank_account.create_function;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field3() {
        return M_BUSHO_BANK_ACCOUNT.m_busho_bank_account.create_timestamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return M_BUSHO_BANK_ACCOUNT.m_busho_bank_account.create_user_id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return M_BUSHO_BANK_ACCOUNT.m_busho_bank_account.record_id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return M_BUSHO_BANK_ACCOUNT.m_busho_bank_account.update_function;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field7() {
        return M_BUSHO_BANK_ACCOUNT.m_busho_bank_account.update_timestamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return M_BUSHO_BANK_ACCOUNT.m_busho_bank_account.update_user_id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return M_BUSHO_BANK_ACCOUNT.m_busho_bank_account.version;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return M_BUSHO_BANK_ACCOUNT.m_busho_bank_account.account_number;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return M_BUSHO_BANK_ACCOUNT.m_busho_bank_account.bank_code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getBushoId();
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
        return getAccountNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component11() {
        return getBankCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getBushoId();
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
        return getAccountNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getBankCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_BUSHO_BANK_ACCOUNT_RECORD value1(String value) {
        setBushoId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_BUSHO_BANK_ACCOUNT_RECORD value2(String value) {
        setCreateFunction(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_BUSHO_BANK_ACCOUNT_RECORD value3(LocalDateTime value) {
        setCreateTimestamp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_BUSHO_BANK_ACCOUNT_RECORD value4(String value) {
        setCreateUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_BUSHO_BANK_ACCOUNT_RECORD value5(String value) {
        setRecordId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_BUSHO_BANK_ACCOUNT_RECORD value6(String value) {
        setUpdateFunction(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_BUSHO_BANK_ACCOUNT_RECORD value7(LocalDateTime value) {
        setUpdateTimestamp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_BUSHO_BANK_ACCOUNT_RECORD value8(String value) {
        setUpdateUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_BUSHO_BANK_ACCOUNT_RECORD value9(Integer value) {
        setVersion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_BUSHO_BANK_ACCOUNT_RECORD value10(String value) {
        setAccountNumber(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_BUSHO_BANK_ACCOUNT_RECORD value11(String value) {
        setBankCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_BUSHO_BANK_ACCOUNT_RECORD values(String value1, String value2, LocalDateTime value3, String value4, String value5, String value6, LocalDateTime value7, String value8, Integer value9, String value10, String value11) {
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
     * Create a detached M_BUSHO_BANK_ACCOUNT_RECORD
     */
    public M_BUSHO_BANK_ACCOUNT_RECORD() {
        super(M_BUSHO_BANK_ACCOUNT.m_busho_bank_account);
    }

    /**
     * Create a detached, initialised M_BUSHO_BANK_ACCOUNT_RECORD
     */
    public M_BUSHO_BANK_ACCOUNT_RECORD(String busho_id, String create_function, LocalDateTime create_timestamp, String create_user_id, String record_id, String update_function, LocalDateTime update_timestamp, String update_user_id, Integer version, String account_number, String bank_code) {
        super(M_BUSHO_BANK_ACCOUNT.m_busho_bank_account);

        set(0, busho_id);
        set(1, create_function);
        set(2, create_timestamp);
        set(3, create_user_id);
        set(4, record_id);
        set(5, update_function);
        set(6, update_timestamp);
        set(7, update_user_id);
        set(8, version);
        set(9, account_number);
        set(10, bank_code);
    }
}
