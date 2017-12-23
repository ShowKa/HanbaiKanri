package com.showka.domain.builder;

import com.showka.domain.BushoDomain;
import com.showka.domain.ShainDomain;

public class ShainDomainBuilder extends com.showka.domain.builder.DomainBuilderBase<ShainDomain, ShainDomainBuilder> {

	// private member
	/** code */
	private String code;

	/** name */
	private String name;

	/** shozokuBusho */
	private BushoDomain shozokuBusho;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(ShainDomain domain, ShainDomainBuilder builder) {
		builder.withCode(domain.getCode());
		builder.withName(domain.getName());
		builder.withShozokuBusho(domain.getShozokuBusho());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected ShainDomain createDomainObject() {
		ShainDomain domain = new ShainDomain(code, name, shozokuBusho);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected ShainDomainBuilder getThis() {
		return this;
	}

	@Override
	protected ShainDomainBuilder newInstance() {
		return new ShainDomainBuilder();
	}

	// public method
	/**
	 * {@link ShainDomain}に与えるcodeをこのビルダに設定する。
	 *
	 * @param code
	 *            code
	 * @return {@link ShainDomainBuilder}
	 */
	public ShainDomainBuilder withCode(final String code) {
		addConfigurator(new BuilderConfigurator<ShainDomainBuilder>() {
			@Override
			public void configure(ShainDomainBuilder builder) {
				builder.code = code;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShainDomain}に与えるnameをこのビルダに設定する。
	 *
	 * @param name
	 *            name
	 * @return {@link ShainDomainBuilder}
	 */
	public ShainDomainBuilder withName(final String name) {
		addConfigurator(new BuilderConfigurator<ShainDomainBuilder>() {
			@Override
			public void configure(ShainDomainBuilder builder) {
				builder.name = name;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShainDomain}に与えるshozokuBushoをこのビルダに設定する。
	 *
	 * @param shozokuBusho
	 *            shozokuBusho
	 * @return {@link ShainDomainBuilder}
	 */
	public ShainDomainBuilder withShozokuBusho(final BushoDomain shozokuBusho) {
		addConfigurator(new BuilderConfigurator<ShainDomainBuilder>() {
			@Override
			public void configure(ShainDomainBuilder builder) {
				builder.shozokuBusho = shozokuBusho;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShainDomain}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link ShainDomainBuilder}
	 */
	public ShainDomainBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<ShainDomainBuilder>() {
			@Override
			public void configure(ShainDomainBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShainDomain}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link ShainDomainBuilder}
	 */
	public ShainDomainBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<ShainDomainBuilder>() {
			@Override
			public void configure(ShainDomainBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}