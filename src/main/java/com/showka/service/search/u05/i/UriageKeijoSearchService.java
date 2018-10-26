package com.showka.service.search.u05.i;

import java.util.List;

import com.showka.domain.z00.Busho;
import com.showka.entity.RUriageKeijo;
import com.showka.value.EigyoDate;

public interface UriageKeijoSearchService {
	/**
	 * 指定した計上日の部署売上計上を取得.
	 * 
	 * @param busho
	 *            部署
	 * @param date
	 *            計上日
	 * @return 売上計上
	 */
	public List<RUriageKeijo> search(Busho busho, EigyoDate date);

	/**
	 * 指定した計上日における部署の売上の計上金額を集計.
	 * 
	 * <pre>
	 * ただし、売上訂正の金額は除く
	 * </pre>
	 * 
	 * @param busho
	 *            部署
	 * @param date
	 *            計上日
	 * @return 集計金額
	 */
	public int getKeijoKingaku(Busho busho, EigyoDate date);

	/**
	 * 指定した計上日における部署の売上の訂正分の計上金額を集計.
	 * 
	 * <pre>
	 * 基本的にマイナス円として集計.
	 * </pre>
	 * 
	 * @param busho
	 *            部署
	 * @param date
	 *            計上日
	 * @return 売上訂正の集計金額
	 */
	public int getTeiseiKingaku(Busho busho, EigyoDate date);
}
