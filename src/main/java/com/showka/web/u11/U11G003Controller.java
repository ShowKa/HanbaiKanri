package com.showka.web.u11;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.web.ControllerBase;
import com.showka.web.Mode;
import com.showka.web.ModelAndViewExtended;

@Controller
@EnableAutoConfiguration
public class U11G003Controller extends ControllerBase {
	/**
	 * 参照.
	 *
	 */
	@RequestMapping(value = "/u11g003/refer", method = RequestMethod.GET)
	public ModelAndViewExtended refer(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// set model
		model.addForm(form);
		model.setMode(Mode.READ);
		model.setViewName("/u11/u11g003");
		// return
		return model;
	}

	/**
	 * 商品在庫取得.
	 *
	 */
	@RequestMapping(value = "/u11g003/get", method = RequestMethod.POST)
	public ResponseEntity<?> get(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// return
		return ResponseEntity.ok(model);
	}
}
