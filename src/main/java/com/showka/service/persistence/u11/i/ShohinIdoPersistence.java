package com.showka.service.persistence.u11.i;

import java.util.List;

import com.showka.domain.u11.ShohinIdo;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shohin;
import com.showka.service.persistence.Persistence;
import com.showka.service.specification.u11.i.ShohinIdoSpecification;
import com.showka.system.exception.UnsatisfiedSpecificationException;
import com.showka.value.TheDate;

public interface ShohinIdoPersistence extends Persistence<ShohinIdo, String> {

	/**
	 * 商品移動リスト取得.
	 * 
	 * <pre>
	 * 対象日における対象商品を含む商品移動をすべて取得する.
	 * 
	 * </pre>
	 * 
	 * @param busho
	 *            部署
	 * @param date
	 *            商品移動日
	 * @param shohin
	 *            商品
	 * @return 商品移動リスト
	 */
	public List<ShohinIdo> getShohinIdoListInDate(Busho busho, TheDate date, Shohin shohin);

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

	/**
	 * 強制削除.
	 * 
	 * @param id
	 *            商品移動ID
	 */
	public void deleteForcibly(String id);
}