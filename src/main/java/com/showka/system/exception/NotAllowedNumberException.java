package com.showka.system.exception;

import java.math.BigDecimal;

import lombok.Getter;

/**
 * 設定不可の数値が設定されたときの例外.
 *
 */
@Getter
public class NotAllowedNumberException extends ValidateException {

	/**
	 * SID.
	 */
	private static final long serialVersionUID = -2691981820144093022L;

	/** 設定対象 */
	private String targetName;

	/** 下限 */
	private BigDecimal allowedMinimum;

	/** 上限 */
	private BigDecimal allowedMaximum;

	/**
	 * コンストラクタ.
	 * 
	 * <pre>
	 * targetName の値は allowedMinimum以上 allowedMaximum以下 を設定する必要があります。
	 * 
	 * 許可最小値をのみ指定した場合、「..は..以上を設定する必要があります。」というふうになります。
	 * 許可最大値をのみ指定した場合、「..は..以下を設定する必要があります。」というふうになります。
	 * 
	 * <pre>
	 * 
	 * @param targetName
	 *            対象項目名
	 * @param allowedMinimum
	 *            許可最小値
	 * @param allowedMaximum
	 *            許可最大値
	 */
	public NotAllowedNumberException(String targetName, BigDecimal allowedMinimum, BigDecimal allowedMaximum) {
		super(targetName + "の値は " + getBetweenMessage(allowedMinimum, allowedMaximum) + " を設定する必要があります。");
		this.targetName = targetName;
		this.allowedMinimum = allowedMinimum;
		this.allowedMaximum = allowedMaximum;
	}

	/**
	 * コンストラクタ.
	 * 
	 * <pre>
	 * targetName の値は allowedNumber を設定する必要があります。
	 * 
	 * <pre>
	 * 
	 * @param targetName
	 * @param allowedNumber
	 */
	public NotAllowedNumberException(String targetName, BigDecimal allowedNumber) {
		super(targetName + "の値は " + allowedNumber.toString() + " を設定する必要があります。");
		this.targetName = targetName;
		this.allowedMaximum = allowedNumber;
		this.allowedMinimum = allowedNumber;
	}

	/**
	 * 範囲メッセージ.
	 * 
	 * @param min
	 *            最小値
	 * @param max
	 *            最大値
	 * @return メッセージ
	 */
	private static String getBetweenMessage(BigDecimal min, BigDecimal max) {
		if (min != null && max != null) {
			if (min.equals(max)) {
				return min.toString();
			}
			return min.toString() + "以上 " + max.toString() + "以下";
		}
		if (min != null) {
			return min.toString() + "以上";
		}
		return max.toString() + "以下";
	}
}
