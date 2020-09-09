package com.showka.web.u01;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.entity.MKokyaku;
import com.showka.service.search.u01.i.KokyakuSearch;
import com.showka.service.search.u01.i.KokyakuSearchCriteria;
import com.showka.web.MavMap;
import com.showka.web.ModelAndViewExtended;

/**
 * U01G001 顧客検索画面
 *
 * @author ShowKa
 *
 */
@Controller
@EnableAutoConfiguration
public class U01G001Controller {

	@Autowired
	private KokyakuSearch service;

	// public method called by request
	/**
	 *
	 * 初期表示
	 *
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/u01g001", method = RequestMethod.GET)
	public ModelAndViewExtended index(@ModelAttribute U01G001Form form, ModelAndViewExtended model) {
		model.addForm(form);
		model.setViewName("/u01/u01g001");
		return model;
	}

	/**
	 * 検索
	 *
	 * @param form
	 */
	@RequestMapping(value = "/u01g001/search", method = RequestMethod.GET)
	public String search(@ModelAttribute U01G001Form form, Model model, HttpSession session) {

		// 検索
		KokyakuSearchCriteria criteria = new KokyakuSearchCriteria();
		criteria.setName(form.getKokyakuName());
		criteria.setBushoName(form.getBushoName());
		List<MKokyaku> result = service.search(criteria);

		// list
		List<MavMap> kokyakuList = new ArrayList<>();
		for (MKokyaku k : result) {
			MavMap m = new MavMap();
			m.put("code", k.getCode());
			m.put("name", k.getName());
			m.put("bushoName", k.getShukanBusho().getName());
			kokyakuList.add(m);
		}
		model.addAttribute("kokyakuList", kokyakuList);

		// 画面
		return "u01/u01g001KokyakuList :: list";
	}

	/**
	 * validate
	 *
	 * @param form
	 *
	 */
	@RequestMapping(value = "/u01g001/valid", method = RequestMethod.GET)
	public String form(@Valid @ModelAttribute U01G001Form form, BindingResult result, Model model,
			HttpSession session) {

		if (result.hasErrors()) {
			// list
			List<MavMap> kokyakuList = new ArrayList<>();
			MavMap m = new MavMap();
			m.put("code", "");
			m.put("name", "valid NG");
			m.put("bushoName", "");
			kokyakuList.add(m);

			model.addAttribute("kokyakuList", kokyakuList);

		} else {
			search(form, model, session);
		}

		return "u01/u01g001KokyakuList :: list";
	}

}
