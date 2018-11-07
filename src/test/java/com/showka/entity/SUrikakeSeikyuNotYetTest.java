package com.showka.entity;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.CrudServiceTestCase;
import com.showka.domain.builder.UriageBuilder;
import com.showka.domain.builder.UrikakeBuilder;
import com.showka.domain.u05.Uriage;
import com.showka.domain.u06.Urikake;
import com.showka.repository.i.SUrikakeSeikyuNotYetRepository;
import com.showka.service.crud.u06.UrikakeCrudServiceImpl;

public class SUrikakeSeikyuNotYetTest extends CrudServiceTestCase {

	@Autowired
	private UrikakeCrudServiceImpl service;

	@Autowired
	private SUrikakeSeikyuNotYetRepository urikakeSeikyurepo;

	// 売掛未請求状態
	private static final Object[] V1 = { "r-001", "r-001" };

	// 売掛
	protected static final Object[] V2 = { "r-001", 100, d("20180820"), "r-001" };

	// 参照元レコードもまとめて削除される
	@Test
	public void test_remove_orphan_01() {
		// database
		super.deleteAndInsert(S_URIKAKE_SEIKYU_NOT_YET, S_URIKAKE_SEIKYU_NOT_YET_COLUMN, V1);
		super.deleteAndInsert(T_URIKAKE, T_URIKAKE_COLUMN, V2);
		// input
		// 売上
		UriageBuilder uab = new UriageBuilder();
		uab.withRecordId("r-001");
		Uriage uriage = uab.build();
		// 売掛
		UrikakeBuilder ub = new UrikakeBuilder();
		ub.withVersion(0);
		ub.withUriage(uriage);
		Urikake urikake = ub.build();
		// do
		assertTrue(urikakeSeikyurepo.existsById("r-001"));
		service.delete(urikake);
		assertFalse(urikakeSeikyurepo.existsById("r-001"));
	}
}
