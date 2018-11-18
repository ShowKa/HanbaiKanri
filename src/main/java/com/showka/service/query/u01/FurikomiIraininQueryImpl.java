package com.showka.service.query.u01;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.u01.FurikomiIrainin;
import com.showka.domain.u01.FurikomiIraininSet;
import com.showka.domain.u01.Kokyaku;
import com.showka.entity.MFurikomiIrainin;
import com.showka.entity.MFurikomiIraininPK;
import com.showka.repository.i.MFurikomiIraininRepository;
import com.showka.service.query.u01.i.FurikomiIraininQuery;

@Service
public class FurikomiIraininQueryImpl implements FurikomiIraininQuery {

	@Autowired
	private MFurikomiIraininRepository repo;

	@Override
	public FurikomiIraininSet search(Kokyaku kokyaku) {
		// search entity
		List<MFurikomiIrainin> resultList = this.searchEntity(kokyaku);
		// build 振込依頼人set
		Set<FurikomiIrainin> furikomiIraininSet = resultList.stream().map(i -> {
			FurikomiIrainin f = new FurikomiIrainin(i.getFurikomiIraininName());
			f.setRecordId(i.getRecordId());
			return f;
		}).collect(Collectors.toSet());
		return new FurikomiIraininSet(kokyaku, furikomiIraininSet);
	}

	/**
	 * 振込依頼人のEntityを検索する。
	 * 
	 * @param kokyaku
	 *            顧客
	 * @return 振込依頼人リスト
	 */
	List<MFurikomiIrainin> searchEntity(Kokyaku kokyaku) {
		// example
		MFurikomiIraininPK pk = new MFurikomiIraininPK();
		pk.setKokyakuId(kokyaku.getRecordId());
		MFurikomiIrainin e = new MFurikomiIrainin();
		e.setPk(pk);
		Example<MFurikomiIrainin> example = Example.of(e);
		// find
		List<MFurikomiIrainin> resultList = repo.findAll(example);
		return resultList;
	}
}
