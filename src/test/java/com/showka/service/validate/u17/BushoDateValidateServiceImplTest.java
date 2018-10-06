package com.showka.service.validate.u17;

import org.junit.Test;

import com.showka.common.SimpleTestCase;
import com.showka.domain.builder.BushoBuilder;
import com.showka.domain.z00.Busho;
import com.showka.entity.MBushoDate;
import com.showka.repository.i.MBushoDateRepository;
import com.showka.system.exception.CanNotUpdateException;
import com.showka.value.EigyoDate;
import com.showka.value.TheDate;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class BushoDateValidateServiceImplTest extends SimpleTestCase {

	@Tested
	private BushoDateValidateServiceImpl bushoDateValidateServiceImpl;

	@Injectable
	private MBushoDateRepository repo;

	/**
	 * 正常
	 * 
	 * @throws Exception
	 */
	@Test
	public void test01_validateForClosing() throws Exception {
		// data
		EigyoDate today = new EigyoDate(2017, 1, 1);
		BushoBuilder b = new BushoBuilder();
		b.withRecordId("r-BS01");
		Busho busho = b.build();
		// expect
		MBushoDate dateExpected = new MBushoDate();
		dateExpected.setEigyoDate(new TheDate(2017, 1, 1).toDate());
		new Expectations() {
			{
				repo.getOne("r-BS01");
				result = dateExpected;
			}
		};
		// do
		bushoDateValidateServiceImpl.validateForClosing(busho, today);
		// verify
		new Verifications() {
			{
				repo.getOne("r-BS01");
				times = 1;
			}
		};
		// assert
		assertTrue(true);
	}

	/**
	 * 営業日がすでに更新されている場合はエラー
	 * 
	 * @throws Exception
	 */
	@Test(expected = CanNotUpdateException.class)
	public void test02_validateForClosing() throws Exception {
		// data
		EigyoDate today = new EigyoDate(2017, 1, 1);
		BushoBuilder b = new BushoBuilder();
		b.withRecordId("r-BS01");
		Busho busho = b.build();
		// expect
		MBushoDate dateExpected = new MBushoDate();
		dateExpected.setEigyoDate(new TheDate(2017, 1, 2).toDate());
		new Expectations() {
			{
				repo.getOne("r-BS01");
				result = dateExpected;
			}
		};
		// do
		bushoDateValidateServiceImpl.validateForClosing(busho, today);
		fail();
	}
}
