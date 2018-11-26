package com.showka.service.query.u08.i;

import com.showka.domain.u08.NyukinKeshikomi;

public interface NyukinKeshikomiQuery {

	/**
	 * 消込有無.
	 * 
	 * @param nyukinId
	 *            入金ID
	 * @return 消込ありの場合true
	 */
	public boolean hasKeshikomi(String nyukinId);

	/**
	 * 入金消込取得.
	 * 
	 * @param nyukinId
	 *            入金ID
	 * @return 入金消込
	 */
	public NyukinKeshikomi getDomain(String nyukinId);
}
