package com.showka.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.showka.domain.builder.UriageDomainBuilder;
import com.showka.domain.builder.UriageMeisaiDomainBuilder;
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

	// TODO
	public void merge(UriageDomain domain) {
		if (list.contains(domain)) {
			list.remove(domain);
		} else {
			// record id
			String rirekiUriageId = UUID.randomUUID().toString();

			// build 明細
			List<UriageMeisaiDomain> newUriageMeisaiList = new ArrayList<UriageMeisaiDomain>();
			domain.getUriageMeisai().forEach(m -> {
				UriageMeisaiDomainBuilder ub = new UriageMeisaiDomainBuilder();
				ub.withUriageId(rirekiUriageId);
				ub.withRecordId(UUID.randomUUID().toString());
				UriageMeisaiDomain newUriageMeisai = ub.apply(m);
				newUriageMeisaiList.add(newUriageMeisai);
			});

			// build 売上
			UriageDomainBuilder b = new UriageDomainBuilder();
			b.withRecordId(rirekiUriageId);
			b.withUriageMeisai(newUriageMeisaiList);
			UriageDomain copiedUriage = b.apply(domain);
			// list.add(copiedUriage);
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

}
