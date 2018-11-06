package com.showka.service.crud.u07;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.SeikyuBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u01.NyukinKakeInfo;
import com.showka.domain.u07.Seikyu;
import com.showka.domain.u07.SeikyuMeisai;
import com.showka.domain.z00.Busho;
import com.showka.entity.TSeikyu;
import com.showka.entity.TSeikyuPK;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.kubun.i.Kubun;
import com.showka.repository.i.TSeikyuRepository;
import com.showka.service.crud.u01.i.KokyakuCrudService;
import com.showka.service.crud.u07.i.SeikyuCrudService;
import com.showka.service.crud.u07.i.SeikyuMeisaiCrudService;
import com.showka.service.crud.z00.i.BushoCrudService;
import com.showka.service.specification.u07.i.SeikyuSpecification;
import com.showka.system.exception.ValidateException;
import com.showka.value.EigyoDate;

@Service
public class SeikyuCrudServiceImpl implements SeikyuCrudService {

	@Autowired
	private TSeikyuRepository repo;

	@Autowired
	private SeikyuMeisaiCrudService seikyuMeisaiCrudService;

	@Autowired
	private KokyakuCrudService kokyakuCrudService;

	@Autowired
	private BushoCrudService bushoCrudService;

	@Override
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
		String recordId = _e.isPresent() ? e.getRecordId() : UUID.randomUUID().toString();
		e.setRecordId(recordId);
		domain.setRecordId(recordId);
		// save
		repo.save(e);
		// override 明細
		seikyuMeisaiCrudService.overrideList(recordId, domain.getSeikyuMeisai());
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
		seikyuMeisaiCrudService.deleteAll(e.getRecordId());
		// delete
		repo.delete(e);
	}

	@Override
	public Seikyu getDomain(TSeikyuPK pk) {
		// entity
		TSeikyu e = repo.getOne(pk);
		// get 顧客
		Kokyaku kokyaku = kokyakuCrudService.getDomain(e.getKokyaku().getCode());
		// get 担当部署
		Busho busho = bushoCrudService.getDomain(e.getTantoBusho().getCode());
		// get 明細
		String recordId = e.getRecordId();
		List<SeikyuMeisai> meisai = seikyuMeisaiCrudService.getDomainList(recordId);
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
	public Seikyu getDomain(SeikyuSpecification spec) {
		TSeikyuPK pk = new TSeikyuPK();
		Kokyaku kokyaku = spec.getKokyaku();
		String kokyakuId = kokyaku.getRecordId();
		pk.setKokyakuId(kokyakuId);
		EigyoDate seikyuDate = spec.getSeikyuDate();
		pk.setSeikyuDate(seikyuDate.toDate());
		return this.getDomain(pk);
	}

	@Override
	public Seikyu getDomain(String seikyuId) {
		TSeikyu e = repo.findByRecordId(seikyuId);
		return this.getDomain(e.getPk());
	}

	@Override
	public boolean exsists(TSeikyuPK pk) {
		return repo.existsById(pk);
	}

	@Override
	public void save(SeikyuSpecification spec) {
		SeikyuBuilder b = new SeikyuBuilder();
		// 顧客
		Kokyaku kokyaku = spec.getKokyaku();
		b.withKokyaku(kokyaku);
		// 請求担当部署
		b.withTantoBusho(kokyaku.getShukanBusho());
		// 入金方法
		Optional<NyukinKakeInfo> _nhk = kokyaku.getNyukinKakeInfo();
		if (!_nhk.isPresent()) {
			throw new ValidateException("請求対象の顧客に入金方法を設定してください。");
		}
		NyukinHohoKubun nyukinHohoKubun = _nhk.get().getNyukinHohoKubun();
		b.withNyukinHohoKubun(nyukinHohoKubun);
		// 請求日
		b.withSeikyuDate(spec.getSeikyuDate());
		// 支払日
		b.withShiharaiDate(spec.getShiharaiDate());
		// 明細
		b.withSeikyuMeisai(spec.getSeikyuMeisai());
		Seikyu seikyu = b.build();
		this.save(seikyu);
	}
}
