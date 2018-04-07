package com.showka.domain.builder;

import java.util.List;

import com.showka.domain.Kokyaku;
import com.showka.domain.Seikyu;
import com.showka.domain.SeikyuMeisai;
import com.showka.value.EigyoDate;

public class SeikyuBuilder extends com.showka.domain.builder.DomainBuilderBase<Seikyu, SeikyuBuilder> {

	// private member
	/** kokyaku */
	private Kokyaku kokyaku;

	/** seikyuDate */
	private EigyoDate seikyuDate;

	/** shiharaiDate */
	private EigyoDate shiharaiDate;

	/** seikyuMeisai */
	private List<SeikyuMeisai> seikyuMeisai;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(Seikyu domain, SeikyuBuilder builder) {
		builder.withKokyaku(domain.getKokyaku());
		builder.withSeikyuDate(domain.getSeikyuDate());
		builder.withShiharaiDate(domain.getShiharaiDate());
		builder.withSeikyuMeisai(domain.getSeikyuMeisai());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected Seikyu createDomainObject() {
		Seikyu domain = new Seikyu(kokyaku, seikyuDate, shiharaiDate, seikyuMeisai);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected SeikyuBuilder getThis() {
		return this;
	}

	@Override
	protected SeikyuBuilder newInstance() {
		return new SeikyuBuilder();
	}

	// public method
	/**
	 * {@link Seikyu}に与えるkokyakuをこのビルダに設定する。
	 *
	 * @param kokyaku
	 *            kokyaku
	 * @return {@link SeikyuBuilder}
	 */
	public SeikyuBuilder withKokyaku(final Kokyaku kokyaku) {
		addConfigurator(new BuilderConfigurator<SeikyuBuilder>() {
			@Override
			public void configure(SeikyuBuilder builder) {
				builder.kokyaku = kokyaku;
			}
		});
		return getThis();
	}

	/**
	 * {@link Seikyu}に与えるseikyuDateをこのビルダに設定する。
	 *
	 * @param seikyuDate
	 *            seikyuDate
	 * @return {@link SeikyuBuilder}
	 */
	public SeikyuBuilder withSeikyuDate(final EigyoDate seikyuDate) {
		addConfigurator(new BuilderConfigurator<SeikyuBuilder>() {
			@Override
			public void configure(SeikyuBuilder builder) {
				builder.seikyuDate = seikyuDate;
			}
		});
		return getThis();
	}

	/**
	 * {@link Seikyu}に与えるshiharaiDateをこのビルダに設定する。
	 *
	 * @param shiharaiDate
	 *            shiharaiDate
	 * @return {@link SeikyuBuilder}
	 */
	public SeikyuBuilder withShiharaiDate(final EigyoDate shiharaiDate) {
		addConfigurator(new BuilderConfigurator<SeikyuBuilder>() {
			@Override
			public void configure(SeikyuBuilder builder) {
				builder.shiharaiDate = shiharaiDate;
			}
		});
		return getThis();
	}

	/**
	 * {@link Seikyu}に与えるseikyuMeisaiをこのビルダに設定する。
	 *
	 * @param seikyuMeisai
	 *            seikyuMeisai
	 * @return {@link SeikyuBuilder}
	 */
	public SeikyuBuilder withSeikyuMeisai(final List<SeikyuMeisai> seikyuMeisai) {
		addConfigurator(new BuilderConfigurator<SeikyuBuilder>() {
			@Override
			public void configure(SeikyuBuilder builder) {
				builder.seikyuMeisai = seikyuMeisai;
			}
		});
		return getThis();
	}

	/**
	 * {@link Seikyu}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link SeikyuBuilder}
	 */
	public SeikyuBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<SeikyuBuilder>() {
			@Override
			public void configure(SeikyuBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link Seikyu}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link SeikyuBuilder}
	 */
	public SeikyuBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<SeikyuBuilder>() {
			@Override
			public void configure(SeikyuBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}