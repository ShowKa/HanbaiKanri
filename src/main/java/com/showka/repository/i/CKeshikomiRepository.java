package com.showka.repository.i;

import org.springframework.data.jpa.repository.JpaRepository;

import com.showka.entity.CKeshikomi;

public interface CKeshikomiRepository extends JpaRepository<CKeshikomi, String>, RepositoryCustom<CKeshikomi, String> {

}
