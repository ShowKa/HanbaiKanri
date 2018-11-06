package com.showka.event.u06;

import com.showka.domain.u06.Urikake;
import com.showka.event.CrudEvent;

@SuppressWarnings("serial")
public class UrikakeCrudEvent extends CrudEvent<Urikake> {

	public UrikakeCrudEvent(Object source, EventType type, Urikake domain) {
		super(source, type, domain);
	}
}
