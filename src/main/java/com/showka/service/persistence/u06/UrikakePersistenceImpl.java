package com.showka.service.persistence.u06;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.UrikakeBuilder;
import com.showka.domain.u05.Uriage;
import com.showka.domain.u05.UriageRireki;
import com.showka.domain.u06.Urikake;
import com.showka.entity.TUriagePK;
import com.showka.entity.TUrikake;
import com.showka.event.CrudEvent.EventType;
import com.showka.event.u06.UrikakeCrudEvent;
import com.showka.repository.i.TUrikakeRepository;
import com.showka.service.construct.u06.i.UrikakeConstruct;
import com.showka.service.persistence.u05.i.UriagePersistence;
import com.showka.service.persistence.u05.i.UriageRirekiPersistence;
import com.showka.service.persistence.u06.i.UrikakePersistence;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

@Service
public class UrikakePersistenceImpl implements UrikakePersistence {

	@Autowired
	private TUrikakeRepository repo;

	@Autowired
	private UriagePersistence uriagePersistence;

	@Autowired
	private UriageRirekiPersistence uriageRirekiPersistence;

	@Autowired
	private UrikakeConstruct urikakeSpecificationService;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Override
	public void save(Urikake domain) {
		String uriageId = domain.getUriageId();
		Optional<TUrikake> _e = repo.findById(uriageId);
		TUrikake e = _e.orElse(new TUrikake());
		// set column
		e.setUriageId(uriageId);
		e.setNyukinYoteiDate(domain.getNyukinYoteiDate().toDate());
		e.setKingaku(domain.getKingaku().intValue());
		// occ
		e.setVersion(domain.getVersion());
		// set record id
		if (!_e.isPresent()) {
			// 売掛ID = 売上ID
			e.setRecordId(uriageId);
		}
		domain.setRecordId(e.getRecordId());
		// save 売掛
		repo.save(e);
		// trigger event
		if (!_e.isPresent()) {
			UrikakeCrudEvent event = new UrikakeCrudEvent(this, EventType.newRegister, domain);
			applicationEventPublisher.publishEvent(event);
		}
	}

	@Override
	public void delete(Urikake domain) {
		// trigger
		UrikakeCrudEvent event = new UrikakeCrudEvent(this, EventType.beforeDelete, domain);
		applicationEventPublisher.publishEvent(event);
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
		Uriage uriage = uriagePersistence.getDomain(pk);
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
	public boolean exsists(String uriageId) {
		return repo.existsById(uriageId);
	}

	@Override
	public void revert(String uriageId, Integer version) {
		// 売上履歴取得
		UriageRireki rirekiList = uriageRirekiPersistence.getUriageRirekiList(uriageId);
		// 売上の前回計上分を取得
		Optional<Uriage> _reverTarget = rirekiList.getPrevious();
		// ない場合はなにもしない。
		if (!_reverTarget.isPresent()) {
			return;
		}
		// build 売掛
		Uriage revertTarget = _reverTarget.get();
		Optional<Urikake> _urikake = urikakeSpecificationService.by(revertTarget);
		// 売掛がある場合はsave
		if (_urikake.isPresent()) {
			Urikake urikake = _urikake.get();
			urikake.setVersion(version);
			this.save(urikake);
		}
	}

	@Override
	public Urikake getDomainById(String urikakeId) {
		TUrikake _u = repo.findByRecordId(urikakeId);
		return this.getDomain(_u.getUriageId());
	}
}
