package com.showka.service.crud.u08;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.entity.WFirmBankFurikomiMatchingError;
import com.showka.kubun.FurikomiMatchintErrorCause;
import com.showka.repository.i.WFirmBankFurikomiMatchingErrorRepository;
import com.showka.service.crud.u08.i.FirmBankFurikomiMatchingErrorCrudService;

@Service
public class FirmBankFurikomiMatchingErrorCrudServiceImpl implements FirmBankFurikomiMatchingErrorCrudService {

	@Autowired
	private WFirmBankFurikomiMatchingErrorRepository repo;

	@Override
	public void save(String fbFurikomiId, FurikomiMatchintErrorCause cause) {
		WFirmBankFurikomiMatchingError e = new WFirmBankFurikomiMatchingError();
		e.setCause(cause.getCode());
		e.setFbFurikomiId(fbFurikomiId);
		String id = UUID.randomUUID().toString();
		e.setRecordId(id);
		repo.save(e);
	}

}
