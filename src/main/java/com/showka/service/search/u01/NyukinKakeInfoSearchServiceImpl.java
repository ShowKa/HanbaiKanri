package com.showka.service.search.u01;

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
import com.showka.service.crud.u01.i.KokyakuCrudService;
import com.showka.service.search.u01.i.NyukinKakeInfoSearchService;
import com.showka.value.ShimeDate;

@Service
public class NyukinKakeInfoSearchServiceImpl implements NyukinKakeInfoSearchService {

	@Autowired
	private MNyukinKakeInfoRepository mNyukinKakeInfoRepository;

	@Autowired
	private KokyakuCrudService kokyakuCrudService;

	@Override
	public List<Kokyaku> getKokyakuOnShimeDate(Busho busho, ShimeDate shimeDate) {
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
			return kokyakuCrudService.getDomain(_n.getKokyaku().getCode());
		}).collect(Collectors.toList());
	}

	@Override
	public List<Kokyaku> getKokyakuOnShimeDate(Busho busho, Collection<ShimeDate> shimeDates) {
		// TODO Auto-generated method stub
		return null;
	}
}
