package com.showka.domain;

import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import com.showka.domain.builder.UriageRirekiMeisaiDomainBuilder;
import com.showka.kubun.HanbaiKubun;
import com.showka.system.BiStreamWrapper;
import com.showka.system.exception.SystemException;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;

import lombok.Getter;

/**
 * 売上履歴ドメイン
 * 
 * <pre>
 * 売上ドメインを継承。
 * 異なる点は、「顧客、伝票番号、計上日」で同値判定を行うこと。
 * </pre>
 * 
 * @author ShowKa
 *
 */

public class UriageRirekiDomain extends UriageDomain {

	/** 売上ID. */
	@Getter
	private String uriageId;

	/**
	 * コンストラクタ.
	 * 
	 * @param uriageId
	 *            売上ID
	 * @param kokyaku
	 *            顧客
	 * @param denpyoNumber
	 *            伝票番号
	 * @param uriageDate
	 *            売上日
	 * @param keijoDate
	 *            計上日
	 * @param hanbaiKubun
	 *            販売区分
	 * @param shohizeiritsu
	 *            消費税率
	 * @param uriageMeisai
	 *            売上明細ドメイン
	 */
	@SuppressWarnings("unchecked")
	public UriageRirekiDomain(String uriageId, KokyakuDomain kokyaku, String denpyoNumber, TheDate uriageDate,
			TheDate keijoDate, HanbaiKubun hanbaiKubun, TaxRate shohizeiritsu,
			List<UriageRirekiMeisaiDomain> uriageMeisai) {
		super(kokyaku, denpyoNumber, uriageDate, keijoDate, hanbaiKubun, shohizeiritsu,
				(List<UriageMeisaiDomain>) (Object) uriageMeisai);
		this.uriageId = uriageId;
	}

	/**
	 * 売上履歴明細取得.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UriageRirekiMeisaiDomain> getUriageRirekiMeisai() {
		return (List<UriageRirekiMeisaiDomain>) (Object) getUriageMeisai();
	}

	/**
	 * 売上で売上履歴を上書きしたインスタンスを返却します.
	 * 
	 * <pre>
	 *  上書対象: 売上日、販売区分、消費税率、売上履歴明細
	 * </pre>
	 * 
	 * @param uriage
	 *            売上ドメイン
	 * @return 上書き後の売上履歴ドメインのインスタンス
	 */
	public UriageRirekiDomain getOverriddenBy(UriageDomain uriage) {

		BiPredicate<UriageRirekiMeisaiDomain, UriageMeisaiDomain> predicate = (m1, m2) -> {
			return m1.getMeisaiNumber().equals(m2.getMeisaiNumber());
		};

		BiFunction<UriageRirekiMeisaiDomain, UriageMeisaiDomain, UriageRirekiMeisaiDomain> function = (m1, m2) -> {
			return m1.getOverriddenBy(m2);
		};

		List<UriageRirekiMeisaiDomain> merged = BiStreamWrapper.of(getUriageRirekiMeisai(), uriage.getUriageMeisai())
				.map(predicate, function)
				.collect(Collectors.toList());

		List<UriageRirekiMeisaiDomain> newers = BiStreamWrapper.of(getUriageRirekiMeisai(), uriage.getUriageMeisai())
				.filterNoneMatched(predicate)
				.second()
				.collect(Collectors.toList())
				.stream()
				.map(meisai -> {
					UriageRirekiMeisaiDomainBuilder b = new UriageRirekiMeisaiDomainBuilder();
					b.withUriageId(getRecordId());
					b.withRecordId(UUID.randomUUID().toString());
					return b.apply(meisai);
				})
				.collect(Collectors.toList());

		merged.addAll(newers);

		UriageRirekiDomain newDomain = new UriageRirekiDomain(getUriageId(), getKokyaku(), getDenpyoNumber(),
				uriage.getUriageDate(), getKeijoDate(), uriage.getHanbaiKubun(), uriage.getShohizeiritsu(), merged);
		newDomain.setRecordId(getRecordId());
		newDomain.setVersion(getVersion());
		return newDomain;
	}

	@Override
	public void validate() throws SystemException {
		// do nothing
	}

	@Override
	public boolean equals(DomainBase other) {
		UriageRirekiDomain o = (UriageRirekiDomain) other;
		return uriageId.equals(o.uriageId) && getKeijoDate().equals(o.getKeijoDate());
	}

	@Override
	public int hashCode() {
		return generateHashCode(uriageId, getKeijoDate());
	}

}