package com.showka.web.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.domain.u11.NyukaSaki;
import com.showka.service.crud.u11.i.NyukaSakiCrud;
import com.showka.web.ControllerBase;
import com.showka.web.ModelAndViewExtended;

@Controller
@EnableAutoConfiguration
public class NyukaSakiController extends ControllerBase {

	@Autowired
	private NyukaSakiCrud nyukaSakiCrud;

	@RequestMapping(value = "/info/getNyukaSaki", method = RequestMethod.GET)
	public ResponseEntity<?> getRireki(@ModelAttribute InfoForm form, ModelAndViewExtended model) {
		// 存在チェック
		String code = form.getCode();
		boolean exists = nyukaSakiCrud.exists(code);
		if (exists) {
			NyukaSaki NyukaSaki = nyukaSakiCrud.getDomain(code);
			model.addObject("code", NyukaSaki.getCode());
			model.addObject("name", NyukaSaki.getName());
		}
		model.addObject("exists", exists);
		model.addForm(form);
		return ResponseEntity.ok(model);
	}
}
