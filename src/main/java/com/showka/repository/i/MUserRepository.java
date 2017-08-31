package com.showka.repository.i;

import org.springframework.data.jpa.repository.JpaRepository;

import com.showka.entity.MUser;

public interface MUserRepository extends JpaRepository<MUser, Long> {

}
