package com.showka.service.listener;

import org.springframework.context.ApplicationListener;

import com.showka.domain.DomainBase;
import com.showka.service.event.CrudEvent;
import com.showka.service.event.CrudEvent.EventType;

public abstract class CrudEventListener<T extends DomainBase> implements ApplicationListener<CrudEvent<T>> {

	@Override
	public final void onApplicationEvent(CrudEvent<T> event) {
		EventType type = event.getType();
		switch (type) {
		case save:
			onSave(event.getDomain());
			break;
		case delete:
			onDelete(event.getDomain());
			break;
		case newRegister:
			onNewRegister(event.getDomain());
			break;
		case update:
			onUpdate(event.getDomain());
			break;
		default:
			break;
		}
	}

	public void onSave(T domain) {
	}

	public void onDelete(T domain) {
	}

	public void onNewRegister(T domain) {
	}

	public void onUpdate(T domain) {
	}
}
