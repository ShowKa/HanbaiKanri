package com.showka.service.crud.u01.i;

import com.showka.domain.u01.NyukinKakeInfo;
import com.showka.service.crud.Crud;

public interface NyukinKakeInfoCrud extends Crud<NyukinKakeInfo, String> {

	/**
	 * 存在すれば削除.
	 * 
	 * @param kokyakuId
	 *            顧客ID
	 */
	void deleteIfExists(String kokyakuId);
}
