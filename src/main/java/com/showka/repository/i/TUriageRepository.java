package com.showka.repository.i;

import org.springframework.data.jpa.repository.JpaRepository;

import com.showka.entity.TUriage;
import com.showka.entity.TUriagePK;

public interface TUriageRepository extends JpaRepository<TUriage, TUriagePK> {

}
