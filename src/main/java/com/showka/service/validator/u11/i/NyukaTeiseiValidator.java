package com.showka.service.validator.u11.i;

import com.showka.domain.u11.Nyuka;

public interface NyukaTeiseiValidator {

	public void validateForTeisei(Nyuka nyuka, String teiseiShohinIdoId);

	public void validateForTeiseiUpdate(Nyuka nyuka, String teiseiShohinIdoId);

	public void validateForTeiseiDelete(Nyuka nyuka, String teiseiShohinIdoId);
}
