package com.showka.system.triggerEvent;

import org.springframework.context.ApplicationListener;

import com.showka.domain.DomainRoot;
import com.showka.system.triggerEvent.CrudEvent.EventType;

public interface CrudEventListener<T extends DomainRoot> extends ApplicationListener<CrudEvent<T>> {

	@Override
	default void onApplicationEvent(CrudEvent<T> event) {
		EventType type = event.getType();
		switch (type) {
		case beforeSave:
			beforeSave(event.getDomain());
			break;
		case beforeDelete:
			beforeDelete(event.getDomain());
			break;
		case beforeNewRegister:
			beforeNewRegister(event.getDomain());
			break;
		case beforeUpdate:
			beforeUpdate(event.getDomain());
			break;
		case save:
			afterSave(event.getDomain());
			break;
		case delete:
			afterDelete(event.getDomain());
			break;
		case newRegister:
			afterNewRegister(event.getDomain());
			break;
		case update:
			afterUpdate(event.getDomain());
			break;
		default:
			break;
		}
	}

	default void beforeSave(T domain) {
	}

	default void beforeDelete(T domain) {
	}

	default void beforeNewRegister(T domain) {
	}

	default void beforeUpdate(T domain) {
	}

	default void afterSave(T domain) {
	}

	default void afterDelete(T domain) {
	}

	default void afterNewRegister(T domain) {
	}

	default void afterUpdate(T domain) {
	}
}
