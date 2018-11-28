package com.showka.service.persistence.u07.i;

import com.showka.service.specification.u07.i.SeikyuSpecification;

public interface SeikyuPersistence {

	/**
	 * 請求.
	 * 
	 * @param spec
	 *            請求仕様
	 */
	public void save(SeikyuSpecification spec);

}
