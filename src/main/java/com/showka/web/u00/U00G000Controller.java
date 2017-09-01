package com.showka.web.u00;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * U00G000 ログインコントローラー
 * 
 * @author ShowKa
 *
 */
@Controller
public class U00G000Controller {

	/**
	 * ログイン画面を表示するだけ
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	// public method called by request
	@RequestMapping(value = "/u00g000", method = RequestMethod.GET)
	public String index(Map<String, Object> model, HttpSession session) {
		return "/u00/u00g000";
	}
}
