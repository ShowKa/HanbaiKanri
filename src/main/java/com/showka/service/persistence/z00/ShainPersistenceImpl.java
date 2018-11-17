package com.showka.service.persistence.z00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.ShainBuilder;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shain;
import com.showka.entity.MShain;
import com.showka.repository.i.MShainRepository;
import com.showka.service.persistence.z00.i.BushoPersistence;
import com.showka.service.persistence.z00.i.ShainPersistence;

/**
 * 社員Persistence implementation
 * 
 * @author ShowKa
 *
 */
@Service
public class ShainPersistenceImpl implements ShainPersistence {

	@Autowired
	private MShainRepository repo;

	@Autowired
	private BushoPersistence bushoPersistence;

	@Override
	public Shain getDomain(String code) {

		MShain shain = repo.getOne(code);

		// 所属部署
		Busho busho = bushoPersistence.getDomain(shain.getBusho().getCode());

		// builder
		ShainBuilder b = new ShainBuilder();
		b.withCode(shain.getCode());
		b.withName(shain.getName());
		b.withRecordId(shain.getRecordId());
		b.withVersion(shain.getVersion());
		b.withShozokuBusho(busho);

		return b.build();
	}

	@Override
	public boolean exists(String shainCode) {
		return repo.existsById(shainCode);
	}

}
