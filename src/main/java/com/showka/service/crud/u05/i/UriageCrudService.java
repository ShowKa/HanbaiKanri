package com.showka.service.crud.u05.i;

import com.showka.domain.Uriage;
import com.showka.entity.TUriagePK;
import com.showka.service.crud.CrudService;

public interface UriageCrudService extends CrudService<Uriage, TUriagePK> {

	/**
	 * 売上キャンセル.
	 * 
	 * @param domain
	 *            売上ドメイン
	 */
	public void cancel(Uriage domain);

	/**
	 * 売上を取得.
	 * 
	 * @param kokyakuCode
	 *            顧客コード
	 * @param denpyoNumber
	 *            伝票ナンバー
	 * @return 売上
	 */
	public Uriage getDomain(String kokyakuCode, String denpyoNumber);

}
