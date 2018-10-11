package com.showka.service.crud.u08;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.ShukinBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u08.Nyukin;
import com.showka.domain.u08.Shukin;
import com.showka.domain.z00.Shain;
import com.showka.entity.TNyukin;
import com.showka.entity.TShukin;
import com.showka.repository.i.TShukinRepository;
import com.showka.service.crud.u08.i.NyukinCrudService;
import com.showka.service.crud.u08.i.ShukinCrudService;
import com.showka.service.crud.z00.i.ShainCrudService;
import com.showka.system.exception.SystemException;
import com.showka.value.EigyoDate;

@Service
public class ShukinCrudServiceImpl implements ShukinCrudService {

	@Autowired
	private TShukinRepository repo;

	@Autowired
	private NyukinCrudService nyukinCrudService;

	@Autowired
	private ShainCrudService shainCrudService;

	/**
	 * 保存.
	 * 
	 * <pre>
	 * OCCは入金テーブルレコードのversionで行う。
	 * </pre>
	 */
	@Override
	public void save(Shukin shukin) {
		// save 入金
		nyukinCrudService.save(shukin);
		// entity
		String id = shukin.getNyukinId();
		Optional<TShukin> _e = repo.findById(id);
		TShukin e = _e.orElse(new TShukin());
		// 入金ID
		e.setNyukinId(id);
		// 集金ID（入金IDを一致させる）
		e.setRecordId(id);
		// 担当者
		e.setTantoShainId(shukin.getTantoShainId());
		// save
		repo.save(e);
	}

	/**
	 * 削除.
	 * 
	 * <pre>
	 * OCC = 入金テーブルで実施する。
	 * </pre>
	 * 
	 * @param id
	 *            入金ID(=集金ID)
	 * @param nyukinVersion
	 *            入金バージョン
	 */
	@Override
	public void delete(String id, Integer nyukinVersion) {
		// delete 入金
		nyukinCrudService.delete(id, nyukinVersion);
		// delete 集金
		repo.deleteById(id);
	}

	@Override
	public Shukin getDomain(String id) {
		// entity
		TShukin e = repo.getOne(id);
		// 入金
		Nyukin nyukin = nyukinCrudService.getDomain(id);
		// 担当社員
		String tantoShainCode = e.getTantoShain().getCode();
		Shain tantoShain = shainCrudService.getDomain(tantoShainCode);
		// builder
		ShukinBuilder sb = new ShukinBuilder();
		sb.withBusho(nyukin.getBusho());
		sb.withDate(nyukin.getDate());
		sb.withKingaku(nyukin.getKingaku());
		sb.withKokyaku(nyukin.getKokyaku());
		sb.withNyukinHohoKubun(nyukin.getNyukinHohoKubun());
		sb.withDenpyoNumber(e.getDenpyoNumber());
		sb.withTantoShain(tantoShain);
		sb.withRecordId(id);
		sb.withVersion(nyukin.getVersion());
		// build
		return sb.build();
	}

	@Override
	public boolean exsists(String id) {
		return repo.existsById(id);
	}

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
			Shukin domain = this.getDomain(result.get(0).getRecordId());
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
