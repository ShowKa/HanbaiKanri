package com.showka.domain.builder;

import java.util.List;

import com.showka.domain.u11.Nyuka;
import com.showka.domain.u11.NyukaSaki;
import com.showka.domain.u11.ShohinIdo;

public class NyukaBuilder extends com.showka.domain.builder.DomainBuilderBase<Nyuka, NyukaBuilder> {

	// private member
	/** nyukaSaki */
	private NyukaSaki nyukaSaki;

	/** nyukaShohinIdo */
	private ShohinIdo nyukaShohinIdo;

	/** teiseiList */
	private List<ShohinIdo> teiseiList;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(Nyuka domain, NyukaBuilder builder) {
		builder.withNyukaSaki(domain.getNyukaSaki());
		builder.withNyukaShohinIdo(domain.getNyukaShohinIdo());
		builder.withTeiseiList(domain.getTeiseiList());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected Nyuka createDomainObject() {
		Nyuka domain = new Nyuka(nyukaSaki, nyukaShohinIdo, teiseiList);
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
	public NyukaBuilder withTeiseiList(final List<ShohinIdo> teiseiList) {
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