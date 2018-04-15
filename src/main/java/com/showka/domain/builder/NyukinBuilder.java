package com.showka.domain.builder;

import com.showka.domain.Busho;
import com.showka.domain.Kokyaku;
import com.showka.domain.Nyukin;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

public class NyukinBuilder extends com.showka.domain.builder.DomainBuilderBase<Nyukin, NyukinBuilder> {

	// private member
	/** kokyaku */
	private Kokyaku kokyaku;

	/** busho */
	private Busho busho;

	/** date */
	private EigyoDate date;

	/** nyukinHohoKubun */
	private NyukinHohoKubun nyukinHohoKubun;

	/** amount */
	private AmountOfMoney kingaku;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(Nyukin domain, NyukinBuilder builder) {
		builder.withKokyaku(domain.getKokyaku());
		builder.withBusho(domain.getBusho());
		builder.withDate(domain.getDate());
		builder.withNyukinHohoKubun(domain.getNyukinHohoKubun());
		builder.withKingaku(domain.getKingaku());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected Nyukin createDomainObject() {
		Nyukin domain = new Nyukin(kokyaku, busho, date, nyukinHohoKubun, kingaku);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected NyukinBuilder getThis() {
		return this;
	}

	@Override
	protected NyukinBuilder newInstance() {
		return new NyukinBuilder();
	}

	// public method
	/**
	 * {@link Nyukin}に与えるkokyakuをこのビルダに設定する。
	 *
	 * @param kokyaku
	 *            kokyaku
	 * @return {@link NyukinBuilder}
	 */
	public NyukinBuilder withKokyaku(final Kokyaku kokyaku) {
		addConfigurator(new BuilderConfigurator<NyukinBuilder>() {
			@Override
			public void configure(NyukinBuilder builder) {
				builder.kokyaku = kokyaku;
			}
		});
		return getThis();
	}

	/**
	 * {@link Nyukin}に与えるbushoをこのビルダに設定する。
	 *
	 * @param busho
	 *            busho
	 * @return {@link NyukinBuilder}
	 */
	public NyukinBuilder withBusho(final Busho busho) {
		addConfigurator(new BuilderConfigurator<NyukinBuilder>() {
			@Override
			public void configure(NyukinBuilder builder) {
				builder.busho = busho;
			}
		});
		return getThis();
	}

	/**
	 * {@link Nyukin}に与えるdateをこのビルダに設定する。
	 *
	 * @param date
	 *            date
	 * @return {@link NyukinBuilder}
	 */
	public NyukinBuilder withDate(final EigyoDate date) {
		addConfigurator(new BuilderConfigurator<NyukinBuilder>() {
			@Override
			public void configure(NyukinBuilder builder) {
				builder.date = date;
			}
		});
		return getThis();
	}

	/**
	 * {@link Nyukin}に与えるnyukinHohoKubunをこのビルダに設定する。
	 *
	 * @param nyukinHohoKubun
	 *            nyukinHohoKubun
	 * @return {@link NyukinBuilder}
	 */
	public NyukinBuilder withNyukinHohoKubun(final NyukinHohoKubun nyukinHohoKubun) {
		addConfigurator(new BuilderConfigurator<NyukinBuilder>() {
			@Override
			public void configure(NyukinBuilder builder) {
				builder.nyukinHohoKubun = nyukinHohoKubun;
			}
		});
		return getThis();
	}

	/**
	 * {@link Nyukin}に与えるamountをこのビルダに設定する。
	 *
	 * @param kingaku
	 *            kingaku
	 * @return {@link NyukinBuilder}
	 */
	public NyukinBuilder withKingaku(final AmountOfMoney kingaku) {
		addConfigurator(new BuilderConfigurator<NyukinBuilder>() {
			@Override
			public void configure(NyukinBuilder builder) {
				builder.kingaku = kingaku;
			}
		});
		return getThis();
	}

	/**
	 * {@link Nyukin}に与えるamountをこのビルダに設定する。
	 *
	 * @param kingaku
	 *            kingaku
	 * @return {@link NyukinBuilder}
	 */
	public NyukinBuilder withKingaku(final Integer kingaku) {
		addConfigurator(new BuilderConfigurator<NyukinBuilder>() {
			@Override
			public void configure(NyukinBuilder builder) {
				builder.kingaku = new AmountOfMoney(kingaku);
			}
		});
		return getThis();
	}

	/**
	 * {@link Nyukin}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link NyukinBuilder}
	 */
	public NyukinBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<NyukinBuilder>() {
			@Override
			public void configure(NyukinBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link Nyukin}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link NyukinBuilder}
	 */
	public NyukinBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<NyukinBuilder>() {
			@Override
			public void configure(NyukinBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}