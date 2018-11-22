package com.showka.service.persistence.u05;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.UriageBuilder;
import com.showka.domain.u05.Uriage;
import com.showka.domain.u05.UriageRireki;
import com.showka.entity.RUriagePK;
import com.showka.entity.TUriagePK;
import com.showka.service.crud.u05.i.UriageCrud;
import com.showka.service.persistence.u05.i.UriageCancelPersistence;
import com.showka.service.persistence.u05.i.UriagePersistence;
import com.showka.service.persistence.u05.i.UriageRirekiPersistence;
import com.showka.value.EigyoDate;

@Service
public class UriagePersistenceImpl implements UriagePersistence {

	@Autowired
	private UriageCrud uriageCrud;

	@Autowired
	private UriageCancelPersistence uriageCancelPersistence;

	@Autowired
	private UriageRirekiPersistence uriageRirekiPersistence;

	@Override
	public void cancel(TUriagePK pk, int version) {
		// 売上
		Uriage uriage = this.buildForCancel(pk);
		// occ
		uriage.setVersion(version);
		// save
		uriageCrud.save(uriage);
		// cancel
		uriageCancelPersistence.save(uriage);
	}

	/**
	 * キャンセルの売上をビルド.
	 * 
	 * <pre>
	 * 計上日 = 顧客.主幹部署.営業日
	 * 売上明細->空
	 * </pre>
	 * 
	 * @param pk
	 *            売上主キー
	 * @return 売上
	 */
	Uriage buildForCancel(TUriagePK pk) {
		// update 計上日
		Uriage _d = uriageCrud.getDomain(pk);
		UriageBuilder b = new UriageBuilder();
		EigyoDate eigyoDate = _d.getKokyaku().getShukanBusho().getEigyoDate();
		b.withKeijoDate(eigyoDate);
		// empty 売上明細
		b.withUriageMeisai(new ArrayList<>());
		// 売上
		Uriage uriage = b.apply(_d);
		return uriage;
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
		UriageRireki rirekiList = uriageRirekiPersistence.getUriageRirekiList(uriageId);
		// 計上済みの履歴で売上を上書きし直す。
		Uriage newest = rirekiList.getNewest();
		newest.setVersion(version);
		uriageCrud.save(newest);
	}

}
