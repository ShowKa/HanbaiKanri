package com.showka.service.crud.u11.i;

import java.util.Optional;

import com.showka.domain.ShohinIdo;
import com.showka.domain.Uriage;
import com.showka.entity.TUriagePK;

public interface ShohinIdoUriageCrudService {

	/**
	 * 保存.
	 * 
	 * @param uriage
	 *            売上
	 */
	public void save(Uriage uriage);

	/**
	 * 商品移動取得.
	 * 
	 * @param uriageId
	 *            売上ID
	 * @return 商品移動
	 */
	public Optional<ShohinIdo> getNewestShohinIdo(String uriageId);

	/**
	 * 商品移動削除.
	 * 
	 * @param pk
	 */
	public void delete(TUriagePK pk);
}
