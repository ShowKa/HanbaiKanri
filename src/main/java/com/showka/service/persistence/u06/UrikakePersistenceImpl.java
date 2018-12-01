package com.showka.service.persistence.u06;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u05.Uriage;
import com.showka.domain.u05.UriageRireki;
import com.showka.domain.u06.Urikake;
import com.showka.service.construct.u06.i.UrikakeConstruct;
import com.showka.service.crud.u06.i.UrikakeCrud;
import com.showka.service.persistence.u06.i.UrikakePersistence;
import com.showka.service.query.u05.i.UriageRirekiQuery;

@Service
public class UrikakePersistenceImpl implements UrikakePersistence {

	@Autowired
	private UrikakeCrud urikakeCrud;

	@Autowired
	private UriageRirekiQuery uriageRirekiQuery;

	@Autowired
	private UrikakeConstruct urikakeConstruct;

	@Override
	public void revert(String uriageId, Integer version) {
		// 売上履歴取得
		UriageRireki rirekiList = uriageRirekiQuery.get(uriageId);
		// 売上の前回計上分を取得
		Optional<Uriage> _reverTarget = rirekiList.getPrevious();
		// ない場合はなにもしない。
		if (!_reverTarget.isPresent()) {
			return;
		}
		// build 売掛
		Uriage revertTarget = _reverTarget.get();
		Optional<Urikake> _urikake = urikakeConstruct.by(revertTarget);
		// 売掛がある場合はsave
		if (_urikake.isPresent()) {
			Urikake urikake = _urikake.get();
			urikake.setVersion(version);
			urikakeCrud.save(urikake);
		}
	}

	@Override
	public void cancel(Urikake urikake) {
		// キャンセル
		urikake.toCanceld();
		// save
		urikakeCrud.save(urikake);
	}
}
