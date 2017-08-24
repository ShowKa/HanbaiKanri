package com.showka.service.crud;

import com.showka.domain.DomainBase;

public interface CrudServie<T extends DomainBase> {

	/**
	 * 保管.
	 * 
	 * @param entity
	 * @return
	 */
	void save(T entity);

}
