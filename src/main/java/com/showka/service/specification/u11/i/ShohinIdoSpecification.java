package com.showka.service.specification.u11.i;

import java.util.List;

import com.showka.domain.ShohinIdo;
import com.showka.system.exception.ValidateException;

/**
 * 商品移動の仕様.
 * 
 */
public interface ShohinIdoSpecification {
	/**
	 * 売上による商品移動を取得.
	 * 
	 * <pre>
	 * 伝票訂正の場合、マイナス移動も含まれる。
	 * </pre>
	 * 
	 * @param uriage
	 *            売上
	 * @return 商品移動ドメイン
	 */
	public List<ShohinIdo> getShohinIdo();

	/**
	 * 整合性検証.
	 * 
	 * @throws ValidateException
	 */
	public void validate() throws ValidateException;
}
