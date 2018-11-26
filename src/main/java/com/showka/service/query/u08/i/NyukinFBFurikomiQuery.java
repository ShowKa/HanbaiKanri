package com.showka.service.query.u08.i;

import com.showka.domain.u08.Nyukin;

public interface NyukinFBFurikomiQuery {

	/**
	 * FB振込による入金を取得する.
	 * 
	 * @param fbFurikomiId
	 *            FB振込ID
	 * @return 入金
	 */
	public Nyukin getNyukin(String fbFurikomiId);
}
