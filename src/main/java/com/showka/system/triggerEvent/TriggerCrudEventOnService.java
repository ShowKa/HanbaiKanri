package com.showka.system.triggerEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.showka.domain.DomainBase;
import com.showka.event.CrudEvent;
import com.showka.event.CrudEvent.EventType;
import com.showka.system.exception.SystemException;

@Aspect
@Component
public class TriggerCrudEventOnService {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Around("execution(* com.showka.service.crud.CrudService+.save(..)) && @annotation(triggerCrudEvent) && args(domain)")
	public Object triggerSaveEvent(ProceedingJoinPoint pjp, TriggerCrudEvent triggerCrudEvent, DomainBase domain)
			throws Throwable {
		// register or update
		boolean doRegister = domain.getVersion() == null ? true : false;
		// do
		Object ret = pjp.proceed();
		// get constructor for event
		Class<?> eventClass = triggerCrudEvent.event();
		if (!CrudEvent.class.isAssignableFrom(eventClass)) {
			throw new SystemException("@TriggerCrudEventにはCrudEventのサブクラスを設定してください。 : " + eventClass);
		}
		Constructor<?>[] constuctor = eventClass.getConstructors();
		if (constuctor.length == 0) {
			throw new SystemException("CrudEventにコンストラクタが設定されていません。 : " + eventClass);
		} else if (constuctor.length > 1) {
			throw new SystemException("CrudEventにコンストラクタは1つだけ許可されています。 : " + eventClass);
		}
		// get event instance
		CrudEvent<?> saveEvent;
		CrudEvent<?> regOrUpdateEvent;
		try {
			saveEvent = (CrudEvent<?>) constuctor[0].newInstance(pjp.getTarget(), EventType.save, domain);
			EventType _regOrUpdate = doRegister ? EventType.newRegister : EventType.update;
			regOrUpdateEvent = (CrudEvent<?>) constuctor[0].newInstance(pjp.getTarget(), _regOrUpdate, domain);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new SystemException("CrudEventにコンストラクタの設定が誤っています。 : " + eventClass);
		}
		// publish event
		applicationEventPublisher.publishEvent(saveEvent);
		applicationEventPublisher.publishEvent(regOrUpdateEvent);
		// return
		return ret;
	}

}
