package com.showka.service.validator.u11.i;

import com.showka.domain.u11.Nyuka;

public interface NyukaValidator {

	public void validate(Nyuka nyuka);

	public void validateForRegister(Nyuka nyuka);

	public void validateForUpdate(Nyuka nyuka);

	public void validateForDelete(Nyuka nyuka);

}
