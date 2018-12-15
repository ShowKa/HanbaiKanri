package com.showka.service.crud.u05.i;

import com.showka.domain.u05.Uriage;
import com.showka.entity.TUriagePK;
import com.showka.service.crud.Crud;

public interface UriageCrud extends Crud<Uriage, TUriagePK> {

	@Override
	public void save(Uriage d);
}
