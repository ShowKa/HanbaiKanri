package com.showka.system;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginUser implements Serializable {

	/** SID. */
	private static final long serialVersionUID = -8742489744275317299L;

	/** ユーザID */
	private String userId;

	/** ユーザ名 */
	private String userName;

}
