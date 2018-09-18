package com.showka.service.crud.u08;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.Busho;
import com.showka.domain.Kokyaku;
import com.showka.domain.MatchedFBFurikomi;
import com.showka.domain.Nyukin;
import com.showka.domain.Seikyu;
import com.showka.domain.builder.NyukinBuilder;
import com.showka.entity.JNyukinFBFurikomi;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.repository.i.JNyukinFBFurikomiRepository;
import com.showka.service.crud.u08.i.NyukinCrudService;
import com.showka.service.crud.u08.i.NyukinFBFurikomiCrudService;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

@Service
public class NyukinFBFurikomiCrudServiceImpl implements NyukinFBFurikomiCrudService {

	@Autowired
	private NyukinCrudService nyukinCrudService;

	@Autowired
	private JNyukinFBFurikomiRepository repo;

	@Override
	public void save(MatchedFBFurikomi matchedFBFurikomim) {
		// 入金登録
		Nyukin nyukin = this.buildNyukin(matchedFBFurikomim);
		nyukinCrudService.save(nyukin);
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
}
