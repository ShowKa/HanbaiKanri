package com.showka.service.crud.z00;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.BushoDomain;
import com.showka.domain.builder.BushoDomainBuilder;
import com.showka.entity.MBusho;
import com.showka.entity.MBushoDate;
import com.showka.kubun.BushoKubun;
import com.showka.kubun.JigyoKubun;
import com.showka.kubun.i.Kubun;
import com.showka.repository.i.MBushoDateRepository;
import com.showka.repository.i.MBushoRepository;
import com.showka.service.crud.z00.i.BushoCrudService;
import com.showka.value.EigyoDate;

@Service
public class BushoCrudServiceImpl implements BushoCrudService {

	@Autowired
	private MBushoRepository repo;

	@Autowired
	private MBushoDateRepository dateRepo;

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

		// date
		MBushoDate bushoDateEntity = dateRepo.getOne(result.getRecordId());
		EigyoDate eigyoDate = new EigyoDate(bushoDateEntity.getEigyoDate());
		builder.withEigyoDate(eigyoDate);

		return builder.build();
	}

	@Override
	public List<MBusho> getMBushoList() {

		return repo.findAll();

	}

}
