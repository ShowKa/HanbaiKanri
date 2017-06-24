package com.showka.u01.service.i;

import java.util.List;

import com.showka.entity.MKokyaku;

/**
 * 顧客Crud
 * 
 * @author kanemotoshouta
 *
 */
public interface MKokyakuCrudService {

	/**
	 * 顧客名検索
	 * 
	 * @param name
	 *            顧客名
	 * @return 顧客
	 */
	public List<MKokyaku> getKokyauByName(String name);
}
