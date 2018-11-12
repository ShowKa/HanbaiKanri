package com.showka.service.crud.u06;

import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.CrudServiceTestCase;
import com.showka.domain.builder.SeikyuBuilder;
import com.showka.domain.u07.Seikyu;
import com.showka.entity.SUrikakeSeikyuDone;
import com.showka.entity.SUrikakeSeikyuNotYet;
import com.showka.repository.i.SUrikakeSeikyuDoneRepository;
import com.showka.repository.i.SUrikakeSeikyuNotYetRepository;
import com.showka.service.search.u07.i.SeikyuSearchService;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class UrikakeSeikyuStatusCrudServiceImplTest extends CrudServiceTestCase {

	@Tested
	@Injectable
	private UrikakeSeikyuStatusCrudServiceImpl service;

	@Autowired
	@Injectable
	private SUrikakeSeikyuNotYetRepository seikyuNotYetRepo;

	@Autowired
	@Injectable
	private SUrikakeSeikyuDoneRepository seikyuDoneRepo;

	@Injectable
	private SeikyuSearchService seikyuSearchService;

	/** 売掛未請求状態. */
	private static final Object[] V1 = { "r-001", "r-001" };

	/** 売掛請求済状態. */
	private static final Object[] V2 = { "r-001", "r-KK01-20180920", "r-001" };

	// 未請求状態テーブルにレコードが登録されること。
	@Test
	public void test_ToNotYet_01() throws Exception {
		// database
		super.deleteAll(S_URIKAKE_SEIKYU_NOT_YET);
		// input
		String urikakeId = "r-001";
		// do
		service.toNotYet(urikakeId);
		// check
		SUrikakeSeikyuNotYet actual = seikyuNotYetRepo.getOne(urikakeId);
		assertEquals(urikakeId, actual.getUrikakeId());
		assertEquals(urikakeId, actual.getRecordId());
	}

	// 未請求状態テーブルにレコードが更新されること。
	@Test
	public void test_ToNotYet_02() throws Exception {
		// database
		super.deleteAndInsert(S_URIKAKE_SEIKYU_NOT_YET, S_URIKAKE_SEIKYU_NOT_YET_COLUMN, V1);
		// input
		String urikakeId = "r-001";
		// do
		service.toNotYet(urikakeId);
		// check
		SUrikakeSeikyuNotYet actual = seikyuNotYetRepo.getOne(urikakeId);
		assertEquals(urikakeId, actual.getUrikakeId());
		assertEquals(urikakeId, actual.getRecordId());
	}

	// 未請求状態テーブルにレコードが削除されること（存在すれば）
	@Test
	public void test_DeleteNotYetIfExists_01() throws Exception {
		// database
		super.deleteAndInsert(S_URIKAKE_SEIKYU_NOT_YET, S_URIKAKE_SEIKYU_NOT_YET_COLUMN, V1);
		// input
		String urikakeId = "r-001";
		// do
		service.deleteNotYetIfExists(urikakeId);
		// check
		boolean actual = seikyuNotYetRepo.existsById(urikakeId);
		assertFalse(actual);
	}

	// 正常終了すればOK（レコードがないので何もしない）
	@Test
	public void test_DeleteNotYetIfExists_02() throws Exception {
		// database
		super.deleteAll(S_URIKAKE_SEIKYU_NOT_YET);
		// input
		String urikakeId = "r-001";
		// do
		service.deleteNotYetIfExists(urikakeId);
	}

	// 請求済状態レコードが登録される。
	@Test
	public void test_ToDone_01() throws Exception {
		// database
		super.deleteAll(S_URIKAKE_SEIKYU_DONE);
		// input
		String urikakeId = "r-001";
		String seikyuId = "r-KK01-20180920";
		// expect
		new Expectations() {
			{
				service.deleteNotYetIfExists(urikakeId);
			}
		};
		// do
		service.toDone(urikakeId, seikyuId);
		// verify
		new Verifications() {
			{
				service.deleteNotYetIfExists(urikakeId);
				times = 1;
			}
		};
		// check
		SUrikakeSeikyuDone actual = seikyuDoneRepo.getOne(urikakeId);
		assertEquals(seikyuId, actual.getSeikyuId());
	}

	// 請求済状態レコードが更新される(請求IDが変わる)。
	@Test
	public void test_ToDone_02() throws Exception {
		// database
		super.deleteAndInsert(S_URIKAKE_SEIKYU_DONE, S_URIKAKE_SEIKYU_DONE_COLUMN, V2);
		// input
		String urikakeId = "r-001";
		String seikyuId = "r-KK02-29990101";
		// expect
		new Expectations() {
			{
				service.deleteNotYetIfExists(urikakeId);
			}
		};
		// do
		service.toDone(urikakeId, seikyuId);
		// verify
		new Verifications() {
			{
				service.deleteNotYetIfExists(urikakeId);
				times = 1;
			}
		};
		// check
		SUrikakeSeikyuDone actual = seikyuDoneRepo.getOne(urikakeId);
		assertEquals(seikyuId, actual.getSeikyuId());
	}

	// すでに請求済状態の場合、復活処理は途中終了する。
	@Test
	public void test_Revert_01() throws Exception {
		// database
		super.deleteAndInsert(S_URIKAKE_SEIKYU_DONE, S_URIKAKE_SEIKYU_DONE_COLUMN, V2);
		// input
		String urikakeId = "r-001";
		// do
		service.revert(urikakeId);
		// verify
		new Verifications() {
			{
				service.toDone(anyString, anyString);
				times = 0;
			}
		};
	}

	// 請求済状態ではない&請求の履歴が存在しない場合、未請求状態となる。
	@Test
	public void test_Revert_02() throws Exception {
		// database
		super.deleteAll(S_URIKAKE_SEIKYU_DONE);
		// input
		String urikakeId = "r-001";
		// expect
		new Expectations() {
			{
				seikyuSearchService.getNewestOf(urikakeId);
				result = Optional.empty();
				service.toNotYet(urikakeId);
				times = 1;
			}
		};
		// do
		service.revert(urikakeId);
		// verify
		new Verifications() {
			{
				seikyuSearchService.getNewestOf(urikakeId);
				times = 1;
			}
		};
	}

	// 請求済状態への復帰処理が実施される。
	@Test
	public void test_Revert_03() throws Exception {
		// database
		super.deleteAll(S_URIKAKE_SEIKYU_DONE);
		// input
		String urikakeId = "r-001";
		// mock
		SeikyuBuilder sb = new SeikyuBuilder();
		String seikyuId = "r-KK01-20180820";
		sb.withRecordId(seikyuId);
		Seikyu seikyu = sb.build();
		// expect
		new Expectations() {
			{
				seikyuSearchService.getNewestOf(urikakeId);
				result = Optional.of(seikyu);
				service.toDone(urikakeId, seikyuId);
			}
		};
		// do
		service.revert(urikakeId);
		// verify
		new Verifications() {
			{
				seikyuSearchService.getNewestOf(urikakeId);
				times = 1;
				service.toDone(urikakeId, seikyuId);
				times = 1;
			}
		};
	}

	// 請求済状態テーブルのレコードが削除されること（存在すれば）
	@Test
	public void test_deleteDoneIfExists_01() throws Exception {
		// database
		super.deleteAndInsert(S_URIKAKE_SEIKYU_DONE, S_URIKAKE_SEIKYU_DONE_COLUMN, V2);
		// input
		String urikakeId = "r-001";
		// do
		service.deleteDoneIfExists(urikakeId);
		// check
		boolean actual = seikyuDoneRepo.existsById(urikakeId);
		assertFalse(actual);
	}

	// 正常終了すればOK（レコードがないので何もしない）
	@Test
	public void test_deleteDoneIfExists_02() throws Exception {
		// database
		super.deleteAll(S_URIKAKE_SEIKYU_DONE);
		// input
		String urikakeId = "r-001";
		// do
		service.deleteDoneIfExists(urikakeId);
	}

	@Test
	public void test_ToSettled_01() throws Exception {
		// input
		String urikakeId = "r-001";
		// expect
		new Expectations() {
			{
				service.deleteNotYetIfExists(urikakeId);
				service.deleteDoneIfExists(urikakeId);
			}
		};
		// do
		service.toSettled(urikakeId);
		// verify
		new Verifications() {
			{
				service.deleteNotYetIfExists(urikakeId);
				times = 1;
				service.deleteDoneIfExists(urikakeId);
				times = 1;
			}
		};
	}
}
