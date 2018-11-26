package com.showka.service.persistence.u08.i;

import java.util.Collection;

import com.showka.domain.u08.Keshikomi;
import com.showka.value.EigyoDate;

public interface KeshikomiPersistence {

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
