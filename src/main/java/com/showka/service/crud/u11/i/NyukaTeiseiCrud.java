package com.showka.service.crud.u11.i;

import com.showka.domain.u08.Nyukin;
import com.showka.domain.u11.ShohinIdo;

public interface NyukaTeiseiCrud {

	public void save(ShohinIdo teiseiShohinIdo, Nyukin nyukin);

	public void delete(ShohinIdo teiseiShohinIdo, Nyukin nyukin);
}
