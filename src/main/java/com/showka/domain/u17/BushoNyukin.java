package com.showka.domain.u17;

import com.showka.domain.z00.Busho;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

public interface BushoNyukin {
	/**
	 * 部署取得.
	 * 
	 * @return 部署
	 */
	public Busho getBusho();

	/**
	 * 計上日取得.
	 * 
	 * @return 計上日
	 */
	public EigyoDate getKeijoDate();

	/**
	 * 当日計上した現金の入金に関し、計上日時点で消込を実施した分の額を取得する。
	 * 
	 * @return 消込金額（現金）
	 */
	public AmountOfMoney getKeshikomiKingaku_Genkin();

	/**
	 * 当日計上した現金の入金に関し、計上日時点で未処理の金額を取得する。
	 * 
	 * <pre>
	 * 未処理=消込及び充当の対象となっていない。
	 * </pre>
	 * 
	 * @return 未処理金額（現金）
	 */
	public AmountOfMoney getMishoriKingaku_Genkin();

	/**
	 * 当日計上した振込の入金に関し、計上日時点で消込を実施した分の額を取得する。
	 * 
	 * @return 消込金額（振込）
	 */
	public AmountOfMoney getKeshikomiKingaku_Furikomi();

	/**
	 * 当日計上した振込の入金に関し、計上日時点で未処理の金額を取得する。
	 * 
	 * <pre>
	 * 未処理=消込及び充当の対象となっていない。
	 * </pre>
	 * 
	 * @return 未処理金額（振込）
	 */
	public AmountOfMoney getMishoriKingaku_Furikomi();
}
