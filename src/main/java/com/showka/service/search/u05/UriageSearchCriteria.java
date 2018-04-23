package com.showka.service.search.u05;

import java.util.Optional;

import com.showka.domain.Kokyaku;
import com.showka.value.EigyoDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UriageSearchCriteria {

	/** 顧客. */
	private Optional<Kokyaku> kokyaku;

	/** 売上日（from). */
	private EigyoDate from;

	/** 売上日（to). */
	private EigyoDate to;

	/** 売掛のみ. */
	private boolean onlyUrikake;
}
