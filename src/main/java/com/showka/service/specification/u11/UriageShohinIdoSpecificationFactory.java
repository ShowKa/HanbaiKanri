package com.showka.service.specification.u11;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import com.showka.domain.Uriage;

@Component
public class UriageShohinIdoSpecificationFactory {

	@Autowired
	private AutowireCapableBeanFactory beanFactory;

	/**
	 * 生成.
	 * 
	 * @param uriage
	 * @return
	 */
	public UriageShohinIdoSpecification create(Uriage uriage) {
		UriageShohinIdoSpecification object = new UriageShohinIdoSpecification();
		beanFactory.autowireBean(object);
		object.setUriage(uriage);
		return object;
	}
}
