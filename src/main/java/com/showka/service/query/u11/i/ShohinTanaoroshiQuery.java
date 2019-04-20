package com.showka.service.query.u11.i;

import com.showka.domain.z00.Busho;
import com.showka.value.TheTimestamp;

// 設計中
public interface ShohinTanaoroshiQuery {

	/**
	 * 商品棚卸中判定.
	 * 
	 * @param busho
	 *            部署
	 * @return 引数.部署が棚卸中ならtrue
	 */
	public boolean onGoing(Busho busho);

	/**
	 * 棚卸済判定
	 * 
	 * @param busho
	 *            部署
	 * @param start
	 *            開始時刻
	 * @param end
	 *            終了時刻
	 * @return 部署が開始〜終了時刻間で棚卸実施済ならtrue
	 */
	public boolean doneBetween(Busho busho, TheTimestamp start, TheTimestamp end);

	/**
	 * 棚卸済判定
	 * 
	 * @param busho
	 *            部署
	 * @param start
	 *            開始時刻
	 * @return 部署が開始〜現在時刻間で棚卸実施済ならtrue
	 */
	public boolean doneBetween(Busho busho, TheTimestamp start);
}
