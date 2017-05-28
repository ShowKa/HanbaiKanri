package com.showka.z00.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.showka.entity.MBusho;
import com.showka.repository.i.MBushoRepository;
import com.showka.z00.service.i.MBushoCrudService;

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
