package com.showka.service.specification.u11;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import com.showka.domain.u05.Uriage;

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
	public ShohinIdoSpecificationAssociatedWithUriage create(Uriage uriage) {
		ShohinIdoSpecificationAssociatedWithUriage object = new ShohinIdoSpecificationAssociatedWithUriage();
		beanFactory.autowireBean(object);
		object.setUriage(uriage);
		return object;
	}
}
