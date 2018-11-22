package com.showka.service.crud.u01;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.KokyakuBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u01.NyukinKakeInfo;
import com.showka.domain.z00.Busho;
import com.showka.entity.MKokyaku;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.KokyakuKubun;
import com.showka.kubun.i.Kubun;
import com.showka.repository.i.MKokyakuRepository;
import com.showka.service.crud.u01.i.KokyakuCrud;
import com.showka.service.crud.u01.i.NyukinKakeInfoCrud;
import com.showka.service.crud.z00.i.BushoCrud;

/**
 * 顧客 CRUD Service
 *
 * @author 25767
 *
 */
@Service
public class KokyakuCrudImpl implements KokyakuCrud {

	@Autowired
	private MKokyakuRepository repo;

	@Autowired
	private NyukinKakeInfoCrud nyukinPersistence;

	@Autowired
	private BushoCrud bushoService;

	/**
	 * 顧客情報を登録する。
	 * 
	 * <pre>
	 * 入金金区分が掛売りの場合は、掛売り情報も合わせて登録する。 現金の場合は、掛売り情報は登録しない。
	 * 
	 * <pre>
	 */
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
		if (domain.getHanbaiKubun() == HanbaiKubun.掛売) {
			NyukinKakeInfo nyukinKakeInfo = domain.getNyukinKakeInfo().get();
			nyukinPersistence.save(nyukinKakeInfo);
		} else {
			nyukinPersistence.deleteIfExists(domain.getRecordId());
		}
	}

	@Override
	public void delete(Kokyaku domain) {
		String code = domain.getCode();
		Integer version = domain.getVersion();
		// entity
		MKokyaku targetKokyaku = repo.getOne(code);
		// 入金掛情報delete
		nyukinPersistence.deleteIfExists(domain.getRecordId());
		// 顧客を削除
		targetKokyaku.setCode(code);
		targetKokyaku.setVersion(version);
		repo.delete(targetKokyaku);
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
			NyukinKakeInfo nyukinKakeInfoDomain = nyukinPersistence.getDomain(kokyakuRecordId);
			builder.withNyukinKakeInfo(Optional.of(nyukinKakeInfoDomain));
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
