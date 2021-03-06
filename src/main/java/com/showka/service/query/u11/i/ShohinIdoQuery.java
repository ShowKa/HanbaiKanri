package com.showka.service.query.u11.i;

import java.util.List;

import com.showka.domain.u11.ShohinIdo;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shohin;
import com.showka.value.TheDate;

public interface ShohinIdoQuery {

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

}
