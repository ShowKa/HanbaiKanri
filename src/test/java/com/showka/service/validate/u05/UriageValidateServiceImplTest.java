package com.showka.service.validate.u05;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.KokyakuDomain;
import com.showka.domain.UriageDomain;
import com.showka.domain.UriageMeisaiDomain;
import com.showka.domain.builder.KokyakuDomainBuilder;
import com.showka.domain.builder.UriageDomainBuilder;
import com.showka.domain.builder.UriageMeisaiDomainBuilder;
import com.showka.entity.TUriagePK;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.KokyakuKubun;
import com.showka.repository.i.CUriageRepository;
import com.showka.repository.i.TUriageRepository;
import com.showka.service.specification.u05.i.UriageKeijoSpecificationService;
import com.showka.service.validate.u05.i.UriageMeisaiValidateService;
import com.showka.system.exception.AlreadyExistsException;
import com.showka.system.exception.CanNotUpdateException;
import com.showka.system.exception.EmptyException;
import com.showka.value.TaxRate;
import com.showka.value.TheDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class UriageValidateServiceImplTest extends SimpleTestCase {

	// tested
	@Tested
	private UriageValidateServiceImpl service;

	@Injectable
	private UriageMeisaiValidateService meisaiService;

	@Injectable
	private TUriageRepository uriageRepo;

	@Injectable
	private CUriageRepository cUriageRepository;

	@Injectable
	private UriageKeijoSpecificationService uriageKeijoSpecificationService;

	// data
	/** 顧客01. */
	public static final KokyakuDomain kokyaku01;
	static {
		KokyakuDomainBuilder b = new KokyakuDomainBuilder();
		kokyaku01 = b.withCode("KK01")
				.withName("顧客01")
				.withCode("左京区")
				.withKokyakuKubun(KokyakuKubun.法人)
				.withHanbaiKubun(HanbaiKubun.現金)
				.withRecordId("KK01")
				.build();
	}
	/** 売上明細01. */
	public static final UriageMeisaiDomain uriageMeisai01;
	static {
		UriageMeisaiDomainBuilder b = new UriageMeisaiDomainBuilder();
		uriageMeisai01 = b.withUriageId("KK01-00001")
				.withMeisaiNumber(1)
				.withHanbaiNumber(5)
				.withHanbaiTanka(BigDecimal.valueOf(1000))
				.withRecordId("KK01-00001-1")
				.build();
	}
	/** 売上01. */
	public static final UriageDomain uriage01;
	static {
		UriageDomainBuilder b = new UriageDomainBuilder();
		ArrayList<UriageMeisaiDomain> meisai = new ArrayList<UriageMeisaiDomain>();
		meisai.add(uriageMeisai01);
		uriage01 = b.withKokyaku(kokyaku01)
				.withDenpyoNumber("00001")
				.withUriageDate(new TheDate(2017, 8, 20))
				.withHanbaiKubun(HanbaiKubun.現金)
				.withShohizeiritsu(new TaxRate(0.08))
				.withUriageMeisai(meisai)
				.withRecordId("KK01-00001")
				.build();
	}

	// test
	@Test(expected = EmptyException.class)
	public void test_validate01() {
		UriageDomainBuilder b = new UriageDomainBuilder();
		UriageDomain d = b.build();
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
				uriageRepo.existsById(pk);
				result = false;
			}
		};

		// do
		service.validateForRegister(uriage01);

		// verification
		new Verifications() {
			{
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
	@Test(expected = CanNotUpdateException.class)
	public void test_validateForUpdate01() throws Exception {
		// input
		String uriageId = "r-KK01-00001";
		UriageDomainBuilder b = new UriageDomainBuilder();
		b.withRecordId(uriageId);
		UriageDomain domain = b.build();
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
		UriageDomainBuilder b = new UriageDomainBuilder();
		b.withRecordId(uriageId);
		UriageDomain domain = b.build();
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
	@Test(expected = CanNotUpdateException.class)
	public void test_validateForDelete01() throws Exception {
		// input
		String uriageId = "r-KK01-00001";
		UriageDomainBuilder b = new UriageDomainBuilder();
		b.withRecordId(uriageId);
		UriageDomain domain = b.build();
		// expect
		new Expectations() {
			{
				cUriageRepository.existsById(uriageId);
				result = true;
			}
		};
		// do
		service.validateForDelete(domain);
	}

	/**
	 * 計上済みの場合エラー
	 * 
	 * @throws Exception
	 */
	@Test(expected = CanNotUpdateException.class)
	public void test_validateForDelete02() throws Exception {
		// input
		String uriageId = "r-KK01-00001";
		UriageDomainBuilder b = new UriageDomainBuilder();
		b.withRecordId(uriageId);
		UriageDomain domain = b.build();
		// expect
		new Expectations() {
			{
				cUriageRepository.existsById(uriageId);
				result = false;
				uriageKeijoSpecificationService.isKeijoZumi(domain);
				result = true;
			}
		};
		// do
		service.validateForDelete(domain);
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
		UriageDomainBuilder b = new UriageDomainBuilder();
		b.withRecordId(uriageId);
		UriageDomain domain = b.build();
		// expect
		new Expectations() {
			{
				cUriageRepository.existsById(uriageId);
				result = false;
				uriageKeijoSpecificationService.isKeijoZumi(domain);
				result = false;
			}
		};
		// do
		service.validateForDelete(domain);
		// verify
		new Verifications() {
			{
				cUriageRepository.existsById(uriageId);
				times = 1;
				uriageKeijoSpecificationService.isKeijoZumi(domain);
				times = 1;
			}
		};
	}

}
