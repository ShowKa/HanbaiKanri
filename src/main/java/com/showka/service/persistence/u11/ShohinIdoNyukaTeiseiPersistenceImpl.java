package com.showka.service.persistence.u11;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u11.Nyuka;
import com.showka.domain.u11.ShohinIdo;
import com.showka.service.crud.u11.i.NyukaTeiseiCrud;
import com.showka.service.crud.u11.i.ShohinIdoCrud;
import com.showka.service.crud.u11.i.ShohinIdoNyukaCrud;
import com.showka.service.persistence.u11.i.ShohinIdoNyukaTeiseiPersistence;

@Service
public class ShohinIdoNyukaTeiseiPersistenceImpl implements ShohinIdoNyukaTeiseiPersistence {

	@Autowired
	private ShohinIdoCrud shohinIdoCrud;

	@Autowired
	private NyukaTeiseiCrud nyukaTeiseiCrud;

	@Autowired
	private ShohinIdoNyukaCrud shohinIdoNyukaCrud;

	@Override
	public void save(Nyuka nyuka) {
		ShohinIdo teiseiShohinIdo = nyuka.getNewestShohinIdo();
		shohinIdoCrud.save(teiseiShohinIdo);
		nyukaTeiseiCrud.save(nyuka.getRecordId(), teiseiShohinIdo);
		// OCC
		shohinIdoNyukaCrud.save(nyuka);
	}

	@Override
	public void delete(Nyuka nyuka) {
		ShohinIdo teiseiShohinIdo = nyuka.getNewestShohinIdo();
		nyukaTeiseiCrud.delete(nyuka.getRecordId(), teiseiShohinIdo);
		shohinIdoCrud.delete(teiseiShohinIdo);
		// OCC
		shohinIdoNyukaCrud.save(nyuka);
	}
}
