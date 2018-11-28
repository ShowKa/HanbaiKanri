package com.showka.service.persistence.u08.i;

import com.showka.kubun.FurikomiMatchintErrorCause;

/**
 * FB振込マッチングサービス.
 */
public interface FirmBankFurikomiMatchingErrorPersistence {

	/**
	 * 登録.
	 */
	public void save(String fbFurikomiId, FurikomiMatchintErrorCause cause);

}
