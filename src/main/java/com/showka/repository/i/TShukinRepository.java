package com.showka.repository.i;

import org.springframework.data.jpa.repository.JpaRepository;

import com.showka.entity.TShukin;

/**
 * 集金Repository
 */
public interface TShukinRepository extends JpaRepository<TShukin, String>, RepositoryCustom<TShukin, String> {

}
