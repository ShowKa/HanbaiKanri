package com.showka.service.crud.u01;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.showka.domain.Busho;
import com.showka.domain.Kokyaku;
import com.showka.domain.NyukinKakeInfo;
import com.showka.domain.builder.KokyakuBuilder;
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
	public void save(Kokyaku domain) {

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
		NyukinKakeInfo nyukinKakeInfo = domain.getNyukinKakeInfo();
		if (domain.getHanbaiKubun() == HanbaiKubun.掛売) {
			nyukinCrudService.save(nyukinKakeInfo);
		} else {
			nyukinCrudService.deleteForciblyIfExists(domain.getRecordId());
		}
	}

	@Override
	@Transactional
	public void delete(String code, Integer version) {
		// entity
		MKokyaku targetKokyaku = repo.getOne(code);

		targetKokyaku.setCode(code);
		targetKokyaku.setVersion(version);

		// 顧客を削除
		repo.delete(targetKokyaku);
	}

	@Override
	@Transactional
	public void delete(Kokyaku domain) {

		// 入金掛情報delete
		nyukinCrudService.deleteForciblyIfExists(domain.getRecordId());

		// 顧客を削除
		this.delete(domain.getCode(), domain.getVersion());
	}

	@Override
	public Kokyaku getDomain(String pk) {
		MKokyaku kokyakuEntity = repo.getOne(pk);
		HanbaiKubun hanbaiKubun = Kubun.get(HanbaiKubun.class, kokyakuEntity.getHanbaiKubun());
		String kokyakuRecordId = kokyakuEntity.getRecordId();

		KokyakuBuilder builder = new KokyakuBuilder();
		builder.withCode(kokyakuEntity.getCode());
		builder.withAddress(kokyakuEntity.getAddress());
		builder.withName(kokyakuEntity.getName());
		builder.withKokyakuKubun(Kubun.get(KokyakuKubun.class, kokyakuEntity.getKokyakuKubun()));
		builder.withHanbaiKubun(hanbaiKubun);

		Busho buhoDomain = bushoService.getDomain(kokyakuEntity.getShukanBusho().getCode());
		builder.withShukanBusho(buhoDomain);

		if (hanbaiKubun == HanbaiKubun.掛売) {
			NyukinKakeInfo nyukinKakeInfoDomain = nyukinCrudService.getDomain(kokyakuRecordId);
			builder.withNyukinKakeInfo(nyukinKakeInfoDomain);
		}
		builder.withRecordId(kokyakuRecordId);
		builder.withVersion(kokyakuEntity.getVersion());

		return builder.build();
	}

	@Override
	public boolean exsists(String pk) {
		return repo.existsById(pk);
	}

}
