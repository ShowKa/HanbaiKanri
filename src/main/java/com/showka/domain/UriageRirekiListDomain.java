package com.showka.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.showka.domain.builder.UriageRirekiDomainBuilder;
import com.showka.domain.builder.UriageRirekiMeisaiDomainBuilder;
import com.showka.system.exception.SystemException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UriageRirekiListDomain extends DomainBase {

	/** 売上履歴 */
	private Set<UriageRirekiDomain> list;

	/**
	 * 最新伝票取得.
	 * 
	 * <pre>
	 * 計上日が最も新しい売上ドメインを返す。
	 * </pre>
	 * 
	 * @return 最新伝票
	 */
	public UriageRirekiDomain getNewest() {
		Optional<UriageRirekiDomain> newest = list.stream().max((r1, r2) -> {
			return r1.getKeijoDate().getDate().compareTo(r2.getKeijoDate().getDate());
		});
		return newest.get();
	}

	/**
	 * 売上を履歴ドメインとして履歴リストにマージする。
	 * 
	 * @param rdomain
	 *            売上
	 */
	public void merge(UriageDomain uriageForMerge) {
		if (list.stream()
				.map(l -> l.getKeijoDate())
				.collect(Collectors.toList())
				.contains(uriageForMerge.getKeijoDate())) {

			// merge
			list.stream().filter(uriageRirekiForOverride -> {
				return uriageRirekiForOverride.getKeijoDate().equals(uriageForMerge.getKeijoDate());
			}).forEach(uriageRirekiOverridden -> {
				list.remove(uriageRirekiOverridden);
				list.add(uriageRirekiOverridden.getOverriddenBy(uriageForMerge));
			});

		} else {
			list.add(buildUriageRirekiDomain(uriageForMerge));
		}
	}

	@Override
	public void validate() throws SystemException {
		// nothing to do
	}

	@Override
	protected boolean equals(DomainBase other) {
		UriageRirekiListDomain o = (UriageRirekiListDomain) other;
		UriageRirekiDomain newest = getNewest();
		UriageRirekiDomain otherNewest = o.getNewest();
		return newest.getUriageId().equals(otherNewest.getUriageId())
				&& newest.getKeijoDate().equals(otherNewest.getKeijoDate());
	}

	@Override
	public int hashCode() {
		UriageRirekiDomain newest = getNewest();
		return generateHashCode(newest.getUriageId(), newest.getKeijoDate());
	}

	/**
	 * 売上ドメインから履歴を生成する。
	 * 
	 * @param domain
	 *            売上ドメイン
	 * @return
	 */
	private UriageRirekiDomain buildUriageRirekiDomain(UriageDomain domain) {
		// record id
		String rirekiUriageId = UUID.randomUUID().toString();

		// build 明細
		List<UriageRirekiMeisaiDomain> uriageMeisaiList = new ArrayList<UriageRirekiMeisaiDomain>();
		domain.getUriageMeisai().forEach(m -> {
			UriageRirekiMeisaiDomainBuilder ub = new UriageRirekiMeisaiDomainBuilder();
			ub.withUriageId(rirekiUriageId);
			ub.withRecordId(UUID.randomUUID().toString());
			ub.withHanbaiNumber(m.getHanbaiNumber());
			ub.withHanbaiTanka(m.getHanbaiTanka());
			ub.withMeisaiNumber(m.getMeisaiNumber());
			ub.withShohinDomain(m.getShohinDomain());
			ub.withVersion(m.getVersion());
			// add
			uriageMeisaiList.add(ub.build());
		});

		// build 売上
		UriageRirekiDomainBuilder b = new UriageRirekiDomainBuilder();
		b.withRecordId(rirekiUriageId);
		b.withUriageMeisai(uriageMeisaiList);
		b.withDenpyoNumber(domain.getDenpyoNumber());
		b.withHanbaiKubun(domain.getHanbaiKubun());
		b.withKeijoDate(domain.getKeijoDate());
		b.withKokyaku(domain.getKokyaku());
		b.withShohizeiritsu(domain.getShohizeiritsu());
		b.withUriageDate(domain.getUriageDate());
		b.withUriageId(domain.getRecordId());
		b.withVersion(domain.getVersion());

		return b.build();
	}

}
