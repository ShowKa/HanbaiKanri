package com.showka.web.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.domain.u01.Kokyaku;
import com.showka.service.crud.u01.i.KokyakuCrud;
import com.showka.web.ControllerBase;
import com.showka.web.ModelAndViewExtended;

@Controller
@EnableAutoConfiguration
public class KokyakuController extends ControllerBase {

	@Autowired
	private KokyakuCrud kokyakuCrud;

	@RequestMapping(value = "/info/getKokyaku", method = RequestMethod.GET)
	public ResponseEntity<?> getRireki(@ModelAttribute InfoForm form, ModelAndViewExtended model) {
		// 存在チェック
		String code = form.getCode();
		boolean exists = kokyakuCrud.exists(code);
		if (exists) {
			Kokyaku kokyaku = kokyakuCrud.getDomain(code);
			model.addObject("code", kokyaku.getCode());
			model.addObject("name", kokyaku.getName());
		}
		model.addObject("exists", exists);
		model.addForm(form);
		return ResponseEntity.ok(model);
	}
}
