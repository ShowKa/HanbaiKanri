package com.showka.service.crud.u05.i;

import com.showka.domain.Uriage;
import com.showka.domain.Urikake;
import com.showka.service.crud.CrudService;

public interface UrikakeCrudService extends CrudService<Urikake, String> {

	/**
	 * 売上から売掛金を登録する.
	 * 
	 * <pre>
	 * 販売区分 = 掛売の場合なにもしない
	 * </pre>
	 * 
	 * @param uriage
	 *            売上
	 */
	void save(Uriage uriage);
}
