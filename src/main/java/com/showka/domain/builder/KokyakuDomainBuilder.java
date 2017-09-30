package com.showka.domain.builder;

import com.showka.domain.BushoDomain;
import com.showka.domain.KokyakuDomain;
import com.showka.domain.NyukinKakeInfoDomain;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.KokyakuKubun;
import com.showka.system.EmptyProxy;

public class KokyakuDomainBuilder
		extends com.showka.domain.builder.DomainBuilderBase<KokyakuDomain, KokyakuDomainBuilder> {

	// private member
	/** code */
	private String code = STRING_EMPTY;;

	/** name */
	private String name = STRING_EMPTY;;

	/** address */
	private String address = STRING_EMPTY;

	/** kokyakuKubun */
	private KokyakuKubun kokyakuKubun = KokyakuKubun.EMPTY;

	/** hanbaiKubun */
	private HanbaiKubun hanbaiKubun = HanbaiKubun.EMPTY;

	/** shukanBusho */
	private BushoDomain shukanBusho = EmptyProxy.domain(BushoDomain.class);

	/** nyukinKakeInfo */
	private NyukinKakeInfoDomain nyukinKakeInfo = EmptyProxy.domain(NyukinKakeInfoDomain.class);

	/** recordId */
	private String recordId = STRING_EMPTY;;

	/** version */
	private Integer version = INTEGER_EMPTY;

	// protected method
	@Override
	protected void apply(KokyakuDomain domain, KokyakuDomainBuilder builder) {
		builder.withCode(domain.getCode());
		builder.withName(domain.getName());
		builder.withAddress(domain.getAddress());
		builder.withKokyakuKubun(domain.getKokyakuKubun());
		builder.withHanbaiKubun(domain.getHanbaiKubun());
		builder.withShukanBusho(domain.getShukanBusho());
		builder.withNyukinKakeInfo(domain.getNyukinKakeInfo());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected KokyakuDomain createDomainObject() {
		KokyakuDomain domain = new KokyakuDomain(code, name, address, kokyakuKubun, hanbaiKubun, shukanBusho,
				nyukinKakeInfo);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected KokyakuDomainBuilder getThis() {
		return this;
	}

	@Override
	protected KokyakuDomainBuilder newInstance() {
		return new KokyakuDomainBuilder();
	}

	// public method
	/**
	 * {@link KokyakuDomain}に与えるcodeをこのビルダに設定する。
	 *
	 * @param code
	 *            code
	 * @return {@link KokyakuDomainBuilder}
	 */
	public KokyakuDomainBuilder withCode(final String code) {
		addConfigurator(new BuilderConfigurator<KokyakuDomainBuilder>() {
			@Override
			public void configure(KokyakuDomainBuilder builder) {
				builder.code = code;
			}
		});
		return getThis();
	}

	/**
	 * {@link KokyakuDomain}に与えるnameをこのビルダに設定する。
	 *
	 * @param name
	 *            name
	 * @return {@link KokyakuDomainBuilder}
	 */
	public KokyakuDomainBuilder withName(final String name) {
		addConfigurator(new BuilderConfigurator<KokyakuDomainBuilder>() {
			@Override
			public void configure(KokyakuDomainBuilder builder) {
				builder.name = name;
			}
		});
		return getThis();
	}

	/**
	 * {@link KokyakuDomain}に与えるaddressをこのビルダに設定する。
	 *
	 * @param address
	 *            address
	 * @return {@link KokyakuDomainBuilder}
	 */
	public KokyakuDomainBuilder withAddress(final String address) {
		addConfigurator(new BuilderConfigurator<KokyakuDomainBuilder>() {
			@Override
			public void configure(KokyakuDomainBuilder builder) {
				builder.address = address;
			}
		});
		return getThis();
	}

	/**
	 * {@link KokyakuDomain}に与えるkokyakuKubunをこのビルダに設定する。
	 *
	 * @param kokyakuKubun
	 *            kokyakuKubun
	 * @return {@link KokyakuDomainBuilder}
	 */
	public KokyakuDomainBuilder withKokyakuKubun(final KokyakuKubun kokyakuKubun) {
		addConfigurator(new BuilderConfigurator<KokyakuDomainBuilder>() {
			@Override
			public void configure(KokyakuDomainBuilder builder) {
				builder.kokyakuKubun = kokyakuKubun;
			}
		});
		return getThis();
	}

	/**
	 * {@link KokyakuDomain}に与えるhanbaiKubunをこのビルダに設定する。
	 *
	 * @param hanbaiKubun
	 *            hanbaiKubun
	 * @return {@link KokyakuDomainBuilder}
	 */
	public KokyakuDomainBuilder withHanbaiKubun(final HanbaiKubun hanbaiKubun) {
		addConfigurator(new BuilderConfigurator<KokyakuDomainBuilder>() {
			@Override
			public void configure(KokyakuDomainBuilder builder) {
				builder.hanbaiKubun = hanbaiKubun;
			}
		});
		return getThis();
	}

	/**
	 * {@link KokyakuDomain}に与えるshukanBushoをこのビルダに設定する。
	 *
	 * @param shukanBusho
	 *            shukanBusho
	 * @return {@link KokyakuDomainBuilder}
	 */
	public KokyakuDomainBuilder withShukanBusho(final BushoDomain shukanBusho) {
		addConfigurator(new BuilderConfigurator<KokyakuDomainBuilder>() {
			@Override
			public void configure(KokyakuDomainBuilder builder) {
				builder.shukanBusho = shukanBusho;
			}
		});
		return getThis();
	}

	/**
	 * {@link KokyakuDomain}に与えるnyukinKakeInfoをこのビルダに設定する。
	 *
	 * @param nyukinKakeInfo
	 *            nyukinKakeInfo
	 * @return {@link KokyakuDomainBuilder}
	 */
	public KokyakuDomainBuilder withNyukinKakeInfo(final NyukinKakeInfoDomain nyukinKakeInfo) {
		addConfigurator(new BuilderConfigurator<KokyakuDomainBuilder>() {
			@Override
			public void configure(KokyakuDomainBuilder builder) {
				builder.nyukinKakeInfo = nyukinKakeInfo;
			}
		});
		return getThis();
	}

	/**
	 * {@link KokyakuDomain}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link KokyakuDomainBuilder}
	 */
	public KokyakuDomainBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<KokyakuDomainBuilder>() {
			@Override
			public void configure(KokyakuDomainBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link KokyakuDomain}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link KokyakuDomainBuilder}
	 */
	public KokyakuDomainBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<KokyakuDomainBuilder>() {
			@Override
			public void configure(KokyakuDomainBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

}