package com.showka.web.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.domain.z00.Busho;
import com.showka.service.crud.z00.i.BushoCrud;
import com.showka.web.ControllerBase;
import com.showka.web.ModelAndViewExtended;

@Controller
@EnableAutoConfiguration
public class BushoController extends ControllerBase {

	@Autowired
	private BushoCrud bushoCrud;

	@RequestMapping(value = "/info/getBusho", method = RequestMethod.GET)
	public ResponseEntity<?> getRireki(@ModelAttribute InfoForm form, ModelAndViewExtended model) {
		// 存在チェック
		String code = form.getCode();
		boolean exists = bushoCrud.exists(code);
		if (exists) {
			Busho Busho = bushoCrud.getDomain(code);
			model.addObject("code", Busho.getCode());
			model.addObject("name", Busho.getName());
		}
		model.addObject("exists", exists);
		model.addForm(form);
		return ResponseEntity.ok(model);
	}
}
