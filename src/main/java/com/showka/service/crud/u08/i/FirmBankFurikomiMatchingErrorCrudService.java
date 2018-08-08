package com.showka.service.crud.u08.i;

import com.showka.kubun.FurikomiMatchintErrorCause;

/**
 * FB振込マッチングサービス.
 */
public interface FirmBankFurikomiMatchingErrorCrudService {

	/**
	 * 登録.
	 */
	public void save(String fbFurikomiId, FurikomiMatchintErrorCause cause);

}
