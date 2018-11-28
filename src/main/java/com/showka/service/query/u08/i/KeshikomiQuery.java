package com.showka.service.query.u08.i;

import java.util.Set;

import com.showka.domain.u08.Keshikomi;

public interface KeshikomiQuery {

	/**
	 * 消込リスト取得.
	 * 
	 * <pre>
	 * 入金の消込のリスト返却。
	 * </pre>
	 * 
	 * @param nyukinId
	 *            入金ID
	 * @return 消込リスト
	 */
	public Set<Keshikomi> getOfNyukin(String nyukinId);

	/**
	 * 消込リスト取得.
	 * 
	 * <pre>
	 * 売掛の消込のリスト返却。
	 * </pre>
	 * 
	 * @param urikakeId
	 *            売掛ID
	 * @return 消込リスト
	 */
	public Set<Keshikomi> getOfUrikake(String urikakeId);

}
