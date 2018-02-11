package com.showka.domain.builder;

import com.showka.domain.ShohinDomain;
import com.showka.domain.ShohinIdoMeisaiDomain;

public class ShohinIdoMeisaiDomainBuilder
		extends com.showka.domain.builder.DomainBuilderBase<ShohinIdoMeisaiDomain, ShohinIdoMeisaiDomainBuilder> {

	// private member
	/** meisaiNumber */
	private Integer meisaiNumber;

	/** shohinDomain */
	private ShohinDomain shohinDomain;

	/** number */
	private Integer number;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(ShohinIdoMeisaiDomain domain, ShohinIdoMeisaiDomainBuilder builder) {
		builder.withMeisaiNumber(domain.getMeisaiNumber());
		builder.withShohinDomain(domain.getShohinDomain());
		builder.withNumber(domain.getNumber());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected ShohinIdoMeisaiDomain createDomainObject() {
		ShohinIdoMeisaiDomain domain = new ShohinIdoMeisaiDomain(meisaiNumber, shohinDomain, number);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected ShohinIdoMeisaiDomainBuilder getThis() {
		return this;
	}

	@Override
	protected ShohinIdoMeisaiDomainBuilder newInstance() {
		return new ShohinIdoMeisaiDomainBuilder();
	}

	// public method
	/**
	 * {@link ShohinIdoMeisaiDomain}に与えるmeisaiNumberをこのビルダに設定する。
	 *
	 * @param meisaiNumber
	 *            meisaiNumber
	 * @return {@link ShohinIdoMeisaiDomainBuilder}
	 */
	public ShohinIdoMeisaiDomainBuilder withMeisaiNumber(final Integer meisaiNumber) {
		addConfigurator(new BuilderConfigurator<ShohinIdoMeisaiDomainBuilder>() {
			@Override
			public void configure(ShohinIdoMeisaiDomainBuilder builder) {
				builder.meisaiNumber = meisaiNumber;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinIdoMeisaiDomain}に与えるshohinDomainをこのビルダに設定する。
	 *
	 * @param shohinDomain
	 *            shohinDomain
	 * @return {@link ShohinIdoMeisaiDomainBuilder}
	 */
	public ShohinIdoMeisaiDomainBuilder withShohinDomain(final ShohinDomain shohinDomain) {
		addConfigurator(new BuilderConfigurator<ShohinIdoMeisaiDomainBuilder>() {
			@Override
			public void configure(ShohinIdoMeisaiDomainBuilder builder) {
				builder.shohinDomain = shohinDomain;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinIdoMeisaiDomain}に与えるnumberをこのビルダに設定する。
	 *
	 * @param number
	 *            number
	 * @return {@link ShohinIdoMeisaiDomainBuilder}
	 */
	public ShohinIdoMeisaiDomainBuilder withNumber(final Integer number) {
		addConfigurator(new BuilderConfigurator<ShohinIdoMeisaiDomainBuilder>() {
			@Override
			public void configure(ShohinIdoMeisaiDomainBuilder builder) {
				builder.number = number;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinIdoMeisaiDomain}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link ShohinIdoMeisaiDomainBuilder}
	 */
	public ShohinIdoMeisaiDomainBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<ShohinIdoMeisaiDomainBuilder>() {
			@Override
			public void configure(ShohinIdoMeisaiDomainBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinIdoMeisaiDomain}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link ShohinIdoMeisaiDomainBuilder}
	 */
	public ShohinIdoMeisaiDomainBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<ShohinIdoMeisaiDomainBuilder>() {
			@Override
			public void configure(ShohinIdoMeisaiDomainBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}