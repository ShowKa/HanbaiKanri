package com.showka.domain.builder;

import java.util.List;

import com.showka.domain.u11.Nyuka;
import com.showka.domain.u11.NyukaSaki;
import com.showka.domain.u11.ShohinIdo;
import com.showka.domain.z00.Busho;
import com.showka.value.EigyoDate;

public class NyukaBuilder extends com.showka.domain.builder.DomainBuilderBase<Nyuka, NyukaBuilder> {

	// private member
	/** busho */
	private Busho busho;

	/** nyukaSaki */
	private NyukaSaki nyukaSaki;

	/** nyukaDate */
	private EigyoDate nyukaDate;

	/** nyukaShohinIdo */
	private ShohinIdo nyukaShohinIdo;

	/** teiseiList */
	private List teiseiList;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(Nyuka domain, NyukaBuilder builder) {
		builder.withBusho(domain.getBusho());
		builder.withNyukaSaki(domain.getNyukaSaki());
		builder.withNyukaDate(domain.getNyukaDate());
		builder.withNyukaShohinIdo(domain.getNyukaShohinIdo());
		builder.withTeiseiList(domain.getTeiseiList());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected Nyuka createDomainObject() {
		Nyuka domain = new Nyuka(busho, nyukaSaki, nyukaDate, nyukaShohinIdo, teiseiList);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected NyukaBuilder getThis() {
		return this;
	}

	@Override
	protected NyukaBuilder newInstance() {
		return new NyukaBuilder();
	}

	// public method
	/**
	 * {@link Nyuka}に与えるbushoをこのビルダに設定する。
	 *
	 * @param busho
	 *            busho
	 * @return {@link NyukaBuilder}
	 */
	public NyukaBuilder withBusho(final Busho busho) {
		addConfigurator(new BuilderConfigurator<NyukaBuilder>() {
			@Override
			public void configure(NyukaBuilder builder) {
				builder.busho = busho;
			}
		});
		return getThis();
	}

	/**
	 * {@link Nyuka}に与えるnyukaSakiをこのビルダに設定する。
	 *
	 * @param nyukaSaki
	 *            nyukaSaki
	 * @return {@link NyukaBuilder}
	 */
	public NyukaBuilder withNyukaSaki(final NyukaSaki nyukaSaki) {
		addConfigurator(new BuilderConfigurator<NyukaBuilder>() {
			@Override
			public void configure(NyukaBuilder builder) {
				builder.nyukaSaki = nyukaSaki;
			}
		});
		return getThis();
	}

	/**
	 * {@link Nyuka}に与えるnyukaDateをこのビルダに設定する。
	 *
	 * @param nyukaDate
	 *            nyukaDate
	 * @return {@link NyukaBuilder}
	 */
	public NyukaBuilder withNyukaDate(final EigyoDate nyukaDate) {
		addConfigurator(new BuilderConfigurator<NyukaBuilder>() {
			@Override
			public void configure(NyukaBuilder builder) {
				builder.nyukaDate = nyukaDate;
			}
		});
		return getThis();
	}

	/**
	 * {@link Nyuka}に与えるnyukaShohinIdoをこのビルダに設定する。
	 *
	 * @param nyukaShohinIdo
	 *            nyukaShohinIdo
	 * @return {@link NyukaBuilder}
	 */
	public NyukaBuilder withNyukaShohinIdo(final ShohinIdo nyukaShohinIdo) {
		addConfigurator(new BuilderConfigurator<NyukaBuilder>() {
			@Override
			public void configure(NyukaBuilder builder) {
				builder.nyukaShohinIdo = nyukaShohinIdo;
			}
		});
		return getThis();
	}

	/**
	 * {@link Nyuka}に与えるteiseiListをこのビルダに設定する。
	 *
	 * @param teiseiList
	 *            teiseiList
	 * @return {@link NyukaBuilder}
	 */
	public NyukaBuilder withTeiseiList(final List teiseiList) {
		addConfigurator(new BuilderConfigurator<NyukaBuilder>() {
			@Override
			public void configure(NyukaBuilder builder) {
				builder.teiseiList = teiseiList;
			}
		});
		return getThis();
	}

	/**
	 * {@link Nyuka}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link NyukaBuilder}
	 */
	public NyukaBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<NyukaBuilder>() {
			@Override
			public void configure(NyukaBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link Nyuka}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link NyukaBuilder}
	 */
	public NyukaBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<NyukaBuilder>() {
			@Override
			public void configure(NyukaBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}