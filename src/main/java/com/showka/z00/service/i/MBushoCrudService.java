package com.showka.z00.service.i;

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
