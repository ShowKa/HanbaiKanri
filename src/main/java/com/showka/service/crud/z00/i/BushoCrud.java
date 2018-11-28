package com.showka.service.crud.z00.i;

import java.util.List;

import com.showka.domain.z00.Busho;
import com.showka.entity.MBusho;
import com.showka.service.crud.Crud;

public interface BushoCrud extends Crud<Busho, String> {

	public List<Busho> getDomains();

	@Deprecated
	public List<MBusho> getMBushoList();

}
