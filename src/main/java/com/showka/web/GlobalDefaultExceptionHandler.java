package com.showka.web;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.showka.system.exception.ApplicationException;

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
	 *            アプリケーション例外（整合性検証例外、仕様未満例外）
	 * @return HTTPレスポンス
	 */
	@ExceptionHandler(value = ApplicationException.class)
	public ResponseEntity<?> defaultErrorHandler(HttpServletRequest req, ApplicationException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convertMessageToJson(e.getMessageAsHtml()));
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
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(convertMessageToJson("他のユーザによりすでにデータが更新されています。画面を開き直してください。"));
	}

	/**
	 * 
	 * StringのメッセージをJSONにする。
	 * 
	 * @param message
	 *            メッセージ
	 * @return {"message : " + "引数のメッセージ"}
	 */
	private String convertMessageToJson(String message) {
		// 改行コードはhtmlタグに置き換える。
		return "{\"message\" : \"" + message + "\"}";
	}
}
