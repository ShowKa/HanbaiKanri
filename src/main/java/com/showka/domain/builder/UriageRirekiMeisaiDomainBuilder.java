package com.showka.domain.builder;

import java.math.BigDecimal;

import com.showka.domain.ShohinDomain;
import com.showka.domain.UriageMeisaiDomain;
import com.showka.domain.UriageRirekiMeisaiDomain;

public class UriageRirekiMeisaiDomainBuilder
		extends com.showka.domain.builder.DomainBuilderBase<UriageRirekiMeisaiDomain, UriageRirekiMeisaiDomainBuilder> {

	// private member
	/** uriageId */
	private String uriageId;

	/** meisaiNumber */
	private Integer meisaiNumber;

	/** shohinDomain */
	private ShohinDomain shohinDomain;

	/** hanbaiNumber */
	private Integer hanbaiNumber;

	/** hanbaiTanka */
	private BigDecimal hanbaiTanka;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(UriageRirekiMeisaiDomain domain, UriageRirekiMeisaiDomainBuilder builder) {
		builder.withUriageId(domain.getUriageId());
		builder.withMeisaiNumber(domain.getMeisaiNumber());
		builder.withShohinDomain(domain.getShohinDomain());
		builder.withHanbaiNumber(domain.getHanbaiNumber());
		builder.withHanbaiTanka(domain.getHanbaiTanka());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected UriageRirekiMeisaiDomain createDomainObject() {
		UriageRirekiMeisaiDomain domain = new UriageRirekiMeisaiDomain(uriageId, meisaiNumber, shohinDomain,
				hanbaiNumber, hanbaiTanka);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected UriageRirekiMeisaiDomainBuilder getThis() {
		return this;
	}

	@Override
	protected UriageRirekiMeisaiDomainBuilder newInstance() {
		return new UriageRirekiMeisaiDomainBuilder();
	}

	// public method
	/**
	 * {@link UriageRirekiMeisaiDomain}に与えるuriageIdをこのビルダに設定する。
	 *
	 * @param uriageId
	 *            uriageId
	 * @return {@link UriageRirekiMeisaiDomainBuilder}
	 */
	public UriageRirekiMeisaiDomainBuilder withUriageId(final String uriageId) {
		addConfigurator(new BuilderConfigurator<UriageRirekiMeisaiDomainBuilder>() {
			@Override
			public void configure(UriageRirekiMeisaiDomainBuilder builder) {
				builder.uriageId = uriageId;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageRirekiMeisaiDomain}に与えるmeisaiNumberをこのビルダに設定する。
	 *
	 * @param meisaiNumber
	 *            meisaiNumber
	 * @return {@link UriageRirekiMeisaiDomainBuilder}
	 */
	public UriageRirekiMeisaiDomainBuilder withMeisaiNumber(final Integer meisaiNumber) {
		addConfigurator(new BuilderConfigurator<UriageRirekiMeisaiDomainBuilder>() {
			@Override
			public void configure(UriageRirekiMeisaiDomainBuilder builder) {
				builder.meisaiNumber = meisaiNumber;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageRirekiMeisaiDomain}に与えるshohinDomainをこのビルダに設定する。
	 *
	 * @param shohinDomain
	 *            shohinDomain
	 * @return {@link UriageRirekiMeisaiDomainBuilder}
	 */
	public UriageRirekiMeisaiDomainBuilder withShohinDomain(final ShohinDomain shohinDomain) {
		addConfigurator(new BuilderConfigurator<UriageRirekiMeisaiDomainBuilder>() {
			@Override
			public void configure(UriageRirekiMeisaiDomainBuilder builder) {
				builder.shohinDomain = shohinDomain;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageRirekiMeisaiDomain}に与えるhanbaiNumberをこのビルダに設定する。
	 *
	 * @param hanbaiNumber
	 *            hanbaiNumber
	 * @return {@link UriageRirekiMeisaiDomainBuilder}
	 */
	public UriageRirekiMeisaiDomainBuilder withHanbaiNumber(final Integer hanbaiNumber) {
		addConfigurator(new BuilderConfigurator<UriageRirekiMeisaiDomainBuilder>() {
			@Override
			public void configure(UriageRirekiMeisaiDomainBuilder builder) {
				builder.hanbaiNumber = hanbaiNumber;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageRirekiMeisaiDomain}に与えるhanbaiTankaをこのビルダに設定する。
	 *
	 * @param hanbaiTanka
	 *            hanbaiTanka
	 * @return {@link UriageRirekiMeisaiDomainBuilder}
	 */
	public UriageRirekiMeisaiDomainBuilder withHanbaiTanka(final BigDecimal hanbaiTanka) {
		addConfigurator(new BuilderConfigurator<UriageRirekiMeisaiDomainBuilder>() {
			@Override
			public void configure(UriageRirekiMeisaiDomainBuilder builder) {
				builder.hanbaiTanka = hanbaiTanka;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageRirekiMeisaiDomain}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link UriageRirekiMeisaiDomainBuilder}
	 */
	public UriageRirekiMeisaiDomainBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<UriageRirekiMeisaiDomainBuilder>() {
			@Override
			public void configure(UriageRirekiMeisaiDomainBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageRirekiMeisaiDomain}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link UriageRirekiMeisaiDomainBuilder}
	 */
	public UriageRirekiMeisaiDomainBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<UriageRirekiMeisaiDomainBuilder>() {
			@Override
			public void configure(UriageRirekiMeisaiDomainBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

	/**
	 * 売上明細から売上履歴明細をコンバートして生成する。
	 * 
	 * @param vo
	 *            売上明細
	 * @return コンバートした売上履歴明細
	 */
	public UriageRirekiMeisaiDomain apply(UriageMeisaiDomain vo) {
		UriageRirekiMeisaiDomainBuilder builder = newInstance();
		apply(vo, builder);
		for (BuilderConfigurator<UriageRirekiMeisaiDomainBuilder> configurator : configurators) {
			builder.addConfigurator(configurator);
		}
		return builder.build();
	}

	private void apply(UriageMeisaiDomain domain, UriageRirekiMeisaiDomainBuilder builder) {
		builder.withUriageId(domain.getUriageId());
		builder.withMeisaiNumber(domain.getMeisaiNumber());
		builder.withShohinDomain(domain.getShohinDomain());
		builder.withHanbaiNumber(domain.getHanbaiNumber());
		builder.withHanbaiTanka(domain.getHanbaiTanka());
	}
}