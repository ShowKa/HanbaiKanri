package com.showka.service.query.u08;

import static com.showka.table.public_.tables.M_BUSHO_BANK_ACCOUNT.*;
import static com.showka.table.public_.tables.M_FURIKOMI_IRAININ.*;
import static com.showka.table.public_.tables.T_FIRM_BANK_FURIKOMI.*;
import static com.showka.table.public_.tables.T_SEIKYU.*;
import static com.showka.table.public_.tables.W_FIRM_BANK_FURIKOMI_MATCHING.*;
import static com.showka.table.public_.tables.W_FIRM_BANK_FURIKOMI_MATCHING_ERROR.*;
import static com.showka.table.public_.tables.W_FIRM_BANK_FURIWAKE.*;

import java.util.List;
import java.util.stream.Collectors;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showka.domain.u08.FBFurikomiMatchingResult;
import com.showka.domain.u08.FBFurikomiMatchingResult.MatchedPair;
import com.showka.domain.z00.Busho;
import com.showka.service.query.u08.i.FirmBankFurikomiQuery;
import com.showka.table.public_.tables.M_BUSHO_BANK_ACCOUNT;
import com.showka.table.public_.tables.M_FURIKOMI_IRAININ;
import com.showka.table.public_.tables.T_FIRM_BANK_FURIKOMI;
import com.showka.table.public_.tables.T_SEIKYU;
import com.showka.table.public_.tables.W_FIRM_BANK_FURIKOMI_MATCHING;
import com.showka.table.public_.tables.W_FIRM_BANK_FURIKOMI_MATCHING_ERROR;
import com.showka.table.public_.tables.W_FIRM_BANK_FURIWAKE;
import com.showka.table.public_.tables.records.T_FIRM_BANK_FURIKOMI_RECORD;
import com.showka.value.TheDate;

@Service
public class FirmBankFurikomiQueryImpl implements FirmBankFurikomiQuery {

	@Autowired
	private DSLContext create;

	// alias
	/** T_FIRM_BANK_FURIKOMI */
	private static final T_FIRM_BANK_FURIKOMI fb = t_firm_bank_furikomi.as("fb");
	/** M_BUSHO_BANK_ACCOUNT */
	private static final M_BUSHO_BANK_ACCOUNT ba = m_busho_bank_account.as("ba");
	/** W_FIRM_BANK_FURIWAKE */
	private static final W_FIRM_BANK_FURIWAKE fw = w_firm_bank_furiwake.as("fw");
	/** M_FURIKOMI_IRAININ */
	private static final M_FURIKOMI_IRAININ fi = m_furikomi_irainin.as("fi");
	/** T_SEIKYU */
	private static final T_SEIKYU ts = t_seikyu.as("ts");
	/** W_FIRM_BANK_FURIKOMI_MATCHING */
	private static final W_FIRM_BANK_FURIKOMI_MATCHING mt = w_firm_bank_furikomi_matching.as("mt");
	/** W_FIRM_BANK_FURIKOMI_MATCHING_ERROR */
	private static final W_FIRM_BANK_FURIKOMI_MATCHING_ERROR me = w_firm_bank_furikomi_matching_error.as("me");

	@Override
	public FBFurikomiMatchingResult searchMatched(Busho busho, TheDate date) {
		// 検索
		Result<Record> result = this.searchOf(busho, date);
		// マッチング
		List<MatchedPair> matched = result.parallelStream().map(r -> {
			String fbFurikomiId = r.getValue(fb.record_id);
			String furiwakeId = r.getValue(fw.record_id);
			String seikyuId = r.getValue(ts.record_id);
			return new MatchedPair(fbFurikomiId, furiwakeId, seikyuId);
		}).collect(Collectors.toList());
		// マッチング結果
		return new FBFurikomiMatchingResult(matched);
	}

	// select from fb
	// where fb.伝送日付 = date
	// and not exists (match.id = fb.id)
	// and not exists(matchingError.id = fb.id)
	@Override
	public List<String> searchUnmatched(TheDate date) {
		// 副問合せ1
		SelectConditionStep<Record1<Integer>> sub1 = create.selectOne()
				.from(mt)
				.where()
				.and(mt.fb_furikomi_id.eq(fb.record_id));
		// 副問合せ2
		SelectConditionStep<Record1<Integer>> sub2 = create.selectOne()
				.from(me)
				.where()
				.and(me.fb_furikomi_id.eq(fb.record_id));
		// 主問い合わせ
		SelectConditionStep<Record> query = create.select()
				.from(fb)
				// FB振込伝送日付
				.where()
				.and(fb.transmission_date.eq(date.getDate().atTime(0, 0)))
				.andNotExists(sub1)
				.andNotExists(sub2);
		// 問い合わせ実施
		Result<T_FIRM_BANK_FURIKOMI_RECORD> result = query.fetchInto(fb);
		// FB振込のIDを抽出
		return result.parallelStream().map(r -> {
			return r.getRecordId();
		}).collect(Collectors.toList());
	}

	/**
	 * 部署のFB振込を検索.
	 * 
	 * <pre>
	 * 対象部署の銀行口座への振込のみ抽出
	 * </pre>
	 * 
	 * @param busho
	 *            部署
	 * @param date
	 *            伝送日付
	 * @return
	 * @return FB振込レコード
	 */
	Result<Record> searchOf(Busho busho, TheDate date) {
		// 対象部署の銀行口座への振込のみ抽出
		String bushoId = busho.getRecordId();
		// 伝送日付 = 引数.日付
		SelectConditionStep<Record> condition = create.select()
				.from(fb)
				// 対象部署の銀行口座への振込のみ抽出
				.innerJoin(ba)
				.on(ba.bank_code.eq(fb.bank_code))
				.and(ba.account_number.eq(fb.account_number))
				.and(ba.busho_id.eq(bushoId))
				// 債権金額=振込金額が振込と一致する振分を抽出
				.innerJoin(fw)
				.on(fw.saiken_kingaku.in(fb.kingaku))
				// 請求担当部署=対象部署で振分を絞り込む
				.innerJoin(ts)
				.on(ts.record_id.eq(fw.seikyu_id))
				.and(ts.tanto_busho_id.eq(bushoId))
				// 振込依頼人名=振分の依頼人名と一致する。
				.innerJoin(fi)
				.on(fi.record_id.eq(fw.furikomi_irainin_id))
				.and(fi.furikomi_irainin_name.eq(fb.furikomi_irainin_name))
				// FB振込伝送日付
				.where()
				.and(fb.transmission_date.eq(date.getDate().atTime(0, 0)));
		// 取得
		return condition.fetch();
	}
}
