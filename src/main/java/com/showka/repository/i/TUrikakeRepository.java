package com.showka.repository.i;

import org.springframework.data.jpa.repository.JpaRepository;

import com.showka.entity.TUrikake;

public interface TUrikakeRepository extends JpaRepository<TUrikake, String>, RepositoryCustom<TUrikake, String> {

}
