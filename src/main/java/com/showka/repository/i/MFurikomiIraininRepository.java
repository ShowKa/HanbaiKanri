package com.showka.repository.i;

import org.springframework.data.jpa.repository.JpaRepository;

import com.showka.entity.MFurikomiIrainin;
import com.showka.entity.MFurikomiIraininPK;

public interface MFurikomiIraininRepository extends JpaRepository<MFurikomiIrainin, MFurikomiIraininPK>,
		RepositoryCustom<MFurikomiIrainin, MFurikomiIraininPK> {

}
