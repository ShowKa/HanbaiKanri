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

	@SuppressWarnings("unchecked")
	public UriageRirekiDomain(String uriageId, KokyakuDomain kokyaku, String denpyoNumber, TheDate uriageDate,
			TheDate keijoDate, HanbaiKubun hanbaiKubun, TaxRate shohizeiritsu,
			List<UriageRirekiMeisaiDomain> uriageMeisai) {
		super(kokyaku, denpyoNumber, uriageDate, keijoDate, hanbaiKubun, shohizeiritsu,
				(List<UriageMeisaiDomain>) (Object) uriageMeisai);
		this.uriageId = uriageId;
	}

	@SuppressWarnings("unchecked")
	public List<UriageRirekiMeisaiDomain> getUriageRirekiMeisai() {
		return (List<UriageRirekiMeisaiDomain>) (Object) getUriageMeisai();
	}

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
					b.withUriageId(getUriageId());
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