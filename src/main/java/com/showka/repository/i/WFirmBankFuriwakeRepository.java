package com.showka.repository.i;

import org.springframework.data.jpa.repository.JpaRepository;

import com.showka.entity.WFirmBankFuriwake;
import com.showka.entity.WFirmBankFuriwakePK;

public interface WFirmBankFuriwakeRepository extends JpaRepository<WFirmBankFuriwake, WFirmBankFuriwakePK>,
		RepositoryCustom<WFirmBankFuriwake, WFirmBankFuriwakePK> {

}
