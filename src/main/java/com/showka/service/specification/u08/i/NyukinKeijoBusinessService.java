package com.showka.service.specification.u08.i;

/**
 * 入金計上業務Service.
 */
public interface NyukinKeijoBusinessService {

	/**
	 * 計上済判定.
	 * 
	 * @param nyukinId
	 *            入金ID
	 * @return 計上済の場合true
	 */
	public boolean keijoDone(String nyukinId);
}
