package com.showka.domain;

import java.util.List;
import java.util.Optional;

import com.showka.system.exception.SystemException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UriageRirekiDomain extends DomainBase {

	/** 売上履歴 */
	private List<UriageDomain> list;

	/**
	 * 最新伝票取得.
	 * 
	 * <pre>
	 * 計上日が最も新しい売上ドメインを返す。
	 * </pre>
	 * 
	 * @return 最新伝票
	 */
	public UriageDomain getNewest() {
		Optional<UriageDomain> newest = list.stream().max((r1, r2) -> {
			return r1.getKeijoDate().getDate().compareTo(r2.getKeijoDate().getDate());
		});
		return newest.get();
	}

	@Override
	public void validate() throws SystemException {
		// nothing to do
	}

	@Override
	protected boolean equals(DomainBase other) {
		UriageRirekiDomain o = (UriageRirekiDomain) other;
		UriageDomain newest = getNewest();
		UriageDomain otherNewest = o.getNewest();
		return newest.equals(otherNewest) && newest.getKeijoDate().equals(otherNewest.getKeijoDate());
	}

	@Override
	public int hashCode() {
		UriageDomain newest = getNewest();
		return generateHashCode(newest, newest.getKeijoDate());
	}

}
