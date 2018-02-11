package com.showka.service.crud.u11.i;

import java.util.List;

import com.showka.domain.BushoDomain;
import com.showka.domain.ShohinDomain;
import com.showka.domain.ShohinIdoDomain;
import com.showka.service.crud.CrudService;
import com.showka.value.TheDate;

public interface ShohinIdoCrudService extends CrudService<ShohinIdoDomain, String> {

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
	public List<ShohinIdoDomain> getShohinIdoListInDate(BushoDomain busho, TheDate date, ShohinDomain shohin);
}
