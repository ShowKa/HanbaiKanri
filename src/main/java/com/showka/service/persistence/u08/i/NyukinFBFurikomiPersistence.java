package com.showka.service.persistence.u08.i;

import com.showka.domain.u08.MatchedFBFurikomi;

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
}
