package com.showka.domain.builder;

import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u08.Shukin;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shain;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

public class ShukinBuilder extends com.showka.domain.builder.DomainBuilderBase<Shukin, ShukinBuilder> {

	// private member
	/** denpyoNumber */
	private String denpyoNumber;

	/** tantoShain */
	private Shain tantoShain;

	/** kokyaku */
	private Kokyaku kokyaku;

	/** busho */
	private Busho busho;

	/** date */
	private EigyoDate date;

	/** nyukinHohoKubun */
	private NyukinHohoKubun nyukinHohoKubun;

	/** kingaku */
	private AmountOfMoney kingaku;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(Shukin domain, ShukinBuilder builder) {
		builder.withDenpyoNumber(domain.getDenpyoNumber());
		builder.withTantoShain(domain.getTantoShain());
		builder.withKokyaku(domain.getKokyaku());
		builder.withBusho(domain.getBusho());
		builder.withDate(domain.getDate());
		builder.withNyukinHohoKubun(domain.getNyukinHohoKubun());
		builder.withKingaku(domain.getKingaku());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected Shukin createDomainObject() {
		Shukin domain = new Shukin(kokyaku, busho, date, nyukinHohoKubun, kingaku, denpyoNumber, tantoShain);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected ShukinBuilder getThis() {
		return this;
	}

	@Override
	protected ShukinBuilder newInstance() {
		return new ShukinBuilder();
	}

	// public method
	/**
	 * {@link Shukin}に与えるdenpyoNumberをこのビルダに設定する。
	 *
	 * @param denpyoNumber
	 *            denpyoNumber
	 * @return {@link ShukinBuilder}
	 */
	public ShukinBuilder withDenpyoNumber(final String denpyoNumber) {
		addConfigurator(new BuilderConfigurator<ShukinBuilder>() {
			@Override
			public void configure(ShukinBuilder builder) {
				builder.denpyoNumber = denpyoNumber;
			}
		});
		return getThis();
	}

	/**
	 * {@link Shukin}に与えるtantoShainをこのビルダに設定する。
	 *
	 * @param tantoShain
	 *            tantoShain
	 * @return {@link ShukinBuilder}
	 */
	public ShukinBuilder withTantoShain(final Shain tantoShain) {
		addConfigurator(new BuilderConfigurator<ShukinBuilder>() {
			@Override
			public void configure(ShukinBuilder builder) {
				builder.tantoShain = tantoShain;
			}
		});
		return getThis();
	}

	/**
	 * {@link Shukin}に与えるkokyakuをこのビルダに設定する。
	 *
	 * @param kokyaku
	 *            kokyaku
	 * @return {@link ShukinBuilder}
	 */
	public ShukinBuilder withKokyaku(final Kokyaku kokyaku) {
		addConfigurator(new BuilderConfigurator<ShukinBuilder>() {
			@Override
			public void configure(ShukinBuilder builder) {
				builder.kokyaku = kokyaku;
			}
		});
		return getThis();
	}

	/**
	 * {@link Shukin}に与えるbushoをこのビルダに設定する。
	 *
	 * @param busho
	 *            busho
	 * @return {@link ShukinBuilder}
	 */
	public ShukinBuilder withBusho(final Busho busho) {
		addConfigurator(new BuilderConfigurator<ShukinBuilder>() {
			@Override
			public void configure(ShukinBuilder builder) {
				builder.busho = busho;
			}
		});
		return getThis();
	}

	/**
	 * {@link Shukin}に与えるdateをこのビルダに設定する。
	 *
	 * @param date
	 *            date
	 * @return {@link ShukinBuilder}
	 */
	public ShukinBuilder withDate(final EigyoDate date) {
		addConfigurator(new BuilderConfigurator<ShukinBuilder>() {
			@Override
			public void configure(ShukinBuilder builder) {
				builder.date = date;
			}
		});
		return getThis();
	}

	/**
	 * {@link Shukin}に与えるnyukinHohoKubunをこのビルダに設定する。
	 *
	 * @param nyukinHohoKubun
	 *            nyukinHohoKubun
	 * @return {@link ShukinBuilder}
	 */
	public ShukinBuilder withNyukinHohoKubun(final NyukinHohoKubun nyukinHohoKubun) {
		addConfigurator(new BuilderConfigurator<ShukinBuilder>() {
			@Override
			public void configure(ShukinBuilder builder) {
				builder.nyukinHohoKubun = nyukinHohoKubun;
			}
		});
		return getThis();
	}

	/**
	 * {@link Shukin}に与えるkingakuをこのビルダに設定する。
	 *
	 * @param kingaku
	 *            kingaku
	 * @return {@link ShukinBuilder}
	 */
	public ShukinBuilder withKingaku(final AmountOfMoney kingaku) {
		addConfigurator(new BuilderConfigurator<ShukinBuilder>() {
			@Override
			public void configure(ShukinBuilder builder) {
				builder.kingaku = kingaku;
			}
		});
		return getThis();
	}

	/**
	 * {@link Shukin}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link ShukinBuilder}
	 */
	public ShukinBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<ShukinBuilder>() {
			@Override
			public void configure(ShukinBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link Shukin}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link ShukinBuilder}
	 */
	public ShukinBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<ShukinBuilder>() {
			@Override
			public void configure(ShukinBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}