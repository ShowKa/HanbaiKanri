package com.showka.web.u01;

import com.showka.annotation.ShiftJis;
import com.showka.web.FormBase;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class U01G001Form extends FormBase {

	@ShiftJis
	private String kokyakuName;

	private String bushoName;

}
