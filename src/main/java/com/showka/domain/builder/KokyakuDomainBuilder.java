package com.showka.domain.builder;

import com.showka.domain.KokyakuDomain;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.KokyakuKubun;

/**
 * 顧客 DomainBuilder
 *
 * @author 25767
 *
 */
public class KokyakuDomainBuilder
		extends com.showka.domain.builder.DomainBuilderBase<KokyakuDomain, KokyakuDomainBuilder> {

	/** 顧客コード */
	private String code;

	/** 顧客名 */
	private String name;

	/** 顧客住所 */
	private String address;

	/** 顧客区分 */
	private KokyakuKubun kokyakuKubun;

	/** 販売区分 */
	private HanbaiKubun hanbaiKubun;

	/** 主幹部署ID */
	private String shukanBushoId;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	@Override
	protected void apply(KokyakuDomain domain, KokyakuDomainBuilder builder) {
		builder.withCode(domain.getCode());
		builder.withName(domain.getName());
		builder.withAddress(domain.getAddress());
		builder.withKokyakuKubun(domain.getKokyakuKubun());
		builder.withHanbaiKubun(domain.getHanbaiKubun());
		builder.withShukanBushoId(domain.getShukanBushoId());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected KokyakuDomain createDomainObject() {
		KokyakuDomain domain = new KokyakuDomain(code, name, address, kokyakuKubun, hanbaiKubun, shukanBushoId);
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
	 * {@link KokyakuDomain}に与えるshukanBushoIdをこのビルダに設定する。
	 *
	 * @param shukanBushoId
	 *            shukanBushoId
	 * @return {@link KokyakuDomainBuilder}
	 */
	public KokyakuDomainBuilder withShukanBushoId(final String shukanBushoId) {
		addConfigurator(new BuilderConfigurator<KokyakuDomainBuilder>() {

			@Override
			public void configure(KokyakuDomainBuilder builder) {
				builder.shukanBushoId = shukanBushoId;
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
