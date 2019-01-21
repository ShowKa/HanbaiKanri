package com.showka.service.construct.u05.i;

import com.showka.domain.u05.Uriage;
import com.showka.entity.TUriagePK;

public interface UriageCancelConstruct {

	/**
	 * 売上キャンセル用のインスタンスを構築
	 * 
	 * @param pk
	 *            売上PK
	 * @return 売上
	 */
	public Uriage by(TUriagePK pk);
}
