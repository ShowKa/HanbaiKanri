package com.showka.service.specification.u11.i;

import java.util.List;

import com.showka.domain.ShohinIdo;
import com.showka.system.exception.UnsatisfiedSpecificationException;

/**
 * 商品移動の仕様.
 * 
 */
public interface ShohinIdoSpecification {

	/**
	 * 商品移動取得.
	 * 
	 * @return
	 */
	public List<ShohinIdo> getShohinIdo();

	/**
	 * 業務ルールに則っているか否かを検証する.
	 * 
	 * @throws UnsatisfiedSpecificationException
	 */
	public void ascertainSatisfaction() throws UnsatisfiedSpecificationException;
}
