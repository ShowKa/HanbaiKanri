package com.showka.domain.builder;

import java.math.BigDecimal;

import com.showka.domain.Shohin;

public class ShohinBuilder
		extends com.showka.domain.builder.DomainBuilderBase<Shohin, ShohinBuilder> {

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
	protected void apply(Shohin domain, ShohinBuilder builder) {
		builder.withCode(domain.getCode());
		builder.withName(domain.getName());
		builder.withHyojunTanka(domain.getHyojunTanka());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected Shohin createDomainObject() {
		Shohin domain = new Shohin(code, name, hyojunTanka);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected ShohinBuilder getThis() {
		return this;
	}

	@Override
	protected ShohinBuilder newInstance() {
		return new ShohinBuilder();
	}

	// public method
	/**
	 * {@link Shohin}に与えるcodeをこのビルダに設定する。
	 *
	 * @param code
	 *            code
	 * @return {@link ShohinBuilder}
	 */
	public ShohinBuilder withCode(final String code) {
		addConfigurator(new BuilderConfigurator<ShohinBuilder>() {
			@Override
			public void configure(ShohinBuilder builder) {
				builder.code = code;
			}
		});
		return getThis();
	}

	/**
	 * {@link Shohin}に与えるnameをこのビルダに設定する。
	 *
	 * @param name
	 *            name
	 * @return {@link ShohinBuilder}
	 */
	public ShohinBuilder withName(final String name) {
		addConfigurator(new BuilderConfigurator<ShohinBuilder>() {
			@Override
			public void configure(ShohinBuilder builder) {
				builder.name = name;
			}
		});
		return getThis();
	}

	/**
	 * {@link Shohin}に与えるhyojunTankaをこのビルダに設定する。
	 *
	 * @param hyojunTanka
	 *            hyojunTanka
	 * @return {@link ShohinBuilder}
	 */
	public ShohinBuilder withHyojunTanka(final BigDecimal hyojunTanka) {
		addConfigurator(new BuilderConfigurator<ShohinBuilder>() {
			@Override
			public void configure(ShohinBuilder builder) {
				builder.hyojunTanka = hyojunTanka;
			}
		});
		return getThis();
	}

	/**
	 * {@link Shohin}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link ShohinBuilder}
	 */
	public ShohinBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<ShohinBuilder>() {
			@Override
			public void configure(ShohinBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link Shohin}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link ShohinBuilder}
	 */
	public ShohinBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<ShohinBuilder>() {
			@Override
			public void configure(ShohinBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}