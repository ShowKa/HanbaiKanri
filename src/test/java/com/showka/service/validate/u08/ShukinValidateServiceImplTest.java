package com.showka.service.validate.u08;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.builder.KokyakuBuilder;
import com.showka.domain.builder.ShainBuilder;
import com.showka.domain.builder.ShukinBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u08.Shukin;
import com.showka.domain.z00.Busho;
import com.showka.domain.z00.Shain;
import com.showka.service.crud.u08.i.ShukinCrudService;
import com.showka.service.specification.u08.i.NyukinKeshikomiSpecificationService;
import com.showka.system.exception.CanNotDeleteException;
import com.showka.system.exception.CanNotUpdateException;
import com.showka.system.exception.DuprecatedException;
import com.showka.system.exception.NotAllowedNumberException;
import com.showka.system.exception.NotMatchedException;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class ShukinValidateServiceImplTest extends SimpleTestCase {

	@Tested
	@Injectable
	private ShukinValidateServiceImpl service;

	@Injectable
	private ShukinCrudService shukinCrudService;

	@Injectable
	private NyukinKeshikomiSpecificationService nyukinKeshikomiSpecificationService;

	/**
	 * 金額1円の場合OK
	 */
	@Test
	public void test_Validate_01() throws Exception {
		// input
		// 金額
		AmountOfMoney kingaku = new AmountOfMoney(1);
		// 集金
		ShukinBuilder sb = new ShukinBuilder();
		sb.withKingaku(kingaku);
		Shukin shukin = sb.build();
		// do
		service.validate(shukin);
	}

	/**
	 * 金額0円の場合、エラー
	 */
	@Test(expected = NotAllowedNumberException.class)
	public void test_Validate_02() throws Exception {
		// input
		// 金額
		AmountOfMoney kingaku = new AmountOfMoney(0);
		// 集金
		ShukinBuilder sb = new ShukinBuilder();
		sb.withKingaku(kingaku);
		Shukin shukin = sb.build();
		// do
		service.validate(shukin);
	}

	/**
	 * 集金の担当社員の所属部署と入金部署が一致する場合、OK
	 */
	@Test
	public void test_ValidateTantoShainShozokuBusho_01() throws Exception {
		// input
		// 部署
		BushoBuilder bb = new BushoBuilder();
		bb.withCode("BS01");
		Busho busho = bb.build();
		// 担当社員
		ShainBuilder sb = new ShainBuilder();
		sb.withShozokuBusho(busho);
		Shain tantoShain = sb.build();
		// 集金
		ShukinBuilder shb = new ShukinBuilder();
		shb.withBusho(busho);
		shb.withTantoShain(tantoShain);
		Shukin shukin = shb.build();
		// do
		service.validateTantoShainShozokuBusho(shukin);
	}

	/**
	 * 集金の担当社員の所属部署と入金部署が一致しない場合、NG
	 */
	@Test(expected = NotMatchedException.class)
	public void test_ValidateTantoShainShozokuBusho_02() throws Exception {
		// input
		// 入金部署
		BushoBuilder bb1 = new BushoBuilder();
		bb1.withCode("BS01");
		Busho busho = bb1.build();
		// 所属部署
		BushoBuilder bb2 = new BushoBuilder();
		bb2.withCode("BS02");
		Busho shozokuBusho = bb2.build();
		// 担当社員
		ShainBuilder sb = new ShainBuilder();
		sb.withShozokuBusho(shozokuBusho);
		Shain tantoShain = sb.build();
		// 集金
		ShukinBuilder shb = new ShukinBuilder();
		shb.withBusho(busho);
		shb.withTantoShain(tantoShain);
		Shukin shukin = shb.build();
		// do
		service.validateTantoShainShozokuBusho(shukin);
	}

	/**
	 * 重複伝票なしの場合、OK
	 */
	@Test
	public void test_ValidateForRegister_01() throws Exception {
		// input
		// 顧客
		KokyakuBuilder kb = new KokyakuBuilder();
		kb.withCode("KK01");
		Kokyaku kokyaku = kb.build();
		// 入金日
		EigyoDate nyukinDate = new EigyoDate(2017, 8, 20);
		// 伝票番号
		String denpyoNumber = "00001";
		// 集金
		ShukinBuilder shb = new ShukinBuilder();
		shb.withKokyaku(kokyaku);
		shb.withDate(nyukinDate);
		shb.withDenpyoNumber(denpyoNumber);
		Shukin shukin = shb.build();
		// expect
		new Expectations() {
			{
				service.validateTantoShainShozokuBusho(shukin);
				shukinCrudService.exists(kokyaku, nyukinDate, denpyoNumber);
				result = false;
			}
		};
		// do
		service.validateForRegister(shukin);
		// verify
		new Verifications() {
			{
				service.validateTantoShainShozokuBusho(shukin);
				times = 1;
				shukinCrudService.exists(kokyaku, nyukinDate, denpyoNumber);
				times = 1;
			}
		};
	}

	/**
	 * 重複伝票ありの場合、NG
	 */
	@Test(expected = DuprecatedException.class)
	public void test_ValidateForRegister_02() throws Exception {
		// input
		// 顧客
		KokyakuBuilder kb = new KokyakuBuilder();
		kb.withCode("KK01");
		Kokyaku kokyaku = kb.build();
		// 入金日
		EigyoDate nyukinDate = new EigyoDate(2017, 8, 20);
		// 伝票番号
		String denpyoNumber = "00001";
		// 集金
		ShukinBuilder shb = new ShukinBuilder();
		shb.withKokyaku(kokyaku);
		shb.withDate(nyukinDate);
		shb.withDenpyoNumber(denpyoNumber);
		Shukin shukin = shb.build();
		// expect
		new Expectations() {
			{
				service.validateTantoShainShozokuBusho(shukin);
				shukinCrudService.exists(kokyaku, nyukinDate, denpyoNumber);
				result = true;
			}
		};
		// do
		service.validateForRegister(shukin);
		// verify
		new Verifications() {
			{
				service.validateTantoShainShozokuBusho(shukin);
				times = 1;
				shukinCrudService.exists(kokyaku, nyukinDate, denpyoNumber);
				times = 1;
			}
		};
	}

	/**
	 * 消込なしの場合、OK
	 */
	@Test
	public void test_ValidateForDelete_01() throws Exception {
		// input
		String nyukinId = "r-001";
		// 集金
		ShukinBuilder shb = new ShukinBuilder();
		shb.withRecordId(nyukinId);
		Shukin shukin = shb.build();
		// expect
		new Expectations() {
			{
				nyukinKeshikomiSpecificationService.hasKeshikomi(nyukinId);
				result = false;
			}
		};
		// do
		service.validateForDelete(shukin);
		// verify
		new Verifications() {
			{
				nyukinKeshikomiSpecificationService.hasKeshikomi(nyukinId);
				times = 1;
			}
		};
	}

	/**
	 * 消込ありの場合、OK
	 */
	@Test(expected = CanNotDeleteException.class)
	public void test_ValidateForDelete_02() throws Exception {
		// input
		// 入金ID
		String nyukinId = "r-001";
		// 集金
		ShukinBuilder shb = new ShukinBuilder();
		shb.withRecordId(nyukinId);
		Shukin shukin = shb.build();
		// expect
		new Expectations() {
			{
				nyukinKeshikomiSpecificationService.hasKeshikomi(nyukinId);
				result = true;
			}
		};
		// do
		service.validateForDelete(shukin);
	}

	/**
	 * 社員が変わった場合、変更後の社員の所属部署を検証することを確認。
	 */
	@Test
	public void test_ValidateForUpdate_01() throws Exception {
		// input
		// 入金ID
		String nyukinId = "r-001";
		// 担当社員1
		ShainBuilder sb1 = new ShainBuilder();
		sb1.withCode("SH01");
		Shain tantoShain1 = sb1.build();
		// 担当社員2（更新前）
		ShainBuilder sb2 = new ShainBuilder();
		sb2.withCode("SH02");
		Shain tantoShain2 = sb2.build();
		// 集金
		ShukinBuilder shb1 = new ShukinBuilder();
		shb1.withRecordId(nyukinId);
		shb1.withTantoShain(tantoShain1);
		Shukin shukin1 = shb1.build();
		// mock
		// 集金
		ShukinBuilder shb2 = new ShukinBuilder();
		shb2.withRecordId(nyukinId);
		shb2.withTantoShain(tantoShain2);
		Shukin old = shb2.build();
		// expect
		new Expectations() {
			{
				shukinCrudService.getDomain(nyukinId);
				result = old;
				service.validateTantoShainShozokuBusho(shukin1);
			}
		};
		// do
		service.validateForUpdate(shukin1);
		// verify
		new Verifications() {
			{
				shukinCrudService.getDomain(nyukinId);
				times = 1;
				service.validateTantoShainShozokuBusho(shukin1);
				times = 1;
			}
		};
	}

	/**
	 * 社員が変わらず、消込ありの場合、エラー
	 */
	@Test(expected = CanNotUpdateException.class)
	public void test_ValidateForUpdate_02() throws Exception {
		// input
		// 入金ID
		String nyukinId = "r-001";
		// 担当社員1
		ShainBuilder sb1 = new ShainBuilder();
		sb1.withCode("SH01");
		Shain tantoShain = sb1.build();
		// 集金
		ShukinBuilder shb1 = new ShukinBuilder();
		shb1.withRecordId(nyukinId);
		shb1.withTantoShain(tantoShain);
		Shukin shukin1 = shb1.build();
		// mock
		// 集金
		ShukinBuilder shb2 = new ShukinBuilder();
		shb2.withRecordId(nyukinId);
		shb2.withTantoShain(tantoShain);
		Shukin old = shb2.build();
		// expect
		new Expectations() {
			{
				shukinCrudService.getDomain(nyukinId);
				result = old;
				nyukinKeshikomiSpecificationService.hasKeshikomi(nyukinId);
				result = true;
			}
		};
		// do
		service.validateForUpdate(shukin1);
	}
}
