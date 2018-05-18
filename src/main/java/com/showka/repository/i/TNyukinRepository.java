package com.showka.repository.i;

import org.springframework.data.jpa.repository.JpaRepository;

import com.showka.entity.TNyukin;

public interface TNyukinRepository extends JpaRepository<TNyukin, String>, RepositoryCustom<TNyukin, String> {

}
