package com.showka.common;

import junit.framework.TestCase;

public abstract class TestCaseBase extends TestCase {

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

	// 売上履歴
	protected String T_URIAGE_RIREKI = "t_uriage_rireki";
	protected static final String[] T_URIAGE_RIREKI_COLUMN = {
			"uriage_id",
			"uriage_date",
			"keijo_date",
			"hanbai_kubun",
			"shohizeiritsu",
			"record_id" };

}
