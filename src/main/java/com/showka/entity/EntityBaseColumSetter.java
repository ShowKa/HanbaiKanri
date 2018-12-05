package com.showka.entity;

import java.util.Date;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.showka.system.Entry;

@Aspect
@Component
public class EntityBaseColumSetter {

	@Autowired
	private Entry entry;

	@Autowired
	private EntityManager entityManager;

	@Around("execution(* org.springframework.data.jpa.repository.JpaRepository+.save*(..)) && args(entity)")
	public Object setBaseColumn(ProceedingJoinPoint pjp, EntityBase entity) throws Throwable {
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
		// XXX Flush & Refresh してもエラーにならにので取り除いた。
		// ただし、save後にJooqでqueryを発行する場合、flushが必要になるかもしれないので要注意。
		// session.flush();
		return ret;
	}

	@Around("execution(* org.springframework.data.jpa.repository.JpaRepository+.getOne(..))")
	public Object refreshWhenGet(ProceedingJoinPoint pjp) throws Throwable {
		Object ret = pjp.proceed();
		if (!(ret instanceof EntityBase)) {
			return ret;
		}
		EntityBase e = (EntityBase) ret;
		if (e.isNew()) {
			this.refresh(e);
		}
		return ret;
	}

	@Around("execution(* org.springframework.data.jpa.repository.JpaRepository+.findById(..))")
	public Object refreshWhenFindById(ProceedingJoinPoint pjp) throws Throwable {
		Object ret = pjp.proceed();
		if (!(ret instanceof Optional)) {
			return ret;
		}
		Optional<?> o = (Optional<?>) ret;
		if (!o.isPresent()) {
			return ret;
		}
		Object _e = o.get();
		if (!(_e instanceof EntityBase)) {
			return ret;
		}
		EntityBase e = (EntityBase) _e;
		if (e.isNew()) {
			this.refresh(e);
		}
		return ret;
	}

	private void refresh(EntityBase e) {
		entityManager.flush();
		entityManager.refresh(e);
		e.setNotNew();
	}

	private String getUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}
}
