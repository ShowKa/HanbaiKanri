package com.showka.service.crud.u08.i;

import java.util.List;

import com.showka.domain.Seikyu;

/**
 * FB振分データのCrudService
 */
public interface FirmBankFuriwakeCrudService {

	/**
	 * 全削除.
	 */
	public void deleteAll();

	/**
	 * 保存.
	 * 
	 * <pre>
	 * 排他制御対象外
	 * insertのみを想定。
	 * </pre>
	 * 
	 * @param seikyuList
	 *            請求リスト
	 */
	public void save(List<Seikyu> seikyuList);

	/**
	 * 保存.
	 * 
	 * <pre>
	 * 排他制御対象外。
	 * insertのみを想定。
	 * </pre>
	 * 
	 * @param seikyu
	 *            請求
	 */
	public void save(Seikyu seikyu);

}
