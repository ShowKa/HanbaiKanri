package com.showka.service.search.u05;

import static com.showka.table.public_.tables.T_URIAGE.*;
import static com.showka.table.public_.tables.T_URIKAKE.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u05.Uriage;
import com.showka.entity.TUriagePK;
import com.showka.service.crud.u05.i.UriageCrudService;
import com.showka.service.search.u05.i.UriageSearchService;
import com.showka.table.public_.tables.T_URIAGE;
import com.showka.table.public_.tables.T_URIKAKE;
import com.showka.table.public_.tables.records.T_URIAGE_RECORD;

@Service
public class UriageSearchServiceImpl implements UriageSearchService {

	@Autowired
	private DSLContext create;

	@Autowired
	private UriageCrudService uriageCrudService;

	@Override
	public List<Uriage> search(UriageSearchCriteria criteria) {
		List<T_URIAGE_RECORD> records = this.searchRecords(criteria);
		return this.buildFromRecord(records);
	}

	/**
	 * 検索.
	 * 
	 * @param criteria
	 *            検索条件.
	 * @return 売上テーブルのレコード
	 */
	List<T_URIAGE_RECORD> searchRecords(UriageSearchCriteria criteria) {
		// alias
		T_URIAGE uri = t_uriage.as("uri");
		T_URIKAKE kake = t_urikake.as("kake");
		// build SQL
		SelectSelectStep<Record> select = create.select(uri.fields());
		SelectJoinStep<Record> from = select.from(uri);
		// join 売掛
		if (criteria.isOnlyUrikake()) {
			from = from.innerJoin(kake).on(kake.uriage_id.eq(uri.record_id));
		}
		SelectConditionStep<Record> where = from.where();
		// 顧客
		if (criteria.getKokyaku().isPresent()) {
			Kokyaku kokyaku = criteria.getKokyaku().get();
			where = where.and(uri.kokyaku_id.eq(kokyaku.getRecordId()));
		}
		// 売上日 between from & to
		LocalDateTime minValue = criteria.getFrom().atStartOfDay();
		LocalDateTime maxValue = criteria.getTo().atStartOfDay();
		where = where.and(uri.uriage_date.between(minValue, maxValue));
		// fetch
		List<T_URIAGE_RECORD> records = where.fetchInto(T_URIAGE_RECORD.class);
		return records;
	}

	/**
	 * レコードからドメインのビルド.
	 * 
	 * @param records
	 *            売上テーブルのレコード
	 * @return 売上
	 */
	List<Uriage> buildFromRecord(List<T_URIAGE_RECORD> records) {
		return records.stream().map(r -> {
			TUriagePK pk = new TUriagePK();
			pk.setKokyakuId(r.get(t_uriage.kokyaku_id));
			pk.setDenpyoNumber(r.get(t_uriage.denpyo_number));
			return uriageCrudService.getDomain(pk);
		}).collect(Collectors.toList());
	}
}
