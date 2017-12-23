package com.showka.domain.builder;

import com.showka.domain.BushoDomain;
import com.showka.kubun.BushoKubun;
import com.showka.kubun.JigyoKubun;
import com.showka.value.EigyoDate;

public class BushoDomainBuilder extends com.showka.domain.builder.DomainBuilderBase<BushoDomain, BushoDomainBuilder> {

	// private member
	/** code */
	private String code;

	/** bushoKubun */
	private BushoKubun bushoKubun;

	/** jigyoKubun */
	private JigyoKubun jigyoKubun;

	/** name */
	private String name;

	/** eigyoDate */
	private EigyoDate eigyoDate;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(BushoDomain domain, BushoDomainBuilder builder) {
		builder.withCode(domain.getCode());
		builder.withBushoKubun(domain.getBushoKubun());
		builder.withJigyoKubun(domain.getJigyoKubun());
		builder.withName(domain.getName());
		builder.withEigyoDate(domain.getEigyoDate());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected BushoDomain createDomainObject() {
		BushoDomain domain = new BushoDomain(code, bushoKubun, jigyoKubun, name, eigyoDate);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected BushoDomainBuilder getThis() {
		return this;
	}

	@Override
	protected BushoDomainBuilder newInstance() {
		return new BushoDomainBuilder();
	}

	// public method
	/**
	 * {@link BushoDomain}に与えるcodeをこのビルダに設定する。
	 *
	 * @param code
	 *            code
	 * @return {@link BushoDomainBuilder}
	 */
	public BushoDomainBuilder withCode(final String code) {
		addConfigurator(new BuilderConfigurator<BushoDomainBuilder>() {
			@Override
			public void configure(BushoDomainBuilder builder) {
				builder.code = code;
			}
		});
		return getThis();
	}

	/**
	 * {@link BushoDomain}に与えるbushoKubunをこのビルダに設定する。
	 *
	 * @param bushoKubun
	 *            bushoKubun
	 * @return {@link BushoDomainBuilder}
	 */
	public BushoDomainBuilder withBushoKubun(final BushoKubun bushoKubun) {
		addConfigurator(new BuilderConfigurator<BushoDomainBuilder>() {
			@Override
			public void configure(BushoDomainBuilder builder) {
				builder.bushoKubun = bushoKubun;
			}
		});
		return getThis();
	}

	/**
	 * {@link BushoDomain}に与えるjigyoKubunをこのビルダに設定する。
	 *
	 * @param jigyoKubun
	 *            jigyoKubun
	 * @return {@link BushoDomainBuilder}
	 */
	public BushoDomainBuilder withJigyoKubun(final JigyoKubun jigyoKubun) {
		addConfigurator(new BuilderConfigurator<BushoDomainBuilder>() {
			@Override
			public void configure(BushoDomainBuilder builder) {
				builder.jigyoKubun = jigyoKubun;
			}
		});
		return getThis();
	}

	/**
	 * {@link BushoDomain}に与えるnameをこのビルダに設定する。
	 *
	 * @param name
	 *            name
	 * @return {@link BushoDomainBuilder}
	 */
	public BushoDomainBuilder withName(final String name) {
		addConfigurator(new BuilderConfigurator<BushoDomainBuilder>() {
			@Override
			public void configure(BushoDomainBuilder builder) {
				builder.name = name;
			}
		});
		return getThis();
	}

	/**
	 * {@link BushoDomain}に与えるeigyoDateをこのビルダに設定する。
	 *
	 * @param eigyoDate
	 *            eigyoDate
	 * @return {@link BushoDomainBuilder}
	 */
	public BushoDomainBuilder withEigyoDate(final EigyoDate eigyoDate) {
		addConfigurator(new BuilderConfigurator<BushoDomainBuilder>() {
			@Override
			public void configure(BushoDomainBuilder builder) {
				builder.eigyoDate = eigyoDate;
			}
		});
		return getThis();
	}

	/**
	 * {@link BushoDomain}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link BushoDomainBuilder}
	 */
	public BushoDomainBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<BushoDomainBuilder>() {
			@Override
			public void configure(BushoDomainBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link BushoDomain}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link BushoDomainBuilder}
	 */
	public BushoDomainBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<BushoDomainBuilder>() {
			@Override
			public void configure(BushoDomainBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}