package com.showka.service.crud.z00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.ShainBuilder;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shain;
import com.showka.entity.MShain;
import com.showka.repository.i.MShainRepository;
import com.showka.service.crud.z00.i.BushoCrud;
import com.showka.service.crud.z00.i.ShainCrud;

/**
 * 社員Persistence implementation
 * 
 * @author ShowKa
 *
 */
@Service
public class ShainCrudImpl implements ShainCrud {

	@Autowired
	private MShainRepository repo;

	@Autowired
	private BushoCrud bushoPersistence;

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

	@Override
	public void save(Shain domain) {
		throw new RuntimeException("未実装");
	}

	@Override
	public void delete(Shain domain) {
		throw new RuntimeException("未実装");
	}
}
