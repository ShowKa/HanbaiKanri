package com.showka.service.persistence.u11.i;

import com.showka.service.specification.u11.i.ShohinIdoSpecification;
import com.showka.system.exception.specification.UnsatisfiedSpecificationException;

/**
 * 商品移動永続化.
 */
public interface ShohinIdoPersistence {

	/**
	 * 商品移動.
	 * 
	 * @param specification
	 *            商品移動仕様
	 * @return
	 */
	public void shohinIdo(ShohinIdoSpecification specification) throws UnsatisfiedSpecificationException;

	/**
	 * 強制商品移動.
	 * 
	 * @param specification
	 *            商品移動使用
	 * @return
	 */
	public void shohinIdoForcibly(ShohinIdoSpecification specification);

}
