package com.showka.handler;

import org.springframework.context.ApplicationListener;

import com.showka.domain.DomainBase;
import com.showka.event.CrudEvent;
import com.showka.event.CrudEvent.EventType;

public interface CrudEventListener<T extends DomainBase> extends ApplicationListener<CrudEvent<T>> {

	@Override
	default void onApplicationEvent(CrudEvent<T> event) {
		EventType type = event.getType();
		switch (type) {
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

	default void afterSave(T domain) {
	}

	default void afterDelete(T domain) {
	}

	default void afterNewRegister(T domain) {
	}

	default void afterUpdate(T domain) {
	}
}
