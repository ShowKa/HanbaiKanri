package com.showka.service.listener.u06;

import org.springframework.stereotype.Component;

import com.showka.domain.u07.Seikyu;
import com.showka.service.listener.CrudEventListener;

@Component
public class SeikyuEventListener extends CrudEventListener<Seikyu> {

	@Override
	public void onSave(Seikyu seikyu) {
		System.out.println("請求-------------------------");
		System.out.println(seikyu);
		System.out.println("だとよ-------------------------");
	}
}
