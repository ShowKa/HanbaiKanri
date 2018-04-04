package com.showka.repository.specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;

import com.showka.entity.TUriage;
import com.showka.entity.TUriagePK_;
import com.showka.entity.TUriage_;
import com.showka.entity.TUrikake;
import com.showka.entity.TUrikake_;

public class TUrikakeSpecification {

	/**
	 * 売掛.残高 > 引数.残高
	 * 
	 * @param zandaka
	 *            残高
	 * @return
	 */
	public static Specification<TUrikake> zandakaGreaterThan(Integer zandaka) {
		if (zandaka == null) {
			return null;
		}
		return (root, query, cb) -> {
			return cb.greaterThan(root.get(TUrikake_.zandaka), zandaka);
		};
	}

	/**
	 * 売掛.売上.顧客 = 引数.顧客ID
	 * 
	 * @param kokyakuId
	 *            顧客ID
	 * @return
	 */
	public static Specification<TUrikake> kokyaku(String kokyakuId) {
		if (kokyakuId == null)
			return null;
		return (root, query, cb) -> {
			Join<TUrikake, TUriage> joinUriage = root.join(TUrikake_.uriage, JoinType.INNER);
			return cb.equal(joinUriage.get(TUriage_.pk).get(TUriagePK_.kokyakuId), kokyakuId);
		};
	}
}
