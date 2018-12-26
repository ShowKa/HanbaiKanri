package com.showka.web.info;

import com.showka.web.FormBase;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoForm extends FormBase {
	/**
	 * SID.
	 */
	private static final long serialVersionUID = 1720268095197814116L;

	/** code */
	private String code;
}
