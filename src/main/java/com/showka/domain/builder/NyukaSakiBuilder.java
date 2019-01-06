package com.showka.domain.builder;

import com.showka.domain.u11.NyukaSaki;

public class NyukaSakiBuilder extends com.showka.domain.builder.DomainBuilderBase<NyukaSaki, NyukaSakiBuilder> {

	// private member
	/** code */
	private String code;

	/** name */
	private String name;

	/** address */
	private String address;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(NyukaSaki domain, NyukaSakiBuilder builder) {
		builder.withCode(domain.getCode());
		builder.withName(domain.getName());
		builder.withAddress(domain.getAddress());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected NyukaSaki createDomainObject() {
		NyukaSaki domain = new NyukaSaki(code, name, address);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected NyukaSakiBuilder getThis() {
		return this;
	}

	@Override
	protected NyukaSakiBuilder newInstance() {
		return new NyukaSakiBuilder();
	}

	// public method
	/**
	 * {@link NyukaSaki}に与えるcodeをこのビルダに設定する。
	 *
	 * @param code
	 *            code
	 * @return {@link NyukaSakiBuilder}
	 */
	public NyukaSakiBuilder withCode(final String code) {
		addConfigurator(new BuilderConfigurator<NyukaSakiBuilder>() {
			@Override
			public void configure(NyukaSakiBuilder builder) {
				builder.code = code;
			}
		});
		return getThis();
	}

	/**
	 * {@link NyukaSaki}に与えるnameをこのビルダに設定する。
	 *
	 * @param name
	 *            name
	 * @return {@link NyukaSakiBuilder}
	 */
	public NyukaSakiBuilder withName(final String name) {
		addConfigurator(new BuilderConfigurator<NyukaSakiBuilder>() {
			@Override
			public void configure(NyukaSakiBuilder builder) {
				builder.name = name;
			}
		});
		return getThis();
	}

	/**
	 * {@link NyukaSaki}に与えるaddressをこのビルダに設定する。
	 *
	 * @param address
	 *            address
	 * @return {@link NyukaSakiBuilder}
	 */
	public NyukaSakiBuilder withAddress(final String address) {
		addConfigurator(new BuilderConfigurator<NyukaSakiBuilder>() {
			@Override
			public void configure(NyukaSakiBuilder builder) {
				builder.address = address;
			}
		});
		return getThis();
	}

	/**
	 * {@link NyukaSaki}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link NyukaSakiBuilder}
	 */
	public NyukaSakiBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<NyukaSakiBuilder>() {
			@Override
			public void configure(NyukaSakiBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link NyukaSaki}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link NyukaSakiBuilder}
	 */
	public NyukaSakiBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<NyukaSakiBuilder>() {
			@Override
			public void configure(NyukaSakiBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}