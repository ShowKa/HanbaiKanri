package com.showka.repository.i;

import org.springframework.data.jpa.repository.JpaRepository;

import com.showka.entity.CUriage;

public interface CUriageRepository extends JpaRepository<CUriage, String>, RepositoryCustom<CUriage, String> {

}
