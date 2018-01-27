package com.showka.repository.i;

import org.springframework.data.jpa.repository.JpaRepository;

import com.showka.entity.RUriageMeisai;
import com.showka.entity.RUriageMeisaiPK;

public interface RUriageMeisaiRepository
		extends JpaRepository<RUriageMeisai, RUriageMeisaiPK>, RepositoryCustom<RUriageMeisai, RUriageMeisaiPK> {

}
