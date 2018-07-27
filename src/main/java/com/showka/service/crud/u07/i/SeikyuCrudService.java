package com.showka.service.crud.u07.i;

import com.showka.domain.Seikyu;
import com.showka.entity.TSeikyuPK;
import com.showka.service.crud.CrudService;
import com.showka.service.specification.u07.i.SeikyuSpecification;

public interface SeikyuCrudService extends CrudService<Seikyu, TSeikyuPK> {

	/**
	 * 請求.
	 * 
	 * @param spec
	 *            請求仕様
	 */
	public void save(SeikyuSpecification spec);

	/**
	 * 請求取得.
	 * 
	 * @param spec
	 *            請求仕様
	 * @return 請求.
	 */
	public Seikyu getDomain(SeikyuSpecification spec);

	/**
	 * 請求取得.
	 * 
	 * @param seikyuId
	 *            請求ID
	 * @return 請求.
	 */
	public Seikyu getDomain(String seikyuId);
}
