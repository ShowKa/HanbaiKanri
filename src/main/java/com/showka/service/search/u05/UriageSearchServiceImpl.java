package com.showka.service.search.u05;

import static com.showka.table.public_.tables.T_URIAGE.*;
import static com.showka.table.public_.tables.T_URIKAKE.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.Kokyaku;
import com.showka.domain.Uriage;
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
			from = from.innerJoin(kake).on(kake.URIAGE_ID.eq(uri.RECORD_ID));
		}
		SelectConditionStep<Record> where = from.where();
		// 顧客
		if (criteria.getKokyaku().isPresent()) {
			Kokyaku kokyaku = criteria.getKokyaku().get();
			where = where.and(uri.KOKYAKU_ID.eq(kokyaku.getRecordId()));
		}
		// 売上日 between from & to
		Timestamp minValue = criteria.getFrom().toTimestamp();
		Timestamp maxValue = criteria.getTo().toTimestamp();
		where = where.and(uri.URIAGE_DATE.between(minValue, maxValue));
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
			pk.setKokyakuId(r.get(t_uriage.KOKYAKU_ID));
			pk.setDenpyoNumber(r.get(t_uriage.DENPYO_NUMBER));
			return uriageCrudService.getDomain(pk);
		}).collect(Collectors.toList());
	}
}
