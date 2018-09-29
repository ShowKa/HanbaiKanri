package com.showka.service.crud.u07;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.Busho;
import com.showka.domain.Kokyaku;
import com.showka.domain.Seikyu;
import com.showka.domain.Urikake;
import com.showka.domain.UrikakeKeshikomi;
import com.showka.entity.JSeikyuUrikake;
import com.showka.repository.i.JSeikyuUrikakeRepository;
import com.showka.service.crud.u05.i.UrikakeCrudService;
import com.showka.service.crud.u06.i.UrikakeKeshikomiCrudService;
import com.showka.service.crud.u07.i.SeikyuCrudService;
import com.showka.service.crud.u07.i.SeikyuUrikakeCrudService;
import com.showka.service.search.u01.i.NyukinKakeInfoSearchService;
import com.showka.service.search.u05.i.UrikakeSearchService;
import com.showka.service.search.u07.i.SeikyuSearchService;
import com.showka.service.specification.u07.SeikyuUrikakeSpecificationFactory;
import com.showka.service.specification.u07.i.SeikyuSpecification;
import com.showka.value.EigyoDate;

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
	private UrikakeKeshikomiCrudService urikakeKeshikomiCrudService;

	@Autowired
	private JSeikyuUrikakeRepository repo;

	@Autowired
	private SeikyuSearchService seikyuSearchService;

	@Override
	public void seikyu(Busho busho, EigyoDate shimeDate) {
		// 締日の顧客リスト
		List<Kokyaku> kokyakuList = nyukinKakeInfoSearchService.getKokyakuOnShimeDate(busho, shimeDate);
		kokyakuList.forEach(kokyaku -> {
			// 請求
			this.seikyu(kokyaku, shimeDate);
		});
	}

	@Override
	public void seikyu(Kokyaku kokyaku, EigyoDate shimeDate) {
		// 売掛リスト取得
		String kokyakuId = kokyaku.getRecordId();
		// TODO 「未請求分」あるいは「請求済みで入金日が過ぎている」もののみを対象とするべき
		List<Urikake> urikakeList = urikakeSearchService.getUrikakeOfKokyaku(kokyakuId);
		// 請求
		this.seikyu(kokyaku, shimeDate, urikakeList);
	}

	@Override
	public void seikyu(Kokyaku kokyaku, EigyoDate shimeDate, List<Urikake> urikakeList) {
		// 請求仕様
		SeikyuSpecification spec = seikyuUrikakeSpecificationFactory.create(kokyaku, shimeDate, urikakeList);
		// save
		seikyuCrudService.save(spec);
		// get saved
		Seikyu seikyu = seikyuCrudService.getDomain(spec);
		urikakeList.forEach(urikake -> {
			// 売掛の入金予定日更新
			// TODO OCC?
			EigyoDate shiharaiDate = spec.getShiharaiDate();
			urikakeCrudService.updateNyukinYoteiDate(urikake, shiharaiDate);
			// 売掛の最新請求を登録
			this.save(seikyu.getRecordId(), urikake.getRecordId());
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
		Optional<JSeikyuUrikake> _e = repo.findById(urikakeId);
		JSeikyuUrikake e = _e.orElse(new JSeikyuUrikake());
		e.setSeikyuId(seikyuId);
		e.setUrikakeId(urikakeId);
		// record id
		String recordId = _e.isPresent() ? e.getRecordId() : UUID.randomUUID().toString();
		e.setRecordId(recordId);
		repo.save(e);
	}

	@Override
	public void deleteIfKeshikomiDone(String urikakeId) {
		UrikakeKeshikomi urikakeKeshikomi = urikakeKeshikomiCrudService.getDomain(urikakeId);
		if (urikakeKeshikomi.done()) {
			repo.deleteById(urikakeId);
		}
	}

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
