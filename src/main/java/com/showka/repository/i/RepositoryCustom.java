package com.showka.repository.i;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.showka.entity.EntityBase;

public interface RepositoryCustom<T extends EntityBase, ID> {

	/**
	 * record_id で 取得.
	 * 
	 * @param recordId
	 *            record_id
	 * @return
	 */
	@Query("select u from #{#entityName} u where u.recordId = :recordId")
	T findByRecordId(@Param("recordId") String recordId);
}
