package com.showka.service.crud.u08;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.repository.i.WFirmBankFuriwakeRepository;
import com.showka.service.crud.u08.i.FirmBankFuriwakeCrudService;

@Service
public class FirmBankFuriwakeCrudServiceImpl implements FirmBankFuriwakeCrudService {

	@Autowired
	private WFirmBankFuriwakeRepository repo;

	@Override
	public void deleteAll() {
		repo.deleteAll();
	}
}
