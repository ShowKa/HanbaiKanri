package com.showka.domain.builder;

import com.showka.domain.Uriage;
import com.showka.domain.Urikake;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

public class UrikakeBuilder extends com.showka.domain.builder.DomainBuilderBase<Urikake, UrikakeBuilder> {

	// private member
	/** uriage */
	private Uriage uriage;

	/** kingaku */
	private AmountOfMoney kingaku;

	/** nyukinYoteiDate */
	private EigyoDate nyukinYoteiDate;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(Urikake domain, UrikakeBuilder builder) {
		builder.withUriage(domain.getUriage());
		builder.withKingaku(domain.getKingaku());
		builder.withNyukinYoteiDate(domain.getNyukinYoteiDate());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected Urikake createDomainObject() {
		Urikake domain = new Urikake(uriage, kingaku, nyukinYoteiDate);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected UrikakeBuilder getThis() {
		return this;
	}

	@Override
	protected UrikakeBuilder newInstance() {
		return new UrikakeBuilder();
	}

	// public method
	/**
	 * {@link Urikake}に与えるuriageをこのビルダに設定する。
	 *
	 * @param uriage
	 *            uriage
	 * @return {@link UrikakeBuilder}
	 */
	public UrikakeBuilder withUriage(final Uriage uriage) {
		addConfigurator(new BuilderConfigurator<UrikakeBuilder>() {
			@Override
			public void configure(UrikakeBuilder builder) {
				builder.uriage = uriage;
			}
		});
		return getThis();
	}

	/**
	 * {@link Urikake}に与えるkingakuをこのビルダに設定する。
	 *
	 * @param kingaku
	 *            kingaku
	 * @return {@link UrikakeBuilder}
	 */
	public UrikakeBuilder withKingaku(final AmountOfMoney kingaku) {
		addConfigurator(new BuilderConfigurator<UrikakeBuilder>() {
			@Override
			public void configure(UrikakeBuilder builder) {
				builder.kingaku = kingaku;
			}
		});
		return getThis();
	}

	/**
	 * {@link Urikake}に与えるkingakuをこのビルダに設定する。
	 *
	 * @param kingaku
	 *            kingaku
	 * @return {@link UrikakeBuilder}
	 */
	public UrikakeBuilder withKingaku(final Integer kingaku) {
		addConfigurator(new BuilderConfigurator<UrikakeBuilder>() {
			@Override
			public void configure(UrikakeBuilder builder) {
				builder.kingaku = new AmountOfMoney(kingaku);
			}
		});
		return getThis();
	}

	/**
	 * {@link Urikake}に与えるnyukinYoteiDateをこのビルダに設定する。
	 *
	 * @param nyukinYoteiDate
	 *            nyukinYoteiDate
	 * @return {@link UrikakeBuilder}
	 */
	public UrikakeBuilder withNyukinYoteiDate(final EigyoDate nyukinYoteiDate) {
		addConfigurator(new BuilderConfigurator<UrikakeBuilder>() {
			@Override
			public void configure(UrikakeBuilder builder) {
				builder.nyukinYoteiDate = nyukinYoteiDate;
			}
		});
		return getThis();
	}

	/**
	 * {@link Urikake}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link UrikakeBuilder}
	 */
	public UrikakeBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<UrikakeBuilder>() {
			@Override
			public void configure(UrikakeBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link Urikake}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link UrikakeBuilder}
	 */
	public UrikakeBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<UrikakeBuilder>() {
			@Override
			public void configure(UrikakeBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}