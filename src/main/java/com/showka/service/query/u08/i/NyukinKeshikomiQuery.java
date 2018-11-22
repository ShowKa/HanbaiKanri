package com.showka.service.query.u08.i;

/**
 * 入金消込仕様Service.
 */
public interface NyukinKeshikomiQuery {

	/**
	 * 消込有無.
	 * 
	 * @param nyukinId
	 *            入金ID
	 * @return 消込ありの場合true
	 */
	public boolean hasKeshikomi(String nyukinId);
}
