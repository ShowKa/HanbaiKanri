package com.showka.service.query.u05.i;

import java.util.List;

import com.showka.domain.u05.Uriage;

public interface UriageQuery {

	/**
	 * 顧客の売上リストを取得する.
	 * 
	 * @param kokyakuCode
	 *            顧客コード
	 * @return 売上リスト
	 */
	public List<Uriage> getUriageOfKokyaku(String kokyakuCode);

}
