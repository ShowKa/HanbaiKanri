package com.showka.service.persistence.u08;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u08.Nyukin;
import com.showka.domain.z00.Busho;
import com.showka.entity.TNyukinKeijo;
import com.showka.repository.i.TNyukinKeijoRepository;
import com.showka.service.persistence.u08.i.NyukinKeijoPersistence;
import com.showka.service.query.u08.i.NyukinKeijoQuery;
import com.showka.value.EigyoDate;

@Service
public class NyukinKeijoPersistenceImpl implements NyukinKeijoPersistence {

	@Autowired
	private NyukinKeijoQuery nyukinKeijoQuery;

	@Autowired
	private TNyukinKeijoRepository repo;

	@Override
	public void keijo(Busho busho, EigyoDate keijoDate) {
		List<Nyukin> nyukinList = nyukinKeijoQuery.getNotDone(busho, keijoDate);
		nyukinList.forEach(nyukin -> {
			TNyukinKeijo e = new TNyukinKeijo();
			e.setBushoId(nyukin.getBushoId());
			e.setDate(nyukin.getDate().toDate());
			e.setNyukinId(nyukin.getRecordId());
			// idは同じ
			e.setRecordId(nyukin.getRecordId());
			repo.save(e);
		});
	}

}
