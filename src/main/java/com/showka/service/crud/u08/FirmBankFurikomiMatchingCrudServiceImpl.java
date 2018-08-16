package com.showka.service.crud.u08;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.FBFurikomiMatched;
import com.showka.entity.WFirmBankFurikomiMatching;
import com.showka.entity.WFirmBankFurikomiMatchingPK;
import com.showka.repository.i.WFirmBankFurikomiMatchingRepository;
import com.showka.service.crud.u08.i.FirmBankFurikomiMatchingCrudService;

@Service
public class FirmBankFurikomiMatchingCrudServiceImpl implements FirmBankFurikomiMatchingCrudService {

	/** FB振込マッチングrepository */
	@Autowired
	private WFirmBankFurikomiMatchingRepository repo;

	@Override
	// TODO 性能悪し: selectしてから、各entityをdeleteしているため。
	public void deleteAll() {
		repo.deleteAll();
	}

	@Override
	public void save(FBFurikomiMatched domain) {
		// pk
		WFirmBankFurikomiMatchingPK pk = new WFirmBankFurikomiMatchingPK();
		pk.setFbFurikomiId(domain.getFurikomiId());
		pk.setFuriwakeId(domain.getFuriwakeId());
		// entity
		WFirmBankFurikomiMatching e = new WFirmBankFurikomiMatching();
		e.setPk(pk);
		// record id
		String recordId = UUID.randomUUID().toString();
		e.setRecordId(recordId);
		repo.save(e);
	}
}
