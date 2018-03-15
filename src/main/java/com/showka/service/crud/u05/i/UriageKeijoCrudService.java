package com.showka.service.crud.u05.i;

import com.showka.domain.Busho;
import com.showka.value.TheDate;

public interface UriageKeijoCrudService {
	/**
	 * 
	 * 売上計上.
	 * 
	 * @param busho
	 *            計上部署.
	 * @param date
	 *            計上日
	 */
	public void keijo(Busho busho, TheDate date);
}