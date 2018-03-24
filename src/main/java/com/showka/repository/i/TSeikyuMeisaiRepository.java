package com.showka.repository.i;

import org.springframework.data.jpa.repository.JpaRepository;

import com.showka.entity.TSeikyuMeisai;
import com.showka.entity.TSeikyuMeisaiPK;

public interface TSeikyuMeisaiRepository
		extends JpaRepository<TSeikyuMeisai, TSeikyuMeisaiPK>, RepositoryCustom<TSeikyuMeisai, TSeikyuMeisaiPK> {

}
