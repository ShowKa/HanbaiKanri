package com.showka.service.specification.u11.i;

import java.util.List;

import com.showka.domain.ShohinIdoDomain;
import com.showka.domain.UriageDomain;

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
	public List<ShohinIdoDomain> buildShohinIdo(UriageDomain uriage);
}