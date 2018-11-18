package com.showka.service.search.u01.i;

import java.util.List;

import com.showka.entity.MKokyaku;

/**
 * 顧客Crud
 * 
 * @author kanemotoshouta
 *
 */
public interface KokyakuSearch {

	/**
	 * 顧客名検索
	 * 
	 * @param name
	 *            顧客名
	 * @return 顧客
	 */
	public List<MKokyaku> getKokyauByName(String name);

	/**
	 * 顧客検索
	 * 
	 * @param criteria
	 *            検索条件
	 * @return 顧客
	 */
	public List<MKokyaku> search(KokyakuSearchCriteria criteria);
}
