package com.showka.repository.i;

import org.springframework.data.jpa.repository.JpaRepository;

import com.showka.entity.TNyukinKeijo;

public interface TNyukinKeijoRepository
		extends JpaRepository<TNyukinKeijo, String>, RepositoryCustom<TNyukinKeijo, String> {

}
