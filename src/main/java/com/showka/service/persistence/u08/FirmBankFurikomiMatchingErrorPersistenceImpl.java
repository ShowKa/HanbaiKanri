package com.showka.service.persistence.u08;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.entity.WFirmBankFurikomiMatchingError;
import com.showka.kubun.FurikomiMatchintErrorCause;
import com.showka.repository.i.WFirmBankFurikomiMatchingErrorRepository;
import com.showka.service.persistence.u08.i.FirmBankFurikomiMatchingErrorPersistence;

@Service
public class FirmBankFurikomiMatchingErrorPersistenceImpl implements FirmBankFurikomiMatchingErrorPersistence {

	@Autowired
	private WFirmBankFurikomiMatchingErrorRepository repo;

	@Override
	public void save(String fbFurikomiId, FurikomiMatchintErrorCause cause) {
		WFirmBankFurikomiMatchingError e = new WFirmBankFurikomiMatchingError();
		e.setCause(cause.getCode());
		e.setFbFurikomiId(fbFurikomiId);
		e.initRecordId();
		repo.save(e);
	}

}
