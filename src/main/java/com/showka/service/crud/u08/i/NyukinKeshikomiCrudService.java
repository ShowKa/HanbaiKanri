package com.showka.service.crud.u08.i;

import com.showka.domain.NyukinKeshikomi;

public interface NyukinKeshikomiCrudService {
	/**
	 * 保存.
	 * 
	 * @param nyukinKeshikomi
	 *            入金消込
	 */
	public void save(NyukinKeshikomi nyukinKeshikomi);

	/**
	 * 入金消込取得.
	 * 
	 * @param nyukinId
	 *            入金ID
	 * @return 入金消込
	 */
	public NyukinKeshikomi getDomain(String nyukinId);
}
