package com.showka.repository.i;

import org.springframework.data.jpa.repository.JpaRepository;

import com.showka.entity.TKeshikomi;

public interface TKeshikomiRepository extends JpaRepository<TKeshikomi, String>, RepositoryCustom<TKeshikomi, String> {

}
