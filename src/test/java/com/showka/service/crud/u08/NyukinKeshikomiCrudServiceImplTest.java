package com.showka.service.crud.u08;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.Keshikomi;
import com.showka.domain.Nyukin;
import com.showka.domain.NyukinKeshikomi;
import com.showka.domain.Urikake;
import com.showka.domain.builder.NyukinKeshikomiBuilder;
import com.showka.service.crud.u08.i.KeshikomiCrudService;
import com.showka.service.crud.u08.i.NyukinCrudService;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;

public class NyukinKeshikomiCrudServiceImplTest extends SimpleTestCase {

	@Tested
	private NyukinKeshikomiCrudServiceImpl service;

	@Injectable
	private NyukinCrudService nyukinCrudService;

	@Injectable
	private KeshikomiCrudService keshikomiCrudService;

	@Test
	public void test01_save(@Mocked Nyukin nyukin, @Mocked Keshikomi keshikomi, @Mocked Urikake urikake)
			throws Exception {
		// input
		// 消込MAP
		Map<Keshikomi, Urikake> keshikomiMap = new HashMap<Keshikomi, Urikake>();
		keshikomiMap.put(keshikomi, urikake);
		// 入金消込
		NyukinKeshikomiBuilder b = new NyukinKeshikomiBuilder();
		b.withNyukin(nyukin);
		b.withKeshikomiMap(keshikomiMap);
		NyukinKeshikomi nyukinKeshikomi = b.build();
		// expect
		new Expectations() {
			{
				nyukinCrudService.save(nyukin);
				keshikomiCrudService.save(nyukin, urikake, keshikomi);
			}
		};
		// do
		service.save(nyukinKeshikomi);
		// verify
		new Verifications() {
			{
				nyukinCrudService.save(nyukin);
				times = 1;
				keshikomiCrudService.save(nyukin, urikake, keshikomi);
				times = 1;
			}
		};
	}
}
