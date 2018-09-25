/*
 * This file is generated by jOOQ.
*/
package com.showka.table.public_.tables.records;


import com.showka.table.public_.tables.M_USER;

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
public class M_USER_RECORD extends UpdatableRecordImpl<M_USER_RECORD> implements Record11<Long, String, LocalDateTime, String, String, String, LocalDateTime, String, Integer, String, String> {

    private static final long serialVersionUID = 196355444;

    /**
     * Setter for <code>PUBLIC.M_USER.ID</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>PUBLIC.M_USER.ID</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>PUBLIC.M_USER.CREATE_FUNCTION</code>.
     */
    public void setCreateFunction(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>PUBLIC.M_USER.CREATE_FUNCTION</code>.
     */
    public String getCreateFunction() {
        return (String) get(1);
    }

    /**
     * Setter for <code>PUBLIC.M_USER.CREATE_TIMESTAMP</code>.
     */
    public void setCreateTimestamp(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>PUBLIC.M_USER.CREATE_TIMESTAMP</code>.
     */
    public LocalDateTime getCreateTimestamp() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>PUBLIC.M_USER.CREATE_USER_ID</code>.
     */
    public void setCreateUserId(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>PUBLIC.M_USER.CREATE_USER_ID</code>.
     */
    public String getCreateUserId() {
        return (String) get(3);
    }

    /**
     * Setter for <code>PUBLIC.M_USER.RECORD_ID</code>.
     */
    public void setRecordId(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>PUBLIC.M_USER.RECORD_ID</code>.
     */
    public String getRecordId() {
        return (String) get(4);
    }

    /**
     * Setter for <code>PUBLIC.M_USER.UPDATE_FUNCTION</code>.
     */
    public void setUpdateFunction(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>PUBLIC.M_USER.UPDATE_FUNCTION</code>.
     */
    public String getUpdateFunction() {
        return (String) get(5);
    }

    /**
     * Setter for <code>PUBLIC.M_USER.UPDATE_TIMESTAMP</code>.
     */
    public void setUpdateTimestamp(LocalDateTime value) {
        set(6, value);
    }

    /**
     * Getter for <code>PUBLIC.M_USER.UPDATE_TIMESTAMP</code>.
     */
    public LocalDateTime getUpdateTimestamp() {
        return (LocalDateTime) get(6);
    }

    /**
     * Setter for <code>PUBLIC.M_USER.UPDATE_USER_ID</code>.
     */
    public void setUpdateUserId(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>PUBLIC.M_USER.UPDATE_USER_ID</code>.
     */
    public String getUpdateUserId() {
        return (String) get(7);
    }

    /**
     * Setter for <code>PUBLIC.M_USER.VERSION</code>.
     */
    public void setVersion(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>PUBLIC.M_USER.VERSION</code>.
     */
    public Integer getVersion() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>PUBLIC.M_USER.PASSWORD</code>.
     */
    public void setPassword(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>PUBLIC.M_USER.PASSWORD</code>.
     */
    public String getPassword() {
        return (String) get(9);
    }

    /**
     * Setter for <code>PUBLIC.M_USER.USERNAME</code>.
     */
    public void setUsername(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>PUBLIC.M_USER.USERNAME</code>.
     */
    public String getUsername() {
        return (String) get(10);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record11 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<Long, String, LocalDateTime, String, String, String, LocalDateTime, String, Integer, String, String> fieldsRow() {
        return (Row11) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<Long, String, LocalDateTime, String, String, String, LocalDateTime, String, Integer, String, String> valuesRow() {
        return (Row11) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return M_USER.m_user.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return M_USER.m_user.create_function;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field3() {
        return M_USER.m_user.create_timestamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return M_USER.m_user.create_user_id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return M_USER.m_user.record_id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return M_USER.m_user.update_function;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field7() {
        return M_USER.m_user.update_timestamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return M_USER.m_user.update_user_id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return M_USER.m_user.version;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return M_USER.m_user.password;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return M_USER.m_user.username;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component1() {
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
        return getPassword();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component11() {
        return getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
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
        return getPassword();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_USER_RECORD value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_USER_RECORD value2(String value) {
        setCreateFunction(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_USER_RECORD value3(LocalDateTime value) {
        setCreateTimestamp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_USER_RECORD value4(String value) {
        setCreateUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_USER_RECORD value5(String value) {
        setRecordId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_USER_RECORD value6(String value) {
        setUpdateFunction(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_USER_RECORD value7(LocalDateTime value) {
        setUpdateTimestamp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_USER_RECORD value8(String value) {
        setUpdateUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_USER_RECORD value9(Integer value) {
        setVersion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_USER_RECORD value10(String value) {
        setPassword(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_USER_RECORD value11(String value) {
        setUsername(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_USER_RECORD values(Long value1, String value2, LocalDateTime value3, String value4, String value5, String value6, LocalDateTime value7, String value8, Integer value9, String value10, String value11) {
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
     * Create a detached M_USER_RECORD
     */
    public M_USER_RECORD() {
        super(M_USER.m_user);
    }

    /**
     * Create a detached, initialised M_USER_RECORD
     */
    public M_USER_RECORD(Long id, String create_function, LocalDateTime create_timestamp, String create_user_id, String record_id, String update_function, LocalDateTime update_timestamp, String update_user_id, Integer version, String password, String username) {
        super(M_USER.m_user);

        set(0, id);
        set(1, create_function);
        set(2, create_timestamp);
        set(3, create_user_id);
        set(4, record_id);
        set(5, update_function);
        set(6, update_timestamp);
        set(7, update_user_id);
        set(8, version);
        set(9, password);
        set(10, username);
    }
}