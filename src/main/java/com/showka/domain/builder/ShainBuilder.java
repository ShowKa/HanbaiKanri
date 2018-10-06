package com.showka.domain.builder;

import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shain;

public class ShainBuilder extends com.showka.domain.builder.DomainBuilderBase<Shain, ShainBuilder> {

	// private member
	/** code */
	private String code;

	/** name */
	private String name;

	/** shozokuBusho */
	private Busho shozokuBusho;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(Shain domain, ShainBuilder builder) {
		builder.withCode(domain.getCode());
		builder.withName(domain.getName());
		builder.withShozokuBusho(domain.getShozokuBusho());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected Shain createDomainObject() {
		Shain domain = new Shain(code, name, shozokuBusho);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected ShainBuilder getThis() {
		return this;
	}

	@Override
	protected ShainBuilder newInstance() {
		return new ShainBuilder();
	}

	// public method
	/**
	 * {@link Shain}に与えるcodeをこのビルダに設定する。
	 *
	 * @param code
	 *            code
	 * @return {@link ShainBuilder}
	 */
	public ShainBuilder withCode(final String code) {
		addConfigurator(new BuilderConfigurator<ShainBuilder>() {
			@Override
			public void configure(ShainBuilder builder) {
				builder.code = code;
			}
		});
		return getThis();
	}

	/**
	 * {@link Shain}に与えるnameをこのビルダに設定する。
	 *
	 * @param name
	 *            name
	 * @return {@link ShainBuilder}
	 */
	public ShainBuilder withName(final String name) {
		addConfigurator(new BuilderConfigurator<ShainBuilder>() {
			@Override
			public void configure(ShainBuilder builder) {
				builder.name = name;
			}
		});
		return getThis();
	}

	/**
	 * {@link Shain}に与えるshozokuBushoをこのビルダに設定する。
	 *
	 * @param shozokuBusho
	 *            shozokuBusho
	 * @return {@link ShainBuilder}
	 */
	public ShainBuilder withShozokuBusho(final Busho shozokuBusho) {
		addConfigurator(new BuilderConfigurator<ShainBuilder>() {
			@Override
			public void configure(ShainBuilder builder) {
				builder.shozokuBusho = shozokuBusho;
			}
		});
		return getThis();
	}

	/**
	 * {@link Shain}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link ShainBuilder}
	 */
	public ShainBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<ShainBuilder>() {
			@Override
			public void configure(ShainBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link Shain}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link ShainBuilder}
	 */
	public ShainBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<ShainBuilder>() {
			@Override
			public void configure(ShainBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}