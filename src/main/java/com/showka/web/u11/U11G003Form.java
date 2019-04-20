package com.showka.web.u11;

import java.util.ArrayList;
import java.util.List;

import com.showka.web.FormBase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class U11G003Form extends FormBase {
	/** SID. */
	private static final long serialVersionUID = 4684421184359829226L;

	/** 部署コード. */
	private String bushoCode;
	/** 入荷先コード. */
	private String nyukaSakiCode;
	/** 入荷明細. */
	private List<U11G003MeisaiForm> meisai = new ArrayList<U11G003MeisaiForm>();
	/** record_id */
	private String nyukaId;
	/** バージョン(排他制御用) */
	private Integer version;

}
