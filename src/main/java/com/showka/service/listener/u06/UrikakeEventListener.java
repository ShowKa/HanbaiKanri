package com.showka.service.listener.u06;

import org.springframework.stereotype.Component;

import com.showka.domain.u06.Urikake;
import com.showka.service.listener.CrudEventListener;

@Component
public class UrikakeEventListener extends CrudEventListener<Urikake> {

	@Override
	public void onSave(Urikake urikake) {
		System.out.println("売掛-------------------------");
		System.out.println(urikake);
		System.out.println("-------------------------");
	}
}
