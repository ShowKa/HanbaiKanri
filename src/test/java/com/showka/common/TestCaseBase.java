package com.showka.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

/**
 * Test Case Class.
 * 
 */
public abstract class TestCaseBase extends TestCase {

	/** date format yyyyMMdd . */
	private static final DateFormat DF = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 文字列からjava.util.Dateのインスタンスを作る.
	 * 
	 * @param yyyyMMdd
	 *            日付文字列
	 * @return Date
	 */
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

	// 社員
	protected static final String M_SHAIN = "m_shain";
	protected static final String[] M_SHAIN_COLUMN = { "code", "name", "shozoku_busho_id", "record_id" };

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

	// 入金掛売情報
	protected static final String M_NYUKIN_KAKE_INFO = "m_nyukin_kake_info";
	protected static final String[] M_NYUKIN_KAKE_INFO_COLUMN = {
			"kokyaku_id",
			"nyukin_hoho_kubun",
			"nyukin_tsuki_kubun",
			"shimebi",
			"nyukin_date",
			"record_id" };

	// 振込依頼人
	protected static final String M_FURIKOMI_IRANIN = "m_furikomi_irainin";
	protected static final String[] M_FURIKOMI_IRANIN_COLUMN = { "kokyaku_id", "furikomi_irainin_name", "record_id" };

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

	// 売上キャンセル
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

	// 売上計上
	protected static final String R_URIAGE_KEIJO = "r_uriage_keijo";
	protected static final String[] R_URIAGE_KEIJO_COLUMN = { "uriage_rireki_id", "busho_id", "record_id" };

	// 売上計上訂正
	protected static final String R_URIAGE_KEIJO_TEISEI = "r_uriage_keijo_teisei";
	protected static final String[] R_URIAGE_KEIJO_TEISEI_COLUMN = {
			"uriage_keijo_id",
			"uriage_rireki_id",
			"record_id" };

	// 売掛
	protected static final String T_URIKAKE = "T_URIKAKE";
	protected static final String[] T_URIKAKE_COLUMN = { "uriage_id", "kingaku", "nyukin_yotei_date", "record_id" };

	// 請求
	protected static final String T_SEIKYU = "T_SEIKYU";
	protected static final String[] T_SEIKYU_COLUMN = {
			"kokyaku_id",
			"tanto_busho_id",
			"nyukin_hoho_kubun",
			"seikyu_date",
			"shiharai_date",
			"record_id" };

	// 請求売掛
	protected static final String J_SEIKYU_URIKAKE = "J_SEIKYU_URIKAKE";
	protected static final String[] J_SEIKYU_URIKAKE_COLUMN = { "urikake_id", "seikyu_id", "record_id" };

	// 消込
	protected static final String T_KESHIKOMI = "T_KESHIKOMI";
	protected static final String[] T_KESHIKOMI_COLUMN = {
			"id",
			"date",
			"kingaku",
			"nyukin_id",
			"urikake_id",
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
