package com.showka.service.validate.u05;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.KokyakuBuilder;
import com.showka.domain.builder.UriageBuilder;
import com.showka.domain.builder.UriageMeisaiBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u05.Uriage;
import com.showka.domain.u05.UriageMeisai;
import com.showka.domain.z00.Busho;
import com.showka.entity.TUriagePK;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.KokyakuKubun;
import com.showka.repository.i.CUriageRepository;
import com.showka.repository.i.TUriageRepository;
import com.showka.service.persistence.u05.i.UriagePersistence;
import com.showka.service.specification.u05.i.UriageKeijoSpecificationService;
import com.showka.service.specification.z00.i.BushoDateBusinessService;
import com.showka.service.validate.u05.i.UriageMeisaiValidateService;
import com.showka.system.exception.AlreadyExistsException;
import com.showka.system.exception.CanNotUpdateOrDeleteException;
import com.showka.system.exception.EmptyException;
import com.showka.system.exception.NotEigyoDateException;
import com.showka.system.exception.ValidateException;
import com.showka.value.EigyoDate;
import com.showka.value.TaxRate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class UriageValidateServiceImplTest extends SimpleTestCase {

	// tested
	@Tested
	@Injectable
	private UriageValidateServiceImpl service;

	@Injectable
	private UriagePersistence uriagePersistence;

	@Injectable
	private UriageMeisaiValidateService meisaiService;

	@Injectable
	private TUriageRepository uriageRepo;

	@Injectable
	private CUriageRepository cUriageRepository;

	@Injectable
	private UriageKeijoSpecificationService uriageKeijoSpecificationService;

	@Injectable
	private BushoDateBusinessService bushoDateBusinessService;

	// data
	/** 顧客01. */
	public static final Kokyaku kokyaku01;
	static {
		KokyakuBuilder b = new KokyakuBuilder();
		kokyaku01 = b.withCode("KK01")
				.withName("顧客01")
				.withCode("左京区")
				.withKokyakuKubun(KokyakuKubun.法人)
				.withHanbaiKubun(HanbaiKubun.現金)
				.withRecordId("KK01")
				.build();
	}
	/** 売上明細01. */
	public static final UriageMeisai uriageMeisai01;
	static {
		UriageMeisaiBuilder b = new UriageMeisaiBuilder();
		uriageMeisai01 = b.withMeisaiNumber(1)
				.withHanbaiNumber(5)
				.withHanbaiTanka(BigDecimal.valueOf(1000))
				.withRecordId("KK01-00001-1")
				.build();
	}
	/** 売上01. */
	public static final Uriage uriage01;
	static {
		UriageBuilder b = new UriageBuilder();
		ArrayList<UriageMeisai> meisai = new ArrayList<UriageMeisai>();
		meisai.add(uriageMeisai01);
		uriage01 = b.withKokyaku(kokyaku01)
				.withDenpyoNumber("00001")
				.withUriageDate(new EigyoDate(2017, 8, 20))
				.withHanbaiKubun(HanbaiKubun.現金)
				.withShohizeiritsu(new TaxRate(0.08))
				.withUriageMeisai(meisai)
				.withRecordId("KK01-00001")
				.build();
	}

	// test
	@Test(expected = EmptyException.class)
	public void test_validate01() {
		// input
		UriageBuilder b = new UriageBuilder();
		Uriage d = b.build();
		// do
		service.validate(d);
	}

	@Test
	public void test_validate02() {
		// expectation
		new Expectations() {
			{
				meisaiService.validate(uriageMeisai01);
			}
		};
		// do
		service.validate(uriage01);
		// verification
		new Verifications() {
			{
				meisaiService.validate(uriageMeisai01);
				times = 1;
			}
		};
	}

	@Test(expected = AlreadyExistsException.class)
	public void test_validateForRegister01() {
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId(uriage01.getKokyaku().getRecordId());
		pk.setDenpyoNumber(uriage01.getDenpyoNumber());
		// expectation
		new Expectations() {
			{
				service.validateUriageDate(uriage01);
				uriageRepo.existsById(pk);
				result = true;
			}
		};
		// do
		service.validateForRegister(uriage01);
	}

	@Test
	public void test_validateForRegister02() {
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId(uriage01.getKokyaku().getRecordId());
		pk.setDenpyoNumber(uriage01.getDenpyoNumber());
		// expectation
		new Expectations() {
			{
				service.validateUriageDate(uriage01);
				uriageRepo.existsById(pk);
				result = false;
			}
		};
		// do
		service.validateForRegister(uriage01);
		// verification
		new Verifications() {
			{
				service.validateUriageDate(uriage01);
				times = 1;
				uriageRepo.existsById(pk);
				times = 1;
			}
		};
	}

	/**
	 * すでにキャンセル済みの場合はエラー
	 * 
	 * @throws Exception
	 */
	@Test(expected = CanNotUpdateOrDeleteException.class)
	public void test_validateForUpdate01() throws Exception {
		// input
		String uriageId = "r-KK01-00001";
		UriageBuilder b = new UriageBuilder();
		b.withRecordId(uriageId);
		Uriage domain = b.build();
		// expect
		new Expectations() {
			{
				cUriageRepository.existsById(uriageId);
				result = true;
			}
		};
		// do
		service.validateForUpdate(domain);
	}

	/**
	 * まだキャンセルしてなければOK
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_validateForUpdate02() throws Exception {
		// input
		String uriageId = "r-KK01-00001";
		UriageBuilder b = new UriageBuilder();
		b.withRecordId(uriageId);
		Uriage domain = b.build();
		// expect
		new Expectations() {
			{
				cUriageRepository.existsById(uriageId);
				result = false;
			}
		};
		// do
		service.validateForUpdate(domain);
		// verify
		new Verifications() {
			{
				cUriageRepository.existsById(uriageId);
				times = 1;
			}
		};
		// assert
		assertTrue(true);
	}

	/**
	 * キャンセル済みの場合エラー
	 * 
	 * @throws Exception
	 */
	@Test(expected = CanNotUpdateOrDeleteException.class)
	public void test_validateForDelete01() throws Exception {
		// input
		String uriageId = "r-KK01-00001";
		UriageBuilder b = new UriageBuilder();
		b.withRecordId(uriageId);
		Uriage domain = b.build();
		// pk
		TUriagePK pk = new TUriagePK("KK01", "00001");
		// expect
		new Expectations() {
			{
				uriagePersistence.getDomain(pk);
				result = domain;
				cUriageRepository.existsById(uriageId);
				result = true;
			}
		};
		// do
		service.validateForDelete(pk);
	}

	/**
	 * 計上済みの場合エラー
	 * 
	 * @throws Exception
	 */
	@Test(expected = CanNotUpdateOrDeleteException.class)
	public void test_validateForDelete02() throws Exception {
		// input
		String uriageId = "r-KK01-00001";
		UriageBuilder b = new UriageBuilder();
		b.withRecordId(uriageId);
		Uriage domain = b.build();
		// pk
		TUriagePK pk = new TUriagePK("KK01", "00001");
		// expect
		new Expectations() {
			{
				uriagePersistence.getDomain(pk);
				result = domain;
				cUriageRepository.existsById(uriageId);
				result = false;
				uriageKeijoSpecificationService.isKeijoZumi(domain);
				result = true;
			}
		};
		// do
		service.validateForDelete(pk);
	}

	/**
	 * 計上済でない、キャンセル済でない場合OK
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_validateForDelete03() throws Exception {
		// input
		String uriageId = "r-KK01-00001";
		UriageBuilder b = new UriageBuilder();
		b.withRecordId(uriageId);
		Uriage domain = b.build();
		// pk
		TUriagePK pk = new TUriagePK("KK01", "00001");
		// expect
		new Expectations() {
			{
				uriagePersistence.getDomain(pk);
				result = domain;
				cUriageRepository.existsById(uriageId);
				result = false;
				uriageKeijoSpecificationService.isKeijoZumi(domain);
				result = false;
			}
		};
		// do
		service.validateForDelete(pk);
		// verify
		new Verifications() {
			{
				uriagePersistence.getDomain(pk);
				times = 1;
				cUriageRepository.existsById(uriageId);
				times = 1;
				uriageKeijoSpecificationService.isKeijoZumi(domain);
				times = 1;
			}
		};
	}

	@Test(expected = ValidateException.class)
	public void test_ValidateForCancel01() throws Exception {
		// input
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId("KK01");
		pk.setDenpyoNumber("00001");
		// expect
		new Expectations() {
			{
				uriagePersistence.getDomain(pk);
				result = uriage01;
				service.validateForUpdate(uriage01);
				result = new ValidateException("");
			}
		};
		// do
		service.validateForCancel(pk);
		fail();
	}

	@Test
	public void test_ValidateForCancel02() throws Exception {
		// input
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId("KK01");
		pk.setDenpyoNumber("00001");
		// expect
		new Expectations() {
			{
				uriagePersistence.getDomain(pk);
				result = uriage01;
				service.validateForUpdate(uriage01);
			}
		};
		// do
		service.validateForCancel(pk);
		assertTrue(true);
	}

	@Test
	public void test_ValidateUriageDate_01() throws Exception {
		// expect
		new Expectations() {
			{
				bushoDateBusinessService.isEigyoDate((Busho) any, uriage01.getUriageDate());
				result = true;
			}
		};
		// do
		service.validateUriageDate(uriage01);
		// verify
		new Verifications() {
			{
				bushoDateBusinessService.isEigyoDate((Busho) any, uriage01.getUriageDate());
				times = 1;
			}
		};
		// check
		assertTrue(true);
	}

	@Test(expected = NotEigyoDateException.class)
	public void test_ValidateUriageDate_02() throws Exception {
		// expect
		new Expectations() {
			{
				bushoDateBusinessService.isEigyoDate((Busho) any, uriage01.getUriageDate());
				result = false;
			}
		};
		// do
		service.validateUriageDate(uriage01);
	}

}
