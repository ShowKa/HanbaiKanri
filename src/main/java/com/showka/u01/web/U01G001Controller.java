package com.showka.u01.web;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * U01G001 顧客検索画面
 * 
 * @author ShowKa
 *
 */
@Controller
@EnableAutoConfiguration
public class U01G001Controller {

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
		model.addAttribute("kokyakuName", form.getKokyakuName());
		return "/u01/u01g001KokyakuList :: list";
	}

}
