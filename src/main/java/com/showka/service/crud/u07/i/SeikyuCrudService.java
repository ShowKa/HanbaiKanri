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
}