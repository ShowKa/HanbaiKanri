package com.showka.searvice.crud.z00.i;

import com.showka.entity.MBusho;

public interface MBushoCrudService {

	/**
	 * 主キー検索
	 * 
	 * @param key
	 * @return
	 */
	public MBusho findById(String key);

}
