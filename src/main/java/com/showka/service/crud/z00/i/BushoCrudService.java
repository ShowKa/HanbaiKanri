package com.showka.service.crud.z00.i;

import java.util.List;

import com.showka.domain.BushoDomain;
import com.showka.entity.MBusho;

public interface BushoCrudService {

	public BushoDomain getDomain(String pk);

	public List<BushoDomain> getDomains();

	@Deprecated
	public List<MBusho> getMBushoList();

}
