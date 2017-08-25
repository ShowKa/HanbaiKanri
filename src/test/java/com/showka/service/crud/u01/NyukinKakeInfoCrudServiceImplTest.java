package com.showka.service.crud.u01;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.showka.common.ServiceCrudTestCase;
import com.showka.domain.NyukinKakeInfoDomain;
import com.showka.domain.builder.NyukinKakeInfoDomainBuilder;
import com.showka.kubun.NyukinHohoKubun;
import com.showka.kubun.NyukinTsukiKubun;

public class NyukinKakeInfoCrudServiceImplTest extends ServiceCrudTestCase {

	@Autowired
	private NyukinKakeInfoCrudServiceImpl service;

	@Test
	public void test_save() {

		// set up builder
		NyukinKakeInfoDomainBuilder builder = new NyukinKakeInfoDomainBuilder();
		builder.withKokyakuId("KK03");
		builder.withNyukinDate(11);
		builder.withNyukinHohoKubun(NyukinHohoKubun.振込);
		builder.withNyukinTsukiKubun(NyukinTsukiKubun.当月);
		builder.withShimeDate(12);
		builder.withVersion(1);

		// build domain
		NyukinKakeInfoDomain domain = builder.build();

		// save
		service.save(domain);

	}
}
