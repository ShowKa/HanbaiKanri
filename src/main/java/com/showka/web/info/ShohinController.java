package com.showka.web.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.domain.z00.Shohin;
import com.showka.service.crud.z00.i.ShohinCrud;
import com.showka.web.ControllerBase;
import com.showka.web.ModelAndViewExtended;

@Controller
@EnableAutoConfiguration
public class ShohinController extends ControllerBase {

	@Autowired
	private ShohinCrud shohinCrud;

	@RequestMapping(value = "/info/getShohin", method = RequestMethod.GET)
	public ResponseEntity<?> getRireki(@ModelAttribute InfoForm form, ModelAndViewExtended model) {
		// 存在チェック
		String code = form.getCode();
		boolean exists = shohinCrud.exists(code);
		if (exists) {
			Shohin Shohin = shohinCrud.getDomain(code);
			model.addObject("code", Shohin.getCode());
			model.addObject("name", Shohin.getName());
		}
		model.addObject("exists", exists);
		model.addForm(form);
		return ResponseEntity.ok(model);
	}
}
