package com.showka.service.specification.u08;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.repository.i.TNyukinKeijoRepository;
import com.showka.service.specification.u08.i.NyukinKeijoBusinessService;

@Service
public class NyukinKeijoBusinessServiceImpl implements NyukinKeijoBusinessService {

	@Autowired
	private TNyukinKeijoRepository repo;

	@Override
	public boolean keijoDone(String nyukinId) {
		return repo.existsById(nyukinId);
	}

}
