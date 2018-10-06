package com.showka.service.crud.u06;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.CrudServiceTestCase;
import com.showka.domain.builder.UriageBuilder;
import com.showka.domain.builder.UrikakeBuilder;
import com.showka.domain.u05.Uriage;
import com.showka.domain.u06.Urikake;
import com.showka.entity.TUrikake;
import com.showka.repository.i.TUrikakeRepository;
import com.showka.service.crud.u06.UrikakeCrudServiceImpl;
import com.showka.value.AmountOfMoney;
import com.showka.value.EigyoDate;

public class UrikakeCrudServiceImplTest extends CrudServiceTestCase {

	@Autowired
	private UrikakeCrudServiceImpl service;

	@Autowired
	private TUrikakeRepository repo;

	private Object[] T_URIKAKE_01 = { "r-KK01-00001", 1000, d("20170101"), "r-KK01-00001" };

	@Test
	@Transactional
	public void test01_updateNyukinYoteiDate() throws Exception {
		// database
		super.deleteAndInsert(T_URIKAKE, T_URIKAKE_COLUMN, T_URIKAKE_01);
		// input
		// 売上
		Uriage uriage = new UriageBuilder().withRecordId("r-KK01-00001").build();
		// 売掛
		UrikakeBuilder b = new UrikakeBuilder();
		b.withKingaku(new AmountOfMoney(108));
		b.withNyukinYoteiDate(new EigyoDate(2017, 1, 1));
		b.withUriage(uriage);
		b.withRecordId("r-KK01-00001");
		b.withVersion(0);
		Urikake urikake = b.build();
		// 更新後入金予定日
		EigyoDate updatedNyukinYoteiDate = new EigyoDate(2017, 1, 2);
		// do
		service.updateNyukinYoteiDate(urikake, updatedNyukinYoteiDate);
		// check
		TUrikake actual = repo.getOne("r-KK01-00001");
		assertEquals(updatedNyukinYoteiDate, new EigyoDate(actual.getNyukinYoteiDate()));
		System.out.println(actual);
	}
}
