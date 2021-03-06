/*
 * This file is generated by jOOQ.
*/
package com.showka.table.public_.tables.records;


import com.showka.table.public_.tables.T_URIAGE;

import java.time.LocalDateTime;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record14;
import org.jooq.Record2;
import org.jooq.Row14;
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
public class T_URIAGE_RECORD extends UpdatableRecordImpl<T_URIAGE_RECORD> implements Record14<String, String, String, LocalDateTime, String, String, String, LocalDateTime, String, Integer, String, LocalDateTime, Double, LocalDateTime> {

    private static final long serialVersionUID = 1072672187;

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
    public void setCreateTimestamp(LocalDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>PUBLIC.T_URIAGE.CREATE_TIMESTAMP</code>.
     */
    public LocalDateTime getCreateTimestamp() {
        return (LocalDateTime) get(3);
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
    public void setUpdateTimestamp(LocalDateTime value) {
        set(7, value);
    }

    /**
     * Getter for <code>PUBLIC.T_URIAGE.UPDATE_TIMESTAMP</code>.
     */
    public LocalDateTime getUpdateTimestamp() {
        return (LocalDateTime) get(7);
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
    public void setKeijoDate(LocalDateTime value) {
        set(11, value);
    }

    /**
     * Getter for <code>PUBLIC.T_URIAGE.KEIJO_DATE</code>.
     */
    public LocalDateTime getKeijoDate() {
        return (LocalDateTime) get(11);
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
    public void setUriageDate(LocalDateTime value) {
        set(13, value);
    }

    /**
     * Getter for <code>PUBLIC.T_URIAGE.URIAGE_DATE</code>.
     */
    public LocalDateTime getUriageDate() {
        return (LocalDateTime) get(13);
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
    // Record14 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row14<String, String, String, LocalDateTime, String, String, String, LocalDateTime, String, Integer, String, LocalDateTime, Double, LocalDateTime> fieldsRow() {
        return (Row14) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row14<String, String, String, LocalDateTime, String, String, String, LocalDateTime, String, Integer, String, LocalDateTime, Double, LocalDateTime> valuesRow() {
        return (Row14) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return T_URIAGE.t_uriage.denpyo_number;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return T_URIAGE.t_uriage.kokyaku_id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return T_URIAGE.t_uriage.create_function;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field4() {
        return T_URIAGE.t_uriage.create_timestamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return T_URIAGE.t_uriage.create_user_id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return T_URIAGE.t_uriage.record_id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return T_URIAGE.t_uriage.update_function;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field8() {
        return T_URIAGE.t_uriage.update_timestamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return T_URIAGE.t_uriage.update_user_id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field10() {
        return T_URIAGE.t_uriage.version;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return T_URIAGE.t_uriage.hanbai_kubun;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field12() {
        return T_URIAGE.t_uriage.keijo_date;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Double> field13() {
        return T_URIAGE.t_uriage.shohizeiritsu;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field14() {
        return T_URIAGE.t_uriage.uriage_date;
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
    public LocalDateTime component4() {
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
    public LocalDateTime component8() {
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
    public LocalDateTime component12() {
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
    public LocalDateTime component14() {
        return getUriageDate();
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
    public LocalDateTime value4() {
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
    public LocalDateTime value8() {
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
    public LocalDateTime value12() {
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
    public LocalDateTime value14() {
        return getUriageDate();
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
    public T_URIAGE_RECORD value4(LocalDateTime value) {
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
    public T_URIAGE_RECORD value8(LocalDateTime value) {
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
    public T_URIAGE_RECORD value12(LocalDateTime value) {
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
    public T_URIAGE_RECORD value14(LocalDateTime value) {
        setUriageDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T_URIAGE_RECORD values(String value1, String value2, String value3, LocalDateTime value4, String value5, String value6, String value7, LocalDateTime value8, String value9, Integer value10, String value11, LocalDateTime value12, Double value13, LocalDateTime value14) {
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
    public T_URIAGE_RECORD(String denpyo_number, String kokyaku_id, String create_function, LocalDateTime create_timestamp, String create_user_id, String record_id, String update_function, LocalDateTime update_timestamp, String update_user_id, Integer version, String hanbai_kubun, LocalDateTime keijo_date, Double shohizeiritsu, LocalDateTime uriage_date) {
        super(T_URIAGE.t_uriage);

        set(0, denpyo_number);
        set(1, kokyaku_id);
        set(2, create_function);
        set(3, create_timestamp);
        set(4, create_user_id);
        set(5, record_id);
        set(6, update_function);
        set(7, update_timestamp);
        set(8, update_user_id);
        set(9, version);
        set(10, hanbai_kubun);
        set(11, keijo_date);
        set(12, shohizeiritsu);
        set(13, uriage_date);
    }
}
