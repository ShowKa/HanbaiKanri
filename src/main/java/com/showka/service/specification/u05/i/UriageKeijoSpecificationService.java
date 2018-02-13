package com.showka.service.specification.u05.i;

import com.showka.domain.Uriage;

/**
 * 売上の計上の仕様.
 * 
 * @author ShowKa
 *
 */
public interface UriageKeijoSpecificationService {

	/**
	 * 計上済みか否かを判定する。
	 * 
	 * <pre>
	 * 計上済み判定の仕様
	 * 顧客の主観部署の営業日 > 売上の計上日
	 * </pre>
	 * 
	 * @param uriage
	 *            売上ドメイン
	 * @return true=計上済み
	 */
	public boolean isKeijoZumi(Uriage uriage);
}
