package com.showka.service.crud.u01;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.showka.domain.BushoDomain;
import com.showka.domain.KokyakuDomain;
import com.showka.domain.NyukinKakeInfoDomain;
import com.showka.domain.builder.KokyakuDomainBuilder;
import com.showka.entity.MKokyaku;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.KokyakuKubun;
import com.showka.kubun.i.Kubun;
import com.showka.repository.i.MKokyakuRepository;
import com.showka.service.crud.u01.i.KokyakuCrudService;
import com.showka.service.crud.u01.i.NyukinKakeInfoCrudService;
import com.showka.service.crud.z00.i.BushoCrudService;

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

	@Autowired
	private NyukinKakeInfoCrudService nyukinCrudService;

	@Autowired
	private BushoCrudService bushoService;

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
		e.setShukanBushoId(domain.getShukanBusho().getRecordId());

		// set record_id & version
		e.setRecordId(domain.getRecordId());
		e.setVersion(domain.getVersion());

		// 顧客save
		repo.save(e);

		// 入金掛情報save
		NyukinKakeInfoDomain nyukinKakeInfo = domain.getNyukinKakeInfo();
		if (domain.getHanbaiKubun() == HanbaiKubun.掛売) {
			nyukinCrudService.save(nyukinKakeInfo);
		} else {
			if (nyukinCrudService.exsists(domain.getCode())) {
				NyukinKakeInfoDomain target = nyukinCrudService.getDomain(domain.getCode());
				nyukinCrudService.delete(target.getKokyakuId(), target.getVersion());
			}
		}
	}

	@Override
	@Transactional
	public void delete(String code, Integer version) {
		MKokyaku targetKokyaku = new MKokyaku();
		targetKokyaku.setCode(code);
		targetKokyaku.setVersion(version);

		// 入金掛情報があれば削除
		if (nyukinCrudService.exsists(code)) {
			Integer nyukinKakeInfoVersion = nyukinCrudService.getDomain(code).getVersion();
			nyukinCrudService.delete(code, nyukinKakeInfoVersion);
		}

		// 顧客を削除
		repo.delete(targetKokyaku);
	}

	@Override
	public KokyakuDomain getDomain(String pk) {
		MKokyaku kokyakuEntity = repo.getOne(pk);

		KokyakuDomainBuilder builder = new KokyakuDomainBuilder();
		builder.withCode(kokyakuEntity.getCode());
		builder.withAddress(kokyakuEntity.getAddress());
		builder.withName(kokyakuEntity.getName());
		builder.withKokyakuKubun(Kubun.get(KokyakuKubun.class, kokyakuEntity.getKokyakuKubun()));
		builder.withHanbaiKubun(Kubun.get(HanbaiKubun.class, kokyakuEntity.getHanbaiKubun()));

		BushoDomain buhoDomain = bushoService.getDomain(kokyakuEntity.getShukanBusho().getRecordId());
		builder.withShukanBusho(buhoDomain);

		NyukinKakeInfoDomain nyukinKakeInfoDomain = nyukinCrudService.getDomain(kokyakuEntity.getCode());
		builder.withNyukinKakeInfo(nyukinKakeInfoDomain);

		builder.withRecordId(kokyakuEntity.getRecordId());
		builder.withVersion(kokyakuEntity.getVersion());

		return builder.build();
	}

	@Override
	public boolean exsists(String pk) {
		return repo.existsById(pk);
	}

}
