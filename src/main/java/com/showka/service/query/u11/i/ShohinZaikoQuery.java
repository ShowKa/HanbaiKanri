package com.showka.service.query.u11.i;

import java.util.List;

import com.showka.domain.u11.ShohinZaiko;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shohin;
import com.showka.value.EigyoDate;

public interface ShohinZaikoQuery {

	/**
	 * 
	 * 商品在庫取得.
	 * 
	 * <pre>
	 * 引数.商品の在庫がない場合、繰越=0, 移動=0の空在庫instanceを返却する。
	 * </pre>
	 * 
	 * @param busho
	 *            部署
	 * @param date
	 *            日付（営業日）
	 * @param shohin
	 *            商品
	 * @return 商品在庫
	 */
	public ShohinZaiko get(Busho busho, EigyoDate date, Shohin shohin);

	/**
	 * 
	 * 部署のすべての商品在庫取得.
	 * 
	 * @param busho
	 *            部署
	 * @param date
	 *            日付（営業日）
	 * @return 商品在庫
	 */
	public List<ShohinZaiko> get(Busho busho, EigyoDate date);

}
