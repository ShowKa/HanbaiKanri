package com.showka.service.crud.u11.i;

import java.util.List;

import com.showka.domain.Busho;
import com.showka.domain.Shohin;
import com.showka.domain.ShohinZaiko;
import com.showka.value.EigyoDate;
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
	public ShohinZaiko getShohinZaiko(Busho busho, TheDate date, Shohin shohin);

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
	public List<ShohinZaiko> getShohinZaiko(Busho busho, TheDate date);

	/**
	 * 商品在庫繰越.
	 * 
	 * <pre>
	 * 営業日更新にあたり、更新後営業日分の在庫データを作ります。
	 * ただし、在庫数=0の商品は繰越対象外。
	 * </pre>
	 * 
	 * @param busho
	 *            部署.
	 * @param eigyoDate
	 *            営業日（更新前）
	 */
	public void kurikoshi(Busho busho, EigyoDate eigyoDate);

	/**
	 * 指定した在庫データが存在しなかった場合、ゼロ在庫データを登録します.
	 * 
	 * <pre>
	 * すでにデータがある場合は無視されます。
	 * </pre>
	 * 
	 * @param busho
	 *            部所
	 * @param date
	 *            日付
	 * @param shohin
	 *            商品
	 */
	public void saveZeroIfEmpty(Busho busho, TheDate date, Shohin shohin);
}
