package com.showka.domain.builder;

import java.util.List;
import java.util.stream.Collectors;

import com.showka.domain.KokyakuDomain;
import com.showka.domain.UriageDomain;
import com.showka.domain.UriageRirekiDomain;
import com.showka.domain.UriageRirekiMeisaiDomain;
import com.showka.kubun.HanbaiKubun;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;

public class UriageRirekiDomainBuilder
		extends com.showka.domain.builder.DomainBuilderBase<UriageRirekiDomain, UriageRirekiDomainBuilder> {

	// private member
	/** uriageId */
	private String uriageId;

	/** kokyaku */
	private KokyakuDomain kokyaku;

	/** denpyoNumber */
	private String denpyoNumber;

	/** uriageDate */
	private TheDate uriageDate;

	/** keijoDate */
	private TheDate keijoDate;

	/** hanbaiKubun */
	private HanbaiKubun hanbaiKubun;

	/** shohizeiritsu */
	private TaxRate shohizeiritsu;

	/** uriageMeisai */
	private List<UriageRirekiMeisaiDomain> uriageMeisai;

	/** recordId */
	private String recordId;

	/** version */
	private Integer version;

	// protected method
	@Override
	protected void apply(UriageRirekiDomain domain, UriageRirekiDomainBuilder builder) {
		builder.withUriageId(domain.getUriageId());
		builder.withKokyaku(domain.getKokyaku());
		builder.withDenpyoNumber(domain.getDenpyoNumber());
		builder.withUriageDate(domain.getUriageDate());
		builder.withKeijoDate(domain.getKeijoDate());
		builder.withHanbaiKubun(domain.getHanbaiKubun());
		builder.withShohizeiritsu(domain.getShohizeiritsu());
		builder.withUriageMeisai(domain.getUriageRirekiMeisai());
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

	@Override
	protected UriageRirekiDomain createDomainObject() {
		UriageRirekiDomain domain = new UriageRirekiDomain(uriageId, kokyaku, denpyoNumber, uriageDate, keijoDate,
				hanbaiKubun, shohizeiritsu, uriageMeisai);
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected UriageRirekiDomainBuilder getThis() {
		return this;
	}

	@Override
	protected UriageRirekiDomainBuilder newInstance() {
		return new UriageRirekiDomainBuilder();
	}

	// public method
	/**
	 * {@link UriageRirekiDomain}に与えるuriageIdをこのビルダに設定する。
	 *
	 * @param uriageId
	 *            uriageId
	 * @return {@link UriageRirekiDomainBuilder}
	 */
	public UriageRirekiDomainBuilder withUriageId(final String uriageId) {
		addConfigurator(new BuilderConfigurator<UriageRirekiDomainBuilder>() {
			@Override
			public void configure(UriageRirekiDomainBuilder builder) {
				builder.uriageId = uriageId;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageRirekiDomain}に与えるkokyakuをこのビルダに設定する。
	 *
	 * @param kokyaku
	 *            kokyaku
	 * @return {@link UriageRirekiDomainBuilder}
	 */
	public UriageRirekiDomainBuilder withKokyaku(final KokyakuDomain kokyaku) {
		addConfigurator(new BuilderConfigurator<UriageRirekiDomainBuilder>() {
			@Override
			public void configure(UriageRirekiDomainBuilder builder) {
				builder.kokyaku = kokyaku;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageRirekiDomain}に与えるdenpyoNumberをこのビルダに設定する。
	 *
	 * @param denpyoNumber
	 *            denpyoNumber
	 * @return {@link UriageRirekiDomainBuilder}
	 */
	public UriageRirekiDomainBuilder withDenpyoNumber(final String denpyoNumber) {
		addConfigurator(new BuilderConfigurator<UriageRirekiDomainBuilder>() {
			@Override
			public void configure(UriageRirekiDomainBuilder builder) {
				builder.denpyoNumber = denpyoNumber;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageRirekiDomain}に与えるuriageDateをこのビルダに設定する。
	 *
	 * @param uriageDate
	 *            uriageDate
	 * @return {@link UriageRirekiDomainBuilder}
	 */
	public UriageRirekiDomainBuilder withUriageDate(final TheDate uriageDate) {
		addConfigurator(new BuilderConfigurator<UriageRirekiDomainBuilder>() {
			@Override
			public void configure(UriageRirekiDomainBuilder builder) {
				builder.uriageDate = uriageDate;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageRirekiDomain}に与えるkeijoDateをこのビルダに設定する。
	 *
	 * @param keijoDate
	 *            keijoDate
	 * @return {@link UriageRirekiDomainBuilder}
	 */
	public UriageRirekiDomainBuilder withKeijoDate(final TheDate keijoDate) {
		addConfigurator(new BuilderConfigurator<UriageRirekiDomainBuilder>() {
			@Override
			public void configure(UriageRirekiDomainBuilder builder) {
				builder.keijoDate = keijoDate;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageRirekiDomain}に与えるhanbaiKubunをこのビルダに設定する。
	 *
	 * @param hanbaiKubun
	 *            hanbaiKubun
	 * @return {@link UriageRirekiDomainBuilder}
	 */
	public UriageRirekiDomainBuilder withHanbaiKubun(final HanbaiKubun hanbaiKubun) {
		addConfigurator(new BuilderConfigurator<UriageRirekiDomainBuilder>() {
			@Override
			public void configure(UriageRirekiDomainBuilder builder) {
				builder.hanbaiKubun = hanbaiKubun;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageRirekiDomain}に与えるshohizeiritsuをこのビルダに設定する。
	 *
	 * @param shohizeiritsu
	 *            shohizeiritsu
	 * @return {@link UriageRirekiDomainBuilder}
	 */
	public UriageRirekiDomainBuilder withShohizeiritsu(final TaxRate shohizeiritsu) {
		addConfigurator(new BuilderConfigurator<UriageRirekiDomainBuilder>() {
			@Override
			public void configure(UriageRirekiDomainBuilder builder) {
				builder.shohizeiritsu = shohizeiritsu;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageRirekiDomain}に与えるuriageMeisaiをこのビルダに設定する。
	 *
	 * @param uriageMeisai
	 *            uriageMeisai
	 * @return {@link UriageRirekiDomainBuilder}
	 */
	public UriageRirekiDomainBuilder withUriageMeisai(final List<UriageRirekiMeisaiDomain> uriageMeisai) {
		addConfigurator(new BuilderConfigurator<UriageRirekiDomainBuilder>() {
			@Override
			public void configure(UriageRirekiDomainBuilder builder) {
				builder.uriageMeisai = uriageMeisai;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageRirekiDomain}に与えるrecordIdをこのビルダに設定する。
	 *
	 * @param recordId
	 *            recordId
	 * @return {@link UriageRirekiDomainBuilder}
	 */
	public UriageRirekiDomainBuilder withRecordId(final String recordId) {
		addConfigurator(new BuilderConfigurator<UriageRirekiDomainBuilder>() {
			@Override
			public void configure(UriageRirekiDomainBuilder builder) {
				builder.recordId = recordId;
			}
		});
		return getThis();
	}

	/**
	 * {@link UriageRirekiDomain}に与えるversionをこのビルダに設定する。
	 *
	 * @param version
	 *            version
	 * @return {@link UriageRirekiDomainBuilder}
	 */
	public UriageRirekiDomainBuilder withVersion(final Integer version) {
		addConfigurator(new BuilderConfigurator<UriageRirekiDomainBuilder>() {
			@Override
			public void configure(UriageRirekiDomainBuilder builder) {
				builder.version = version;
			}
		});
		return getThis();
	}

	public UriageRirekiDomain apply(UriageDomain vo) {
		UriageRirekiDomainBuilder builder = new UriageRirekiDomainBuilder();
		this.apply(vo, builder);
		for (BuilderConfigurator<UriageRirekiDomainBuilder> configurator : configurators) {
			builder.addConfigurator(configurator);
		}
		return builder.build();
	}

	private void apply(UriageDomain domain, UriageRirekiDomainBuilder builder) {
		builder.withUriageId(domain.getRecordId());
		builder.withKokyaku(domain.getKokyaku());
		builder.withDenpyoNumber(domain.getDenpyoNumber());
		builder.withUriageDate(domain.getUriageDate());
		builder.withKeijoDate(domain.getKeijoDate());
		builder.withHanbaiKubun(domain.getHanbaiKubun());
		builder.withShohizeiritsu(domain.getShohizeiritsu());
		List<UriageRirekiMeisaiDomain> list = domain.getUriageMeisai().stream().map(meiai -> {
			UriageRirekiMeisaiDomainBuilder urmdb = new UriageRirekiMeisaiDomainBuilder();
			return urmdb.apply(meiai);
		}).collect(Collectors.toList());
		builder.withUriageMeisai(list);
		builder.withRecordId(domain.getRecordId());
		builder.withVersion(domain.getVersion());
	}

}