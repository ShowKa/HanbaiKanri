package com.showka.service.crud.u08.i;

import java.util.Map;

import com.showka.domain.Keshikomi;
import com.showka.domain.Nyukin;
import com.showka.domain.Urikake;

public interface KeshikomiCrudService {

	/**
	 * 消込保存.
	 * 
	 * @param nyukin
	 *            入金
	 * @param urikake
	 *            売掛
	 * @param keshikomi
	 *            消込
	 */
	public void save(Nyukin nyukin, Urikake urikake, Keshikomi keshikomi);

	/**
	 * 消込マップ取得.
	 * 
	 * <pre>
	 * 消込とそれに対応する売掛のマップを返却。
	 * </pre>
	 * 
	 * @param nyukinId
	 *            入金ID
	 * @return 消込マップ
	 */
	public Map<Keshikomi, Urikake> getKeshikomiMap(String nyukinId);
}
