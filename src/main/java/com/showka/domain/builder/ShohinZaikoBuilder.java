package com.showka.domain.builder;

import java.util.List;

import com.showka.domain.u11.ShohinIdo;
import com.showka.domain.u11.ShohinZaiko;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shohin;
import com.showka.value.EigyoDate;

public class ShohinZaikoBuilder extends com.showka.domain.builder.DomainBuilderBase<ShohinZaiko, ShohinZaikoBuilder> {

	// private member
	/** busho */
	private Busho busho;

	/** date */
	private EigyoDate date;

	/** shohin */
	private Shohin shohin;

	/** kurikoshiNumber */
	private Integer kurikoshiNumber;

	/** shohinIdoList */
	private List<ShohinIdo> shohinIdoList;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(ShohinZaiko domain, ShohinZaikoBuilder builder) {
		builder.withBusho(domain.getBusho());
		builder.withDate(domain.getDate());
		builder.withShohin(domain.getShohin());
		builder.withKurikoshiNumber(domain.getKurikoshiNumber());
		builder.withShohinIdoList(domain.getShohinIdoList());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected ShohinZaiko createDomainObject() {
		ShohinZaiko domain = new ShohinZaiko(busho, date, shohin, kurikoshiNumber, shohinIdoList);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected ShohinZaikoBuilder getThis() {
		return this;
	}

	@Override
	protected ShohinZaikoBuilder newInstance() {
		return new ShohinZaikoBuilder();
	}

	// public method
	/**
	 * {@link ShohinZaiko}に与えるbushoをこのビルダに設定する。
	 *
	 * @param busho
	 *            busho
	 * @return {@link ShohinZaikoBuilder}
	 */
	public ShohinZaikoBuilder withBusho(final Busho busho) {
		addConfigurator(new BuilderConfigurator<ShohinZaikoBuilder>() {
			@Override
			public void configure(ShohinZaikoBuilder builder) {
				builder.busho = busho;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinZaiko}に与えるdateをこのビルダに設定する。
	 *
	 * @param date
	 *            date
	 * @return {@link ShohinZaikoBuilder}
	 */
	public ShohinZaikoBuilder withDate(final EigyoDate date) {
		addConfigurator(new BuilderConfigurator<ShohinZaikoBuilder>() {
			@Override
			public void configure(ShohinZaikoBuilder builder) {
				builder.date = date;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinZaiko}に与えるshohinをこのビルダに設定する。
	 *
	 * @param shohin
	 *            shohin
	 * @return {@link ShohinZaikoBuilder}
	 */
	public ShohinZaikoBuilder withShohin(final Shohin shohin) {
		addConfigurator(new BuilderConfigurator<ShohinZaikoBuilder>() {
			@Override
			public void configure(ShohinZaikoBuilder builder) {
				builder.shohin = shohin;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinZaiko}に与えるkurikoshiNumberをこのビルダに設定する。
	 *
	 * @param kurikoshiNumber
	 *            kurikoshiNumber
	 * @return {@link ShohinZaikoBuilder}
	 */
	public ShohinZaikoBuilder withKurikoshiNumber(final Integer kurikoshiNumber) {
		addConfigurator(new BuilderConfigurator<ShohinZaikoBuilder>() {
			@Override
			public void configure(ShohinZaikoBuilder builder) {
				builder.kurikoshiNumber = kurikoshiNumber;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinZaiko}に与えるshohinIdoListをこのビルダに設定する。
	 *
	 * @param shohinIdoList
	 *            shohinIdoList
	 * @return {@link ShohinZaikoBuilder}
	 */
	public ShohinZaikoBuilder withShohinIdoList(final List<ShohinIdo> shohinIdoList) {
		addConfigurator(new BuilderConfigurator<ShohinZaikoBuilder>() {
			@Override
			public void configure(ShohinZaikoBuilder builder) {
				builder.shohinIdoList = shohinIdoList;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinZaiko}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link ShohinZaikoBuilder}
	 */
	public ShohinZaikoBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<ShohinZaikoBuilder>() {
			@Override
			public void configure(ShohinZaikoBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link ShohinZaiko}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link ShohinZaikoBuilder}
	 */
	public ShohinZaikoBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<ShohinZaikoBuilder>() {
			@Override
			public void configure(ShohinZaikoBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}