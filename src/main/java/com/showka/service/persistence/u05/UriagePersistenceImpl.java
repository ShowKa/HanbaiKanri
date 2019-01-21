package com.showka.service.persistence.u05;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u05.Uriage;
import com.showka.domain.u05.UriageRireki;
import com.showka.entity.RUriagePK;
import com.showka.entity.TUriagePK;
import com.showka.service.construct.u05.i.UriageCancelConstruct;
import com.showka.service.crud.u05.i.UriageCrud;
import com.showka.service.persistence.u05.i.UriageCancelPersistence;
import com.showka.service.persistence.u05.i.UriagePersistence;
import com.showka.service.persistence.u05.i.UriageRirekiPersistence;
import com.showka.service.query.u05.i.UriageRirekiQuery;

@Service
public class UriagePersistenceImpl implements UriagePersistence {

	@Autowired
	private UriageCrud uriageCrud;

	@Autowired
	private UriageCancelConstruct uriageCancelConstruct;

	@Autowired
	private UriageCancelPersistence uriageCancelPersistence;

	@Autowired
	private UriageRirekiPersistence uriageRirekiPersistence;

	@Autowired
	private UriageRirekiQuery uriageRirekiQuery;

	@Override
	public void cancel(TUriagePK pk, int version) {
		// 売上
		Uriage uriage = uriageCancelConstruct.by(pk);
		// occ
		uriage.setVersion(version);
		// save
		uriageCrud.save(uriage);
		// cancel
		uriageCancelPersistence.save(uriage);
	}

	@Override
	public void revert(TUriagePK pk, int version) {
		// OCC
		Uriage uriage = uriageCrud.getDomain(pk);
		// 売上履歴削除
		String uriageId = uriage.getRecordId();
		RUriagePK rpk = new RUriagePK(uriageId, uriage.getKeijoDate().toDate());
		uriageRirekiPersistence.delete(rpk);
		// 残った履歴取得
		UriageRireki rirekiList = uriageRirekiQuery.get(uriageId);
		// 計上済みの履歴で売上を上書きし直す。
		Uriage newest = rirekiList.getNewest();
		newest.setVersion(version);
		uriageCrud.save(newest);
	}

}
