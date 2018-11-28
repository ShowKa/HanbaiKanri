package com.showka.service.persistence.u11.i;

import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shohin;
import com.showka.value.EigyoDate;

public interface ShohinZaikoPersistence {
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
	public void saveZeroIfEmpty(Busho busho, EigyoDate date, Shohin shohin);
}
