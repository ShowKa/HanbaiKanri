package com.showka.service.persistence.u11;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.u05.Uriage;
import com.showka.domain.u11.ShohinIdo;
import com.showka.entity.JShohinIdoUriage;
import com.showka.entity.TUriagePK;
import com.showka.repository.i.JShohinIdoUriageRepository;
import com.showka.service.persistence.u05.i.UriagePersistence;
import com.showka.service.persistence.u11.i.ShohinIdoPersistence;
import com.showka.service.persistence.u11.i.ShohinIdoUriagePersistence;
import com.showka.service.specification.u11.ShohinIdoSpecificationAssociatedWithUriage;
import com.showka.service.specification.u11.ShohinIdoSpecificationFactory;
import com.showka.value.EigyoDate;

@Service
public class ShohinIdoUriagePersistenceImpl implements ShohinIdoUriagePersistence {

	@Autowired
	private JShohinIdoUriageRepository repo;

	@Autowired
	private ShohinIdoSpecificationFactory shohinIdoSpecificationFactory;

	@Autowired
	private ShohinIdoPersistence shohinIdoPersistence;

	@Autowired
	private UriagePersistence uriagePersistence;

	@Override
	public void save(Uriage uriage) {
		// 商品移動仕様
		ShohinIdoSpecificationAssociatedWithUriage specification = shohinIdoSpecificationFactory.create(uriage);
		// 削除対象のレコードを削除
		List<ShohinIdo> shohinIdoForDelete = specification.getShohinIdoForDelete();
		shohinIdoForDelete.forEach(d -> {
			repo.deleteById(d.getRecordId());
		});
		// 商品移動
		shohinIdoPersistence.shohinIdo(specification);
		// 商品移動売上
		List<ShohinIdo> shohinIdo = specification.getShohinIdo();
		shohinIdo.forEach(si -> {
			// insert only
			JShohinIdoUriage e = new JShohinIdoUriage();
			e.setShohinIdoId(si.getRecordId());
			e.setUriageId(uriage.getRecordId());
			e.setRecordId(UUID.randomUUID().toString());
			repo.save(e);
		});
	}

	@Override
	public Optional<ShohinIdo> getNewestShohinIdo(String uriageId) {
		JShohinIdoUriage e = new JShohinIdoUriage();
		e.setUriageId(uriageId);
		Example<JShohinIdoUriage> example = Example.of(e);
		List<JShohinIdoUriage> results = repo.findAll(example);
		if (results.isEmpty()) {
			return Optional.empty();
		}
		Optional<JShohinIdoUriage> newest = results.stream().max((ido1, ido2) -> {
			Date timestamp1 = ido1.getShohinIdo().getTimestamp();
			Date timestamp2 = ido2.getShohinIdo().getTimestamp();
			return timestamp1.compareTo(timestamp2);
		});
		ShohinIdo shohinIdo = shohinIdoPersistence.getDomain(newest.get().getShohinIdoId());
		return Optional.of(shohinIdo);
	}

	@Override
	public void delete(TUriagePK pk) {
		// find records
		Uriage uriage = uriagePersistence.getDomain(pk);
		String uriageId = uriage.getRecordId();
		JShohinIdoUriage e = new JShohinIdoUriage();
		e.setUriageId(uriageId);
		Example<JShohinIdoUriage> example = Example.of(e);
		List<JShohinIdoUriage> shohinIdoList = repo.findAll(example);
		shohinIdoList.stream().filter(ido -> {
			// 営業日の移動のみ抽出
			EigyoDate shohinIdoDate = new EigyoDate(ido.getShohinIdo().getDate());
			return shohinIdoDate.equals(uriage.getKokyaku().getShukanBusho().getEigyoDate());
		}).forEach(ido -> {
			// delete records
			repo.delete(ido);
			shohinIdoPersistence.deleteForcibly(ido.getShohinIdoId());
		});
	}
}
