package com.showka.domain.builder;

import java.math.BigDecimal;

import com.showka.domain.ShohinDomain;

public class ShohinDomainBuilder
		extends com.showka.domain.builder.DomainBuilderBase<ShohinDomain, ShohinDomainBuilder> {

	// private member
	/** code */
	private String code;

	/** name */
	private String name;

	/** hyojunTanka */
	private BigDecimal hyojunTanka;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(ShohinDomain domain, ShohinDomainBuilder builder) {
		builder.withCode(domain.getCode());
		builder.withName(domain.getName());
		builder.withHyojunTanka(domain.getHyojunTanka());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected ShohinDomain createDomainObject() {
		ShohinDomain domain = new ShohinDomain(code, name, hyojunTanka);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected ShohinDomainBuilder getThis() {
		return this;
	}

	@Override
	protected ShohinDomainBuilder newInstance() {
		return new ShohinDomainBuilder();
	}

	// public method
	/**
	 * {@link ShohinDomain}に与えるcodeをこのビルダに設定する。
	 *
	 * @param code
	 *            code
	 * @return {@link ShohinDomainBuilder}
	 */
	public ShohinDomainBuilder withCode(final String code) {
		addConfigurator(new BuilderConfigurator<ShohinDomainBuilder>() {
			@Override
			public void configure(ShohinDomainBuilder builder) {
				builder.code = code;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinDomain}に与えるnameをこのビルダに設定する。
	 *
	 * @param name
	 *            name
	 * @return {@link ShohinDomainBuilder}
	 */
	public ShohinDomainBuilder withName(final String name) {
		addConfigurator(new BuilderConfigurator<ShohinDomainBuilder>() {
			@Override
			public void configure(ShohinDomainBuilder builder) {
				builder.name = name;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinDomain}に与えるhyojunTankaをこのビルダに設定する。
	 *
	 * @param hyojunTanka
	 *            hyojunTanka
	 * @return {@link ShohinDomainBuilder}
	 */
	public ShohinDomainBuilder withHyojunTanka(final BigDecimal hyojunTanka) {
		addConfigurator(new BuilderConfigurator<ShohinDomainBuilder>() {
			@Override
			public void configure(ShohinDomainBuilder builder) {
				builder.hyojunTanka = hyojunTanka;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinDomain}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link ShohinDomainBuilder}
	 */
	public ShohinDomainBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<ShohinDomainBuilder>() {
			@Override
			public void configure(ShohinDomainBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinDomain}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link ShohinDomainBuilder}
	 */
	public ShohinDomainBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<ShohinDomainBuilder>() {
			@Override
			public void configure(ShohinDomainBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}