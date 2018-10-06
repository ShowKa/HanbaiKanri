package com.showka.service.crud.u01.i;

import com.showka.domain.u01.NyukinKakeInfo;
import com.showka.service.crud.CrudService;

public interface NyukinKakeInfoCrudService extends CrudService<NyukinKakeInfo, String> {

	/**
	 * 排他制御なしの強制削除.
	 * 
	 * <pre>
	 * ルートドメインの更新に伴い、不要となったレコードの削除したい時に使用することを想定。
	 * ルートドメインの更新時に排他制御を行うことで、整合性を保証する。
	 * </pre>
	 * 
	 * @param pk
	 */
	public void deleteForciblyIfExists(String pk);

}
