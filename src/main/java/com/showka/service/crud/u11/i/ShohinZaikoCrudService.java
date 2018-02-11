package com.showka.service.crud.u11.i;

import java.util.List;

import com.showka.domain.BushoDomain;
import com.showka.domain.ShohinDomain;
import com.showka.domain.ShohinZaikoDomain;
import com.showka.value.TheDate;

public interface ShohinZaikoCrudService {
	/**
	 * 
	 * 商品在庫取得.
	 * 
	 * @param busho
	 *            部署
	 * @param date
	 *            商品移動日付
	 * @param shohin
	 *            商品
	 * @return 商品在庫
	 */
	public ShohinZaikoDomain getShohinZaiko(BushoDomain busho, TheDate date, ShohinDomain shohin);

	/**
	 * 
	 * すべての商品在庫取得.
	 * 
	 * @param busho
	 *            部署
	 * @param date
	 *            商品移動日付
	 * @return 商品在庫
	 */
	public List<ShohinZaikoDomain> getShohinZaiko(BushoDomain busho, TheDate date);

}
