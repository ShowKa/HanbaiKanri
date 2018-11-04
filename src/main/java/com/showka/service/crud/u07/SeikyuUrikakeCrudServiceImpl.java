package com.showka.service.crud.u07;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u06.Urikake;
import com.showka.domain.u07.Seikyu;
import com.showka.domain.z00.Busho;
import com.showka.entity.SUrikakeSeikyuDone;
import com.showka.repository.i.SUrikakeSeikyuDoneRepository;
import com.showka.service.crud.u06.i.UrikakeCrudService;
import com.showka.service.crud.u07.i.SeikyuCrudService;
import com.showka.service.crud.u07.i.SeikyuUrikakeCrudService;
import com.showka.service.search.u01.i.NyukinKakeInfoSearchService;
import com.showka.service.search.u06.i.UrikakeSearchService;
import com.showka.service.search.u07.i.SeikyuSearchService;
import com.showka.service.specification.u07.SeikyuUrikakeSpecificationFactory;
import com.showka.service.specification.u07.i.SeikyuSpecification;
import com.showka.service.specification.u07.i.ShimeDateBusinessService;
import com.showka.value.EigyoDate;
import com.showka.value.ShimeDate;
import com.showka.value.TheDate;

@Service
public class SeikyuUrikakeCrudServiceImpl implements SeikyuUrikakeCrudService {

	@Autowired
	private NyukinKakeInfoSearchService nyukinKakeInfoSearchService;

	@Autowired
	private UrikakeSearchService urikakeSearchService;

	@Autowired
	private UrikakeCrudService urikakeCrudService;

	@Autowired
	private SeikyuCrudService seikyuCrudService;

	@Autowired
	private SeikyuUrikakeSpecificationFactory seikyuUrikakeSpecificationFactory;

	@Autowired
	private SUrikakeSeikyuDoneRepository repo;

	@Autowired
	private SeikyuSearchService seikyuSearchService;

	// @Autowired
	private ShimeDateBusinessService shimeDateBusinessService;

	@Override
	public void seikyu(Busho busho, EigyoDate eigyoDate) {
		// 締日リスト
		Set<ShimeDate> shimeDateSet = shimeDateBusinessService.getShimeDate(busho, eigyoDate);
		// 締日の顧客リスト
		List<Kokyaku> kokyakuList = nyukinKakeInfoSearchService.getKokyakuOnShimeDate(busho, shimeDateSet);
		kokyakuList.forEach(kokyaku -> {
			// 請求
			this.seikyu(kokyaku, eigyoDate);
		});
	}

	@Override
	public void seikyu(Kokyaku kokyaku, EigyoDate eigyoDate) {
		// 売掛リスト取得
		String kokyakuId = kokyaku.getRecordId();
		List<Urikake> urikakeList = urikakeSearchService.getUrikakeForSeikyu(kokyakuId);
		// 請求
		this.seikyu(kokyaku, eigyoDate, urikakeList);
	}

	@Override
	// TODO 売掛の入金予定を更新しているが排他制御できていない。 ==> 締処理中は画面更新不可とする制御が必要。
	public void seikyu(Kokyaku kokyaku, EigyoDate eigyoDate, List<Urikake> urikakeList) {
		// 請求仕様
		SeikyuSpecification spec = seikyuUrikakeSpecificationFactory.create(kokyaku, eigyoDate, urikakeList);
		// save 請求
		seikyuCrudService.save(spec);
		// get 登録請求
		Seikyu seikyu = seikyuCrudService.getDomain(spec);
		urikakeList.forEach(urikake -> {
			// 請求中売掛登録
			this.save(seikyu.getRecordId(), urikake.getRecordId());
			// 売掛の入金予定日更新 = 請求.支払日
			TheDate shiharaiDate = spec.getShiharaiDate();
			urikakeCrudService.updateNyukinYoteiDate(urikake, shiharaiDate);
		});
	}

	/**
	 * 売掛の最新請求を登録.
	 * 
	 * <pre>
	 * OCC対象外
	 * </pre>
	 * 
	 * @param seikyuId
	 *            請求ID
	 * @param urikakeId
	 *            売掛ID
	 */
	void save(String seikyuId, String urikakeId) {
		Optional<SUrikakeSeikyuDone> _e = repo.findById(urikakeId);
		SUrikakeSeikyuDone e = _e.orElse(new SUrikakeSeikyuDone());
		e.setSeikyuId(seikyuId);
		e.setUrikakeId(urikakeId);
		// record id
		String recordId = _e.isPresent() ? e.getRecordId() : UUID.randomUUID().toString();
		e.setRecordId(recordId);
		repo.save(e);
	}

	@Override
	public void deleteIfExists(String urikakeId) {
		// レコードがない場合、処理は不要
		if (!repo.existsById(urikakeId)) {
			return;
		}
		repo.deleteById(urikakeId);
	}

	// TODO Refactoring StatusCrudServiceに移行する。
	@Override
	public void revert(String urikakeId) {
		// レコード存在チェック
		// 既存の場合は何もせず処理終了。
		boolean exists = repo.existsById(urikakeId);
		if (exists) {
			return;
		}
		// 最新請求を取得。
		// 未請求の場合、処理終了。
		Optional<Seikyu> _newest = seikyuSearchService.getNewestOf(urikakeId);
		if (!_newest.isPresent()) {
			return;
		}
		// 最新請求
		Seikyu seikyu = _newest.get();
		// save
		this.save(seikyu.getRecordId(), urikakeId);
	}

}
