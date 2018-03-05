package com.showka.service.crud.u11.i;

import com.showka.service.specification.u11.ShohinIdoSpecificationAssociatedWithUriage;

public interface ShohinIdoUriageCrudService {

	/**
	 * 保存.
	 * 
	 * @param specification
	 *            売上に伴う商品移動の仕様.
	 */
	public void save(ShohinIdoSpecificationAssociatedWithUriage specification);
}
