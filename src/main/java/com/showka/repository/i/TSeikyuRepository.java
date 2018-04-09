package com.showka.repository.i;

import org.springframework.data.jpa.repository.JpaRepository;

import com.showka.entity.TSeikyu;
import com.showka.entity.TSeikyuPK;

public interface TSeikyuRepository extends JpaRepository<TSeikyu, TSeikyuPK>, RepositoryCustom<TSeikyu, TSeikyuPK> {

}
