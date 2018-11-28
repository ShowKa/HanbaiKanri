package com.showka.service.query.u08;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u08.Shukin;
import com.showka.entity.TNyukin;
import com.showka.entity.TShukin;
import com.showka.repository.i.TShukinRepository;
import com.showka.service.crud.u08.i.ShukinCrud;
import com.showka.service.query.u08.i.ShukinQuery;
import com.showka.system.exception.SystemException;
import com.showka.value.EigyoDate;

@Service
public class ShukinQueryImpl implements ShukinQuery {

	@Autowired
	private TShukinRepository repo;

	@Autowired
	private ShukinCrud shukinCrud;

	@Override
	public Optional<Shukin> getIfExists(Kokyaku kokyaku, EigyoDate nyukinDate, String denpyoNumber) {
		TNyukin nyukin = new TNyukin();
		nyukin.setKokyakuId(kokyaku.getRecordId());
		nyukin.setDate(nyukinDate.toDate());
		TShukin shukin = new TShukin();
		shukin.setDenpyoNumber(denpyoNumber);
		shukin.setNyukin(nyukin);
		Example<TShukin> example = Example.of(shukin);
		List<TShukin> result = repo.findAll(example);
		if (result.size() == 0) {
			return Optional.empty();
		} else if (result.size() == 1) {
			Shukin domain = shukinCrud.getDomain(result.get(0).getRecordId());
			return Optional.of(domain);
		} else {
			throw new SystemException("集金伝票重複: " + kokyaku.getCode() + " " + nyukinDate + " " + denpyoNumber);
		}
	}

	@Override
	public boolean exists(Kokyaku kokyaku, EigyoDate nyukinDate, String denpyoNumber) {
		return this.getIfExists(kokyaku, nyukinDate, denpyoNumber).isPresent();
	}

	@Override
	public Shukin get(Kokyaku kokyaku, EigyoDate nyukinDate, String denpyoNumber) {
		return this.getIfExists(kokyaku, nyukinDate, denpyoNumber).get();
	}

}
