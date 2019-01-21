package com.showka.service.construct.u05;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.UriageBuilder;
import com.showka.domain.u05.Uriage;
import com.showka.entity.TUriagePK;
import com.showka.service.construct.u05.i.UriageCancelConstruct;
import com.showka.service.crud.u05.i.UriageCrud;
import com.showka.value.EigyoDate;

@Service
public class UriageCancelConstructImpl implements UriageCancelConstruct {

	@Autowired
	private UriageCrud uriageCrud;

	@Override
	public Uriage by(TUriagePK pk) {
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
}
