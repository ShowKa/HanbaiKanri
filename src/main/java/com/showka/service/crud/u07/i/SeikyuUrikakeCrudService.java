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
	 * ・$営業日を基準にして$締日リスト取得
	 * ・顧客Tableから締日の$顧客リストを取得
	 * ・売掛Tableから$顧客の$売掛リスト取得
	 * ・$顧客 & $売掛リストから$請求を構築
	 * ・$請求を請求Tableに登録
	 * ・売掛Tableの入金予定日を$支払日で更新
	 * ・請求中売掛Table更新
	 * </pre>
	 * 
	 * @param busho
	 *            部署
	 * @param shimeDate
	 *            営業日（請求を行う営業日）
	 */
	public void seikyu(Busho busho, EigyoDate eigyoDate);

	/**
	 * 請求を行う.
	 * 
	 * <pre>
	 * ・売掛Tableから$顧客の$売掛リスト取得
	 * ・$顧客 & $売掛リストから$請求を構築
	 * ・$請求を請求Tableに登録
	 * ・売掛Tableの入金予定日を$支払日で更新
	 * ・請求中売掛Table更新
	 * </pre>
	 * 
	 * @param kokyaku
	 *            対象顧客
	 * @param shimeDate
	 *            営業日
	 */
	public void seikyu(Kokyaku kokyaku, EigyoDate eigyoDate);

	/**
	 * 請求を行う.
	 * 
	 * <pre>
	 * ・$顧客 & $売掛リストから$請求を構築
	 * ・$請求を請求Tableに登録
	 * ・売掛Tableの入金予定日を$支払日で更新
	 * ・請求中売掛Table更新新
	 * </pre>
	 * 
	 * @param kokyaku
	 *            対象顧客
	 * @param shimeDate
	 *            営業日
	 * @param urikakeList
	 *            売掛 list
	 */
	public void seikyu(Kokyaku kokyaku, EigyoDate eigyoDate, List<Urikake> urikakeList);

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
