package com.showka.service.persistence.u06.i;

import com.showka.domain.u06.UrikakeKeshikomi;

public interface UrikakeKeshikomiPersistence {

	/**
	 * 売掛消込取得.
	 * 
	 * @param urikakeId
	 *            売掛ID
	 * @return 売掛消込
	 */
	public UrikakeKeshikomi getDomain(String urikakeId);
}
