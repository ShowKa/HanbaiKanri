package com.showka.u01.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.entity.MKokyaku;
import com.showka.u01.service.MKokyakuCrudSearchCriteria;
import com.showka.u01.service.i.MKokyakuCrudService;

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
	private MKokyakuCrudService service;

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
		MKokyakuCrudSearchCriteria criteria = new MKokyakuCrudSearchCriteria();
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

}
