package com.showka.web;

import java.util.ArrayList;
import java.util.List;

import com.showka.kubun.i.Kubun;

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

	/**
	 * mode 設定
	 * 
	 * @param mode
	 */
	public void setMode(Mode mode) {
		this.addObject("mode", mode.getCode());
	}

	/**
	 * 区分 追加.
	 * 
	 * @param 区分
	 */
	public void addKubun(String name, Kubun<?>... values) {
		List<Kubun<?>> list = new ArrayList<>();
		for (Kubun<?> k : values) {
			// ignore empty
			if (k.eq("")) {
				continue;
			}
			list.add(k);
		}
		this.addObject(name, list);
	}

}
