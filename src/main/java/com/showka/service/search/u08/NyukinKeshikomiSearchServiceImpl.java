package com.showka.service.search.u08;

import static com.showka.table.public_.tables.T_NYUKIN.*;
import static com.showka.table.public_.tables.T_SHUKIN.*;

import java.util.List;
import java.util.stream.Collectors;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u08.Nyukin;
import com.showka.domain.u08.NyukinKeshikomi;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shain;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.service.crud.u08.i.NyukinCrudService;
import com.showka.service.crud.u08.i.NyukinKeshikomiCrudService;
import com.showka.service.search.u08.i.NyukinKeshikomiSearchParm;
import com.showka.service.search.u08.i.NyukinKeshikomiSearchService;
import com.showka.table.public_.tables.T_NYUKIN;
import com.showka.table.public_.tables.T_SHUKIN;
import com.showka.table.public_.tables.records.T_NYUKIN_RECORD;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

@Service
public class NyukinKeshikomiSearchServiceImpl implements NyukinKeshikomiSearchService {

	@Autowired
	private DSLContext create;

	@Autowired
	private NyukinCrudService nyukinCrudService;

	@Autowired
	private NyukinKeshikomiCrudService nyukinKeshikomiCrudService;

	// alias
	private static final T_NYUKIN nk = t_nyukin.as("nk");
	private static final T_SHUKIN sk = t_shukin.as("sk");

	// XXX 性能は悪そう。
	@Override
	public List<NyukinKeshikomi> search(NyukinKeshikomiSearchParm param) {
		List<T_NYUKIN_RECORD> results = this.searchRecords(param);
		// 入金
		List<Nyukin> nyukinList = results.parallelStream().map(r -> {
			Nyukin nyukin = nyukinCrudService.getDomain(r.get(nk.record_id));
			return nyukin;
		}).collect(Collectors.toList());
		// 入金消込取得
		List<NyukinKeshikomi> keshikomiList = nyukinList.parallelStream().map(n -> {
			NyukinKeshikomi nyukinKeshikomi = nyukinKeshikomiCrudService.getDomain(n.getRecordId());
			return nyukinKeshikomi;
		}).filter(nyukinKeshikomi -> {
			if (param.isIncludeKeshikomiDone()) {
				// 消込完了含む = 全て
				return true;
			} else {
				// 消込完了含まない = 消込未完了のみ
				return !nyukinKeshikomi.done();
			}
		}).collect(Collectors.toList());
		return keshikomiList;
	}

	List<T_NYUKIN_RECORD> searchRecords(NyukinKeshikomiSearchParm param) {
		SelectJoinStep<Record> from = create.select().from(nk);
		// 集金の場合、担当社員も検索条件に加える。
		Shain tantoShain = param.getTantoShain();
		NyukinHohoKubun hoho = param.getNyukinHoho();
		if (NyukinHohoKubun.集金.equals(hoho) && tantoShain != null) {
			from = from.innerJoin(sk)
					.on(nk.record_id.eq(sk.nyukin_id))
					.and(sk.tanto_shain_id.eq(tantoShain.getRecordId()));
		}
		// 顧客
		SelectConditionStep<Record> where = from.where();
		Kokyaku kokyaku = param.getKokyaku();
		if (kokyaku != null) {
			where = where.and(nk.kokyaku_id.eq(kokyaku.getRecordId()));
		}
		// 部署
		Busho busho = param.getBusho();
		if (busho != null) {
			where = where.and(nk.busho_id.eq(busho.getRecordId()));
		}
		// 入金日
		EigyoDate minDate = param.getMinNyukinDate();
		if (minDate != null) {
			where = where.and(nk.date.greaterOrEqual(minDate.toLocalDateTime()));
		}
		EigyoDate maxDate = param.getMaxNyukinDate();
		if (maxDate != null) {
			where = where.and(nk.date.lessOrEqual(maxDate.toLocalDateTime()));
		}
		// 金額
		AmountOfMoney minKingaku = param.getMinKingaku();
		if (minKingaku != null) {
			where = where.and(nk.kingaku.greaterOrEqual(minKingaku.intValue()));
		}
		AmountOfMoney maxKingaku = param.getMaxKingaku();
		if (maxKingaku != null) {
			where = where.and(nk.kingaku.lessOrEqual(maxKingaku.intValue()));
		}
		// 入金方法区分
		if (hoho != null) {
			where = where.and(nk.nyukin_hoho_kubun.eq(hoho.getCode()));
		}
		// 検索
		Result<T_NYUKIN_RECORD> results = where.fetchInto(t_nyukin);
		return results;
	}

}
