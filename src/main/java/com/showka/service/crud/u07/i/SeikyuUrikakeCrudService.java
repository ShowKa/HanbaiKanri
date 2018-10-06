package com.showka.service.crud.u07.i;

import java.util.List;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u06.Urikake;
import com.showka.domain.z00.Busho;
import com.showka.value.EigyoDate;

public interface SeikyuUrikakeCrudService {
	/**
	 * 請求を行う.
	 * 
	 * <pre>
	 * ・締日を迎えた顧客を取得
	 * ・顧客の売掛を取得
	 * ・請求データ作成
	 * ・請求した売掛の入金予定日を更新
	 * </pre>
	 * 
	 * @param busho
	 *            対象部署
	 * @param shimeDate
	 *            締日
	 */
	public void seikyu(Busho busho, EigyoDate shimeDate);

	/**
	 * 請求を行う.
	 * 
	 * <pre>
	 * ・顧客の売掛を取得
	 * ・請求データ作成
	 * ・請求した売掛の入金予定日を更新
	 * </pre>
	 * 
	 * @param kokyaku
	 *            対象顧客
	 * @param shimeDate
	 *            締日
	 */
	public void seikyu(Kokyaku kokyaku, EigyoDate shimeDate);

	/**
	 * 請求を行う.
	 * 
	 * <pre>
	 * ・請求データ作成
	 * ・請求した売掛の入金予定日を更新
	 * </pre>
	 * 
	 * @param kokyaku
	 *            対象顧客
	 * @param shimeDate
	 *            締日
	 * @param urikakeList
	 *            売掛 list
	 */
	public void seikyu(Kokyaku kokyaku, EigyoDate shimeDate, List<Urikake> urikakeList);

	/**
	 * 対象レコードが存在した場合のみ、請求・売掛を削除する。
	 * 
	 * @param urikakeId
	 *            売掛ID
	 */
	public void deleteIfExists(String urikakeId);

	/**
	 * 請求・売掛を復活させる。
	 * 
	 * <pre>
	 * 消込完了等で削除対象となったレコードを復活させる。
	 * 売掛の最新請求を検索し、その請求を対象とする。
	 * ただし、対象レコードが既存の場合は何もせず処理終了。
	 * また、請求済みでない場合も何もせず処理終了。
	 * </pre>
	 * 
	 * @param urikakeId
	 */
	public void revert(String urikakeId);

}
