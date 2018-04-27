package com.showka.service.crud.u08;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.Keshikomi;
import com.showka.domain.Nyukin;
import com.showka.domain.NyukinKeshikomi;
import com.showka.domain.builder.KeshikomiBuilder;
import com.showka.domain.builder.NyukinBuilder;
import com.showka.domain.builder.NyukinKeshikomiBuilder;
import com.showka.service.crud.u08.i.KeshikomiCrudService;
import com.showka.service.crud.u08.i.NyukinCrudService;

import mockit.Expectations;
import mockit.Injectable;
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
	public void test01_save() throws Exception {
		// input
		// 入金
		NyukinBuilder nb = new NyukinBuilder();
		Nyukin nyukin = nb.build();
		// 消込
		KeshikomiBuilder kb = new KeshikomiBuilder();
		kb.withNyukin(nyukin);
		Keshikomi keshikomi = kb.build();
		// 消込MAP
		List<Keshikomi> keshikomiList = new ArrayList<Keshikomi>();
		keshikomiList.add(keshikomi);
		// 入金消込
		NyukinKeshikomiBuilder b = new NyukinKeshikomiBuilder();
		b.withNyukin(nyukin);
		b.withKeshikomiList(keshikomiList);
		NyukinKeshikomi nyukinKeshikomi = b.build();
		// expect
		new Expectations() {
			{
				nyukinCrudService.save(nyukin);
				keshikomiCrudService.save(keshikomi);
			}
		};
		// do
		service.save(nyukinKeshikomi);
		// verify
		new Verifications() {
			{
				nyukinCrudService.save(nyukin);
				times = 1;
				keshikomiCrudService.save(keshikomi);
				times = 1;
			}
		};
	}
}
