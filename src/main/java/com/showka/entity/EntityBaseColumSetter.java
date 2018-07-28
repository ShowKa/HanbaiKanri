package com.showka.entity;

import java.util.Date;

import javax.persistence.EntityManager;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.showka.system.Entry;

@Aspect
@Component
public class EntityBaseColumSetter {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Entry entry;

	@Around("execution(* org.springframework.data.jpa.repository.JpaRepository+.save*(..)) && args(entity)")
	public Object checkSearchResultSize(ProceedingJoinPoint pjp, EntityBase entity) throws Throwable {
		// param
		String u = getUserId();
		Date d = new Date();
		String f = entry.getEntryPointName();
		// set create
		if (entity.getCreate_function() == null) {
			entity.setCreate_function(f);
		}
		if (entity.getCreate_timestamp() == null) {
			entity.setCreate_timestamp(d);
		}
		if (entity.getCreate_user_id() == null) {
			entity.setCreate_user_id(u);
		}
		// set update
		entity.setUpdate_function(f);
		entity.setUpdate_timestamp(d);
		entity.setUpdate_user_id(u);
		// do
		Object ret = pjp.proceed();
		// insert and refresh entity => fetch all columns
		Session session = this.getSession();
		session.flush();
		session.refresh(entity);
		return ret;
	}

	private String getUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}

	private Session getSession() {
		return entityManager.unwrap(Session.class);
	}

}
