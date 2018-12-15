package com.showka.service.validator.u01;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.UriageBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u05.Uriage;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.KokyakuKubun;
import com.showka.repository.i.MKokyakuRepository;
import com.showka.service.query.u05.i.UriageQuery;
import com.showka.service.validator.u01.KokyakuValidatorImpl;
import com.showka.service.validator.u01.NyukinKakeInfoValidatorImpl;
import com.showka.system.exception.validate.CanNotUpdateOrDeleteException;
import com.showka.system.exception.validate.NotExistException;
import com.showka.system.exception.validate.ValidateException;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

/**
 * 顧客 Validate Service Test
 *
 * @author 25767
 *
 */
public class KokyakuValidatorImplTest extends SimpleTestCase {

	@Tested
	private KokyakuValidatorImpl service;

	@Injectable
	private MKokyakuRepository repo;

	@Injectable
	private NyukinKakeInfoValidatorImpl nyukinKakeInfoValidator;

	@Injectable
	private UriageQuery uriageQuery;

	@Test(expected = NotExistException.class)
	public void test_validateForRefer1() {
		// input
		String id = "KK99_dummy";
		// do
		service.validateForRefer(id);
	}

	@Test
	public void test_validateForRefer2() {
		// input
		String id = "KK01";
		// expect
		new Expectations() {
			{
				repo.existsById(id);
				result = true;
			}
		};
		// do
		service.validateForRefer(id);
		// verify
		new Verifications() {
			{
				repo.existsById(id);
				times = 1;
			}
		};
		// check
		assertTrue(true);
	}

	/**
	 * validateForRegister 顧客コードが既に使われているか検証する
	 *
	 * <pre>
	 * 入力：顧客domain <br>
	 * 条件：顧客コードが使われていない <br>
	 * 結果：成功
	 *
	 * <pre>
	 */
	@Test
	public void test_validateForRegister1(@Injectable Kokyaku domain) {
		// input
		String kokyakuCode = "KK01";
		// expect
		new Expectations() {
			{
				domain.getCode();
				result = kokyakuCode;
				repo.existsById(kokyakuCode);
				result = false;
			}
		};
		// do
		service.validateForRegister(domain);
		// verify
		new Verifications() {
			{
				domain.getCode();
				times = 1;
				repo.existsById(kokyakuCode);
				times = 1;
			}
		};
	}

	/**
	 * validateForRegister 顧客コードが既に使われているか検証する
	 *
	 * <pre>
	 * 入力：顧客domain <br>
	 * 条件：顧客コードが既に使われている <br>
	 * 結果：失敗
	 *
	 * <pre>
	 */
	@Test(expected = ValidateException.class)
	public void test_validateForRegister2(@Injectable Kokyaku domain) {
		// input
		String kokyakuCode = "KK01";
		// expect
		new Expectations() {
			{
				domain.getCode();
				result = kokyakuCode;
				repo.existsById(kokyakuCode);
				result = true;
			}
		};
		// do
		service.validateForRegister(domain);
		// verify
		new Verifications() {
			{
				domain.getCode();
				times = 1;
				repo.existsById(kokyakuCode);
				times = 1;
			}
		};
	}

	/**
	 * validate 個人顧客に掛売が設定されていないか検証する
	 *
	 * <pre>
	 * 入力：顧客domain <br>
	 * 条件：個人顧客に現金が設定されている <br>
	 * 結果：成功
	 *
	 * <pre>
	 */
	@Test
	public void test_validate1(@Injectable Kokyaku domain) {
		// expect
		new Expectations() {
			{
				domain.getKokyakuKubun();
				result = KokyakuKubun.個人;
				domain.getHanbaiKubun();
				result = HanbaiKubun.現金;
			}
		};
		// do
		service.validate(domain);
		// verify
		new Verifications() {
			{
				domain.getKokyakuKubun();
				times = 1;
				domain.getHanbaiKubun();
				times = 1;
			}
		};
	}

	/**
	 * validate 個人顧客に掛売が設定されていないか検証する
	 *
	 * <pre>
	 * 入力：顧客domain <br>
	 * 条件：法人顧客 <br>
	 * 結果：成功
	 *
	 * <pre>
	 */
	@Test
	public void test_validate2(@Injectable Kokyaku domain) {
		// expect
		new Expectations() {
			{
				domain.getKokyakuKubun();
				result = KokyakuKubun.法人;
				domain.getHanbaiKubun();
				result = HanbaiKubun.現金;
			}
		};
		// do
		service.validate(domain);
		// verify
		new Verifications() {
			{
				domain.getKokyakuKubun();
				times = 1;
				domain.getHanbaiKubun();
				times = 1;
			}
		};
	}

	/**
	 * validate 個人顧客に掛売が設定されていないか検証する
	 *
	 * <pre>
	 * 入力：顧客domain <br>
	 * 条件：個人顧客に掛売が設定されている <br>
	 * 結果：失敗
	 *
	 * <pre>
	 */
	@Test(expected = ValidateException.class)
	public void test_validate3(@Injectable Kokyaku domain) {
		// expect
		new Expectations() {
			{
				domain.getKokyakuKubun();
				result = KokyakuKubun.個人;
				domain.getHanbaiKubun();
				result = HanbaiKubun.掛売;
			}
		};
		// do
		service.validate(domain);
		// verify
		new Verifications() {
			{
				domain.getKokyakuKubun();
				times = 1;
				domain.getHanbaiKubun();
				times = 1;
			}
		};
	}

	@Test(expected = CanNotUpdateOrDeleteException.class)
	public void test_validateForDelete1() throws Exception {
		// expect
		List<Uriage> uriageList = new ArrayList<Uriage>();
		uriageList.add(new UriageBuilder().build());
		new Expectations() {
			{
				uriageQuery.get("KK01");
				result = uriageList;
			}
		};
		// do
		service.validateForDelete("KK01");
	}

	@Test
	public void test_validateForDelete2() throws Exception {
		// expect
		List<Uriage> uriageList = new ArrayList<Uriage>();
		new Expectations() {
			{
				uriageQuery.get("KK01");
				result = uriageList;
			}
		};
		// do
		service.validateForDelete("KK01");
		// check
		assertTrue(true);
	}
}
