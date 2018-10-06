package com.showka.domain.builder;

import com.showka.domain.u17.BushoUriage;
import com.showka.domain.z00.Busho;
import com.showka.value.TheDate;

public class BushoUriageBuilder extends com.showka.domain.builder.DomainBuilderBase<BushoUriage, BushoUriageBuilder> {

	// private member
	/** busho */
	private Busho busho;

	/** keijoDate */
	private TheDate keijoDate;

	/** keijoKingaku */
	private Integer keijoKingaku;

	/** teiseiKingaku */
	private Integer teiseiKingaku;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(BushoUriage domain, BushoUriageBuilder builder) {
		builder.withBusho(domain.getBusho());
		builder.withKeijoDate(domain.getKeijoDate());
		builder.withKeijoKingaku(domain.getKeijoKingaku());
		builder.withTeiseiKingaku(domain.getTeiseiKingaku());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected BushoUriage createDomainObject() {
		BushoUriage domain = new BushoUriage(busho, keijoDate, keijoKingaku, teiseiKingaku);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected BushoUriageBuilder getThis() {
		return this;
	}

	@Override
	protected BushoUriageBuilder newInstance() {
		return new BushoUriageBuilder();
	}

	// public method
	/**
	 * {@link BushoUriage}に与えるbushoをこのビルダに設定する。
	 *
	 * @param busho
	 *            busho
	 * @return {@link BushoUriageBuilder}
	 */
	public BushoUriageBuilder withBusho(final Busho busho) {
		addConfigurator(new BuilderConfigurator<BushoUriageBuilder>() {
			@Override
			public void configure(BushoUriageBuilder builder) {
				builder.busho = busho;
			}
		});
		return getThis();
	}

	/**
	 * {@link BushoUriage}に与えるkeijoDateをこのビルダに設定する。
	 *
	 * @param keijoDate
	 *            keijoDate
	 * @return {@link BushoUriageBuilder}
	 */
	public BushoUriageBuilder withKeijoDate(final TheDate keijoDate) {
		addConfigurator(new BuilderConfigurator<BushoUriageBuilder>() {
			@Override
			public void configure(BushoUriageBuilder builder) {
				builder.keijoDate = keijoDate;
			}
		});
		return getThis();
	}

	/**
	 * {@link BushoUriage}に与えるkeijoKingakuをこのビルダに設定する。
	 *
	 * @param keijoKingaku
	 *            keijoKingaku
	 * @return {@link BushoUriageBuilder}
	 */
	public BushoUriageBuilder withKeijoKingaku(final Integer keijoKingaku) {
		addConfigurator(new BuilderConfigurator<BushoUriageBuilder>() {
			@Override
			public void configure(BushoUriageBuilder builder) {
				builder.keijoKingaku = keijoKingaku;
			}
		});
		return getThis();
	}

	/**
	 * {@link BushoUriage}に与えるteiseiKingakuをこのビルダに設定する。
	 *
	 * @param teiseiKingaku
	 *            teiseiKingaku
	 * @return {@link BushoUriageBuilder}
	 */
	public BushoUriageBuilder withTeiseiKingaku(final Integer teiseiKingaku) {
		addConfigurator(new BuilderConfigurator<BushoUriageBuilder>() {
			@Override
			public void configure(BushoUriageBuilder builder) {
				builder.teiseiKingaku = teiseiKingaku;
			}
		});
		return getThis();
	}

	/**
	 * {@link BushoUriage}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link BushoUriageBuilder}
	 */
	public BushoUriageBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<BushoUriageBuilder>() {
			@Override
			public void configure(BushoUriageBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link BushoUriage}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link BushoUriageBuilder}
	 */
	public BushoUriageBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<BushoUriageBuilder>() {
			@Override
			public void configure(BushoUriageBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}