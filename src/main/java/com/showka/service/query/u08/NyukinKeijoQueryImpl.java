package com.showka.service.query.u08;

import static com.showka.table.public_.tables.T_NYUKIN.*;
import static com.showka.table.public_.tables.T_NYUKIN_KEIJO.*;

import java.util.List;
import java.util.stream.Collectors;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.showka.domain.u08.Nyukin;
import com.showka.domain.z00.Busho;
import com.showka.entity.TNyukinKeijo;
import com.showka.repository.i.TNyukinKeijoRepository;
import com.showka.service.persistence.u08.i.NyukinPersistence;
import com.showka.service.query.u08.i.NyukinKeijoQuery;
import com.showka.table.public_.tables.T_NYUKIN;
import com.showka.table.public_.tables.T_NYUKIN_KEIJO;
import com.showka.table.public_.tables.records.T_NYUKIN_RECORD;
import com.showka.value.EigyoDate;

@Service
public class NyukinKeijoQueryImpl implements NyukinKeijoQuery {

	@Autowired
	private DSLContext create;

	@Autowired
	private TNyukinKeijoRepository repo;

	@Autowired
	private NyukinPersistence nyukinPersistence;

	// alias
	private static final T_NYUKIN nk = t_nyukin.as("nk");
	private static final T_NYUKIN_KEIJO kj = t_nyukin_keijo.as("kj");

	@Override
	public List<Nyukin> getNotDone(Busho busho, EigyoDate kijunDate) {
		Result<T_NYUKIN_RECORD> results = this._searchNotDone(busho, kijunDate);
		return results.stream().map(r -> {
			return nyukinPersistence.getDomain(r.getRecordId());
		}).collect(Collectors.toList());
	}

	Result<T_NYUKIN_RECORD> _searchNotDone(Busho busho, EigyoDate kijunDate) {
		// select * from 入金
		SelectJoinStep<Record> from = create.select().from(nk);
		// inner join 入金計上
		from.leftJoin(kj).on(nk.record_id.eq(kj.nyukin_id));
		// where
		SelectConditionStep<Record> where = from.where();
		// 入金.部署 = 引数.部署
		where.and(nk.busho_id.eq(busho.getRecordId()));
		// 入金日 <= 引数.基準日
		where.and(nk.date.lessOrEqual(kijunDate.toLocalDateTime()));
		// 未計上
		where.and(kj.nyukin_id.isNull());
		// fetch
		return where.fetchInto(nk);
	}

	@Override
	public List<Nyukin> getDone(Busho busho, EigyoDate keijoDate) {
		TNyukinKeijo nk = new TNyukinKeijo();
		nk.setBushoId(busho.getRecordId());
		nk.setDate(keijoDate.toDate());
		Example<TNyukinKeijo> example = Example.of(nk);
		List<TNyukinKeijo> results = repo.findAll(example);
		return results.stream().map(r -> {
			return nyukinPersistence.getDomain(r.getNyukinId());
		}).collect(Collectors.toList());
	}

	@Override
	public boolean hasDone(String nyukinId) {
		return repo.existsById(nyukinId);
	}

}
