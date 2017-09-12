package com.showka.web;

import lombok.Getter;

/**
 * 
 * ModelAndViewの拡張.
 * 
 * <pre>
 * formを追加する関数を追加した。
 * </pre>
 * 
 * @author ShowKa.
 * @see org.springframework.web.servlet.ModelAndView
 * 
 */
@Getter
public class ModelAndViewExtended extends org.springframework.web.servlet.ModelAndView {

	/**
	 * form 追加.
	 * 
	 * @param form
	 */
	public void addForm(FormBase form) {
		this.addObject("form", form);
	}

}
