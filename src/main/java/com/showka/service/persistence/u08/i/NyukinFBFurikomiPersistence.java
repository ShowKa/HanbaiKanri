package com.showka.service.persistence.u08.i;

import com.showka.domain.u08.MatchedFBFurikomi;
import com.showka.domain.u08.Nyukin;

/**
 * マッチング済FB振込を入金登録
 */
public interface NyukinFBFurikomiPersistence {
	/**
	 * 
	 * マッチング済FB振込を入金登録
	 * 
	 * @param mathedFBFurikomi
	 *            マッチング済FB振込
	 */
	public void save(MatchedFBFurikomi mathedFBFurikomi);

	/**
	 * FB振込による入金を取得する.
	 * 
	 * @param fbFurikomiId
	 *            FB振込ID
	 * @return 入金
	 */
	public Nyukin getNyukin(String fbFurikomiId);

}