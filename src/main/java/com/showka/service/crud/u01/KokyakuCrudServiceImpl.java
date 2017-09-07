package com.showka.service.crud.u01;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.KokyakuDomain;
import com.showka.domain.builder.KokyakuDomainBuilder;
import com.showka.entity.MKokyaku;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.KokyakuKubun;
import com.showka.kubun.i.Kubun;
import com.showka.repository.i.MKokyakuRepository;
import com.showka.service.crud.u01.i.KokyakuCrudService;

/**
 * 顧客 CRUD Service
 *
 * @author 25767
 *
 */
@Service
public class KokyakuCrudServiceImpl implements KokyakuCrudService {

	@Autowired
	private MKokyakuRepository repo;

	@Override
	public void save(KokyakuDomain domain) {

		// entity
		Optional<MKokyaku> entity = repo.findById(domain.getCode());
		MKokyaku e = entity.orElse(new MKokyaku());

		// set columns
		e.setCode(domain.getCode());
		e.setName(domain.getName());
		e.setAddress(domain.getAddress());
		e.setKokyakuKubun(domain.getKokyakuKubun().getCode());
		e.setHanbaiKubun(domain.getHanbaiKubun().getCode());
		e.setShukanBushoId(domain.getShukanBushoId());

		// set record_id & version
		e.setRecordId(domain.getRecordId());
		e.setVersion(domain.getVersion());

		// save
		repo.save(e);
	}

	@Override
	public void delete(String code, Integer version) {
		MKokyaku target = new MKokyaku();
		target.setCode(code);
		target.setVersion(version);
		repo.delete(target);
	}

	@Override
	public KokyakuDomain getDomain(String pk) {
		MKokyaku result = repo.getOne(pk);

		KokyakuDomainBuilder builder = new KokyakuDomainBuilder();
		builder.withCode(result.getCode());
		builder.withAddress(result.getAddress());
		builder.withName(result.getName());
		builder.withKokyakuKubun(Kubun.get(KokyakuKubun.class, result.getKokyakuKubun()));
		builder.withHanbaiKubun(Kubun.get(HanbaiKubun.class, result.getHanbaiKubun()));
		builder.withShukanBushoId(result.getShukanBushoId());

		builder.withRecordId(result.getRecordId());
		builder.withVersion(result.getVersion());

		return builder.build();
	}

	@Override
	public boolean exsists(String pk) {
		return repo.existsById(pk);
	}

}
