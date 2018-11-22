package com.showka.service.crud.u05.i;

import com.showka.domain.u05.UriageMeisai;
import com.showka.entity.TUriageMeisaiPK;
import com.showka.service.crud.MeisaiCrud;

public interface UriageMeisaiCrud extends MeisaiCrud<UriageMeisai, TUriageMeisaiPK> {
	/**
	 * 明細番号の最大値を取得する。
	 * 
	 * @param uriageId
	 *            売上ID
	 * @return 明細番号の最大値
	 */
	public Integer getMaxMeisaiNumber(String uriageId);
}
