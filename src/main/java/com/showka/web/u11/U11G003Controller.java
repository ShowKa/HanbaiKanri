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

	/** 参照モード. */
	@RequestMapping(value = "/u11g003/refer", method = RequestMethod.GET)
	public ModelAndViewExtended refer(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// set model
		model.addForm(form);
		model.setMode(Mode.READ);
		model.setViewName("/u11/u11g003");
		// return
		return model;
	}

	/** 新規登録モード */
	@RequestMapping(value = "/u11g003/registerForm", method = RequestMethod.GET)
	public ModelAndViewExtended registerForm(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// set model
		model.addForm(form);
		model.setMode(Mode.REGISTER);
		model.setViewName("/u11/u11g003");
		// return
		return model;
	}

	/** 商品入荷登録. */
	@RequestMapping(value = "/u11g003/register", method = RequestMethod.POST)
	public ResponseEntity<?> register(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// return
		return ResponseEntity.ok(model);
	}

	/** 商品入荷更新. */
	@RequestMapping(value = "/u11g003/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// return
		return ResponseEntity.ok(model);
	}

	/** 商品入荷削除. */
	@RequestMapping(value = "/u11g003/delete", method = RequestMethod.POST)
	public ResponseEntity<?> delete(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// return
		return ResponseEntity.ok(model);
	}

	/** 商品入荷訂正登録. */
	@RequestMapping(value = "/u11g003/registerTeisei", method = RequestMethod.POST)
	public ResponseEntity<?> registerTeisei(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// return
		return ResponseEntity.ok(model);
	}

	/** 商品入荷訂正更新. */
	@RequestMapping(value = "/u11g003/updateTeisei", method = RequestMethod.POST)
	public ResponseEntity<?> updateTeisei(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// return
		return ResponseEntity.ok(model);
	}

	/** 商品入荷訂正削除. */
	@RequestMapping(value = "/u11g003/deleteTeisei", method = RequestMethod.POST)
	public ResponseEntity<?> deleteTeisei(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// return
		return ResponseEntity.ok(model);
	}

	/** 商品入荷取得. */
	@RequestMapping(value = "/u11g003/get", method = RequestMethod.POST)
	public ResponseEntity<?> get(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// return
		return ResponseEntity.ok(model);
	}

	/** ヘッダー整合性検証. */
	@RequestMapping(value = "/u11g003/validateHeader", method = RequestMethod.POST)
	public ResponseEntity<?> validateHeader(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// return
		return ResponseEntity.ok(model);
	}

	/** 明細整合性検証. */
	@RequestMapping(value = "/u11g003/validateMeisai", method = RequestMethod.POST)
	public ResponseEntity<?> validateMeisai(@ModelAttribute U11G003Form form, ModelAndViewExtended model) {
		// return
		return ResponseEntity.ok(model);
	}
}
