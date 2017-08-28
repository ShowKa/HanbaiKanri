package com.showka.web.u01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.showka.service.search.u01.KokyakuSearchCriteria;
import com.showka.service.search.u01.i.KokyakuSearchService;

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
	private KokyakuSearchService service;

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
	public String index(Map<String, Object> model, HttpSession session) {
		return "/u01/u01g001";
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
		ArrayList<HashMap<String, String>> kokyakuList = new ArrayList<>();
		for (MKokyaku k : result) {
			HashMap<String, String> m = new HashMap<String, String>();
			m.put("name", k.getName());
			m.put("bushoName", k.getShukanBusho().getName());
			kokyakuList.add(m);
		}
		model.addAttribute("kokyakuList", kokyakuList);

		// 画面
		return "/u01/u01g001KokyakuList :: list";
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
		System.out.println("doing validate");

		if (result.hasErrors()) {
			// list
			ArrayList<HashMap<String, String>> kokyakuList = new ArrayList<>();
			HashMap<String, String> m = new HashMap<String, String>();
			m.put("name", "valid NG");
			m.put("bushoName", "");
			kokyakuList.add(m);
			System.out.println("has error");

			model.addAttribute("kokyakuList", kokyakuList);

		} else {
			search(form, model, session);
		}

		return "/u01/u01g001KokyakuList :: list";
	}

}
