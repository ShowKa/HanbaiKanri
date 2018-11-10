package com.showka.service.search.u06;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u06.Urikake;
import com.showka.entity.SUrikakeSeikyuDone;
import com.showka.entity.SUrikakeSeikyuNotYet;
import com.showka.entity.TSeikyu;
import com.showka.entity.TUriage;
import com.showka.entity.TUriagePK;
import com.showka.entity.TUrikake;
import com.showka.repository.i.SUrikakeSeikyuDoneRepository;
import com.showka.repository.i.SUrikakeSeikyuNotYetRepository;
import com.showka.service.crud.u06.i.UrikakeCrudService;
import com.showka.service.search.u06.i.UrikakeSearchService;
import com.showka.value.EigyoDate;

@Service
public class UrikakeSearchServiceImpl implements UrikakeSearchService {

	@Autowired
	private SUrikakeSeikyuNotYetRepository sUrikakeSeikyuNotYetRepository;

	@Autowired
	private SUrikakeSeikyuDoneRepository sUrikakeSeikyuDoneRepository;

	@Autowired
	private UrikakeCrudService urikakeCrudService;

	@Override
	public List<Urikake> getUrikakeForSeikyu(Kokyaku kokyaku) {
		// 主幹部署の営業日
		EigyoDate eigyoDate = kokyaku.getShukanBusho().getEigyoDate();
		// entities
		List<TUrikake> result1 = this.getSeikyuNotYetEntity(kokyaku);
		List<TUrikake> result2 = this.getSeikyuDoneButDelayedEntity(kokyaku, eigyoDate);
		// merge
		List<TUrikake> result = new ArrayList<>();
		result.addAll(result1);
		result.addAll(result2);
		// build domain
		return result.stream().map(r -> {
			return urikakeCrudService.getDomain(r.getUriageId());
		}).collect(Collectors.toList());
	}

	@Override
	public List<Urikake> getUrikakeNotSettled(Kokyaku kokyaku) {
		// entities
		List<TUrikake> result1 = this.getSeikyuNotYetEntity(kokyaku);
		List<TUrikake> result2 = this.getSeikyuDoneEntity(kokyaku);
		// merge
		List<TUrikake> result = new ArrayList<>();
		result.addAll(result1);
		result.addAll(result2);
		// build domain
		return result.stream().map(r -> {
			return urikakeCrudService.getDomain(r.getUriageId());
		}).collect(Collectors.toList());
	}

	/**
	 * 未請求状態の売掛を取得.
	 * 
	 * @param kokyaku
	 *            顧客
	 * @return 売掛Entity
	 */
	List<TUrikake> getSeikyuNotYetEntity(Kokyaku kokyaku) {
		// criteria
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId(kokyaku.getRecordId());
		TUriage ur = new TUriage();
		ur.setPk(pk);
		TUrikake uk = new TUrikake();
		uk.setUriage(ur);
		SUrikakeSeikyuNotYet su = new SUrikakeSeikyuNotYet();
		su.setUrikake(uk);
		Example<SUrikakeSeikyuNotYet> example = Example.of(su);
		// find
		List<SUrikakeSeikyuNotYet> entities = sUrikakeSeikyuNotYetRepository.findAll(example);
		// get 売掛
		return entities.stream().map(e -> {
			return e.getUrikake();
		}).collect(Collectors.toList());
	}

	/**
	 * 請求済状態の売掛を取得.
	 * 
	 * @param kokyaku
	 *            顧客
	 * @return 売掛Entity
	 */
	List<TUrikake> getSeikyuDoneEntity(Kokyaku kokyaku) {
		// find
		List<SUrikakeSeikyuDone> entities = this._getSeikyuDoneEntity(kokyaku);
		// get 売掛
		return entities.stream().map(e -> {
			return e.getUrikake();
		}).collect(Collectors.toList());
	}

	/**
	 * 請求済状態の売掛を取得.
	 * 
	 * <pre>
	 * ただし支払日が過ぎているもののみ。
	 * 請求支払日 <= 引数.基準日
	 * </pre>
	 * 
	 * @param kokyaku
	 *            顧客
	 * @param date
	 *            基準日
	 * @return 売掛Entity
	 */
	List<TUrikake> getSeikyuDoneButDelayedEntity(Kokyaku kokyaku, EigyoDate date) {
		// find
		List<SUrikakeSeikyuDone> entities = this._getSeikyuDoneEntity(kokyaku);
		// get 売掛
		return entities.stream().filter(e -> {
			TSeikyu seikyu = e.getSeikyu();
			return seikyu.getShiharaiDate().compareTo(date.toDate()) <= 0;
		}).map(e -> {
			return e.getUrikake();
		}).collect(Collectors.toList());
	}

	/**
	 * 売掛請求済状態のレコードを取得.
	 * 
	 * @param kokyaku
	 *            顧客
	 * @return 売掛請求済状態Entity
	 */
	private List<SUrikakeSeikyuDone> _getSeikyuDoneEntity(Kokyaku kokyaku) {
		// criteria
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId(kokyaku.getRecordId());
		TUriage ur = new TUriage();
		ur.setPk(pk);
		TUrikake uk = new TUrikake();
		uk.setUriage(ur);
		SUrikakeSeikyuDone sd = new SUrikakeSeikyuDone();
		sd.setUrikake(uk);
		Example<SUrikakeSeikyuDone> example = Example.of(sd);
		// find
		return sUrikakeSeikyuDoneRepository.findAll(example);
	}
}
