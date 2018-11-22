package com.showka.service.query.u08;

import static com.showka.table.public_.tables.T_FIRM_BANK_FURIKOMI.*;
import static com.showka.table.public_.tables.W_FIRM_BANK_FURIKOMI_MATCHING.*;
import static com.showka.table.public_.tables.W_FIRM_BANK_FURIWAKE.*;

import java.util.List;
import java.util.stream.Collectors;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.builder.MatchedFBFurikomiBuilder;
import com.showka.domain.u07.Seikyu;
import com.showka.domain.u08.MatchedFBFurikomi;
import com.showka.service.persistence.u07.i.SeikyuPersistence;
import com.showka.service.query.u08.i.MatchedFBFurikomiQuery;
import com.showka.table.public_.tables.T_FIRM_BANK_FURIKOMI;
import com.showka.table.public_.tables.W_FIRM_BANK_FURIKOMI_MATCHING;
import com.showka.table.public_.tables.W_FIRM_BANK_FURIWAKE;
import com.showka.value.AmountOfMoney;
import com.showka.value.TheDate;

@Service
public class MatchedFBFurikomiQueryImpl implements MatchedFBFurikomiQuery {

	@Autowired
	private DSLContext create;

	@Autowired
	private SeikyuPersistence seikyuPersistence;

	// alias
	/** T_FIRM_BANK_FURIKOMI */
	private static final T_FIRM_BANK_FURIKOMI fb = t_firm_bank_furikomi.as("fb");
	/** W_FIRM_BANK_FURIKOMI_MATCHING */
	private static final W_FIRM_BANK_FURIKOMI_MATCHING mt = w_firm_bank_furikomi_matching.as("mt");
	/** W_FIRM_BANK_FURIWAKE */
	private static final W_FIRM_BANK_FURIWAKE fw = w_firm_bank_furiwake.as("fw");

	@Override
	public List<MatchedFBFurikomi> get(TheDate transmissionDate) {
		Result<Record> result = this.query(transmissionDate);
		// build
		List<MatchedFBFurikomi> matchedFbFurikomiList = result.parallelStream().map(r -> {
			MatchedFBFurikomiBuilder b = new MatchedFBFurikomiBuilder();
			// FB振込ID
			String id = r.get(fb.record_id);
			b.withFBFurikomiId(id);
			// 金額
			Integer _kingaku = r.get(fb.kingaku);
			AmountOfMoney kingaku = new AmountOfMoney(_kingaku);
			b.withKingaku(kingaku);
			// 請求
			String seikyuId = r.get(fw.seikyu_id);
			Seikyu seikyu = seikyuPersistence.getDomain(seikyuId);
			b.withSeikyu(seikyu);
			// 伝送日付
			b.withTransmissionDate(transmissionDate);
			return b.build();
		}).collect(Collectors.toList());
		// return
		return matchedFbFurikomiList;
	}

	/**
	 * 
	 * FB振込Table から$マッチング済FB振込を検索
	 * 
	 * @param transmissionDate
	 *            伝送日付
	 * @return 検索結果
	 */
	Result<Record> query(TheDate transmissionDate) {
		// FB振込Table から$マッチング済FB振込を検索
		Result<Record> result = create.select()
				.from(fb)
				// join マッチングTable
				// - マッチングテーブルに登録されている
				.innerJoin(mt)
				.on(fb.record_id.eq(mt.fb_furikomi_id))
				// join 振分Table
				.innerJoin(fw)
				.on(fw.record_id.eq(mt.furiwake_id))
				// where
				// - 伝送日付=入力.伝送日付
				.where(fb.transmission_date.eq(transmissionDate.toLocalDateTime()))
				// fetch
				.fetch();
		return result;
	}
}
