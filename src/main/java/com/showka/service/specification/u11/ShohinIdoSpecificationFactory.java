package com.showka.service.specification.u11;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import com.showka.domain.u05.Uriage;
import com.showka.service.specification.u11.i.ShohinIdoSpecification;

@Component
public class ShohinIdoSpecificationFactory {

	@Autowired
	private AutowireCapableBeanFactory beanFactory;

	/**
	 * 生成.
	 * 
	 * @param uriage
	 * @return
	 */
	public ShohinIdoSpecification create(Uriage uriage) {
		ShohinIdoUriageSpecification object = new ShohinIdoUriageSpecification();
		object.setUriage(uriage);
		beanFactory.autowireBean(object);
		return object;
	}
}
