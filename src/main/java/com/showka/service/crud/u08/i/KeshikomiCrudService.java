package com.showka.service.crud.u08.i;

import java.util.Collection;
import java.util.Set;

import com.showka.domain.u08.Keshikomi;
import com.showka.value.EigyoDate;

public interface KeshikomiCrudService {

	/**
	 * 消込保存.
	 * 
	 * <pre>
	 * 消込が完了した場合は、請求状態を解除する（請求・売掛関係テーブルのレコードも削除する）。
	 * </pre>
	 * 
	 * @param keshikomi
	 *            消込
	 */
	public void save(Keshikomi keshikomi);

	/**
	 * 消込上書保存.
	 * 
	 * <pre>
	 * 対象営業日の消込リストを保存。
	 * 既存消込のうち、リストに含まれないものは削除対象となる。
	 * </pre>
	 * 
	 * @param keshikomiList
	 *            消込リスト
	 * @param date
	 *            対象営業日
	 */
	public void override(String nyukinId, EigyoDate date, Collection<Keshikomi> keshikomiList);

	/**
	 * 消込リスト取得.
	 * 
	 * <pre>
	 * 入金の消込のリスト返却。
	 * </pre>
	 * 
	 * @param nyukinId
	 *            入金ID
	 * @return 消込リスト
	 */
	public Set<Keshikomi> getKeshikomiSetOfNyukin(String nyukinId);

	/**
	 * 消込リスト取得.
	 * 
	 * <pre>
	 * 売掛の消込のリスト返却。
	 * </pre>
	 * 
	 * @param urikakeId
	 *            売掛ID
	 * @return 消込リスト
	 */
	public Set<Keshikomi> getKeshikomiSetOfUrikake(String urikakeId);

	/**
	 * キャンセル.
	 * 
	 * <pre>
	 * OCC対象外
	 * </pre>
	 * 
	 * @param keshikomiId
	 *            消込ID
	 * @param date
	 *            キャンセル日付
	 */
	public void cancel(String keshikomiId, EigyoDate date);
}
