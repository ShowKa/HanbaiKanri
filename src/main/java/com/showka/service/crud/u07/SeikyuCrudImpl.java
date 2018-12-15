package com.showka.service.crud.u07;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.SeikyuBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u07.Seikyu;
import com.showka.domain.u07.SeikyuMeisai;
import com.showka.domain.z00.Busho;
import com.showka.entity.TSeikyu;
import com.showka.entity.TSeikyuPK;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.kubun.i.Kubun;
import com.showka.repository.i.TSeikyuRepository;
import com.showka.service.crud.u01.i.KokyakuCrud;
import com.showka.service.crud.u07.i.SeikyuCrud;
import com.showka.service.crud.u07.i.SeikyuMeisaiCrud;
import com.showka.service.crud.z00.i.BushoCrud;
import com.showka.system.triggerEvent.TriggerCrudEvent;
import com.showka.value.EigyoDate;

@Service
public class SeikyuCrudImpl implements SeikyuCrud {

	@Autowired
	private TSeikyuRepository repo;

	@Autowired
	private SeikyuMeisaiCrud seikyuMeisaiPersistence;

	@Autowired
	private KokyakuCrud kokyakuPersistence;

	@Autowired
	private BushoCrud bushoPersistence;

	@Override
	@TriggerCrudEvent
	public void save(Seikyu domain) {
		// PK
		TSeikyuPK pk = new TSeikyuPK();
		pk.setKokyakuId(domain.getKokyakuId());
		pk.setSeikyuDate(domain.getSeikyuDate().toDate());
		// entity
		Optional<TSeikyu> _e = repo.findById(pk);
		TSeikyu e = _e.orElse(new TSeikyu());
		// set columns
		e.setPk(pk);
		e.setShiharaiDate(domain.getShiharaiDate().toDate());
		String tantoBushoId = domain.getTantoBusho().getRecordId();
		e.setTantoBushoId(tantoBushoId);
		e.setNyukinHohoKubun(domain.getNyukinHohoKubun().getCode());
		// OCC
		e.setVersion(domain.getVersion());
		// record id
		e.setRecordId(domain.getRecordId());
		// save
		repo.save(e);
		// override 明細
		seikyuMeisaiPersistence.overrideList(e.getRecordId(), domain.getSeikyuMeisai());
	}

	@Override
	public void delete(Seikyu domain) {
		TSeikyuPK pk = new TSeikyuPK();
		pk.setKokyakuId(domain.getKokyakuId());
		pk.setSeikyuDate(domain.getSeikyuDate().toDate());
		Integer version = domain.getVersion();
		// entity
		TSeikyu e = repo.getOne(pk);
		// OCC
		e.setVersion(version);
		// delete 明細
		seikyuMeisaiPersistence.deleteAll(e.getRecordId());
		// delete
		repo.delete(e);
	}

	@Override
	public Seikyu getDomain(TSeikyuPK pk) {
		// entity
		TSeikyu e = repo.getOne(pk);
		// get 顧客
		Kokyaku kokyaku = kokyakuPersistence.getDomain(e.getKokyaku().getCode());
		// get 担当部署
		Busho busho = bushoPersistence.getDomain(e.getTantoBusho().getCode());
		// get 明細
		String recordId = e.getRecordId();
		List<SeikyuMeisai> meisai = seikyuMeisaiPersistence.getDomainList(recordId);
		// build
		SeikyuBuilder b = new SeikyuBuilder();
		b.withKokyaku(kokyaku);
		b.withTantoBusho(busho);
		b.withNyukinHohoKubun(Kubun.get(NyukinHohoKubun.class, e.getNyukinHohoKubun()));
		b.withSeikyuDate(new EigyoDate(e.getSeikyuDate()));
		b.withShiharaiDate(new EigyoDate(e.getShiharaiDate()));
		b.withSeikyuMeisai(meisai);
		b.withRecordId(recordId);
		b.withVersion(e.getVersion());
		return b.build();
	}

	@Override
	public Seikyu getDomain(String seikyuId) {
		TSeikyu e = repo.findByRecordId(seikyuId);
		return this.getDomain(e.getPk());
	}

	@Override
	public boolean exists(TSeikyuPK pk) {
		return repo.existsById(pk);
	}
}
