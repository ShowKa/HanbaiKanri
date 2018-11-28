package com.showka.service.query.u08;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.NyukinBuilder;
import com.showka.domain.u08.Nyukin;
import com.showka.entity.JNyukinFBFurikomi;
import com.showka.repository.i.JNyukinFBFurikomiRepository;
import com.showka.service.crud.u08.i.NyukinCrud;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class NyukinFBFurikomiQueryImplTest2 extends SimpleTestCase {

	@Tested
	@Injectable
	private NyukinFBFurikomiQueryImpl service;

	@Injectable
	private NyukinCrud nyukinCrud;

	@Injectable
	private JNyukinFBFurikomiRepository repo;

	/**
	 * 入金関係テーブルのレコードの入金IDを取得する。その入金IDを用いて入金を取得する。
	 */
	@Test
	public void test_GetNyukin_01() throws Exception {
		// input
		String fbFurikomiId = "r-20170820-001";
		// entity
		JNyukinFBFurikomi entity = new JNyukinFBFurikomi();
		String nyukinId = "r-001";
		entity.setNyukinId(nyukinId);
		// 入金
		NyukinBuilder nb = new NyukinBuilder();
		nb.withRecordId(nyukinId);
		Nyukin nyukin = nb.build();
		// expect
		new Expectations() {
			{
				service.findByFbFurikomiId(fbFurikomiId);
				result = entity;
				nyukinCrud.getDomain(nyukinId);
				result = nyukin;
			}
		};
		// do
		Nyukin actual = service.getNyukin(fbFurikomiId);
		// verify
		new Verifications() {
			{
				service.findByFbFurikomiId(fbFurikomiId);
				times = 1;
				nyukinCrud.getDomain(nyukinId);
				times = 1;
			}
		};
		// check
		assertEquals(nyukin, actual);
	}
}
