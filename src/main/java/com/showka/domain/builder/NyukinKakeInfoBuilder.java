package com.showka.domain.builder;

import com.showka.domain.u01.NyukinKakeInfo;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.kubun.NyukinTsukiKubun;

public class NyukinKakeInfoBuilder
		extends com.showka.domain.builder.DomainBuilderBase<NyukinKakeInfo, NyukinKakeInfoBuilder> {

	// private member
	/** kokyakuId */
	private String kokyakuId;

	/** shimeDate */
	private Integer shimeDate;

	/** nyukinTsukiKubun */
	private NyukinTsukiKubun nyukinTsukiKubun;

	/** nyukinHohoKubun */
	private NyukinHohoKubun nyukinHohoKubun;

	/** nyukinDate */
	private Integer nyukinDate;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(NyukinKakeInfo domain, NyukinKakeInfoBuilder builder) {
		builder.withKokyakuId(domain.getKokyakuId());
		builder.withShimeDate(domain.getShimeDate());
		builder.withNyukinTsukiKubun(domain.getNyukinTsukiKubun());
		builder.withNyukinHohoKubun(domain.getNyukinHohoKubun());
		builder.withNyukinDate(domain.getNyukinDate());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected NyukinKakeInfo createDomainObject() {
		NyukinKakeInfo domain = new NyukinKakeInfo(kokyakuId, shimeDate, nyukinTsukiKubun, nyukinHohoKubun,
				nyukinDate);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected NyukinKakeInfoBuilder getThis() {
		return this;
	}

	@Override
	protected NyukinKakeInfoBuilder newInstance() {
		return new NyukinKakeInfoBuilder();
	}

	// public method
	/**
	 * {@link NyukinKakeInfo}に与えるkokyakuIdをこのビルダに設定する。
	 *
	 * @param kokyakuId
	 *            kokyakuId
	 * @return {@link NyukinKakeInfoBuilder}
	 */
	public NyukinKakeInfoBuilder withKokyakuId(final String kokyakuId) {
		addConfigurator(new BuilderConfigurator<NyukinKakeInfoBuilder>() {
			@Override
			public void configure(NyukinKakeInfoBuilder builder) {
				builder.kokyakuId = kokyakuId;
			}
		});
		return getThis();
	}

	/**
	 * {@link NyukinKakeInfo}に与えるshimeDateをこのビルダに設定する。
	 *
	 * @param shimeDate
	 *            shimeDate
	 * @return {@link NyukinKakeInfoBuilder}
	 */
	public NyukinKakeInfoBuilder withShimeDate(final Integer shimeDate) {
		addConfigurator(new BuilderConfigurator<NyukinKakeInfoBuilder>() {
			@Override
			public void configure(NyukinKakeInfoBuilder builder) {
				builder.shimeDate = shimeDate;
			}
		});
		return getThis();
	}

	/**
	 * {@link NyukinKakeInfo}に与えるnyukinTsukiKubunをこのビルダに設定する。
	 *
	 * @param nyukinTsukiKubun
	 *            nyukinTsukiKubun
	 * @return {@link NyukinKakeInfoBuilder}
	 */
	public NyukinKakeInfoBuilder withNyukinTsukiKubun(final NyukinTsukiKubun nyukinTsukiKubun) {
		addConfigurator(new BuilderConfigurator<NyukinKakeInfoBuilder>() {
			@Override
			public void configure(NyukinKakeInfoBuilder builder) {
				builder.nyukinTsukiKubun = nyukinTsukiKubun;
			}
		});
		return getThis();
	}

	/**
	 * {@link NyukinKakeInfo}に与えるnyukinHohoKubunをこのビルダに設定する。
	 *
	 * @param nyukinHohoKubun
	 *            nyukinHohoKubun
	 * @return {@link NyukinKakeInfoBuilder}
	 */
	public NyukinKakeInfoBuilder withNyukinHohoKubun(final NyukinHohoKubun nyukinHohoKubun) {
		addConfigurator(new BuilderConfigurator<NyukinKakeInfoBuilder>() {
			@Override
			public void configure(NyukinKakeInfoBuilder builder) {
				builder.nyukinHohoKubun = nyukinHohoKubun;
			}
		});
		return getThis();
	}

	/**
	 * {@link NyukinKakeInfo}に与えるnyukinDateをこのビルダに設定する。
	 *
	 * @param nyukinDate
	 *            nyukinDate
	 * @return {@link NyukinKakeInfoBuilder}
	 */
	public NyukinKakeInfoBuilder withNyukinDate(final Integer nyukinDate) {
		addConfigurator(new BuilderConfigurator<NyukinKakeInfoBuilder>() {
			@Override
			public void configure(NyukinKakeInfoBuilder builder) {
				builder.nyukinDate = nyukinDate;
			}
		});
		return getThis();
	}

	/**
	 * {@link NyukinKakeInfo}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link NyukinKakeInfoBuilder}
	 */
	public NyukinKakeInfoBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<NyukinKakeInfoBuilder>() {
			@Override
			public void configure(NyukinKakeInfoBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link NyukinKakeInfo}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link NyukinKakeInfoBuilder}
	 */
	public NyukinKakeInfoBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<NyukinKakeInfoBuilder>() {
			@Override
			public void configure(NyukinKakeInfoBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}