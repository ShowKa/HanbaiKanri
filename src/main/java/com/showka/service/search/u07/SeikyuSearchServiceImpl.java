package com.showka.service.search.u07;

import static com.showka.table.public_.tables.J_SEIKYU_URIKAKE.*;
import static com.showka.table.public_.tables.T_SEIKYU.*;

import java.util.List;
import java.util.stream.Collectors;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.Busho;
import com.showka.domain.Kokyaku;
import com.showka.domain.Seikyu;
import com.showka.entity.TSeikyu;
import com.showka.entity.TSeikyuPK;
import com.showka.repository.i.TSeikyuRepository;
import com.showka.service.crud.u07.i.SeikyuCrudService;
import com.showka.service.search.u07.i.SeikyuSearchService;
import com.showka.table.public_.tables.J_SEIKYU_URIKAKE;
import com.showka.table.public_.tables.T_SEIKYU;
import com.showka.table.public_.tables.records.T_SEIKYU_RECORD;

@Service
public class SeikyuSearchServiceImpl implements SeikyuSearchService {

	@Autowired
	private TSeikyuRepository repo;

	@Autowired
	private SeikyuCrudService seikyuCrudService;

	@Autowired
	private DSLContext create;

	// inner join j_seikyu_urikake
	// on = seikyu.seikyu_id = j.seikyu_id
	// where seikyu.tantoBusho = busho
	@Override
	public List<Seikyu> getAllOf(Busho busho) {
		List<T_SEIKYU_RECORD> seikyuRecords = this.getAllRecordsOf(busho);
		return seikyuRecords.stream().map(r -> {
			return seikyuCrudService.getDomain(r.getRecordId());
		}).collect(Collectors.toList());
	}

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
	 * 請求レコードを取得
	 * 
	 * @param busho
	 *            部署
	 * @return 請求レコード
	 */
	protected List<T_SEIKYU_RECORD> getAllRecordsOf(Busho busho) {
		// alias
		T_SEIKYU s = t_seikyu.as("s");
		J_SEIKYU_URIKAKE su = j_seikyu_urikake.as("su");
		// build SQL
		SelectSelectStep<Record> select = create.select(s.fields());
		SelectJoinStep<Record> from = select.from(s);
		// join
		from.innerJoin(su).on(s.record_id.eq(su.seikyu_id));
		// where
		SelectConditionStep<Record> where = from.where();
		where = where.and(s.tanto_busho_id.eq(busho.getRecordId()));
		List<T_SEIKYU_RECORD> records = where.fetchInto(T_SEIKYU_RECORD.class);
		return records;
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
