package com.showka.service.crud.z00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.BushoDomain;
import com.showka.domain.ShainDomain;
import com.showka.domain.builder.ShainDomainBuilder;
import com.showka.entity.MShain;
import com.showka.repository.i.MShainRepository;
import com.showka.service.crud.z00.i.BushoCrudService;
import com.showka.service.crud.z00.i.ShainCrudService;

/**
 * 社員CrudService implementation
 * 
 * @author ShowKa
 *
 */
@Service
public class ShainCrudServiceImpl implements ShainCrudService {

	@Autowired
	private MShainRepository repo;

	@Autowired
	private BushoCrudService bushoCrudService;

	@Override
	public ShainDomain getDomain(String code) {

		MShain shain = repo.getOne(code);

		// 所属部署
		BushoDomain busho = bushoCrudService.getDomain(shain.getBusho().getCode());

		// builder
		ShainDomainBuilder b = new ShainDomainBuilder();
		b.withCode(shain.getCode());
		b.withName(shain.getName());
		b.withRecordId(shain.getRecordId());
		b.withVersion(shain.getVersion());
		b.withShozokuBusho(busho);

		return b.build();
	}

}
