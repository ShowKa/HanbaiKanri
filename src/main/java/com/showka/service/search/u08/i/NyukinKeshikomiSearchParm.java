package com.showka.service.search.u08.i;

import java.util.List;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shain;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NyukinKeshikomiSearchParm {
	/** 顧客. */
	private Kokyaku kokyaku;
	/** 部署. */
	private Busho busho;
	/** 入金日. */
	private EigyoDate minNyukinDate;
	/** 入金日. */
	private EigyoDate maxNyukinDate;
	/** 金額. */
	private AmountOfMoney minKingaku;
	/** 金額. */
	private AmountOfMoney maxKingaku;
	/** 入金方法区分. */
	private List<NyukinHohoKubun> nyukinHoho;
	/** 消込完了. */
	private boolean includeKeshikomiDone;
	/** 担当社員コード. */
	private Shain tantoShain;
}
