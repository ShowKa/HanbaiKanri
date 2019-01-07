package com.showka.service.crud.u11.i;

import java.util.List;

import com.showka.domain.u11.Nyuka;
import com.showka.domain.u11.ShohinIdo;

public interface NyukaTeiseiCrud {

	public void save(ShohinIdo teiseiShohinIdo, Nyuka nyuka);

	public void delete(ShohinIdo teiseiShohinIdo, Nyuka nyuka);

	public List<ShohinIdo> get(String nyukaId);
}
