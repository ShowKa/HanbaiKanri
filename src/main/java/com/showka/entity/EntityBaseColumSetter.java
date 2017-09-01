package com.showka.entity;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EntityBaseColumSetter {

	@Around("execution(* org.springframework.data.jpa.repository.JpaRepository+.save*(..)) && args(entity)")
	public Object checkSearchResultSize(ProceedingJoinPoint pjp, EntityBase entity) throws Throwable {
		// param
		String u = getUserId();
		Date d = new Date();

		// set create
		if (entity.getCreate_function() == null) {
			entity.setCreate_function(u);
		}
		if (entity.getCreate_timestamp() == null) {
			entity.setCreate_timestamp(d);
		}
		if (entity.getCreate_user_id() == null) {
			entity.setCreate_user_id(u);
		}
		// set update
		entity.setUpdate_function(u);
		entity.setUpdate_timestamp(d);
		entity.setUpdate_user_id(u);

		Object ret = pjp.proceed();
		return ret;
	}

	private String getUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}
}
