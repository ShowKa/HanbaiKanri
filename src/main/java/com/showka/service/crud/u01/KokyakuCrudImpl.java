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
	private NyukinKakeInfoCrud nyukinKakeInfoCrud;

	@Autowired
	private BushoCrud bushoCrud;

	/**
	 * 顧客情報を登録する。
	 * 
	 * <pre>
	 * 入金金区分が掛売りの場合は、掛売り情報も合わせて登録する。 現金の場合は、掛売り情報は登録しない。
	 * 
	 * <pre>
	 */
	@Override
	public void save(Kokyaku kokyaku) {
		// entity
		Optional<MKokyaku> entity = repo.findById(kokyaku.getCode());
		MKokyaku e = entity.orElse(new MKokyaku());
		// set columns
		e.setCode(kokyaku.getCode());
		e.setName(kokyaku.getName());
		e.setAddress(kokyaku.getAddress());
		e.setKokyakuKubun(kokyaku.getKokyakuKubun().getCode());
		e.setHanbaiKubun(kokyaku.getHanbaiKubun().getCode());
		e.setShukanBushoId(kokyaku.getShukanBusho().getRecordId());
		// set record_id & version
		String recordId = kokyaku.getRecordId();
		e.setRecordId(recordId);
		e.setVersion(kokyaku.getVersion());
		// save 顧客
		repo.save(e);
		// save 入金掛情報
		Optional<NyukinKakeInfo> _nk = kokyaku.getNyukinKakeInfo();
		if (_nk.isPresent()) {
			NyukinKakeInfo nyukinKakeInfo = _nk.get();
			nyukinKakeInfoCrud.save(nyukinKakeInfo);
		} else {
			nyukinKakeInfoCrud.deleteIfExists(recordId);
		}
	}

	@Override
	public void delete(Kokyaku kokyaku) {
		// delete 入金掛情報
		nyukinKakeInfoCrud.deleteIfExists(kokyaku.getRecordId());
		// entity
		MKokyaku e = repo.getOne(kokyaku.getCode());
		// OCC
		e.setVersion(kokyaku.getVersion());
		// delete
		repo.delete(e);
	}

	@Override
	public Kokyaku getDomain(String code) {
		MKokyaku e = repo.getOne(code);
		// builder
		KokyakuBuilder builder = new KokyakuBuilder();
		builder.withCode(e.getCode());
		builder.withAddress(e.getAddress());
		builder.withName(e.getName());
		builder.withKokyakuKubun(Kubun.get(KokyakuKubun.class, e.getKokyakuKubun()));
		// 販売区分
		HanbaiKubun hanbaiKubun = Kubun.get(HanbaiKubun.class, e.getHanbaiKubun());
		builder.withHanbaiKubun(hanbaiKubun);
		// 部署
		Busho buhoDomain = bushoCrud.getDomain(e.getShukanBusho().getCode());
		builder.withShukanBusho(buhoDomain);
		String recordId = e.getRecordId();
		if (hanbaiKubun == HanbaiKubun.掛売) {
			NyukinKakeInfo nyukinKakeInfoDomain = nyukinKakeInfoCrud.getDomain(recordId);
			builder.withNyukinKakeInfo(Optional.of(nyukinKakeInfoDomain));
		}
		builder.withRecordId(recordId);
		builder.withVersion(e.getVersion());
		return builder.build();
	}

	@Override
	public boolean exists(String pk) {
		return repo.existsById(pk);
	}
}
