package com.showka.web.u00;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Menu.
 * 
 * @author Administrator
 *
 */
@Controller
public class U00G001Controller {

	// public method called by request
	@RequestMapping(value = "/u00g001", method = RequestMethod.GET)
	public String index(Map<String, Object> model, HttpSession session) {
		return "u00/u00g001";
	}

}
