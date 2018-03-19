package com.showka.repository.i;

import org.springframework.data.jpa.repository.JpaRepository;

import com.showka.entity.TUriage;
import com.showka.entity.TUriagePK;
import com.showka.entity.TUrikakeZandaka;

public interface TUrikakeZandakaRepository
		extends JpaRepository<TUrikakeZandaka, String>, RepositoryCustom<TUriage, TUriagePK> {

}
