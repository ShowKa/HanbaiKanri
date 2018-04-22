package com.showka.service.search.u05;

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
	private Kokyaku kokyaku;

	/** 売上日（from). */
	private EigyoDate from;

	/** 売上日（to). */
	private EigyoDate to;

	/** 売掛残. */
	private boolean existsUrikake;
}
