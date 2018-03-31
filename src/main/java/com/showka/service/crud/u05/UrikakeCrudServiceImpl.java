package com.showka.service.crud.u05;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.Uriage;
import com.showka.domain.UriageRireki;
import com.showka.domain.Urikake;
import com.showka.domain.builder.UrikakeBuilder;
import com.showka.entity.TUriagePK;
import com.showka.entity.TUrikake;
import com.showka.repository.i.TUrikakeRepository;
import com.showka.service.crud.u05.i.UriageCrudService;
import com.showka.service.crud.u05.i.UriageRirekiCrudService;
import com.showka.service.crud.u05.i.UrikakeCrudService;
import com.showka.service.specification.u05.i.UrikakeSpecificationService;
import com.showka.value.EigyoDate;

@Service
public class UrikakeCrudServiceImpl implements UrikakeCrudService {

	@Autowired
	private TUrikakeRepository repo;

	@Autowired
	private UriageCrudService uriageCrudService;

	@Autowired
	private UriageRirekiCrudService uriageRirekiCrudService;

	@Autowired
	private UrikakeSpecificationService urikakeSpecificationService;

	@Override
	public void save(Urikake domain) {
		String uriageId = domain.getUriageId();
		Optional<TUrikake> _e = repo.findById(uriageId);
		TUrikake e = _e.orElse(new TUrikake());
		// set column
		e.setUriageId(uriageId);
		e.setNyukinYoteiDate(domain.getNyukinYoteiDate().toDate());
		e.setZandaka(domain.getZandaka());
		// occ
		e.setVersion(domain.getVersion());
		// set record id
		if (!_e.isPresent()) {
			String recordId = UUID.randomUUID().toString();
			e.setRecordId(recordId);
		}
		domain.setRecordId(e.getRecordId());
		// save
		repo.save(e);
	}

	@Override
	public void delete(String uriageId, Integer version) {
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
		Uriage uriage = uriageCrudService.getDomain(pk);
		// build
		UrikakeBuilder b = new UrikakeBuilder();
		b.withUriage(uriage);
		b.withNyukinYoteiDate(new EigyoDate(e.getNyukinYoteiDate()));
		b.withRecordId(e.getRecordId());
		b.withVersion(e.getVersion());
		b.withZandaka(e.getZandaka());
		return b.build();
	}

	@Override
	public boolean exsists(String uriageId) {
		return repo.existsById(uriageId);
	}

	@Override
	public void deleteIfExists(String uriageId, Integer version) {
		if (this.exsists(uriageId)) {
			this.delete(uriageId, version);
		}
	}

	@Override
	public void revert(String uriageId, Integer version) {
		// 売上履歴取得
		UriageRireki rirekiList = uriageRirekiCrudService.getUriageRirekiList(uriageId);
		// 売上の前回計上分を取得
		Optional<Uriage> _reverTarget = rirekiList.getPrevious();
		// ない場合はなにもしない。
		if (!_reverTarget.isPresent()) {
			return;
		}
		// build 売掛
		Uriage revertTarget = _reverTarget.get();
		Optional<Urikake> _urikake = urikakeSpecificationService.buildUrikakeBy(revertTarget);
		// 売掛がある場合はsave
		if (_urikake.isPresent()) {
			Urikake urikake = _urikake.get();
			urikake.setVersion(version);
			this.save(urikake);
		}
	}

	@Override
	public void updateNyukinYoteiDate(Urikake urikake, EigyoDate updatedNyukinYoteiDate) {
		// TODO Auto-generated method stub
	}
}
