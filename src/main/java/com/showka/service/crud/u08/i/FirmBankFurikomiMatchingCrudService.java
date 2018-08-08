package com.showka.service.crud.u08.i;

import com.showka.domain.FBFurikomiMatched;

/**
 * FB振込マッチングサービス.
 */
public interface FirmBankFurikomiMatchingCrudService {

	/**
	 * 全削除.
	 */
	public void deleteAll();

	/**
	 * 登録.
	 * 
	 * <pre>
	 * 排他制御なし。
	 * </pre>
	 * 
	 * @param domain
	 *            FB振込Matched
	 */
	public void save(FBFurikomiMatched domain);

}
