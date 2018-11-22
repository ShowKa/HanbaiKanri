package com.showka.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.showka.domain.z00.Shain;
import com.showka.service.crud.z00.i.ShainCrud;

/**
 * Controllerの基盤クラス.
 * 
 * @author ShowKa
 *
 */
@Controller
public abstract class ControllerBase {

	/** 社員Persistence */
	@Autowired
	private ShainCrud shainPersistence;

	/** Logger. */
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

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
		return shainPersistence.getDomain(code);
	}
}
