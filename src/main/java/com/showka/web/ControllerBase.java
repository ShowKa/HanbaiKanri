package com.showka.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.showka.domain.Shain;
import com.showka.service.crud.z00.i.ShainCrudService;

/**
 * Controllerの基盤クラス.
 * 
 * @author ShowKa
 *
 */
@Controller
public abstract class ControllerBase {

	/** 社員CrudService */
	@Autowired
	private ShainCrudService shainCrudService;

	/**
	 * ログインユーザーのユーザーコードを返却.
	 * 
	 * @return ユーザーコード
	 */
	protected String getLoginUserCode() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}

	/**
	 * ログイン社員ドメイン取得
	 * 
	 * @return 社員ドメイン
	 */
	protected Shain getLoginShain() {
		String code = getLoginUserCode();
		return shainCrudService.getDomain(code);
	}
}
