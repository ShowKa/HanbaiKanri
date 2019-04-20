package com.showka.web;

import java.util.ArrayList;
import java.util.List;

import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shain;
import com.showka.kubun.i.Kubun;
import com.showka.value.EigyoDate;
import com.showka.value.TheDate;

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

	/**
	 * ログインユーザー情報の設定.
	 * 
	 * @param shain
	 *            社員
	 */
	public void setUserInfo(Shain shain) {
		this.addObject("loginUserName", shain.getName());
		Busho shozoku = shain.getShozokuBusho();
		EigyoDate eigyoDate = shozoku.getEigyoDate();
		this.addObject("loginUserBushoName", shozoku.getName());
		this.addObject("loginUserBushoEigyoDate", eigyoDate.toString() + " (" + eigyoDate.getDayOfWeek() + ")");
	}

	// extension
	/**
	 * 日付.
	 * 
	 * @param attributeName
	 *            属性名
	 * @param date
	 *            日付
	 * @return ModeAndView
	 */
	public ModelAndViewExtended addObject(String attributeName, TheDate date) {
		super.addObject(attributeName, date.toString());
		return this;
	}
}
