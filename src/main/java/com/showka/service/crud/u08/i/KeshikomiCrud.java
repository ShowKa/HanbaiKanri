package com.showka.service.crud.u08.i;

import com.showka.domain.u08.Keshikomi;
import com.showka.service.crud.Crud;

public interface KeshikomiCrud extends Crud<Keshikomi, String> {
	/**
	 * 消込保存.
	 * 
	 * <pre>
	 * 消込が完了した場合は、請求状態を解除する（請求・売掛関係テーブルのレコードも削除する）。
	 * </pre>
	 * 
	 * @param keshikomi
	 *            消込
	 */
	@Override
	public void save(Keshikomi keshikomi);
}
