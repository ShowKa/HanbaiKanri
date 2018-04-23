/*
 * This file is generated by jOOQ.
*/
package com.showka.table.public_.tables.records;


import com.showka.table.public_.tables.T_URIAGE;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record15;
import org.jooq.Record2;
import org.jooq.Row15;
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
public class T_URIAGE_RECORD extends UpdatableRecordImpl<T_URIAGE_RECORD> implements Record15<String, String, String, Timestamp, String, String, String, Timestamp, String, Integer, String, Timestamp, Double, Timestamp, String> {

    private static final long serialVersionUID = 1610268392;

    /**
     * Setter for <code>PUBLIC.T_URIAGE.DENPYO_NUMBER</code>.
     */
    public void setDenpyoNumber(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>PUBLIC.T_URIAGE.DENPYO_NUMBER</code>.
     */
    public String getDenpyoNumber() {
        return (String) get(0);
    }

    /**
     * Setter for <code>PUBLIC.T_URIAGE.KOKYAKU_ID</code>.
     */
    public void setKokyakuId(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>PUBLIC.T_URIAGE.KOKYAKU_ID</code>.
     */
    public String getKokyakuId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>PUBLIC.T_URIAGE.CREATE_FUNCTION</code>.
     */
    public void setCreateFunction(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>PUBLIC.T_URIAGE.CREATE_FUNCTION</code>.
     */
    public String getCreateFunction() {
        return (String) get(2);
    }

    /**
     * Setter for <code>PUBLIC.T_URIAGE.CREATE_TIMESTAMP</code>.
     */
    public void setCreateTimestamp(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>PUBLIC.T_URIAGE.CREATE_TIMESTAMP</code>.
     */
    public Timestamp getCreateTimestamp() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>PUBLIC.T_URIAGE.CREATE_USER_ID</code>.
     */
    public void setCreateUserId(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>PUBLIC.T_URIAGE.CREATE_USER_ID</code>.
     */
    public String getCreateUserId() {
        return (String) get(4);
    }

    /**
     * Setter for <code>PUBLIC.T_URIAGE.RECORD_ID</code>.
     */
    public void setRecordId(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>PUBLIC.T_URIAGE.RECORD_ID</code>.
     */
    public String getRecordId() {
        return (String) get(5);
    }

    /**
     * Setter for <code>PUBLIC.T_URIAGE.UPDATE_FUNCTION</code>.
     */
    public void setUpdateFunction(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>PUBLIC.T_URIAGE.UPDATE_FUNCTION</code>.
     */
    public String getUpdateFunction() {
        return (String) get(6);
    }

    /**
     * Setter for <code>PUBLIC.T_URIAGE.UPDATE_TIMESTAMP</code>.
     */
    public void setUpdateTimestamp(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>PUBLIC.T_URIAGE.UPDATE_TIMESTAMP</code>.
     */
    public Timestamp getUpdateTimestamp() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>PUBLIC.T_URIAGE.UPDATE_USER_ID</code>.
     */
    public void setUpdateUserId(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>PUBLIC.T_URIAGE.UPDATE_USER_ID</code>.
     */
    public String getUpdateUserId() {
        return (String) get(8);
    }

    /**
     * Setter for <code>PUBLIC.T_URIAGE.VERSION</code>.
     */
    public void setVersion(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>PUBLIC.T_URIAGE.VERSION</code>.
     */
    public Integer getVersion() {
        return (Integer) get(9);
    }

    /**
     * Setter for <code>PUBLIC.T_URIAGE.HANBAI_KUBUN</code>.
     */
    public void setHanbaiKubun(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>PUBLIC.T_URIAGE.HANBAI_KUBUN</code>.
     */
    public String getHanbaiKubun() {
        return (String) get(10);
    }

    /**
     * Setter for <code>PUBLIC.T_URIAGE.KEIJO_DATE</code>.
     */
    public void setKeijoDate(Timestamp value) {
        set(11, value);
    }

    /**
     * Getter for <code>PUBLIC.T_URIAGE.KEIJO_DATE</code>.
     */
    public Timestamp getKeijoDate() {
        return (Timestamp) get(11);
    }

    /**
     * Setter for <code>PUBLIC.T_URIAGE.SHOHIZEIRITSU</code>.
     */
    public void setShohizeiritsu(Double value) {
        set(12, value);
    }

    /**
     * Getter for <code>PUBLIC.T_URIAGE.SHOHIZEIRITSU</code>.
     */
    public Double getShohizeiritsu() {
        return (Double) get(12);
    }

    /**
     * Setter for <code>PUBLIC.T_URIAGE.URIAGE_DATE</code>.
     */
    public void setUriageDate(Timestamp value) {
        set(13, value);
    }

    /**
     * Getter for <code>PUBLIC.T_URIAGE.URIAGE_DATE</code>.
     */
    public Timestamp getUriageDate() {
        return (Timestamp) get(13);
    }

    /**
     * Setter for <code>PUBLIC.T_URIAGE.CANCEL_URIAGE_ID</code>.
     */
    public void setCancelUriageId(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>PUBLIC.T_URIAGE.CANCEL_URIAGE_ID</code>.
     */
    public String getCancelUriageId() {
        return (String) get(14);
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
    // Record15 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row15<String, String, String, Timestamp, String, String, String, Timestamp, String, Integer, String, Timestamp, Double, Timestamp, String> fieldsRow() {
        return (Row15) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row15<String, String, String, Timestamp, String, String, String, Timestamp, String, Integer, String, Timestamp, Double, Timestamp, String> valuesRow() {
        return (Row15) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return T_URIAGE.t_uriage.DENPYO_NUMBER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return T_URIAGE.t_uriage.KOKYAKU_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return T_URIAGE.t_uriage.CREATE_FUNCTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return T_URIAGE.t_uriage.CREATE_TIMESTAMP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return T_URIAGE.t_uriage.CREATE_USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return T_URIAGE.t_uriage.RECORD_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return T_URIAGE.t_uriage.UPDATE_FUNCTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return T_URIAGE.t_uriage.UPDATE_TIMESTAMP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return T_URIAGE.t_uriage.UPDATE_USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field10() {
        return T_URIAGE.t_uriage.VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return T_URIAGE.t_uriage.HANBAI_KUBUN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field12() {
        return T_URIAGE.t_uriage.KEIJO_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Double> field13() {
        return T_URIAGE.t_uriage.SHOHIZEIRITSU;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field14() {
        return T_URIAGE.t_uriage.URIAGE_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field15() {
        return T_URIAGE.t_uriage.CANCEL_URIAGE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getDenpyoNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getKokyakuId();
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
    public String component11() {
        return getHanbaiKubun();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component12() {
        return getKeijoDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double component13() {
        return getShohizeiritsu();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component14() {
        return getUriageDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component15() {
        return getCancelUriageId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getDenpyoNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getKokyakuId();
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
    public String value11() {
        return getHanbaiKubun();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value12() {
        return getKeijoDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double value13() {
        return getShohizeiritsu();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value14() {
        return getUriageDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value15() {
        return getCancelUriageId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_URIAGE_RECORD value1(String value) {
        setDenpyoNumber(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_URIAGE_RECORD value2(String value) {
        setKokyakuId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_URIAGE_RECORD value3(String value) {
        setCreateFunction(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_URIAGE_RECORD value4(Timestamp value) {
        setCreateTimestamp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_URIAGE_RECORD value5(String value) {
        setCreateUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_URIAGE_RECORD value6(String value) {
        setRecordId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_URIAGE_RECORD value7(String value) {
        setUpdateFunction(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_URIAGE_RECORD value8(Timestamp value) {
        setUpdateTimestamp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_URIAGE_RECORD value9(String value) {
        setUpdateUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_URIAGE_RECORD value10(Integer value) {
        setVersion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_URIAGE_RECORD value11(String value) {
        setHanbaiKubun(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_URIAGE_RECORD value12(Timestamp value) {
        setKeijoDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_URIAGE_RECORD value13(Double value) {
        setShohizeiritsu(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_URIAGE_RECORD value14(Timestamp value) {
        setUriageDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_URIAGE_RECORD value15(String value) {
        setCancelUriageId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_URIAGE_RECORD values(String value1, String value2, String value3, Timestamp value4, String value5, String value6, String value7, Timestamp value8, String value9, Integer value10, String value11, Timestamp value12, Double value13, Timestamp value14, String value15) {
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
        value14(value14);
        value15(value15);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached T_URIAGE_RECORD
     */
    public T_URIAGE_RECORD() {
        super(T_URIAGE.t_uriage);
    }

    /**
     * Create a detached, initialised T_URIAGE_RECORD
     */
    public T_URIAGE_RECORD(String denpyoNumber, String kokyakuId, String createFunction, Timestamp createTimestamp, String createUserId, String recordId, String updateFunction, Timestamp updateTimestamp, String updateUserId, Integer version, String hanbaiKubun, Timestamp keijoDate, Double shohizeiritsu, Timestamp uriageDate, String cancelUriageId) {
        super(T_URIAGE.t_uriage);

        set(0, denpyoNumber);
        set(1, kokyakuId);
        set(2, createFunction);
        set(3, createTimestamp);
        set(4, createUserId);
        set(5, recordId);
        set(6, updateFunction);
        set(7, updateTimestamp);
        set(8, updateUserId);
        set(9, version);
        set(10, hanbaiKubun);
        set(11, keijoDate);
        set(12, shohizeiritsu);
        set(13, uriageDate);
        set(14, cancelUriageId);
    }
}
