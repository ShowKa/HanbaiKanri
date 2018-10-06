package com.showka.service.validate.u08.i;

import com.showka.domain.u08.NyukinKeshikomi;
import com.showka.system.exception.ValidateException;

public interface NyukinKeshikomiValidateService {

	/**
	 * 整合性検証.
	 * 
	 * <pre>
	 * エラー
	 * 1. 消込.金額の合計 > 入金.金額
	 * 2. 消込.金額 > 売掛.残高
	 * 3. 1つの売掛への消込が、2つ以上存在する
	 * </pre>
	 * 
	 * @param nyukinKeshikomi
	 *            入金消込
	 */
	public void validate(NyukinKeshikomi nyukinKeshikomi) throws ValidateException;
}
