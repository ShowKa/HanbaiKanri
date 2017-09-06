package com.showka.service.crud.z00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.BushoDomain;
import com.showka.domain.builder.BushoDomainBuilder;
import com.showka.entity.MBusho;
import com.showka.kubun.BushoKubun;
import com.showka.kubun.JigyoKubun;
import com.showka.kubun.i.Kubun;
import com.showka.repository.i.MBushoRepository;
import com.showka.service.crud.z00.i.BushoCrudService;

@Service
public class BushoCrudServiceImpl implements BushoCrudService {

	@Autowired
	private MBushoRepository repo;

	@Override
	public BushoDomain getDomain(String pk) {

		MBusho result = repo.getOne(pk);

		BushoDomainBuilder builder = new BushoDomainBuilder();
		builder.withCode(result.getCode());
		builder.withName(result.getName());
		builder.withBushoKubun(Kubun.get(BushoKubun.class, result.getBushoKubun()));
		builder.withJigyoKubun(Kubun.get(JigyoKubun.class, result.getJigyoKubun()));
		builder.withRecordId(result.getRecordId());
		builder.withVersion(result.getVersion());

		return builder.build();
	}

}
