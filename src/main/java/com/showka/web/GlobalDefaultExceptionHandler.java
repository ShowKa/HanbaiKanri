package com.showka.web;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.showka.system.exception.ValidateException;

/**
 * ExceptionHandler
 * 
 * @author ShowKa
 *
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	/**
	 * 例外メッセージをbodyにセットして返すだけ。HttpStatus=400
	 * 
	 * @param req
	 *            リクエスト
	 * @param e
	 *            整合性検証例外
	 * @return HTTPレスポンス
	 */
	@ExceptionHandler(value = ValidateException.class)
	public ResponseEntity<?> defaultErrorHandler(HttpServletRequest req, ValidateException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	/**
	 * 例外メッセージをbodyにセットして返すだけ。HttpStatus=409
	 * 
	 * @param req
	 *            リクエスト
	 * @param e
	 *            排他制御例外
	 * @return HTTPレスポンス
	 */
	@ExceptionHandler(value = OptimisticLockException.class)
	public ResponseEntity<?> optimisticLockExceptionHandler(HttpServletRequest req, OptimisticLockException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body("他のユーザによりすでにデータが更新されています。画面を開き直してください。");
	}
}
