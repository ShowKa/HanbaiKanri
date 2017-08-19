package com.showka.domain.builder;

import com.showka.domain.NyukinKakeInfoDomain;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.kubun.NyukinTsukiKubun;

public class NyukinKakeInfoDomainBuilder
		extends com.showka.domain.builder.DomainBuilderBase<NyukinKakeInfoDomain, NyukinKakeInfoDomainBuilder> {

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

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(NyukinKakeInfoDomain domain, NyukinKakeInfoDomainBuilder builder) {
		builder.withKokyakuId(domain.getKokyakuId());
		builder.withShimeDate(domain.getShimeDate());
		builder.withNyukinTsukiKubun(domain.getNyukinTsukiKubun());
		builder.withNyukinHohoKubun(domain.getNyukinHohoKubun());
		builder.withNyukinDate(domain.getNyukinDate());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected NyukinKakeInfoDomain createDomainObject() {
		return new NyukinKakeInfoDomain(kokyakuId, shimeDate, nyukinTsukiKubun, nyukinHohoKubun, nyukinDate, version);
	}

	@Override
	protected NyukinKakeInfoDomainBuilder getThis() {
		return this;
	}

	@Override
	protected NyukinKakeInfoDomainBuilder newInstance() {
		return new NyukinKakeInfoDomainBuilder();
	}

	// public method
	/**
	 * {@link NyukinKakeInfoDomain}に与えるkokyakuIdをこのビルダに設定する。
	 *
	 * @param kokyakuId
	 *            kokyakuId
	 * @return {@link NyukinKakeInfoDomainBuilder}
	 */
	public NyukinKakeInfoDomainBuilder withKokyakuId(final String kokyakuId) {
		addConfigurator(new BuilderConfigurator<NyukinKakeInfoDomainBuilder>() {
			@Override
			public void configure(NyukinKakeInfoDomainBuilder builder) {
				builder.kokyakuId = kokyakuId;
			}
		});
		return getThis();
	}

	/**
	 * {@link NyukinKakeInfoDomain}に与えるshimeDateをこのビルダに設定する。
	 *
	 * @param shimeDate
	 *            shimeDate
	 * @return {@link NyukinKakeInfoDomainBuilder}
	 */
	public NyukinKakeInfoDomainBuilder withShimeDate(final Integer shimeDate) {
		addConfigurator(new BuilderConfigurator<NyukinKakeInfoDomainBuilder>() {
			@Override
			public void configure(NyukinKakeInfoDomainBuilder builder) {
				builder.shimeDate = shimeDate;
			}
		});
		return getThis();
	}

	/**
	 * {@link NyukinKakeInfoDomain}に与えるnyukinTsukiKubunをこのビルダに設定する。
	 *
	 * @param nyukinTsukiKubun
	 *            nyukinTsukiKubun
	 * @return {@link NyukinKakeInfoDomainBuilder}
	 */
	public NyukinKakeInfoDomainBuilder withNyukinTsukiKubun(final NyukinTsukiKubun nyukinTsukiKubun) {
		addConfigurator(new BuilderConfigurator<NyukinKakeInfoDomainBuilder>() {
			@Override
			public void configure(NyukinKakeInfoDomainBuilder builder) {
				builder.nyukinTsukiKubun = nyukinTsukiKubun;
			}
		});
		return getThis();
	}

	/**
	 * {@link NyukinKakeInfoDomain}に与えるnyukinHohoKubunをこのビルダに設定する。
	 *
	 * @param nyukinHohoKubun
	 *            nyukinHohoKubun
	 * @return {@link NyukinKakeInfoDomainBuilder}
	 */
	public NyukinKakeInfoDomainBuilder withNyukinHohoKubun(final NyukinHohoKubun nyukinHohoKubun) {
		addConfigurator(new BuilderConfigurator<NyukinKakeInfoDomainBuilder>() {
			@Override
			public void configure(NyukinKakeInfoDomainBuilder builder) {
				builder.nyukinHohoKubun = nyukinHohoKubun;
			}
		});
		return getThis();
	}

	/**
	 * {@link NyukinKakeInfoDomain}に与えるnyukinDateをこのビルダに設定する。
	 *
	 * @param nyukinDate
	 *            nyukinDate
	 * @return {@link NyukinKakeInfoDomainBuilder}
	 */
	public NyukinKakeInfoDomainBuilder withNyukinDate(final Integer nyukinDate) {
		addConfigurator(new BuilderConfigurator<NyukinKakeInfoDomainBuilder>() {
			@Override
			public void configure(NyukinKakeInfoDomainBuilder builder) {
				builder.nyukinDate = nyukinDate;
			}
		});
		return getThis();
	}

	/**
	 * {@link NyukinKakeInfoDomain}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link NyukinKakeInfoDomainBuilder}
	 */
	public NyukinKakeInfoDomainBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<NyukinKakeInfoDomainBuilder>() {
			@Override
			public void configure(NyukinKakeInfoDomainBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}