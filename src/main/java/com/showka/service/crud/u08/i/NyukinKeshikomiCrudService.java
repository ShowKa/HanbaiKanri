package com.showka.service.crud.u08.i;

import java.util.Set;

import com.showka.domain.Nyukin;
import com.showka.domain.NyukinKeshikomi;
import com.showka.value.EigyoDate;

public interface NyukinKeshikomiCrudService {
	/**
	 * 保存.
	 * 
	 * @param date
	 *            消込登録対象日付
	 * @param nyukinKeshikomi
	 *            入金消込
	 */
	public void save(EigyoDate date, NyukinKeshikomi nyukinKeshikomi);

	/**
	 * 入金消込取得.
	 * 
	 * @param nyukinId
	 *            入金ID
	 * @return 入金消込
	 */
	public NyukinKeshikomi getDomain(String nyukinId);

	/**
	 * 入金消込キャンセル.
	 * 
	 * @param nyukin
	 *            入金
	 * @param target
	 *            キャンセル対象となる消込ID
	 */
	public void cancel(Nyukin nyukin, Set<String> target);
}
