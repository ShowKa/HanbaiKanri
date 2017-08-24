package com.showka.service.crud.z00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.showka.entity.MBusho;
import com.showka.repository.i.MBushoRepository;
import com.showka.service.crud.z00.i.MBushoCrudService;

@Service
@Transactional
public class MBushoServiceCrudImpl implements MBushoCrudService {

	@Autowired
	MBushoRepository mBushoRepository;

	@Override
	public MBusho findById(String id) {
		return mBushoRepository.findById(id).get();
	}

}
