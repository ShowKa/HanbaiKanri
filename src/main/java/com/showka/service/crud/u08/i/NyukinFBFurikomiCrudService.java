package com.showka.service.crud.u08.i;

import com.showka.domain.MatchedFBFurikomi;

/**
 * マッチング済FB振込を入金登録
 */
public interface NyukinFBFurikomiCrudService {
	/**
	 * 
	 * マッチング済FB振込を入金登録
	 * 
	 * @param mathedFBFurikomi
	 *            マッチング済FB振込
	 */
	public void save(MatchedFBFurikomi mathedFBFurikomi);
}
