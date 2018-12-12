package com.showka.service.crud.u06;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.UrikakeBuilder;
import com.showka.domain.u05.Uriage;
import com.showka.domain.u06.Urikake;
import com.showka.entity.TUriagePK;
import com.showka.entity.TUrikake;
import com.showka.repository.i.TUrikakeRepository;
import com.showka.service.crud.u05.i.UriageCrud;
import com.showka.service.crud.u06.i.UrikakeCrud;
import com.showka.system.triggerEvent.TriggerCrudEvent;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

@Service
public class UrikakeCrudImpl implements UrikakeCrud {

	@Autowired
	private TUrikakeRepository repo;

	@Autowired
	private UriageCrud uriageCrud;

	@Autowired
	private UrikakeCrud _this;

	@Override
	@TriggerCrudEvent
	public void save(Urikake domain) {
		String uriageId = domain.getUriageId();
		Optional<TUrikake> _e = repo.findById(uriageId);
		TUrikake e = _e.orElse(new TUrikake());
		// set column
		e.setUriageId(uriageId);
		e.setNyukinYoteiDate(domain.getNyukinYoteiDate().toDate());
		e.setKingaku(domain.getKingaku().intValue());
		// OCC
		e.setVersion(domain.getVersion());
		// set record id
		e.setRecordId(domain.getRecordId());
		// save 売掛
		repo.save(e);
	}

	@Override
	@TriggerCrudEvent
	public void delete(Urikake domain) {
		// trigger
		String uriageId = domain.getUriageId();
		Integer version = domain.getVersion();
		// get entity
		TUrikake e = repo.getOne(uriageId);
		// occ
		e.setVersion(version);
		// delete
		repo.delete(e);
	}

	@Override
	public Urikake getDomain(String uriageId) {
		// get entity
		TUrikake e = repo.getOne(uriageId);
		// 売上
		TUriagePK pk = e.getUriage().getPk();
		Uriage uriage = uriageCrud.getDomain(pk);
		// build
		UrikakeBuilder b = new UrikakeBuilder();
		b.withUriage(uriage);
		b.withNyukinYoteiDate(new EigyoDate(e.getNyukinYoteiDate()));
		b.withRecordId(e.getRecordId());
		b.withVersion(e.getVersion());
		b.withKingaku(new AmountOfMoney(e.getKingaku()));
		return b.build();
	}

	@Override
	public boolean exists(String uriageId) {
		return repo.existsById(uriageId);
	}

	@Override
	public Urikake getDomainById(String urikakeId) {
		TUrikake _u = repo.findByRecordId(urikakeId);
		return this.getDomain(_u.getUriageId());
	}

	@Override
	public void deleteIfExists(String uriageId, Integer version) {
		if (this.exists(uriageId)) {
			Urikake uriage = this.getDomain(uriageId);
			// クラス内呼び出しでもAOPを有効にする裏技
			uriage.setVersion(version);
			_this.delete(uriage);
		}
	}
}
