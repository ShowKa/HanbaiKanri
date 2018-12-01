package com.showka.service.validator.u05;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.KeshikomiBuilder;
import com.showka.domain.builder.KokyakuBuilder;
import com.showka.domain.builder.UriageBuilder;
import com.showka.domain.builder.UriageMeisaiBuilder;
import com.showka.domain.builder.UrikakeBuilder;
import com.showka.domain.builder.UrikakeKeshikomiBuilder;
import com.showka.domain.u01.Kokyaku;
import com.showka.domain.u05.Uriage;
import com.showka.domain.u05.UriageMeisai;
import com.showka.domain.u06.Urikake;
import com.showka.domain.u06.UrikakeKeshikomi;
import com.showka.domain.u08.Keshikomi;
import com.showka.domain.z00.Busho;
import com.showka.entity.TUriagePK;
import com.showka.kubun.HanbaiKubun;
import com.showka.kubun.KokyakuKubun;
import com.showka.repository.i.CUriageRepository;
import com.showka.service.crud.u05.i.UriageCrud;
import com.showka.service.crud.u06.i.UrikakeCrud;
import com.showka.service.query.u05.i.UriageKeijoQuery;
import com.showka.service.query.u06.i.UrikakeKeshikomiQuery;
import com.showka.service.query.z00.i.BushoDateQuery;
import com.showka.service.validator.u05.i.UriageMeisaiValidator;
import com.showka.system.exception.AlreadyExistsException;
import com.showka.system.exception.CanNotUpdateOrDeleteException;
import com.showka.system.exception.EmptyException;
import com.showka.system.exception.NotEigyoDateException;
import com.showka.value.EigyoDate;
import com.showka.value.TaxRate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class UriageValidatorImplTest extends SimpleTestCase {

	// tested
	@Tested
	@Injectable
	private UriageValidatorImpl service;

	@Injectable
	private CUriageRepository cUriageRepository;

	@Injectable
	private UriageCrud uriageCrud;

	@Injectable
	private UrikakeCrud urikakeCrud;

	@Injectable
	private UriageKeijoQuery uriageKeijoQuery;

	@Injectable
	private UrikakeKeshikomiQuery urikakeKeshikomiQuery;

	@Injectable
	private BushoDateQuery bushoDateBusinessService;

	@Injectable
	private UriageMeisaiValidator uriageMeisaiValidator;

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

	// 明細がない場合エラー
	@Test(expected = EmptyException.class)
	public void test_validateMeisaiSize_01() {
		// input
		UriageBuilder b = new UriageBuilder();
		b.withUriageMeisai(new ArrayList<>());
		Uriage d = b.build();
		// do
		service.validateMeisaiSize(d);
	}

	// 明細がある場合、正常終了
	@Test
	public void test_validateMeisaiSize_02() {
		UriageMeisai meisai = new UriageMeisaiBuilder().build();
		List<UriageMeisai> meisaiList = new ArrayList<>();
		meisaiList.add(meisai);
		// input
		UriageBuilder b = new UriageBuilder();
		b.withUriageMeisai(meisaiList);
		Uriage d = b.build();
		// do
		service.validateMeisaiSize(d);
	}

	// すでに同じ番号がある場合エラー
	@Test(expected = AlreadyExistsException.class)
	public void test_validateExistsSameNumber_01() {
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId(uriage01.getKokyaku().getRecordId());
		pk.setDenpyoNumber(uriage01.getDenpyoNumber());
		// expectation
		new Expectations() {
			{
				uriageCrud.exists(pk);
				result = true;
			}
		};
		// do
		service.validateExistsSameNumber(uriage01);
	}

	// 同じ番号が未登録の場合、正常終了
	@Test
	public void test_validateExistsSameNumber_02() {
		TUriagePK pk = new TUriagePK();
		pk.setKokyakuId(uriage01.getKokyaku().getRecordId());
		pk.setDenpyoNumber(uriage01.getDenpyoNumber());
		// expectation
		new Expectations() {
			{
				uriageCrud.exists(pk);
				result = false;
			}
		};
		// do
		service.validateExistsSameNumber(uriage01);
		// verification
		new Verifications() {
			{
				uriageCrud.exists(pk);
				times = 1;
			}
		};
	}

	// すでにキャンセル済みの場合はエラー
	@Test(expected = CanNotUpdateOrDeleteException.class)
	public void test_validateCanceld_01() throws Exception {
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
		service.validateCanceld(domain);
	}

	// まだキャンセルしてなければOK
	@Test
	public void test_validateCanceld_02() throws Exception {
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
		service.validateCanceld(domain);
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
	 * 計上済みの場合エラー
	 * 
	 * @throws Exception
	 */
	@Test(expected = CanNotUpdateOrDeleteException.class)
	public void test_validateKeijo_01() throws Exception {
		// input
		String uriageId = "r-KK01-00001";
		UriageBuilder b = new UriageBuilder();
		b.withRecordId(uriageId);
		Uriage domain = b.build();
		// expect
		new Expectations() {
			{
				uriageKeijoQuery.hasDone(domain);
				result = true;
			}
		};
		// do
		service.validateKeijo(domain);
	}

	// 計上済でない、キャンセル済でない場合OK
	@Test
	public void test_validateKeijo_02() throws Exception {
		// input
		String uriageId = "r-KK01-00001";
		UriageBuilder b = new UriageBuilder();
		b.withRecordId(uriageId);
		Uriage domain = b.build();
		// expect
		new Expectations() {
			{
				uriageKeijoQuery.hasDone(domain);
				result = false;
			}
		};
		// do
		service.validateKeijo(domain);
		// verify
		new Verifications() {
			{
				uriageKeijoQuery.hasDone(domain);
				times = 1;
			}
		};
	}

	// 売上日=営業日の場合OK
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

	// 売上日 != 営業日の場合、エラー
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

	// 売掛なし(=消込なし)の場合、正常終了
	@Test
	public void test_ValidateHasKeshikomi_01() throws Exception {
		// input
		// 売上
		String uriageId = "r-KK01-00001";
		UriageBuilder ub = new UriageBuilder();
		ub.withRecordId(uriageId);
		Uriage uriage = ub.build();
		// mock
		// expect
		new Expectations() {
			{
				urikakeCrud.exists(uriageId);
				result = false;
			}
		};
		// do
		service.validateHasKeshikomi(uriage);
		// verify
		new Verifications() {
			{
				urikakeCrud.exists(uriageId);
				times = 1;
			}
		};
	}

	// 消込ありの場合、エラー
	@Test(expected = CanNotUpdateOrDeleteException.class)
	public void test_ValidateHasKeshikomi_02() throws Exception {
		// input
		// 売上
		String uriageId = "r-KK01-00001";
		UriageBuilder ub = new UriageBuilder();
		ub.withRecordId(uriageId);
		Uriage uriage = ub.build();
		// mock
		// 売掛
		UrikakeBuilder kb = new UrikakeBuilder();
		String urikakeId = "r-KK01-00001";
		kb.withRecordId(urikakeId);
		Urikake urikake = kb.build();
		// 消込
		UrikakeKeshikomiBuilder ukb = new UrikakeKeshikomiBuilder();
		Set<Keshikomi> keshikomiSet = new HashSet<>();
		Keshikomi k = new KeshikomiBuilder().build();
		keshikomiSet.add(k);
		ukb.withKeshikomiSet(keshikomiSet);
		UrikakeKeshikomi urikakeKeshikomi = ukb.build();
		// expect
		new Expectations() {
			{
				urikakeCrud.exists(uriageId);
				result = true;
				urikakeCrud.getDomain(uriageId);
				result = urikake;
				urikakeKeshikomiQuery.get(urikakeId);
				result = urikakeKeshikomi;
			}
		};
		// do
		service.validateHasKeshikomi(uriage);
	}

	// 消込なしの場合、正常終了
	@Test
	public void test_ValidateHasKeshikomi_03() throws Exception {
		// input
		// 売上
		String uriageId = "r-KK01-00001";
		UriageBuilder ub = new UriageBuilder();
		ub.withRecordId(uriageId);
		Uriage uriage = ub.build();
		// mock
		// 売掛
		UrikakeBuilder kb = new UrikakeBuilder();
		String urikakeId = "r-KK01-00001";
		kb.withRecordId(urikakeId);
		Urikake urikake = kb.build();
		// 消込
		UrikakeKeshikomiBuilder ukb = new UrikakeKeshikomiBuilder();
		ukb.withKeshikomiSet(new HashSet<>());
		UrikakeKeshikomi urikakeKeshikomi = ukb.build();
		// expect
		new Expectations() {
			{
				urikakeCrud.exists(uriageId);
				result = true;
				urikakeCrud.getDomain(uriageId);
				result = urikake;
				urikakeKeshikomiQuery.get(urikakeId);
				result = urikakeKeshikomi;
			}
		};
		// do
		service.validateHasKeshikomi(uriage);
		// verify
		new Verifications() {
			{
				urikakeCrud.exists(uriageId);
				times = 1;
				urikakeCrud.getDomain(uriageId);
				times = 1;
				urikakeKeshikomiQuery.get(urikakeId);
				times = 1;
			}
		};
	}

}
