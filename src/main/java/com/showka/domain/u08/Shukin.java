package com.showka.domain.u08;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shain;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

import lombok.Getter;

/**
 * 集金.
 */
@Getter
public class Shukin extends Nyukin {

	/** 伝票番号. */
	private String denpyoNumber;

	/** 担当社員 */
	private Shain tantoShain;

	/**
	 * constructor.
	 * 
	 * @param kokyaku
	 * @param busho
	 * @param date
	 * @param nyukinHohoKubun
	 * @param kingaku
	 * @param tantoShain
	 */
	public Shukin(Kokyaku kokyaku, Busho busho, EigyoDate date, NyukinHohoKubun nyukinHohoKubun, AmountOfMoney kingaku,
			String denpyoNumber, Shain tantoShain) {
		super(kokyaku, busho, date, nyukinHohoKubun, kingaku);
		this.denpyoNumber = denpyoNumber;
		this.tantoShain = tantoShain;
	}

	// getter
	/**
	 * 担当社員ID取得.
	 * 
	 * @return 社員ID
	 */
	public String getTantoShainId() {
		return tantoShain.getRecordId();
	}

	/**
	 * 入金ID(集金.レコードID）を取得。
	 * 
	 * @return 入金ID
	 */
	public String getNyukinId() {
		return getRecordId();
	}
}
