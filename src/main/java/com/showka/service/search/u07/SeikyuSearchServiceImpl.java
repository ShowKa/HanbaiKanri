package com.showka.service.search.u07;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.Kokyaku;
import com.showka.domain.Seikyu;
import com.showka.entity.TSeikyu;
import com.showka.entity.TSeikyuPK;
import com.showka.repository.i.TSeikyuRepository;
import com.showka.service.crud.u07.i.SeikyuCrudService;
import com.showka.service.search.u07.i.SeikyuSearchService;

@Service
public class SeikyuSearchServiceImpl implements SeikyuSearchService {

	@Autowired
	private TSeikyuRepository repo;

	@Autowired
	private SeikyuCrudService seikyuCrudService;

	@Override
	public List<Seikyu> getAllOf(Kokyaku kokyaku) {
		// 請求リスト
		List<TSeikyu> seikyuList = this.getAllEntitiesOf(kokyaku);
		// entity -> domain
		return seikyuList.stream().map(seikyu -> {
			return seikyuCrudService.getDomain(seikyu.getPk());
		}).collect(Collectors.toList());
	}

	/**
	 * 全entity取得.
	 * 
	 * @param kokyaku
	 *            顧客
	 * @return 請求Entity
	 */
	protected List<TSeikyu> getAllEntitiesOf(Kokyaku kokyaku) {
		// 請求PK
		TSeikyuPK pk = new TSeikyuPK();
		pk.setKokyakuId(kokyaku.getRecordId());
		// 請求 entity
		TSeikyu entity = new TSeikyu();
		entity.setPk(pk);
		// example
		Example<TSeikyu> example = Example.of(entity);
		// find
		return repo.findAll(example);
	}
}
