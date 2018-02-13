package com.showka.domain.builder;

import com.showka.domain.Busho;
import com.showka.kubun.BushoKubun;
import com.showka.kubun.JigyoKubun;
import com.showka.value.EigyoDate;

public class BushoBuilder extends com.showka.domain.builder.DomainBuilderBase<Busho, BushoBuilder> {

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
	protected void apply(Busho domain, BushoBuilder builder) {
		builder.withCode(domain.getCode());
		builder.withBushoKubun(domain.getBushoKubun());
		builder.withJigyoKubun(domain.getJigyoKubun());
		builder.withName(domain.getName());
		builder.withEigyoDate(domain.getEigyoDate());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected Busho createDomainObject() {
		Busho domain = new Busho(code, bushoKubun, jigyoKubun, name, eigyoDate);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected BushoBuilder getThis() {
		return this;
	}

	@Override
	protected BushoBuilder newInstance() {
		return new BushoBuilder();
	}

	// public method
	/**
	 * {@link Busho}に与えるcodeをこのビルダに設定する。
	 *
	 * @param code
	 *            code
	 * @return {@link BushoBuilder}
	 */
	public BushoBuilder withCode(final String code) {
		addConfigurator(new BuilderConfigurator<BushoBuilder>() {
			@Override
			public void configure(BushoBuilder builder) {
				builder.code = code;
			}
		});
		return getThis();
	}

	/**
	 * {@link Busho}に与えるbushoKubunをこのビルダに設定する。
	 *
	 * @param bushoKubun
	 *            bushoKubun
	 * @return {@link BushoBuilder}
	 */
	public BushoBuilder withBushoKubun(final BushoKubun bushoKubun) {
		addConfigurator(new BuilderConfigurator<BushoBuilder>() {
			@Override
			public void configure(BushoBuilder builder) {
				builder.bushoKubun = bushoKubun;
			}
		});
		return getThis();
	}

	/**
	 * {@link Busho}に与えるjigyoKubunをこのビルダに設定する。
	 *
	 * @param jigyoKubun
	 *            jigyoKubun
	 * @return {@link BushoBuilder}
	 */
	public BushoBuilder withJigyoKubun(final JigyoKubun jigyoKubun) {
		addConfigurator(new BuilderConfigurator<BushoBuilder>() {
			@Override
			public void configure(BushoBuilder builder) {
				builder.jigyoKubun = jigyoKubun;
			}
		});
		return getThis();
	}

	/**
	 * {@link Busho}に与えるnameをこのビルダに設定する。
	 *
	 * @param name
	 *            name
	 * @return {@link BushoBuilder}
	 */
	public BushoBuilder withName(final String name) {
		addConfigurator(new BuilderConfigurator<BushoBuilder>() {
			@Override
			public void configure(BushoBuilder builder) {
				builder.name = name;
			}
		});
		return getThis();
	}

	/**
	 * {@link Busho}に与えるeigyoDateをこのビルダに設定する。
	 *
	 * @param eigyoDate
	 *            eigyoDate
	 * @return {@link BushoBuilder}
	 */
	public BushoBuilder withEigyoDate(final EigyoDate eigyoDate) {
		addConfigurator(new BuilderConfigurator<BushoBuilder>() {
			@Override
			public void configure(BushoBuilder builder) {
				builder.eigyoDate = eigyoDate;
			}
		});
		return getThis();
	}

	/**
	 * {@link Busho}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link BushoBuilder}
	 */
	public BushoBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<BushoBuilder>() {
			@Override
			public void configure(BushoBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link Busho}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link BushoBuilder}
	 */
	public BushoBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<BushoBuilder>() {
			@Override
			public void configure(BushoBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}