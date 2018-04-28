package com.showka.service.crud.u08.i;

import java.util.Collection;
import java.util.Set;

import com.showka.domain.Keshikomi;
import com.showka.value.EigyoDate;

public interface KeshikomiCrudService {

	/**
	 * 消込保存.
	 * 
	 * @param nyukin
	 *            入金
	 * @param urikake
	 *            売掛
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
}
