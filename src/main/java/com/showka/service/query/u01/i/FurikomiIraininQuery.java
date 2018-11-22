package com.showka.service.query.u01.i;

import com.showka.domain.u01.FurikomiIraininSet;
import com.showka.domain.u01.Kokyaku;

public interface FurikomiIraininQuery {

	/**
	 * 振込依頼人Set検索.
	 * 
	 * @param kokyaku
	 *            顧客
	 * @return 顧客の振込依頼人Set
	 */
	public FurikomiIraininSet get(Kokyaku kokyaku);

}
