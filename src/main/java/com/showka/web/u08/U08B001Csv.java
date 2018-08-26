package com.showka.web.u08;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.showka.entity.TFirmBankFurikomi;
import com.showka.entity.TFirmBankFurikomiPK;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class U08B001Csv {

	/** 伝送日付. */
	// FIXME Timezone SpringBootでデフォルト値を設定しても有効になってくれない。。。
	@JsonProperty("transmissionDate")
	@JsonFormat(pattern = "yyyy/MM/dd", timezone = "Asia/Tokyo")
	private Date transmissionDate;

	/** 伝送番号（日付毎の連番）. */
	@JsonProperty("transmissionNumber")
	private Integer transmissionNumber;

	/** 銀行コード. */
	@JsonProperty("bankCode")
	private String bankCode;

	/** 口座番号. */
	@JsonProperty("accountNumber")
	private String accountNumber;

	/** 勘定日付(決済日付). */
	@JsonProperty("settlementDate")
	@JsonFormat(pattern = "yyyy/MM/dd", timezone = "Asia/Tokyo")
	private Date settlementDate;

	/** 振込金額. */
	@JsonProperty("kingaku")
	private Integer kingaku;

	/** 振込依頼人名. */
	@JsonProperty("furikomiIraininName")
	private String furikomiIraininName;

	/**
	 * FB Entity.
	 * 
	 * <pre>
	 * id, version == null
	 * </pre>
	 * 
	 * @return entity
	 */
	public TFirmBankFurikomi toFirmBankFurikomiEntity() {
		TFirmBankFurikomi e = new TFirmBankFurikomi();
		e.setAccountNumber(accountNumber);
		e.setBankCode(bankCode);
		e.setFurikomiIraininName(furikomiIraininName);
		e.setKingaku(kingaku);
		e.setSettlementDate(settlementDate);
		TFirmBankFurikomiPK pk = new TFirmBankFurikomiPK();
		pk.setTransmissionDate(transmissionDate);
		pk.setTransmissionNumber(transmissionNumber);
		e.setPk(pk);
		return e;
	}
}
