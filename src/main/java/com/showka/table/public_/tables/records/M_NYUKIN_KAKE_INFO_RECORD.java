/*
 * This file is generated by jOOQ.
*/
package com.showka.table.public_.tables.records;


import com.showka.table.public_.tables.M_NYUKIN_KAKE_INFO;

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
public class M_NYUKIN_KAKE_INFO_RECORD extends UpdatableRecordImpl<M_NYUKIN_KAKE_INFO_RECORD> implements Record13<String, String, Timestamp, String, String, String, Timestamp, String, Integer, Integer, String, String, Integer> {

    private static final long serialVersionUID = 1578919649;

    /**
     * Setter for <code>PUBLIC.M_NYUKIN_KAKE_INFO.KOKYAKU_ID</code>.
     */
    public void setKokyakuId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>PUBLIC.M_NYUKIN_KAKE_INFO.KOKYAKU_ID</code>.
     */
    public String getKokyakuId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>PUBLIC.M_NYUKIN_KAKE_INFO.CREATE_FUNCTION</code>.
     */
    public void setCreateFunction(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>PUBLIC.M_NYUKIN_KAKE_INFO.CREATE_FUNCTION</code>.
     */
    public String getCreateFunction() {
        return (String) get(1);
    }

    /**
     * Setter for <code>PUBLIC.M_NYUKIN_KAKE_INFO.CREATE_TIMESTAMP</code>.
     */
    public void setCreateTimestamp(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>PUBLIC.M_NYUKIN_KAKE_INFO.CREATE_TIMESTAMP</code>.
     */
    public Timestamp getCreateTimestamp() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>PUBLIC.M_NYUKIN_KAKE_INFO.CREATE_USER_ID</code>.
     */
    public void setCreateUserId(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>PUBLIC.M_NYUKIN_KAKE_INFO.CREATE_USER_ID</code>.
     */
    public String getCreateUserId() {
        return (String) get(3);
    }

    /**
     * Setter for <code>PUBLIC.M_NYUKIN_KAKE_INFO.RECORD_ID</code>.
     */
    public void setRecordId(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>PUBLIC.M_NYUKIN_KAKE_INFO.RECORD_ID</code>.
     */
    public String getRecordId() {
        return (String) get(4);
    }

    /**
     * Setter for <code>PUBLIC.M_NYUKIN_KAKE_INFO.UPDATE_FUNCTION</code>.
     */
    public void setUpdateFunction(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>PUBLIC.M_NYUKIN_KAKE_INFO.UPDATE_FUNCTION</code>.
     */
    public String getUpdateFunction() {
        return (String) get(5);
    }

    /**
     * Setter for <code>PUBLIC.M_NYUKIN_KAKE_INFO.UPDATE_TIMESTAMP</code>.
     */
    public void setUpdateTimestamp(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>PUBLIC.M_NYUKIN_KAKE_INFO.UPDATE_TIMESTAMP</code>.
     */
    public Timestamp getUpdateTimestamp() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>PUBLIC.M_NYUKIN_KAKE_INFO.UPDATE_USER_ID</code>.
     */
    public void setUpdateUserId(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>PUBLIC.M_NYUKIN_KAKE_INFO.UPDATE_USER_ID</code>.
     */
    public String getUpdateUserId() {
        return (String) get(7);
    }

    /**
     * Setter for <code>PUBLIC.M_NYUKIN_KAKE_INFO.VERSION</code>.
     */
    public void setVersion(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>PUBLIC.M_NYUKIN_KAKE_INFO.VERSION</code>.
     */
    public Integer getVersion() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>PUBLIC.M_NYUKIN_KAKE_INFO.NYUKIN_DATE</code>.
     */
    public void setNyukinDate(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>PUBLIC.M_NYUKIN_KAKE_INFO.NYUKIN_DATE</code>.
     */
    public Integer getNyukinDate() {
        return (Integer) get(9);
    }

    /**
     * Setter for <code>PUBLIC.M_NYUKIN_KAKE_INFO.NYUKIN_HOHO_KUBUN</code>.
     */
    public void setNyukinHohoKubun(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>PUBLIC.M_NYUKIN_KAKE_INFO.NYUKIN_HOHO_KUBUN</code>.
     */
    public String getNyukinHohoKubun() {
        return (String) get(10);
    }

    /**
     * Setter for <code>PUBLIC.M_NYUKIN_KAKE_INFO.NYUKIN_TSUKI_KUBUN</code>.
     */
    public void setNyukinTsukiKubun(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>PUBLIC.M_NYUKIN_KAKE_INFO.NYUKIN_TSUKI_KUBUN</code>.
     */
    public String getNyukinTsukiKubun() {
        return (String) get(11);
    }

    /**
     * Setter for <code>PUBLIC.M_NYUKIN_KAKE_INFO.SHIMEBI</code>.
     */
    public void setShimebi(Integer value) {
        set(12, value);
    }

    /**
     * Getter for <code>PUBLIC.M_NYUKIN_KAKE_INFO.SHIMEBI</code>.
     */
    public Integer getShimebi() {
        return (Integer) get(12);
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
    public Row13<String, String, Timestamp, String, String, String, Timestamp, String, Integer, Integer, String, String, Integer> fieldsRow() {
        return (Row13) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row13<String, String, Timestamp, String, String, String, Timestamp, String, Integer, Integer, String, String, Integer> valuesRow() {
        return (Row13) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return M_NYUKIN_KAKE_INFO.m_nyukin_kake_info.KOKYAKU_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return M_NYUKIN_KAKE_INFO.m_nyukin_kake_info.CREATE_FUNCTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return M_NYUKIN_KAKE_INFO.m_nyukin_kake_info.CREATE_TIMESTAMP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return M_NYUKIN_KAKE_INFO.m_nyukin_kake_info.CREATE_USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return M_NYUKIN_KAKE_INFO.m_nyukin_kake_info.RECORD_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return M_NYUKIN_KAKE_INFO.m_nyukin_kake_info.UPDATE_FUNCTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return M_NYUKIN_KAKE_INFO.m_nyukin_kake_info.UPDATE_TIMESTAMP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return M_NYUKIN_KAKE_INFO.m_nyukin_kake_info.UPDATE_USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return M_NYUKIN_KAKE_INFO.m_nyukin_kake_info.VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field10() {
        return M_NYUKIN_KAKE_INFO.m_nyukin_kake_info.NYUKIN_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return M_NYUKIN_KAKE_INFO.m_nyukin_kake_info.NYUKIN_HOHO_KUBUN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return M_NYUKIN_KAKE_INFO.m_nyukin_kake_info.NYUKIN_TSUKI_KUBUN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field13() {
        return M_NYUKIN_KAKE_INFO.m_nyukin_kake_info.SHIMEBI;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getKokyakuId();
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
    public Integer component10() {
        return getNyukinDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component11() {
        return getNyukinHohoKubun();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component12() {
        return getNyukinTsukiKubun();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component13() {
        return getShimebi();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getKokyakuId();
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
    public Integer value10() {
        return getNyukinDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getNyukinHohoKubun();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getNyukinTsukiKubun();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value13() {
        return getShimebi();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_NYUKIN_KAKE_INFO_RECORD value1(String value) {
        setKokyakuId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_NYUKIN_KAKE_INFO_RECORD value2(String value) {
        setCreateFunction(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_NYUKIN_KAKE_INFO_RECORD value3(Timestamp value) {
        setCreateTimestamp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_NYUKIN_KAKE_INFO_RECORD value4(String value) {
        setCreateUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_NYUKIN_KAKE_INFO_RECORD value5(String value) {
        setRecordId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_NYUKIN_KAKE_INFO_RECORD value6(String value) {
        setUpdateFunction(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_NYUKIN_KAKE_INFO_RECORD value7(Timestamp value) {
        setUpdateTimestamp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_NYUKIN_KAKE_INFO_RECORD value8(String value) {
        setUpdateUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_NYUKIN_KAKE_INFO_RECORD value9(Integer value) {
        setVersion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_NYUKIN_KAKE_INFO_RECORD value10(Integer value) {
        setNyukinDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_NYUKIN_KAKE_INFO_RECORD value11(String value) {
        setNyukinHohoKubun(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_NYUKIN_KAKE_INFO_RECORD value12(String value) {
        setNyukinTsukiKubun(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_NYUKIN_KAKE_INFO_RECORD value13(Integer value) {
        setShimebi(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M_NYUKIN_KAKE_INFO_RECORD values(String value1, String value2, Timestamp value3, String value4, String value5, String value6, Timestamp value7, String value8, Integer value9, Integer value10, String value11, String value12, Integer value13) {
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
     * Create a detached M_NYUKIN_KAKE_INFO_RECORD
     */
    public M_NYUKIN_KAKE_INFO_RECORD() {
        super(M_NYUKIN_KAKE_INFO.m_nyukin_kake_info);
    }

    /**
     * Create a detached, initialised M_NYUKIN_KAKE_INFO_RECORD
     */
    public M_NYUKIN_KAKE_INFO_RECORD(String kokyakuId, String createFunction, Timestamp createTimestamp, String createUserId, String recordId, String updateFunction, Timestamp updateTimestamp, String updateUserId, Integer version, Integer nyukinDate, String nyukinHohoKubun, String nyukinTsukiKubun, Integer shimebi) {
        super(M_NYUKIN_KAKE_INFO.m_nyukin_kake_info);

        set(0, kokyakuId);
        set(1, createFunction);
        set(2, createTimestamp);
        set(3, createUserId);
        set(4, recordId);
        set(5, updateFunction);
        set(6, updateTimestamp);
        set(7, updateUserId);
        set(8, version);
        set(9, nyukinDate);
        set(10, nyukinHohoKubun);
        set(11, nyukinTsukiKubun);
        set(12, shimebi);
    }
}