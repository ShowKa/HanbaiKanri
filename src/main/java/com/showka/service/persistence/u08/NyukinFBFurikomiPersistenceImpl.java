package com.showka.service.persistence.u08;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.NyukinBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u07.Seikyu;
import com.showka.domain.u08.MatchedFBFurikomi;
import com.showka.domain.u08.Nyukin;
import com.showka.domain.z00.Busho;
import com.showka.entity.JNyukinFBFurikomi;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.repository.i.JNyukinFBFurikomiRepository;
import com.showka.service.crud.u08.i.NyukinCrud;
import com.showka.service.persistence.u08.i.NyukinFBFurikomiPersistence;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

@Service
public class NyukinFBFurikomiPersistenceImpl implements NyukinFBFurikomiPersistence {

	@Autowired
	private NyukinCrud nyukinPersistence;

	@Autowired
	private JNyukinFBFurikomiRepository repo;

	@Override
	public void save(MatchedFBFurikomi matchedFBFurikomim) {
		// 入金登録
		Nyukin nyukin = this.buildNyukin(matchedFBFurikomim);
		nyukinPersistence.save(nyukin);
		// 入金FB振込登録
		JNyukinFBFurikomi e = this.buildEntity(matchedFBFurikomim, nyukin);
		repo.save(e);
	}

	JNyukinFBFurikomi buildEntity(MatchedFBFurikomi matchedFBFurikomim, Nyukin nyukin) {
		// entity
		JNyukinFBFurikomi e = new JNyukinFBFurikomi();
		// FB振込ID
		e.setFbFurikomiId(matchedFBFurikomim.getFBFurikomiId());
		// 入金ID
		e.setNyukinId(nyukin.getRecordId());
		// record id
		String recordId = UUID.randomUUID().toString();
		e.setRecordId(recordId);
		return e;
	}

	/**
	 * マッチング済FB振込から入金を生成.
	 * 
	 * @param mathedFBFurikomi
	 *            マッチング済入金FB振込
	 * @return 入金
	 */
	Nyukin buildNyukin(MatchedFBFurikomi mathedFBFurikomi) {
		NyukinBuilder nb = new NyukinBuilder();
		// 請求
		Seikyu seikyu = mathedFBFurikomi.getSeikyu();
		// 部署
		Busho busho = seikyu.getTantoBusho();
		nb.withBusho(busho);
		// 営業日
		EigyoDate date = busho.getEigyoDate();
		nb.withDate(date);
		// 金額
		AmountOfMoney kingaku = mathedFBFurikomi.getKingaku();
		nb.withKingaku(kingaku);
		// 顧客
		Kokyaku kokyaku = seikyu.getKokyaku();
		nb.withKokyaku(kokyaku);
		// 入金方法
		nb.withNyukinHohoKubun(NyukinHohoKubun.振込);
		// build
		Nyukin nyukin = nb.build();
		return nyukin;
	}

	@Override
	public Nyukin getNyukin(String fbFurikomiId) {
		JNyukinFBFurikomi result = this.findByFbFurikomiId(fbFurikomiId);
		String nyukinId = result.getNyukinId();
		Nyukin nyukin = nyukinPersistence.getDomain(nyukinId);
		return nyukin;
	}

	/**
	 * 入金FB振込レコード取得.
	 * 
	 * @param fbFurikomiId
	 *            FB振込ID
	 * @return 入金FB振込レコード
	 */
	JNyukinFBFurikomi findByFbFurikomiId(String fbFurikomiId) {
		JNyukinFBFurikomi e = new JNyukinFBFurikomi();
		e.setFbFurikomiId(fbFurikomiId);
		Example<JNyukinFBFurikomi> example = Example.of(e);
		JNyukinFBFurikomi result = repo.findOne(example).get();
		return result;
	}
}
