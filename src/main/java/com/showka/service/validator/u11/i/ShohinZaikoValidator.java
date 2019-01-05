package com.showka.service.validator.u11.i;

import com.showka.domain.u11.ShohinIdo;
import com.showka.system.exception.specification.MinusZaikoException;

public interface ShohinZaikoValidator {

	/**
	 * 商品マイナス在庫検証.
	 * 
	 * <pre>
	 * 商品移動の結果、在庫がマイナスとなる商品がある場合エラー.
	 * </pre>
	 * 
	 * @param shohinIDo
	 *            商品移動
	 * @throws MinusZaikoException
	 *             マイナス在庫例外.
	 */
	public void validateMinusZaiko(ShohinIdo shohinIDo) throws MinusZaikoException;
}
