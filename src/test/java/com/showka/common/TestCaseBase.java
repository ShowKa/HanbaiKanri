package com.showka.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

public abstract class TestCaseBase extends TestCase {

	/** date format yyyyMMdd . */
	private static final DateFormat DF = new SimpleDateFormat("yyyyMMdd");

	public static Date d(String yyyyMMdd) {
		try {
			return DF.parse(yyyyMMdd);
		} catch (ParseException e) {
			throw new RuntimeException("unexpected date formater : " + yyyyMMdd);
		}
	}

	// 部署
	protected static final String M_BUSHO = "m_busho";
	protected static final String[] M_BUSHO_COLUMN = { "code", "busho_kubun", "jigyo_kubun", "name", "record_id" };

	// 部署日付
	protected static final String M_BUSHO_DATE = "m_busho_date";
	protected static final String[] M_BUSHO_DATE_COLUMN = { "busho_id", "eigyo_date", "record_id" };

	// 顧客
	protected static final String M_KOKYAKU = "m_kokyaku";
	protected static final String[] M_KOKYAKU_COLUMN = {
			"code",
			"name",
			"address",
			"kokyaku_kubun",
			"hanbai_kubun",
			"shukan_busho_id",
			"record_id" };

	// 売上
	protected static final String T_URIAGE = "t_uriage";
	protected static final String[] T_URIAGE_COLUMN = {
			"kokyaku_id",
			"denpyo_number",
			"uriage_date",
			"keijo_date",
			"hanbai_kubun",
			"shohizeiritsu",
			"record_id" };

	// 売上履歴明細
	protected static final String T_URIAGE_MEISAI = "t_uriage_meisai";
	protected static final String[] T_URIAGE_MEISAI_COLUMN = {
			"uriage_id",
			"meisai_number",
			"shohin_id",
			"hanbai_number",
			"hanbai_tanka",
			"record_id" };

	// 売上履歴
	protected static final String C_URIAGE = "c_uriage";
	protected static final String[] C_URIAGE_COLUMN = { "uriage_id", "record_id" };

	// 売上履歴
	protected static final String R_URIAGE = "r_uriage";
	protected static final String[] R_URIAGE_COLUMN = {
			"uriage_id",
			"uriage_date",
			"keijo_date",
			"hanbai_kubun",
			"shohizeiritsu",
			"record_id" };

	// 売上履歴明細
	protected static final String R_URIAGE_MEISAI = "r_uriage_meisai";
	protected static final String[] R_URIAGE_MEISAI_COLUMN = {
			"uriage_id",
			"meisai_number",
			"shohin_id",
			"hanbai_number",
			"hanbai_tanka",
			"record_id" };

	// 商品
	protected static final String M_SHOHIN = "M_SHOHIN";
	protected static final String[] M_SHOHIN_COLUMN = { "code", "name", "hyojun_tanka", "record_id" };

	// 商品在庫
	protected static final String T_SHOHIN_ZAIKO = "t_shohin_zaiko";
	protected static final String[] T_SHOHIN_ZAIKO_COLUMN = {
			"busho_id",
			"eigyo_date",
			"shohin_id",
			"number",
			"record_id" };

	// 商品移動
	protected static final String T_SHOHIN_IDO = "t_shohin_ido";
	protected static final String[] T_SHOHIN_IDO_COLUMN = {
			"id",
			"busho_id",
			"date",
			"kubun",
			"sagyo_timestamp",
			"record_id" };

	// 商品移動明細
	protected static final String T_SHOHIN_IDO_MEISAI = "t_shohin_ido_meisai";
	protected static final String[] T_SHOHIN_IDO_MEISAI_COLUMN = {
			"shohin_ido_id",
			"meisai_number",
			"shohin_id",
			"number",
			"record_id" };

}
