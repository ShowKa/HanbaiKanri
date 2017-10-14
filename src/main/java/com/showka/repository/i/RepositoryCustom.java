package com.showka.repository.i;

import org.springframework.data.jpa.repository.Query;

import com.showka.entity.EntityBase;

public interface RepositoryCustom<T extends EntityBase, ID> {

	/**
	 * record_id で 取得.
	 * 
	 * @param recordId
	 *            record_id
	 * @return
	 */
	@Query("select u from #{#entityName} u where u.recordId = ?1")
	T findByRecordId(String recordId);
}
