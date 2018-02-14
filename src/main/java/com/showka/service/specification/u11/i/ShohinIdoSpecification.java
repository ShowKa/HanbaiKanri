package com.showka.service.specification.u11.i;

import java.util.List;

import com.showka.domain.ShohinIdo;
import com.showka.domain.Uriage;

/**
 * 商品移動の仕様.
 * 
 */
public interface ShohinIdoSpecification {

	/**
	 * 売上による商品移動をビルド.
	 * 
	 * <pre>
	 * 伝票訂正の場合、マイナス移動も含めて返却する。
	 * </pre>
	 * 
	 * @param uriage
	 *            売上
	 * @return 商品移動ドメイン
	 */
	public List<ShohinIdo> buildShohinIdo(Uriage uriage);
}
