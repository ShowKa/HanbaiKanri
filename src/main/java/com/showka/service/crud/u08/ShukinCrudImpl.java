package com.showka.service.crud.u08;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.ShukinBuilder;
import com.showka.domain.u08.Nyukin;
import com.showka.domain.u08.Shukin;
import com.showka.domain.z00.Shain;
import com.showka.entity.TShukin;
import com.showka.repository.i.TShukinRepository;
import com.showka.service.crud.u08.i.NyukinCrud;
import com.showka.service.crud.u08.i.ShukinCrud;
import com.showka.service.crud.z00.i.ShainCrud;

@Service
public class ShukinCrudImpl implements ShukinCrud {

	@Autowired
	private TShukinRepository repo;

	@Autowired
	private NyukinCrud nyukinPersistence;

	@Autowired
	private ShainCrud shainPersistence;

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
		nyukinPersistence.save(shukin);
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
		// 伝票番号
		e.setDenpyoNumber(shukin.getDenpyoNumber());
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
	public void delete(Shukin domain) {
		// delete 集金
		repo.deleteById(domain.getRecordId());
		// delete 入金
		nyukinPersistence.delete(domain);
	}

	@Override
	public Shukin getDomain(String id) {
		// entity
		TShukin e = repo.getOne(id);
		// 入金
		Nyukin nyukin = nyukinPersistence.getDomain(id);
		// 担当社員
		String tantoShainCode = e.getTantoShain().getCode();
		Shain tantoShain = shainPersistence.getDomain(tantoShainCode);
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
}
