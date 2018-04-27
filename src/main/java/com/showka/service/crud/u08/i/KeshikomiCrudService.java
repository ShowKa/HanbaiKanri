package com.showka.service.crud.u08.i;

import java.util.List;

import com.showka.domain.Keshikomi;

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
	 * 消込リスト取得.
	 * 
	 * <pre>
	 * 入金の消込のリスト返却。
	 * </pre>
	 * 
	 * @param nyukinId
	 *            入金ID
	 * @return 消込マップ
	 */

	public List<Keshikomi> getKeshikomiList(String nyukinId);
}
