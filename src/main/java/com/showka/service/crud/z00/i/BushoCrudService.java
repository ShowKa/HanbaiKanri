package com.showka.service.crud.z00.i;

import java.util.List;

import com.showka.domain.z00.Busho;
import com.showka.entity.MBusho;

public interface BushoCrudService {

	public Busho getDomain(String pk);

	public boolean exists(String pk);

	public List<Busho> getDomains();

	@Deprecated
	public List<MBusho> getMBushoList();

}
