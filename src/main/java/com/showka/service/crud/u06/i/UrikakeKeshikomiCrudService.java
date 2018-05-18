package com.showka.service.crud.u06.i;

import com.showka.domain.UrikakeKeshikomi;

public interface UrikakeKeshikomiCrudService {

	/**
	 * 売掛消込取得.
	 * 
	 * @param urikakeId
	 *            売掛ID
	 * @return 売掛消込
	 */
	public UrikakeKeshikomi getDomain(String urikakeId);
}
