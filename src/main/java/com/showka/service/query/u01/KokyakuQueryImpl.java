package com.showka.service.query.u01;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.z00.Busho;
import com.showka.entity.MKokyaku;
import com.showka.entity.MNyukinKakeInfo;
import com.showka.repository.i.MNyukinKakeInfoRepository;
import com.showka.service.crud.u01.i.KokyakuCrud;
import com.showka.service.query.u01.i.KokyakuQuery;
import com.showka.value.ShimeDate;

@Service
public class KokyakuQueryImpl implements KokyakuQuery {

	@Autowired
	private MNyukinKakeInfoRepository mNyukinKakeInfoRepository;

	@Autowired
	private KokyakuCrud kokyakuPersistence;

	@Override
	public List<Kokyaku> getOnShimeDate(Busho busho, Collection<ShimeDate> shimeDates) {
		return shimeDates.stream().map(shimeDate -> {
			return this.getOnShimeDate(busho, shimeDate);
		}).flatMap(List::stream).collect(Collectors.toList());
	}

	@Override
	public List<Kokyaku> getOnShimeDate(Busho busho, ShimeDate shimeDate) {
		// 顧客条件
		MKokyaku k = new MKokyaku();
		k.setShukanBushoId(busho.getRecordId());
		// 入金掛売情報条件
		MNyukinKakeInfo n = new MNyukinKakeInfo();
		n.setShimebi(shimeDate.getDateValue());
		n.setKokyaku(k);
		// example
		Example<MNyukinKakeInfo> example = Example.of(n);
		// find
		List<MNyukinKakeInfo> result = mNyukinKakeInfoRepository.findAll(example);
		// get domain list
		return result.stream().map(_n -> {
			return kokyakuPersistence.getDomain(_n.getKokyaku().getCode());
		}).collect(Collectors.toList());
	}
}
