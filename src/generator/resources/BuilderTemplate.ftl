package com.showka.domain.builder;

public class ${domain}Builder extends ${builderBaseClass}<${domain}, ${domain}Builder> {

	// private member
	<#list members as m>
	/** ${m.explain} */
	private ${m.type} ${m.name};

    </#list>
	
	// protected method
	@Override
	protected void apply(${domain} domain, ${domain}Builder builder) {
		<#list members as m>
		builder.with${m.name?cap_first}(domain.get${m.name?cap_first}());
		</#list>
	}

	@Override
	protected ${domain} createDomainObject() {
		${domain} domain = new ${domain}(${memberListWithComma});
		domain.setRecordId(recordId);
		domain.setVersion(version);
		return domain;
	}

	@Override
	protected ${domain}Builder getThis() {
		return this;
	}

	@Override
	protected ${domain}Builder newInstance() {
		return new ${domain}Builder();
	}

	// public method
	<#list members as m>
	/**
	 * {@link ${domain}}に与える${m.explain}をこのビルダに設定する。
	 *
	 * @param ${m.name}
	 *            ${m.explain}
	 * @return {@link ${domain}Builder}
	 */
	public ${domain}Builder with${m.name?cap_first}(final ${m.type} ${m.name}) {
		addConfigurator(new BuilderConfigurator<${domain}Builder>() {
			@Override
			public void configure(${domain}Builder builder) {
				builder.${m.name} = ${m.name};
			}
		});
		return getThis();
	}
	
	</#list>
}