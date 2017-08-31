package com.showka.web.u01;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.service.search.u01.i.KokyakuSearchService;

/**
 * U01G002 顧客登録画面
 *
 * @author 25767
 *
 */
@Controller
@EnableAutoConfiguration
public class U01G002Controller {

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
	@RequestMapping(value = "/u01g002", method = RequestMethod.GET)
	public String index(Map<String, Object> model, HttpSession session) {
		return "/u01/u01g002";
	}

}
