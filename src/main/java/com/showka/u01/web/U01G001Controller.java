package com.showka.u01.web;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class U01G001Controller {

	// public method called by request
	@RequestMapping(value = "/u01g001", method = RequestMethod.GET)
	public String index(Map<String, Object> model, HttpSession session) {
		return "/u01/u01g001";
	}

}
