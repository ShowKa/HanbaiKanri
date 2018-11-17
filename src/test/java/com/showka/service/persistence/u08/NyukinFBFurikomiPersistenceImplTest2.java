package com.showka.service.persistence.u08;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.PersistenceTestCase;
import com.showka.entity.JNyukinFBFurikomi;
import com.showka.service.persistence.u08.NyukinFBFurikomiPersistenceImpl;

public class NyukinFBFurikomiPersistenceImplTest2 extends PersistenceTestCase {

	@Autowired
	private NyukinFBFurikomiPersistenceImpl service;

	/** 入金FB振込データ. */
	private Object[] DATA01 = { "r-001", "r-20170820-001", "r-001^20170820-001" };

	/**
	 * FB振込IDで入金FB関係TableのRecordを取得できる.
	 */
	@Test
	public void test_findByFbFurikomiId_01() throws Exception {
		// database
		super.deleteAndInsert(J_NYUKIN_FB_FURIKOMI, J_NYUKIN_FB_FURIKOMI_COLUMN, DATA01);
		// input
		String fbFurikomiId = "r-20170820-001";
		// do
		JNyukinFBFurikomi actual = service.findByFbFurikomiId(fbFurikomiId);
		// check
		assertEquals("r-001", actual.getNyukinId());
	}
}
