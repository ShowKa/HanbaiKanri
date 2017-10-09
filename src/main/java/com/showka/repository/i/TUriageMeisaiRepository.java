package com.showka.repository.i;

import org.springframework.data.jpa.repository.JpaRepository;

import com.showka.entity.TUriageMeisai;
import com.showka.entity.TUriageMeisaiPK;

public interface TUriageMeisaiRepository
		extends JpaRepository<TUriageMeisai, TUriageMeisaiPK>, RepositoryCustom<TUriageMeisai, TUriageMeisaiPK> {

}
