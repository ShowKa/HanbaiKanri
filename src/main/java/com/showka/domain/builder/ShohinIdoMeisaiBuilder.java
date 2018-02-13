package com.showka.domain.builder;

import com.showka.domain.Shohin;
import com.showka.domain.ShohinIdoMeisai;

public class ShohinIdoMeisaiBuilder
		extends com.showka.domain.builder.DomainBuilderBase<ShohinIdoMeisai, ShohinIdoMeisaiBuilder> {

	// private member
	/** meisaiNumber */
	private Integer meisaiNumber;

	/** shohinDomain */
	private Shohin shohinDomain;

	/** number */
	private Integer number;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(ShohinIdoMeisai domain, ShohinIdoMeisaiBuilder builder) {
		builder.withMeisaiNumber(domain.getMeisaiNumber());
		builder.withShohinDomain(domain.getShohinDomain());
		builder.withNumber(domain.getNumber());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected ShohinIdoMeisai createDomainObject() {
		ShohinIdoMeisai domain = new ShohinIdoMeisai(meisaiNumber, shohinDomain, number);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected ShohinIdoMeisaiBuilder getThis() {
		return this;
	}

	@Override
	protected ShohinIdoMeisaiBuilder newInstance() {
		return new ShohinIdoMeisaiBuilder();
	}

	// public method
	/**
	 * {@link ShohinIdoMeisai}に与えるmeisaiNumberをこのビルダに設定する。
	 *
	 * @param meisaiNumber
	 *            meisaiNumber
	 * @return {@link ShohinIdoMeisaiBuilder}
	 */
	public ShohinIdoMeisaiBuilder withMeisaiNumber(final Integer meisaiNumber) {
		addConfigurator(new BuilderConfigurator<ShohinIdoMeisaiBuilder>() {
			@Override
			public void configure(ShohinIdoMeisaiBuilder builder) {
				builder.meisaiNumber = meisaiNumber;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinIdoMeisai}に与えるshohinDomainをこのビルダに設定する。
	 *
	 * @param shohinDomain
	 *            shohinDomain
	 * @return {@link ShohinIdoMeisaiBuilder}
	 */
	public ShohinIdoMeisaiBuilder withShohinDomain(final Shohin shohinDomain) {
		addConfigurator(new BuilderConfigurator<ShohinIdoMeisaiBuilder>() {
			@Override
			public void configure(ShohinIdoMeisaiBuilder builder) {
				builder.shohinDomain = shohinDomain;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinIdoMeisai}に与えるnumberをこのビルダに設定する。
	 *
	 * @param number
	 *            number
	 * @return {@link ShohinIdoMeisaiBuilder}
	 */
	public ShohinIdoMeisaiBuilder withNumber(final Integer number) {
		addConfigurator(new BuilderConfigurator<ShohinIdoMeisaiBuilder>() {
			@Override
			public void configure(ShohinIdoMeisaiBuilder builder) {
				builder.number = number;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinIdoMeisai}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link ShohinIdoMeisaiBuilder}
	 */
	public ShohinIdoMeisaiBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<ShohinIdoMeisaiBuilder>() {
			@Override
			public void configure(ShohinIdoMeisaiBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinIdoMeisai}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link ShohinIdoMeisaiBuilder}
	 */
	public ShohinIdoMeisaiBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<ShohinIdoMeisaiBuilder>() {
			@Override
			public void configure(ShohinIdoMeisaiBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}