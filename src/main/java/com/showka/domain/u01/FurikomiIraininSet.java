package com.showka.domain.u01;

import java.util.Set;

import com.showka.domain.DomainAggregation;
import com.showka.system.exception.SystemException;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 顧客と紐づく振込依頼人名Set
 * 
 */
@AllArgsConstructor
@Getter
public class FurikomiIraininSet extends DomainAggregation {

	/** 顧客. */
	private Kokyaku kokyaku;

	/** 振込依頼人Set. */
	private Set<FurikomiIrainin> set;

	@Override
	public void validate() throws SystemException {
		// do nothing
	}
}
