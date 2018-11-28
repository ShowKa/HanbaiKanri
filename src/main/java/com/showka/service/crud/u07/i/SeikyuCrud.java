package com.showka.service.crud.u07.i;

import com.showka.domain.u07.Seikyu;
import com.showka.entity.TSeikyuPK;
import com.showka.service.crud.Crud;

public interface SeikyuCrud extends Crud<Seikyu, TSeikyuPK> {

	/**
	 * 請求取得.
	 * 
	 * @param seikyuId
	 *            請求ID
	 * @return 請求.
	 */
	public Seikyu getDomain(String seikyuId);
}
