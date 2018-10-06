package com.showka.service.search.u07;

import static com.showka.table.public_.tables.J_SEIKYU_URIKAKE.*;
import static com.showka.table.public_.tables.T_SEIKYU.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u07.Seikyu;
import com.showka.domain.z00.Busho;
import com.showka.entity.TSeikyu;
import com.showka.entity.TSeikyuMeisai;
import com.showka.entity.TSeikyuMeisaiPK;
import com.showka.entity.TSeikyuPK;
import com.showka.repository.i.TSeikyuMeisaiRepository;
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
	private TSeikyuMeisaiRepository meisaiRepo;

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
	List<T_SEIKYU_RECORD> getAllRecordsOf(Busho busho) {
		// alias
		T_SEIKYU s = t_seikyu.as("s");
		J_SEIKYU_URIKAKE su = j_seikyu_urikake.as("su");
		// build SQL
		SelectSelectStep<Record> select = create.selectDistinct(s.fields());
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
	List<TSeikyu> getAllEntitiesOf(Kokyaku kokyaku) {
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

	@Override
	public List<Seikyu> getHistoryOf(String urikakeId) {
		// 請求明細取得
		List<TSeikyuMeisai> result = this.getHistoryEntitiesOf(urikakeId);
		// 請求IDのみ抽出（重複排除）
		Set<String> seikyuIdSet = result.parallelStream().map(r -> {
			return r.getSeikyuId();
		}).collect(Collectors.toSet());
		// 請求リスト
		List<Seikyu> seikyuList = seikyuIdSet.parallelStream().map(id -> {
			return seikyuCrudService.getDomain(id);
		}).collect(Collectors.toList());
		return seikyuList;
	}

	/**
	 * 売掛IDで請求明細テーブルのレコードを検索し取得する。
	 * 
	 * @param urikakeId
	 *            売掛ID
	 * @return 請求明細レコード
	 */
	List<TSeikyuMeisai> getHistoryEntitiesOf(String urikakeId) {
		// pk
		TSeikyuMeisaiPK pk = new TSeikyuMeisaiPK();
		pk.setUrikakeId(urikakeId);
		// entity
		TSeikyuMeisai e = new TSeikyuMeisai();
		e.setPk(pk);
		// findAll
		Example<TSeikyuMeisai> example = Example.of(e);
		List<TSeikyuMeisai> result = meisaiRepo.findAll(example);
		return result;
	}

	@Override
	public Optional<Seikyu> getNewestOf(String urikakeId) {
		// 請求履歴
		List<Seikyu> seikyuList = this.getHistoryOf(urikakeId);
		// 最新のものを抽出。
		Optional<Seikyu> newest = seikyuList.parallelStream().max((one, two) -> {
			return one.getSeikyuDate().compareTo(two.getSeikyuDate());
		});
		return newest;
	}
}
