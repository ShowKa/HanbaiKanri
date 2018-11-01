package com.showka.service.search.u06;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.u06.Urikake;
import com.showka.entity.TUriage;
import com.showka.entity.TUriagePK;
import com.showka.entity.TUrikake;
import com.showka.repository.i.TUrikakeRepository;
import com.showka.service.crud.u06.i.UrikakeKeshikomiCrudService;
import com.showka.service.search.u06.i.UrikakeSearchService;

@Service
public class UrikakeSearchServiceImpl implements UrikakeSearchService {

	@Autowired
	private TUrikakeRepository tUrikakeRepository;

	@Autowired
	private UrikakeKeshikomiCrudService urikakeKeshikomiCrudService;

	@Override
	public List<Urikake> getUrikakeForSeikyu(String kokyakuId) {
		// find entity
		List<TUrikake> result = this.getUrikakeOfKokyakuRecord(kokyakuId);
		// build domain
		return result.stream().map(urikake -> {
			// 売掛消込取得
			return urikakeKeshikomiCrudService.getDomain(urikake.getRecordId());
		}).filter(urikakeKeshikomi -> {
			// 消込未完了のみ抽出
			return !urikakeKeshikomi.done();
		}).map(urikakeKeshikomi -> {
			// 売掛取得
			return urikakeKeshikomi.getUrikake();
		}).collect(Collectors.toList());
	}

	/**
	 * 顧客の売掛レコードを取得.
	 * 
	 * @param kokyakuId
	 *            顧客ID
	 * @return 売掛レコード
	 */
	List<TUrikake> getUrikakeOfKokyakuRecord(String kokyakuId) {
		// find entity
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId(kokyakuId);
		TUriage ur = new TUriage();
		ur.setPk(pk);
		TUrikake uk = new TUrikake();
		uk.setUriage(ur);
		Example<TUrikake> exmple = Example.of(uk);
		return tUrikakeRepository.findAll(exmple);
	}
}
