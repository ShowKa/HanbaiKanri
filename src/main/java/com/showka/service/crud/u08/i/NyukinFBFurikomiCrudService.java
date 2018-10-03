package com.showka.service.crud.u08.i;

import com.showka.domain.MatchedFBFurikomi;
import com.showka.domain.Nyukin;

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

	/**
	 * FB振込による入金を取得する.
	 * 
	 * @param fbFurikomiId
	 *            FB振込ID
	 * @return 入金
	 */
	public Nyukin getNyukin(String fbFurikomiId);

}
