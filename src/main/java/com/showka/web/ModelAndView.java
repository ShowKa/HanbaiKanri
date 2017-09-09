package com.showka.web;

import lombok.Getter;

@Getter
public class ModelAndView extends org.springframework.web.servlet.ModelAndView {

	/**
	 * form 追加.
	 * 
	 * @param form
	 */
	public void addForm(FormBase form) {
		this.addObject("form", form);
	}

}
