package com.showka.service.persistence.u11;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u11.Nyuka;
import com.showka.service.crud.u11.i.ShohinIdoCrud;
import com.showka.service.crud.u11.i.ShohinIdoNyukaCrud;
import com.showka.service.persistence.u11.i.ShohinIdoNyukaPersistence;

@Service
public class ShohinIdoNyukaPersistenceImpl implements ShohinIdoNyukaPersistence {

	@Autowired
	private ShohinIdoCrud shohinIdoCrud;

	@Autowired
	private ShohinIdoNyukaCrud shohinIdoNyukaCrud;

	@Override
	public void save(Nyuka nyuka) {
		shohinIdoCrud.save(nyuka.getNyukaShohinIdo());
		shohinIdoNyukaCrud.save(nyuka);
	}

	@Override
	public void delete(Nyuka nyuka) {
		shohinIdoNyukaCrud.delete(nyuka);
		shohinIdoCrud.delete(nyuka.getNyukaShohinIdo());
	}
}
